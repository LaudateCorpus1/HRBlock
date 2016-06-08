package com.hnrblock.chatfile.objects;

public class Restriction {
	private String q_id, r_type, r_data;

	public Restriction(String q_id, String r_type, String r_data) {
		this.q_id = q_id;
		this.r_type = r_type;
		this.r_data = r_data;
	}

	public String getId() {
		return q_id;
	}

	public String getType() {
		return r_type;
	}

	public String getData() {
		return r_data;
	}
}