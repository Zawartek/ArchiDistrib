package org.isep.cd;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

public interface Document extends Remote {

	String getName() throws RemoteException;

	HashMap<Integer, Section> getSections() throws RemoteException;

	HashMap<Integer, Boolean> getLocks() throws RemoteException;

	void setLocks(HashMap<Integer, Boolean> locks) throws RemoteException;
	
	Section getSection(Integer id) throws RemoteException;
	
	void setSection(int id, Section section) throws RemoteException;
}
