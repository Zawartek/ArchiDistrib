package org.isep.gd;

import java.util.HashMap;
import java.util.List;

public class Document {
	private String name;
	private HashMap<Integer, Section> sections;
	private HashMap<Integer, bool> locks;

	public Document(String name, HashMap<Integer, Section> sections, HashMap<Integer, bool> locks) {
		super();
		this.name = name;
		this.sections = sections;
		this.locks = locks;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HashMap<Integer, Section> getSections() {
		return sections;
	}

	public HashMap<Integer, bool> getLocks() {
		return locks;
	}

	public void setLocks(HashMap<Integer, bool> locks) {
		this.locks = locks;
	}
}
