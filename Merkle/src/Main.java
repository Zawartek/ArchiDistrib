package src;

import java.io.File;
import java.security.NoSuchAlgorithmException;

public class Main {

	public static void main(String[] args) {
		try {
			MerkleTree mt;
			File file = new File("Modif-DS1-trace.txt");
			//mt = new MerkleTree(file);
			//System.out.println("Final Merkle Tree\n" + mt + "\n");
			
			LogServer ls = new LogServer(file);
			//System.out.println(ls.getRootHash());
			System.out.println(ls.genPath(3));

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

	}

}
