package com.foodorder.view;

import java.io.IOException;

import com.foodorder.client.R;
import com.foodorder.client.R.id;
import com.foodorder.client.R.layout;
import com.foodorder.client.R.menu;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class OrderDetail extends Activity {

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_detail);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.order_detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}else if(id == R.id.action_cart){
			Intent intentViewCart = new Intent(OrderDetail.this,
					ShoppingCartActivity.class);
			intentViewCart.putExtra("ViewCart", "View Cart Successful");
			startActivity(intentViewCart);
		}
		return super.onOptionsItemSelected(item);
	}
}
