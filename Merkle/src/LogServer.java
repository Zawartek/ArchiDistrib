package src;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class LogServer {

	MerkleTree mt;

	public LogServer(File file) throws NoSuchAlgorithmException {
		mt = new MerkleTree(file);
	}

	public String getRootHash() {
		return "Root hash : " + mt.getEncodedValue();
	}

	public HashMap<String, String> genPath(int idEvent) {
		MerkleTree currentTree = mt;
		HashMap<String, String> path = new HashMap<>();

		if ((currentTree.beginIndex() > idEvent) || (currentTree.endIndex() < idEvent)) {
			System.out.println("Error event " + idEvent + " does'nt exist.");
			return null;
		}

		while ((currentTree.beginIndex() != idEvent) || (currentTree.endIndex() != idEvent)) {
			if ((currentTree.left().beginIndex() <= idEvent) && (currentTree.left().endIndex() >= idEvent)) {
				path.put(currentTree.right().getIndexes(), currentTree.right().getEncodedValue());
				currentTree = currentTree.left();
			} else {
				path.put(currentTree.left().getIndexes(), currentTree.left().getEncodedValue());
				currentTree = currentTree.right();
			}
		}
		return path;
	}

	public HashMap<String, String> genProof(int idEvent) {
		MerkleTree currentTree = mt;
		HashMap<String, String> proof = new HashMap<>();

		while ((currentTree.beginIndex() != idEvent) && (currentTree.endIndex() != idEvent)) {
			if ((currentTree.left().beginIndex() <= idEvent) && (currentTree.left().endIndex() >= idEvent)) {
				if (currentTree.left().endIndex() - currentTree.left().beginIndex() >= 4) {
					proof.put(currentTree.right().getIndexes(), currentTree.right().getEncodedValue());
					currentTree = currentTree.left();
				} else {
					proof.put(currentTree.right().getIndexes(), currentTree.right().getEncodedValue());
					proof.put(currentTree.left().getIndexes(), currentTree.left().getEncodedValue());
					currentTree = currentTree.left();
				}
			} else {
				proof.put(currentTree.left().getIndexes(), currentTree.left().getEncodedValue());
				currentTree = currentTree.right();
			}
		}
		return proof;
	}
}
