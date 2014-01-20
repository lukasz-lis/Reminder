package com.example.reminder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "tasks.db";
	private static final int DATABASE_VERSION = 1;

	public DataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	private static final String TO_DO_DATABASE_CREATE = "CREATE TABLE TASK_T( _ID INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ "TASK_NAME TEXT NOT NULL, "
			+ "TASK_STATUS TEXT NOT NULL, "
			+ "TASK_DUE_DATE TEXT NOT NULL, " 
			+ "TASK_GEO TEXT NOT NULL, " 
			+ "TASK_ID TEXT NOT NULL);";
	private static final String TO_DO_DATABASE_DROP = "DROP TABLE IF EXISTS TASK_T";

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
	
		arg0.execSQL(TO_DO_DATABASE_CREATE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		arg0.execSQL(TO_DO_DATABASE_DROP);

		onCreate(arg0);

	}

}
