package org.isep.ft;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class ClientMain {


    static private Registry reg = null;
    static private FTBillboard currentServer = null;
    static private String serverID = null;
	static private List<String> serverReplicas = null;

    static public void main(String [] args) throws NotBoundException {

    	int cptErr=0;
    	String[] parse = null;

        if(args.length !=2) {
            System.out.println("USAGE: ServerMain master port");
            System.exit(0);
        }

        String address = args[0];
        int port = Integer.parseInt(args[1]);
        serverID = address+":"+port;

        connection(address, port);

        System.out.println("Starting Stupid client");

        for(int i = 0; i<1000 ; i++) {
            String message = "Hello guys " + i, received="";

            System.out.println("Test with message " + message);
            
            try {
                currentServer.setMessage(message);
                Thread.sleep(2500);
                received = currentServer.getMessage();
            } catch (RemoteException e) {
            	cptErr++;
            } catch (InterruptedException e) {
                e.printStackTrace();
                cptErr++;
            }

            if(received.equals(message)) {
                System.out.println("no problem");
            }
            else {
                System.out.println("Problem: " + received + " instead of " + message );
                System.out.println(serverReplicas);
                if (cptErr>=2) {
                	serverReplicas.remove(serverID);
                	if (serverReplicas.size()>0) {
                		serverID = serverReplicas.get(0);
                		parse = serverID.split(":");
                		connection(parse[0], Integer.parseInt(parse[1]));
                	}
                	else {
                		System.out.println("All server are down");
                		break;
                	}
                }
            }
        }
    }

    public static void connection(String address, int port) {
    	

        try {
            reg = LocateRegistry.getRegistry(address,port);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        try {
            currentServer = (FTBillboard) reg.lookup(FTBillboard.LOOKUP_NAME);
            serverReplicas = (currentServer.getNeighbors());
            System.out.println(serverReplicas);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
