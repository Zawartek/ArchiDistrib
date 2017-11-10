package org.isep.cd;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;


/**
 * This class delegates to a DocumentServer instance and replicates values top the other guys
 * Fault detection is done by pinging each others, and leader election is carried out by sorting the server names thx
 * to a local sorted map.
 */
public class FTDocumentServer extends UnicastRemoteObject implements FTDocument {

    public static int THREAD_POOL_SIZE = 2;
    public static int PING_FREQ = 1000;

    private boolean stop =false;

    private final Object coordinatorLock = new Object();
    private String coordinator, serverName;

    private boolean isLeader = false;
    private final ConcurrentSkipListMap<String, FTDocument> replicas = new ConcurrentSkipListMap<>();
    private final ExecutorService pool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    private final Document delegate = new DocumentServer();

    /**
     * Registration of neighbours from the leader
     * Ensures fault detection and change of leadership
     */
    private final Thread pingThread = new Thread(() -> {

        while(!stop) {
            try {
                Thread.sleep(PING_FREQ);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String cName = null;
            synchronized (coordinatorLock) {
                cName = new String(coordinator);
            }

            FTDocument master = replicas.get(cName);
            if(master!=null && !isLeader) {
                List<String> repList = null;
                try {
                    System.out.println(serverName);
                    master.registerReplica(serverName,this);
                    repList = master.getNeighbors();
                } catch (RemoteException e) {
                    System.out.println("Leader Ping failed = " + e.getMessage());
                    changeLeader(cName);
                }
                updateReplicas(repList);
            }


        }
    });

    private  void connectToReplica(String address) {
        String [] parseAddr = address.split(":");
        String host = parseAddr[0];
        int port = Integer.parseInt(parseAddr[1]);
        try {
            Registry remoteTmp = LocateRegistry.getRegistry(host,port);
            FTDocument nvReplica = (FTDocument) remoteTmp.lookup(FTDocument.LOOKUP_NAME);
            replicas.put(address, nvReplica);
            nvReplica.registerReplica(serverName,this);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }

    }
    
    private void updateReplicas(List<String> repList) {
        if(repList !=null) {
            for (String nb : repList) {
                if(!replicas.containsKey(nb)) {
                    connectToReplica(nb);
                }

            }
        }
    }

    private void changeLeader(String master) {
        replicas.remove(master);
        String newLeader = replicas.firstKey();
        System.out.println(replicas);
        synchronized (coordinatorLock) {
            coordinator = newLeader;
            if(coordinator.equals(serverName))
                isLeader = true;
        }

        System.out.println("New leader is " + newLeader);
    }

    public FTDocumentServer(String serverName, String coordinator, FTDocument masterServer) throws RemoteException {
        super();
        this.coordinator = coordinator;
        this.serverName = serverName;
        if(coordinator.equals(serverName)) {
            isLeader = true;
        }

        replicas.put(serverName,this);
        if(masterServer!=null) {
            replicas.put(coordinator,masterServer);
        }
    }

    /**
     * Return this node's leader name
     * @return
     * @throws RemoteException
     */
    @Override
    public String getLeader() throws RemoteException {
        String def = null;
        synchronized (coordinatorLock) {
            def = new String(coordinator);
        }
        return def;
    }
    
	@Override
	public String getName() throws RemoteException {
		return delegate.getName();
	}
	
	@Override
	public HashMap<Integer, Section> getSections() throws RemoteException {
		return delegate.getSections();
	}
	
	@Override
	public HashMap<Integer, Boolean> getLocks() throws RemoteException {
		return delegate.getLocks();
	}
	
	@Override
	public void setLocks(HashMap<Integer, Boolean> locks) throws RemoteException {
		delegate.setLocks(locks);
	}

	@Override
	public Section getSection(Integer id) throws RemoteException {
		return delegate.getSection(id);
	}
	
	@Override
	public void setSection(int id, Section section) throws RemoteException {
		delegate.setSection(id, section);
		replicateSection(section);
	}

    private void replicateSection(Section section) {
        List<Future> ftList = new ArrayList<>();
        for(Map.Entry<String, FTDocument> replica : replicas.entrySet()) {

            if(!serverName.equals(replica.getKey())) {
                Future f = pool.submit(() -> {
                    try {
                        replica.getValue().setSection(section.getNumber(), section);
                    } catch (RemoteException e) {
                        System.err.println("Problem replicating with " + replica.getKey());
                        System.err.println("Removing from replica set");
                        replicas.remove(replica.getKey());
                    }
                });

                ftList.add(f);
            }
        }

        // Wait for each task to end.
        for(Future f: ftList)
            try {
                f.get(30, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
    }

    /**
     * Lists the names of known replicas if it is leader.
     * @return
     * @throws RemoteException
     */
    @Override
    public List<String> getNeighbors() throws RemoteException {
        List<String> replicaCopy = new ArrayList<>();
        replicaCopy.addAll(replicas.keySet());
        return replicaCopy;
    }

    @Override
    public void registerReplica(String server, FTDocument replica) throws RemoteException {
        replicas.put(server,replica);
        System.out.println("Registered " + server);
        // If it is leader, registers back to the replica
        /**if(isLeader) {
            replica.registerReplica(serverName,this);
        }*/

    }

    public void startPing() {
        this.pingThread.start();
    }

	
    @Override
	public boolean startEdition(Integer id, Section sec) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean commitSection(long id, Section sec) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean releaseLock(long id, Section sec) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Document getDocument(Document doc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FTDocument> getReplicas() {
		// TODO Auto-generated method stub
		return null;
	}
}
