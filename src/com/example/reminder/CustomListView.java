package com.example.reminder;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.example.reminder.dao.TaskDAO;
import com.example.reminder.entity.TaskEntity;

public class CustomListView extends ArrayAdapter<TaskEntity> {

	private final Context context;
	private final List<TaskEntity> values;
	private final TaskDAO taskDAO;
	private int pos;

	public CustomListView(Context context, List<TaskEntity> objects) {
		super(context, R.layout.task_row, objects);
		this.context = context;
		this.values = objects;
		this.taskDAO = new TaskDAO(context);
	

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		pos = position;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.task_row, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.name);
		CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.checkedBox);
		TextView dateView = (TextView) rowView.findViewById(R.id.date);
		textView.setText(values.get(position).getTaskName());
		dateView.setText(values.get(position).getTaskDueDate());		
		// Change the icon for Windows and iPhone
		String s = values.get(position).getTaskStatus();
		if (s.equals("DONE")) {
			checkBox.setChecked(true);
		} else {
			checkBox.setChecked(false);
		}

		return rowView;
	}

}
