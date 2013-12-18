package com.example.reminder;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.reminder.dao.TaskDAO;
import com.example.reminder.entity.TaskEntity;

public class AddReminderActivity extends Activity {

	private Button addButton;
	private TaskDAO taskDAO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_reminder);
		taskDAO = new TaskDAO(getBaseContext());
		addButton = (Button) findViewById(R.id.addTask);
		addButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addNewTask();
				addNewAlarmForTask();
				startActivity(new Intent(getApplicationContext(),
						MainActivity.class));

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_reminder, menu);
		return true;
	}
	
	private void addNewAlarmForTask() {
		Log.d(ALARM_SERVICE, "Dodaje alarm");
		TaskEntity temp = getTaskFormView();

		Intent intent = new Intent(this, AlarmReciever.class);
		intent.putExtra("TASK_NAME", temp.getTaskName());

		Calendar now = Calendar.getInstance();
		now.add(Calendar.SECOND, 10);
		
		PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
		
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, now.getTimeInMillis(), 6000, sender);
	}

	private TaskEntity getTaskFormView() {
		TaskEntity newTask = new TaskEntity();
		newTask.setTaskDueDate(((EditText) findViewById(R.id.taskDueDate))
				.getText().toString());
		newTask.setTaskName(((EditText) findViewById(R.id.taskName)).getText()
				.toString());
		newTask.setTaskGeo(((EditText) findViewById(R.id.taskGeo)).getText()
				.toString());
		newTask.setTaskStatus("NOTDONE");
		return newTask;
	}

	private void addNewTask() {
		taskDAO.open();
		taskDAO.save(getTaskFormView());

	}

}
