package org.isep.cd;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

public interface Document extends Remote {

	/**
	 * Retourne le nom du document
	 * @return
	 * @throws RemoteException
	 */
	String getName() throws RemoteException;

	/**
	 * Retourne l'ensemble des sections du document
	 * @return
	 * @throws RemoteException
	 */
	HashMap<Integer, Section> getSections() throws RemoteException;

	/**
	 * Rtourne l'ensemble des verrous des sections du document
	 * @return
	 * @throws RemoteException
	 */
	HashMap<Integer, Boolean> getLocks() throws RemoteException;

	/**
	 * Modifie l'ensemble des verrous des sections du document
	 * @param locks
	 * @throws RemoteException
	 */
	void setLocks(HashMap<Integer, Boolean> locks) throws RemoteException;
	
	/**
	 * Retourne la section du document a partir de son ID
	 * @param id
	 * @return
	 * @throws RemoteException
	 */
	Section getSection(Integer id) throws RemoteException;
	
	/**
	 * Modifie la section du document correspondant à l'ID
	 * @param id
	 * @param section
	 * @throws RemoteException
	 */
	void setSection(int id, Section section) throws RemoteException;
}
