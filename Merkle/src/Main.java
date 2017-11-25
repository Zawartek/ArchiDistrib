package src;

import java.io.File;
import java.security.NoSuchAlgorithmException;

public class Main {

	public static void main(String[] args) {
		try {
			MerkleTree mt;
			File file = new File("DS1-trace.txt");
			mt = new MerkleTree(file);
			System.out.println("Final Merkle Tree\n"+mt+"\n");
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
	}

}
