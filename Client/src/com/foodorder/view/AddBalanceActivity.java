package com.foodorder.view;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.foodorder.beans.ApplicationData;
import com.foodorder.beans.CommonModel;
import com.foodorder.client.R;
import com.foodorder.net.FoodOrderRequest;
import com.foodorder.net.Parse;
import com.google.gson.JsonSyntaxException;

public class AddBalanceActivity extends Activity {
	private DialogActivity dialog;
	private TextView txtBalance;
	private Intent intentViewBalance;
	private Bundle b;
	private GetData gd;
	double currentBalance;
	
	@Override
	protected void onStop(){
		Log.e("AddBalanceActivity", "onStop");
		super.onStop();
	}
	
	@Override
	protected void onDestroy (){
		Log.e("AddBalanceActivity", "onDestroy");
		if(null != gd)
			gd.cancel(true);
		super.onDestroy();
	}
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_balance);   
        
        setTitle("Review Account Balance");

		intentViewBalance = getIntent();
		b = intentViewBalance.getExtras();
		b.get("ViewBalance");
		
		txtBalance=(TextView) findViewById(R.id.txtBalance);
		
		currentBalance = ApplicationData.getBalance();
		txtBalance.setText(String.valueOf(currentBalance));
		
		gd = new GetData(this, ApplicationData.getUser().getUserid());
		gd.execute("");
    }
	
	private class GetData extends AsyncTask<String, String, String> {
		//private Context mContext;
		private String userid;
		private String balance;
		
		private GetData(Context context, String userid) {
			//this.mContext = context;
			this.userid = userid;
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
			FoodOrderRequest request = new FoodOrderRequest(AddBalanceActivity.this);

			try {
				result = request.getBalance(userid);
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
					CommonModel comm = new Parse().CommonPares(result);
					if(comm == null || comm.getResult() == null || comm.getResult().length() <= 0){
						isFailed = true;
					}else{
						balance =comm.getResult();
					}
					
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
					isFailed = true;
				} catch (Exception e) {
					e.printStackTrace();
					isFailed = true;
				} 
				finally{
					if (balance != null) {
						ApplicationData.setBalance(Double.parseDouble(balance));
						txtBalance.setText("$ " + balance);
					} else {
						isFailed = true;
					}
					
					if(isFailed){
						AlertDialog.Builder adbLogin = new AlertDialog.Builder( AddBalanceActivity.this);
						adbLogin.setTitle("Network Error");
						adbLogin.setMessage("Error while access to server to retrieve balance amount");
						adbLogin.setPositiveButton("Ok",
								new AlertDialog.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										 // TODO do something when failed to retrieve new amount from server
									}
								});
						adbLogin.setIcon(R.drawable.ic_launcher);
						adbLogin.show();
					}
						
				}
			}
		}

	}	
	
}
