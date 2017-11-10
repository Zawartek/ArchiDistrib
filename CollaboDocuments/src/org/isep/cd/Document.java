package org.isep.cd;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

public interface Document extends Remote {

	/**
	 * Return the document name
	 * @return
	 * @throws RemoteException
	 */
	String getName() throws RemoteException;

	/**
	 * Return all sections of the document
	 * @return
	 * @throws RemoteException
	 */
	HashMap<Integer, Section> getSections() throws RemoteException;

	/**
	 * Return all locks of sections of the document
	 * @return
	 * @throws RemoteException
	 */
	HashMap<Integer, Boolean> getLocks() throws RemoteException;

	/**
	 * Modify all locks of sections of the document
	 * @param locks
	 * @throws RemoteException
	 */
	void setLocks(HashMap<Integer, Boolean> locks) throws RemoteException;
	
	/**
	 * Return the section corresponding to the number given in parameter
	 * @param number
	 * @return
	 * @throws RemoteException
	 */
	Section getSection(Integer number) throws RemoteException;
	
	/**
	 * Replace the section of a document by a new section
	 * @param number : number of a section
	 * @param section : new section
	 * @throws RemoteException
	 */
	void setSection(int number, Section section) throws RemoteException;
}
