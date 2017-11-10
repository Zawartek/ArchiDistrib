package org.isep.gd;

public class Section {
	private int number;
	private long version;
	private String title;
	private String content;

	public Section(int number, long version, String title, String content) {
		this.number = number;
		this.version = version;
		this.title = title;
		this.content = content;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getNumber() {
		return number;
	}
}
