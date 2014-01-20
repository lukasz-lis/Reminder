package com.example.reminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

public class AlarmGPSReciver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
	
		GPSTracker gps = new GPSTracker(context);
		
		Location loc = gps.getLocation();
		Log.d("GPS", Double.toString(loc.getLatitude())+Double.toString(loc.getLongitude()));
		Double longitude = intent.getExtras().getDouble("LONG");
		Double latitude= intent.getExtras().getDouble("LATIT");
		Location desc = new Location("network");
		desc.setLatitude(latitude);
		desc.setLongitude(longitude);
		Float dist = loc.distanceTo(desc);
		
		
		Toast.makeText(context, "Dystans do zadania "+intent.getExtras().getString("TASK_NAME")+": "+dist.toString(), Toast.LENGTH_LONG).show();
		Log.d("GPS", "GPS Dziala!");
	}

}
