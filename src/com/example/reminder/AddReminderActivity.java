package com.example.reminder;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.reminder.dao.TaskDAO;
import com.example.reminder.entity.TaskEntity;

public class AddReminderActivity extends Activity {

	private final static int DATE_PICKER_ID = 111;
	private final static int TIME_PICKER_ID = 112;
	
	private static Context context;

	private Button addTaskButton;
	private Button datePickerButton;
	private Button timePickerButton;

	private TaskDAO taskDAO;
	
	private TaskEntity task;
	private Double longi;
	private Double lati;

	private final Calendar actualTime = Calendar.getInstance();
	private Calendar timeForTask;

	private OnClickListener changeDateButtonListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			showDialog(DATE_PICKER_ID);
		}
	};

	private OnClickListener changeTimeButtonListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			showDialog(TIME_PICKER_ID);
		}
	};

	private OnClickListener addNewTaskButtonListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			saveNewTask();
			addNewAlarmForTask();
			addNewAlarmGPSForTask();
			startActivity(new Intent(getApplicationContext(), MainActivity.class));
			finish();
		}
	};

	private OnDateSetListener datePickerListener = new OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			timeForTask.set(Calendar.YEAR, selectedYear);
			timeForTask.set(Calendar.MONTH, selectedMonth);
			timeForTask.set(Calendar.DAY_OF_MONTH, selectedDay);
		}
	};

	private OnTimeSetListener timePickerListener = new OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int selectedHour,
				int selectedMinute) {
			timeForTask.set(Calendar.HOUR, selectedHour);
			timeForTask.set(Calendar.MINUTE, selectedMinute);
			timeForTask.set(Calendar.SECOND, 0);
		}
	};

	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		switch (id) {
		case DATE_PICKER_ID:
			return new DatePickerDialog(this, datePickerListener,
					actualTime.get(Calendar.YEAR),
					actualTime.get(Calendar.MONTH),
					actualTime.get(Calendar.DATE));
		case TIME_PICKER_ID:
			return new TimePickerDialog(this, timePickerListener,
					actualTime.get(Calendar.HOUR),
					actualTime.get(Calendar.MINUTE), true);
		}
		return null;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		AddReminderActivity.context = getApplicationContext();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_reminder);

		timeForTask = Calendar.getInstance();

		taskDAO = new TaskDAO(getBaseContext());

		addTaskButton = (Button) findViewById(R.id.addTask);
		datePickerButton = (Button) findViewById(R.id.changeDate);
		timePickerButton = (Button) findViewById(R.id.changeTime);

		datePickerButton.setOnClickListener(changeDateButtonListener);
		timePickerButton.setOnClickListener(changeTimeButtonListener);
		addTaskButton.setOnClickListener(addNewTaskButtonListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_reminder, menu);
		return true;
	}

	private void addNewAlarmForTask() {
		Log.d(ALARM_SERVICE, "Dodaje alarm");
		

		Intent intent = new Intent(this, AlarmReciever.class);
		intent.putExtra("TASK_NAME", task.getTaskName());

		String temp = task.getTaskId().substring(5);
		
		PendingIntent sender = PendingIntent.getBroadcast(getApplicationContext(), Integer.parseInt(temp), intent,
				PendingIntent.FLAG_ONE_SHOT);

		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeForTask.getTimeInMillis(), 6000, sender);
	}
	
	private void addNewAlarmGPSForTask() {		

		Intent intent = new Intent(this, AlarmGPSReciver.class);
		intent.putExtra("TASK_NAME", task.getTaskName());
		intent.putExtra("LONG", longi);
		intent.putExtra("LATIT", lati);

		String temp = task.getTaskId().substring(5);
		
		PendingIntent sender = PendingIntent.getBroadcast(getApplicationContext(), Integer.parseInt(temp), intent, 0);

		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000L*60L, sender);
	}

	private TaskEntity getTaskFormView() {
		
		TaskEntity temp = new TaskEntity();
		temp.setTaskDueDate(timeForTask.getTime().toLocaleString());
		temp.setTaskName(((EditText) findViewById(R.id.taskName)).getText()
				.toString());
		String cords = getCordFromAddress(((EditText) findViewById(R.id.taskGeo)).getText().toString());
		temp.setTaskGeo(cords);
		temp.setTaskStatus("NOTDONE");
		temp.setTaskId(Long.toString(System.currentTimeMillis()));
		return temp;
	}

	private String getCordFromAddress(String address) {
		Log.d("GETING ADDRESS ERROR", "Sprawdzam cordy");
		Geocoder geocoder = new Geocoder(getApplicationContext());  
		List<Address> addresses = null;
		try {
			addresses = geocoder.getFromLocationName(address, 1);
		}
		catch(IOException e) {
			Log.d("GETING ADDRESS ERROR", e.getMessage());
		}
		if(addresses != null && addresses.size() > 0) {
		    Double latitude= addresses.get(0).getLatitude();
		    Double longitude= addresses.get(0).getLongitude();
		    lati = latitude;
		    longi = longitude;
		    Log.d("Oto twoje cordy: ", latitude+" "+longitude);
		    return latitude+";"+longitude;
		}
		return "";
	}
	
	private void saveNewTask() {
		taskDAO.open();
		task = getTaskFormView();
		taskDAO.save(task);
	}
	
	public static Context getAppContex() {
		return AddReminderActivity.context;
	}

}
