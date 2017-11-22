package src;

import java.security.*;
import java.util.Base64;

public class MerkleTree {

	private static final byte LEAF = 0x00;
	private static final byte NODE = 0x00;
	private MerkleTree left;
	private MerkleTree right;
	private byte[] value;
	private String encodedValue;
	private int start;
	private int end;

	public MerkleTree(byte[] v) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.update(LEAF);
		digest.update(v);
		value = digest.digest();
		encodedValue = Base64.getEncoder().encodeToString(value);
	}
	
	public MerkleTree(MerkleTree left, MerkleTree right) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		this.left = left;
		this.right = right;
		digest.update(NODE);
		if (left !=null) {
			digest.update(left.getValue());
			start = left.beginIndex();
		}
		if (right !=null) {
			digest.update(right.getValue());
			end = right.endIndex();
		}
		value = digest.digest();
		encodedValue = Base64.getEncoder().encodeToString(value);
	}
	
	public MerkleTree left() {
		return left;
	}
	
	public MerkleTree right() {
		return right;
	}
	
	public byte[] getValue() {
		return value;
	}
	
	public int beginIndex() {
		return start;
	}
	
	public int endIndex() {
		return end;
	}

	public String getEncodedValue() {
		return encodedValue;
	}

	public void setEncodedValue(String encodedValue) {
		this.encodedValue = encodedValue;
	}
}
