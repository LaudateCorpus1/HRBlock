package com.hnrblock.chatfile.objects;

import java.util.ArrayList;

public class WholeQuestion {
	private Question question;
	private ArrayList<Restriction> restrictions;

	public WholeQuestion(Question question, ArrayList<Restriction> restrictions) {
		this.question = question;
		this.restrictions = restrictions;
	}

	public Question getQuestion() {
		return question;
	}

	public ArrayList<Restriction> getRestriction() {
		return restrictions;
	}
}