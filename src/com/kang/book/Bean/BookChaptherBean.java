package com.kang.book.Bean;

public class BookChaptherBean {
	String chapther;
	String path;

	public BookChaptherBean(String chapther, String path) {
		this.chapther = chapther;
		this.path = path;
	}

	public String getChapther() {
		return chapther;
	}

	public void setChapther(String chapther) {
		this.chapther = chapther;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}