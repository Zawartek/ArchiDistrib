package src;

import java.security.NoSuchAlgorithmException;

public class Main {

	public static void main(String[] args) {
		byte b[] = {0x10};
		try {
			MerkleTree mt, mt2,mt3;
			mt = new MerkleTree(b);
			System.out.println(mt.getValue());
			mt2 = new MerkleTree(mt,null);
			System.out.println(mt2.getValue());
			mt3 = new MerkleTree(mt,null);
			System.out.println(mt3.getValue());
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
	}

}
