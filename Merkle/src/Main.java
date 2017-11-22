package src;

import java.security.NoSuchAlgorithmException;

public class Main {

	public static void main(String[] args) {
		byte b[] = {0x10};
		try {
			MerkleTree mt, mt2,mt3;
			mt = new MerkleTree(b);
			System.out.println("MT value : " + mt.getValue());
			System.out.println("MT encoded value : " + mt.getEncodedValue());
			mt2 = new MerkleTree(mt,null);
			System.out.println("MT2 value : " + mt2.getValue());
			System.out.println("MT2 encoded value : " + mt2.getEncodedValue());
			mt3 = new MerkleTree(null,mt);
			System.out.println("MT3 value : " + mt3.getValue());
			System.out.println("MT3 encoded value : " + mt3.getEncodedValue());
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
	}

}
