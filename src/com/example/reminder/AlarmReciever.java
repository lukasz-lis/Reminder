package com.example.reminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlarmReciever extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Toast.makeText(context, "Przypomnienie, zadanie do wykonainia: "+intent.getExtras().getString("TASK_NAME"), Toast.LENGTH_LONG).show();
		Log.d("Przypominacz", "Pominajka dziala!");
	}

}
