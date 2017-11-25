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
		return mt.getEncodedValue();
	}

	public HashMap<String, String> genPath(int idEvent) {
		MerkleTree currentTree = mt;
		HashMap<String, String> path = new HashMap<>();

		while ((currentTree.beginIndex() != idEvent) || (currentTree.endIndex() != idEvent)) {
			if ((currentTree.left() != null) && (currentTree.left().beginIndex() <= idEvent) && (currentTree.left().endIndex() >= idEvent)) {
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
		HashMap<String, String> proof = new HashMap<>();

		return proof;
	}
}
