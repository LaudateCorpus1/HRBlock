package com.hnrblock.chatfile.objects;

import android.text.InputFilter;
import android.text.Spanned;

public class NoSpecialCharsInputFilter implements InputFilter {
	@Override
	public CharSequence filter(CharSequence source, int start, int end,
			Spanned dest, int dstart, int dend) {
		// if (source.equals("")) { // for backspace
		// return source;
		// }
		// if (source.toString().matches("[a-zA-Z0-9 ]+")) {
		// return source;
		// }
		// return source.subSequence(start, end - 1);
		StringBuilder builder = new StringBuilder();
		for (int i = start; i < end; i++) {
			char c = source.charAt(i);
			if (Character.isLetterOrDigit(c)) {
				builder.append(c);
			}
		}
		return builder.toString();
	}
}