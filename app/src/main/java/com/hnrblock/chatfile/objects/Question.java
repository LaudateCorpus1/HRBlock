package com.hnrblock.chatfile.objects;

import java.util.ArrayList;

public class Question {
	private String id, seq, title, shortTitle, answer, regex, help_1, help_2,
			timestamp, answerTimestamp;
	private int status, input_type, min_length, max_length, q_type, is_valid;
	private ArrayList<String> options;

	public Question(String id, String seq, String title, String shortTitle,
			ArrayList<String> options, String answer, int q_type,
			int input_type, int min_length, int max_length, String regex,
			String help_1, String help_2, int is_valid, int status,
			String timestamp, String answerTimestamp) {
		this.id = id;
		this.setSeq(seq);
		this.title = title;
		this.setShortTitle(shortTitle);
		this.setOptions(options);
		this.setAnswer(answer);
		this.setQType(q_type);
		this.setInputType(input_type);
		this.setMinLength(min_length);
		this.setMaxLength(max_length);
		this.setRegex(regex);
		this.setHelp1(help_1);
		this.setHelp2(help_2);
		this.setIsValid(is_valid);
		this.setStatus(status);
		this.setTimestamp(timestamp);
		this.setAnswerTimestamp(answerTimestamp);
	}

	/*
	 * public Question(String q_id, String q_title, String q_desc, String
	 * q_answer, int q_status) { this.id = q_id; this.title = q_title; this.desc
	 * = q_desc; this.answer = q_answer; this.status = q_status; }
	 */

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setAnswer(String q_answer) {
		this.answer = q_answer;
	}

	public String getAnswer() {
		return answer;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public String getHelp1() {
		return help_1;
	}

	public void setHelp1(String help_1) {
		this.help_1 = help_1;
	}

	public String getHelp2() {
		return help_2;
	}

	public void setHelp2(String help_2) {
		this.help_2 = help_2;
	}

	public int getInputType() {
		return input_type;
	}

	public void setInputType(int input_type) {
		this.input_type = input_type;
	}

	public int getMinLength() {
		return min_length;
	}

	public void setMinLength(int min_length) {
		this.min_length = min_length;
	}

	public int getMaxLength() {
		return max_length;
	}

	public void setMaxLength(int max_length) {
		this.max_length = max_length;
	}

	public int getQType() {
		return q_type;
	}

	public void setQType(int q_type) {
		this.q_type = q_type;
	}

	public int getIsValid() {
		return is_valid;
	}

	public void setIsValid(int is_valid) {
		this.is_valid = is_valid;
	}

	public ArrayList<String> getOptions() {
		return options;
	}

	public void setOptions(ArrayList<String> options) {
		this.options = options;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getAnswerTimestamp() {
		return answerTimestamp;
	}

	public void setAnswerTimestamp(String answerTimestamp) {
		this.answerTimestamp = answerTimestamp;
	}

	public String getShortTitle() {
		return shortTitle;
	}

	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}
}