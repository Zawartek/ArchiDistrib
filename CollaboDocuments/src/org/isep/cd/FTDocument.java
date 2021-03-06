package org.isep.cd;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface FTDocument extends Remote,Document {

    String LOOKUP_NAME = "FTDocument";

    /**
     * Returns the name of the current leader for this instance
     * @return String containing address:port of the leader's registry
     * @throws RemoteException
     */
    String getLeader() throws RemoteException;

    /**
     * List of address:port of neighbors of this node
     * @return
     * @throws RemoteException
     */
    List<String> getNeighbors() throws RemoteException;

    /**
     * Start the edition of a section
     * @param number : number of the section
     * @param sec : the section
     * @return boolean : succeed or not (depends if the section is locked and if the last version is available)
     */
    boolean startEdition(int number, Section sec);
    
    /**
     * Update a section of a file with her latest version
     * @param number : number of the section
     * @param sec : the modified section
     * @return boolean : succeed or not (depends if the section is update and not locked)
     */
    boolean commitSection(int number, Section sec);
    
    /**
     * Release the lock of a section
     * @param number : number of the section
     * @param sec : the section
     * @return boolean : succeed or not (depends if the section is not locked)
     */
    boolean releaseLock(int number, Section sec);

    /**
     * Get the last version of the document
     * @return document : the latest version
     */
    Document getDocument(Document doc);

    /**
     * Get the list of replicas of this document
     * @return list : list of server
     */
    List<FTDocument> getReplicas();
    
    /**
     * Registers a replica to a leader.
     * @param server address:port of the caller
     * @param replica callback
     * @throws RemoteException
     */
    void registerReplica(String server, FTDocument replica) throws RemoteException;
}
