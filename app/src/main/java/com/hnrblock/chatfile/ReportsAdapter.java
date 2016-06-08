package com.hnrblock.chatfile;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hnrblock.chatfile.objects.ReportRow;

public class ReportsAdapter extends ArrayAdapter<ReportRow> {
	private ArrayList<ReportRow> reports;

	public ReportsAdapter(Context context, int resource,
			ArrayList<ReportRow> reports) {
		super(context, resource, reports);
		this.reports = reports;
		notifyDataSetChanged();
	}

	private class ViewHolder {
		TextView titleText, valueText;
	}

	public ReportRow getItem(int index) {
		return this.reports.get(index);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ReportRow rr = reports.get(position);
		ViewHolder holder = new ViewHolder();
		LayoutInflater inflater = (LayoutInflater) this.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		convertView = inflater.inflate(R.layout.report_row, parent, false);
		holder.titleText = (TextView) convertView.findViewById(R.id.title);
		holder.valueText = (TextView) convertView.findViewById(R.id.value);
		holder.titleText.setText(rr.getTitle());
		holder.valueText.setText(rr.getValue());
		return convertView;
	}

}
