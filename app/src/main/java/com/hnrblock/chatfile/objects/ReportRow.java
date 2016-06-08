package com.hnrblock.chatfile.objects;

public class ReportRow {
	private String title, value;

	public ReportRow(String title, String value) {
		setTitle(title);
		setValue(value);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}