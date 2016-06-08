package com.hnrblock.chatfile;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.hnrblock.chatfile.helpers.MyTimeUtils;
import com.hnrblock.chatfile.objects.ChatMessage;

public class ButtonsGridViewAdapter extends ArrayAdapter<String> {
	// private TextView chatText;
	private List<String> options = new ArrayList<String>();
	private Context context;
	private OptionClickListener mecl;

	@Override
	public void add(String object) {
		options.add(object);
		super.add(object);
	}

	@Override
	public void insert(String object, int pos) {
		if (pos != -1) {
			options.remove(pos);
			options.add(pos, object);
			super.insert(object, pos);
		} else {
			options.add(object);
			super.add(object);
		}
	}

	public ButtonsGridViewAdapter(Context context, int textViewResourceId,
			OptionClickListener mecl, ArrayList<String> options) {
		super(context, textViewResourceId);
		this.mecl = mecl;
		this.options = options;
		this.context = context;
	}

	public int getCount() {
		return this.options.size();
	}

	public String getItem(int index) {
		return this.options.get(index);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		String chatMessageObj = getItem(position).replace("[", "").replace("]",
				"");
		LayoutInflater inflater = (LayoutInflater) this.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		holder = new ViewHolder();
		convertView = inflater.inflate(R.layout.option_button, parent, false);
		holder.optionButton = (Button) convertView.findViewById(R.id.btn);
		holder.optionButton.setText(chatMessageObj);
		// holder.optionButton.setTransformationMethod(null);
		holder.optionButton.setTag(position);
		holder.optionButton.setOnClickListener(onClick);

		return convertView;
	}

	private OnClickListener onClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			int pos = (int) v.getTag();
			mecl.onOptionClicked(((Button) v).getText().toString());
		}
	};

	private class ViewHolder {
		Button optionButton;
	}

	public interface OptionClickListener {
		public void onOptionClicked(String id);
	}
}
