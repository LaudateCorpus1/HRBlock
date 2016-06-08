package com.hnrblock.chatfile.objects;

import java.util.ArrayList;

public class ChatMessage extends ChatMessageBase {
	public boolean left, isReport = false, canSkip = false;
	public String message, id, timestamp, help;
	private ArrayList<ReportRow> rows;

	public ChatMessage(boolean left, String message, String id,
			String timsestamp, String help, boolean canSkip) {
		super();
		this.id = id;
		this.help = help;
		this.left = left;
		this.message = message;
		this.timestamp = timsestamp;
		this.canSkip = canSkip;
	}

	public ChatMessage(int id, ArrayList<ReportRow> rows) {
		super();
		// rows = new ArrayList<ReportRow>();
		this.id = id + "";
		timestamp = "1";
		left = true;
		isReport = true;
		setRows(rows);
	}

	public ArrayList<ReportRow> getRows() {
		return rows;
	}

	public void setRows(ArrayList<ReportRow> rows) {
		this.rows = rows;
	}
}