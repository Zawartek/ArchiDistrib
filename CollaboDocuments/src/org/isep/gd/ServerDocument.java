package org.isep.gd;

public interface ServerDocument{

	/**
	 * Lock a section of a document to allow an user to edit it
	 * @param id : user id
	 * @param sec : the section of the file
	 * @return boolean : succeed or not (depends if the section is update and not locked)
	 */
    boolean setSectionLock(long id, Section sec);

    /**
     * Update a section of a file with her latest version
     * @param id : user id
     * @param sec : the section of the file
     * @return boolean : succeed or not (depends if the section is update and not locked)
     */
    boolean commitSection(long id, Section sec);

    /**
     * Release the lock on a section
     * @param id : user id
     * @param sec : the section of the file
     * @return boolean : succeed or not (depends if the section is not locked)
     */
    boolean releaseSection(long id, Section sec);

    /**
     * Register a new replica (server) for this document
     * @return document : the new replica (server)
     */
    ServerDocument registerReplica();

    /**
     * The MASTER send the latest version of a section to all replicas of this document
     * The MASTER or REPLICAS replicate a section of this document to all clients
     * @param sec : the section of the file
     */
    void replicateSection(Section sec);

    /**
     * The MASTER send the locks to all replicas of this document
     */
    void replicateLocks();
}
