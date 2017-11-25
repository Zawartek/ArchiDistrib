package src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class MerkleTree {

	private static final byte LEAF = 0x00;
	private static final byte NODE = 0x01;
	private MerkleTree left;
	private MerkleTree right;
	private byte[] value;
	private String encodedValue;
	private int start;
	private int end;
	private int level;

	public MerkleTree(String val) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.update(LEAF);
		digest.update(val.getBytes());
		value = digest.digest();
		level = 0;
		encodedValue = Base64.getEncoder().encodeToString(value);
	}

	public MerkleTree(MerkleTree left, MerkleTree right) throws NoSuchAlgorithmException {
		addNodes(left, right);
	}

	public void addNodes(MerkleTree left, MerkleTree right) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		this.left = left;
		this.right = right;
		digest.update(NODE);
		if (left != null) {
			digest.update(left.getValue());
			start = left.beginIndex();
			level = left.getLevel() + 1;
			if (right == null) {
				end = start;
			}
		}
		if (right != null) {
			digest.update(right.getValue());
			end = right.endIndex();
			level = right.getLevel() + 1;
			if (left == null) {
				start = end;
			}
		}
		value = digest.digest();
		encodedValue = Base64.getEncoder().encodeToString(value);
	}

	public MerkleTree(File file) throws NoSuchAlgorithmException {
		BufferedReader br;
		List<MerkleTree> merkleTrees = new ArrayList<MerkleTree>();
		List<MerkleTree> newMerkleTrees;
		MerkleTree tree;
		int cpt = 0;
		String readLine = "";

		try {
			br = new BufferedReader(new FileReader(file));
			while ((readLine = br.readLine()) != null) {
				tree = new MerkleTree(readLine);
				tree.setBegin(cpt);
				tree.setEnd(cpt);
				merkleTrees.add(tree);
				cpt++;
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		do {
			newMerkleTrees = new ArrayList<MerkleTree>();
			for (int i = 0; i < merkleTrees.size(); i += 2) {
				if (merkleTrees.size() - 1 == i) {
					newMerkleTrees.add(new MerkleTree(merkleTrees.get(i), null));
				} else {
					newMerkleTrees.add(new MerkleTree(merkleTrees.get(i), merkleTrees.get(i + 1)));
				}
			}
			merkleTrees = newMerkleTrees;
		} while (merkleTrees != null && merkleTrees.size() > 2);
		addNodes(merkleTrees.get(0), merkleTrees.get(1));
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

	public void setBegin(int begin) {
		start = begin;
	}

	public int endIndex() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getLevel() {
		return level;
	}

	public String getEncodedValue() {
		return encodedValue;
	}

	public void setEncodedValue(String encodedValue) {
		this.encodedValue = encodedValue;
	}

	public String getIndexes() {
		return "[" + beginIndex() + "-" + endIndex() + "]";
	}

	public String toString() {
		String s = "";
		String line = getIndexes() + " " + getEncodedValue() + "\n";
		int length = getLevel() + line.length();
		s += String.format("%1$" + length + "s", line);

		if (left != null) {
			s += left.toString();
		}
		if (right != null) {
			s += right.toString();
		}
		return s;
	}
}
