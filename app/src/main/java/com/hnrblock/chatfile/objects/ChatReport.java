package com.hnrblock.chatfile.objects;

import java.util.ArrayList;

public class ChatReport extends ChatMessageBase {
	private ArrayList<ReportRow> rows = new ArrayList<ReportRow>();

	public ChatReport(ArrayList<ReportRow> rows) {
		setRows(rows);
	}

	public ArrayList<ReportRow> getRows() {
		return rows;
	}

	public void setRows(ArrayList<ReportRow> rows) {
		this.rows = rows;
	}
}