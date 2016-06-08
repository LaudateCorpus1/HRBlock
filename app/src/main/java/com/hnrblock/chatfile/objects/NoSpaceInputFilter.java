package com.hnrblock.chatfile.objects;

import android.text.InputFilter;
import android.text.Spanned;

public class NoSpaceInputFilter implements InputFilter {
	@Override
	public CharSequence filter(CharSequence source, int start, int end,
			Spanned dest, int dstart, int dend) {
		StringBuilder builder = new StringBuilder();
		for (int i = start; i < end; i++) {
			char c = source.charAt(i);
			if (c != ' ') {
				builder.append(c);
			}
		}
		return builder.toString();
	}
}