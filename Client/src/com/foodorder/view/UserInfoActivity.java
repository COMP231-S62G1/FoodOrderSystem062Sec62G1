package com.foodorder.view;


import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeoutException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.foodorder.beans.ApplicationData;
import com.foodorder.beans.UserInfo;
import com.foodorder.client.R;
import com.foodorder.net.FoodOrderRequest;
import com.foodorder.net.Parse;
import com.foodorder.utils.LogInOut;
import com.google.gson.JsonSyntaxException;

public class UserInfoActivity extends Activity {
	
	private Button btnEdit;
	private TextView username;
	private TextView phone;
	private TextView email;
	private TextView balance;
	private UserInfo user;
	private int aBalance;
	private TextView error;
	//private Intent getUpdate;
	
	private DialogActivity dialog;
	
	
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info);   
               
		username = (TextView) findViewById(R.id.info1);
		phone = (TextView) findViewById(R.id.info2);
		email = (TextView) findViewById(R.id.info3);
		balance = (TextView) findViewById(R.id.info4);
		error = (TextView) findViewById(R.id.textView9);
		
		
        
        btnEdit = (Button) findViewById(R.id.edit3);
		btnEdit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intentEdit = new Intent(UserInfoActivity.this,
						EditUserInfoActivity.class);
//				intentEdit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//						| Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intentEdit);		
			}
		});
		/*
		getUpdate = getIntent();
		Bundle b = getUpdate.getExtras();
		b.get("ViewUpdate");
		*/
    }
	
	@Override
	protected void onResume(){
		super.onResume();
		user = ApplicationData.getUser();
		aBalance = ApplicationData.getBalance();
		if (user != null)
		{
			username.setText(user.getName());
			phone.setText(user.getPhone());
			email.setText(user.getEmail());
			double fAmt = (double)aBalance / 100;
			balance.setText(
					   String.format(Locale.CANADA, "%,d", aBalance)
				    + " points\n"+" - equivalant to $ "+String.format(Locale.CANADA, "%,.2f", fAmt));
		}else{    	   
			error.setText("Please login");
		}
		
		
	}
	
	
	public void runAddBal(View view){
		Intent intentViewCart = new Intent(this, AddBalanceActivity.class);
		startActivity(intentViewCart);
		return;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rest_list, menu);
		LogInOut.setLogin(true, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		LogInOut.handleOptionItem(this, item);
		return super.onOptionsItemSelected(item);
	}
	
	
	private class GetData extends AsyncTask<String, String, String> {
		//private Context mContext;
		private UserInfo user;


		private GetData(Context context) {
			//this.mContext = context;
			dialog = new DialogActivity(context, 1);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (null != dialog && !dialog.isShowing()) {
				dialog.show();
			}
		}

		@Override
		protected String doInBackground(String... params) {
			String result = null;
			user = ApplicationData.getUser();
			
			FoodOrderRequest request = new FoodOrderRequest(UserInfoActivity.this);

			try {
				result = request.getUser(user.getUserid() );
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TimeoutException e) {
				e.printStackTrace();
			}

			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			boolean isFailed = false;
			if (null != dialog) {
				dialog.dismiss();
			}
			if (result == null || result.equals("")) {
				isFailed = true;
			} else {
				try {
					user = new Parse().GetLoginInfo(result);
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				} finally {
					ApplicationData.setUser(user);
					user = ApplicationData.getUser();
					aBalance = ApplicationData.getBalance();
					if (user != null)
					{
						username.setText(user.getName());
						phone.setText(user.getPhone());
						email.setText(user.getEmail());
						double fAmt = (double)aBalance / 100;
						balance.setText(
								   String.format(Locale.CANADA, "%,d", aBalance)
							    + " points\n"+" - equivalant to $ "+String.format(Locale.CANADA, "%,.2f", fAmt));
						username.invalidate();
						phone.invalidate();
						email.invalidate();
						balance.invalidate();
					}else{    	   
						error.setText("Please login");
					}
				}
			}
		}

	}
}
