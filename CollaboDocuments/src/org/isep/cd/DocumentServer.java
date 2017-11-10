package org.isep.cd;

import java.rmi.RemoteException;

import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class DocumentServer extends UnicastRemoteObject implements Document {

    private String name;
	private HashMap<Integer, Section> sections;
	private HashMap<Integer, Boolean> locks;

    protected DocumentServer() throws RemoteException {
        super();
    }

	@Override
	public String getName() {
		return name;
	}

	@Override
	public HashMap<Integer, Section> getSections() {
		return sections;
	}

	@Override
	public HashMap<Integer, Boolean> getLocks() {
		return locks;
	}

	@Override
	public void setLocks(HashMap<Integer, Boolean> locks) {
		this.locks = locks;
	}

	@Override
	public Section getSection(Integer id) {
		if (locks.containsKey(id) && locks.get(id)) {
			return sections.get(id);
		}
		else {
			return null;
		}
	}

	@Override
	public void setSection(int id, Section section) throws RemoteException {
		sections.put(id, section);
	}


}
