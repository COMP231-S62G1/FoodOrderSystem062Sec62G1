package com.foodorder.services;

import java.util.Timer;
import java.util.TimerTask;
import com.foodorder.beans.ApplicationData;
import com.foodorder.beans.CommonModel;
import com.foodorder.client.R;
import com.foodorder.net.FoodOrderRequest;
import com.foodorder.net.Parse;
import com.foodorder.view.OrderDetail;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class UpdateOrderStatus extends Service {
	
	// parameters to start this service
	
	// when client sent new order to server
	final public static int NEW_ORDER = 0;
	
	// when client get order confirm message from server
	// start this service to check when order is completed
	final public int PROCESSING_ORDER = 1;
	
	private int reason=0;
	
	
	private Timer updateTimer;
	private boolean autoUpdate = true;
	private int updateFreq = 10;
	private int orderId;
	
	private NotificationManager notificationManager;
	private Notification.Builder foodNotiBuilder;
	private int NOTIFICATION_ID;
	

	@Override
	public void onCreate() {
		Log.e("UpdateOrderStatus", "onCreate()");
		updateTimer = new Timer("orderstatus");
		
		notificationManager 
	      = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		
		foodNotiBuilder = new Notification.Builder(this);
		
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		Log.e("UpdateOrderStatus", "onBind()");
		return null;
	}
	
	@Override
    public void onDestroy() {
        // Cancel the persistent notification.
		Log.e("UpdateOrderStatus", "onDestroy()");
		updateTimer.cancel();
        // Tell the user we stopped.
        Toast.makeText(this, "Service Stoppted", Toast.LENGTH_SHORT).show();
    }

	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		Bundle extras = intent.getExtras(); 
		if(extras == null)
		    Log.e("UpdateOrderStatusService Service","extra is null");
		else
		{
		    Log.e("UpdateOrderStatusService Service","extra is not null");
		    String getString = (String)extras.get("NEW_MENU");
		    Log.e("UpdateOrderStatusService Service","extra is not null and getString is " +getString);
		    int getInt = Integer.parseInt(getString);
		    //int getInt = extras.getInt("NEW_MENU");
		    Log.e("UpdateOrderStatusService Service","extra is not null and getInt is " +getInt);
			reason = 0;
			if(getInt == 0){
				extras.get("From");
				getString = (String)extras.get("PROCESSING_ORDER");
				getInt = Integer.parseInt(getString);
				//getInt = extras.getInt("PROCESSING_ORDER");
				Log.e("UpdateOrderStatusService Service","extra is not null and getInt is " +getInt);
				reason = 1;
				if(getInt == 0){
					reason = 2;
					stopSelf();
				}
			}
			orderId = getInt;
			if(!ApplicationData.arrOrderId.contains(Integer.toString(orderId))){
				ApplicationData.arrOrderId.add(Integer.toString(orderId));
			}
			NOTIFICATION_ID = ApplicationData.NOTIFICATION_ID++;
		}
		
		
		Log.e("UpdateOrderStatus Service", "onStartCommand() startId is "+startId + ", orderId is "+orderId+" flag is "+flags);

		//if ((flags & START_FLAG_RETRY) == 0) {
			
		//}
		//else 
		{
			
			updateTimer.cancel();
			if(autoUpdate) {
				updateTimer = new Timer("orderstatus");
				updateTimer.scheduleAtFixedRate(new TimerTask() {
						@Override
						public void run() {
							refreshStatus();
						}
					}
					, 5000, updateFreq*1000);
				
			}
		}
		//refreshStatus();
		return Service.START_NOT_STICKY;
	}
	
	private String refreshStatus(){
		
		Thread thread = new Thread() {

			
			@SuppressLint("NewApi")
			@Override
			public void run() {
				super.run();
				FoodOrderRequest request = new FoodOrderRequest(
						getApplicationContext());
				String result = null;
				
				try {
					result = request.checkStatus(Integer.toString(orderId));
					CommonModel resultCom = new Parse().CommonPares(result);
					String status =  resultCom.getResult();
					int orderStatus = Integer.parseInt(status);
					//int orderStatus = Integer.parseInt("1");
					
					Log.e("UpdateOrderStatus", "refreshStatus() Order status"+status+" reason="+reason);
					
					String notiMsg = null;
					String tickerMsg = "Order status was updated";
					if(reason == 0){
						if (orderStatus == 1){
							// notify order was confirmed and add new service to check order completed
							notiMsg = "Your order is under processing now";
							reason = 1;
						}else if (orderStatus == 3){
							notiMsg = "Your order was rejected";
							updateTimer.cancel();
							int idx = ApplicationData.arrOrderId.indexOf(Integer.toString(orderId));
							Log.e("remove order", "      ID: "+orderId+", index: " + idx);
							ApplicationData.arrOrderId.remove(idx);
							stopSelf();
						}else{
							return;
						}
						
					}else if (reason == 1){
						if (orderStatus == 2){
							// notify order was completed
							notiMsg = "Your order was completed. Please pick it up";
							updateTimer.cancel();
							int idx = ApplicationData.arrOrderId.indexOf(Integer.toString(orderId));
							Log.e("remove order", "      ID: "+orderId+", index: " + idx);
							ApplicationData.arrOrderId.remove(idx);
							stopSelf();
						}else{
							return;
						}
					}
					//Notification Bar
					foodNotiBuilder
					 .setTicker(tickerMsg)
			         .setContentTitle("Food Order")
			         .setContentText(notiMsg)
			         .setSmallIcon(R.drawable.ic_launcher);
					
					
					// Creates an explicit intent for an Activity in your app
					Intent resultIntent = new Intent(getApplicationContext(), OrderDetail.class);
					resultIntent.putExtra("orderId", orderId);
					resultIntent.putExtra("orderStatus", orderStatus);
					resultIntent.putExtra("notiId", NOTIFICATION_ID);
					
					
					//This ensures that navigating backward from the Activity leads out of the app to Home page
					TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
					
					// Adds the back stack for the Intent
					stackBuilder.addParentStack(OrderDetail.class);
					
					// Adds the Intent that starts the Activity to the top of the stack
					stackBuilder.addNextIntent(resultIntent);
					PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
							0, PendingIntent.FLAG_ONE_SHOT //can only be used once
							);
					// start the activity when the user clicks the notification text
					foodNotiBuilder.setContentIntent(resultPendingIntent);
					notificationManager.notify(NOTIFICATION_ID,
							foodNotiBuilder.build() );
					
				}/* catch (IOException e) {
					e.printStackTrace();
				} catch (TimeoutException e) {
					e.printStackTrace();
				}*/
				catch(Exception e){
					e.printStackTrace();
				}
				//stopSelf();
			}
		};
		thread.start();
		
		
		return null;
	}

}

