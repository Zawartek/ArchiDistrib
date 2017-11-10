package org.isep.gd;

import java.util.List;

public interface ClientDocument {

    /**
     * Start the edition of a section
     * @param sec : the section
     * @return boolean : succeed or not (depends if the section is locked and if the last version is available)
     */
    boolean startEdition(Integer id, Section sec);

    /**
     * Commit the new version of a section
     * @param sec : the section
     * @return boolean : succeed or not (depends if the user have the lock and the latest version)
     */
    boolean editSection(Integer id, Section sec);

    /**
     * End the edition of a section
     * @param sec : the section
     * @return boolean : succeed or not (depends if the user have the lock)
     */
    boolean endEdition(Integer id, Section sec);

    /**
     * Get the last version of the document
     * @return document : the latest version
     */
    Document getDocument();

    /**
     * Get the list of replicas of this document
     * @return list : list of server
     */
    List<String> getReplicas();
}
