package com.foodorder.view;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeoutException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.foodorder.beans.ApplicationData;
import com.foodorder.beans.CommonModel;
import com.foodorder.beans.Giftcard;
import com.foodorder.client.R;
import com.foodorder.net.FoodOrderRequest;
import com.foodorder.net.Parse;
import com.google.gson.JsonSyntaxException;

public class AddBalanceActivity extends Activity {
	private DialogActivity dialog;
	private TextView txtBalance;
	private GetData gd;
	private CheckCard cc;
	private Button btnRefill;
	private Button btnCheck;
	private boolean fChecked = false;
	private TextView txtCardBalance;
	private EditText editGiftcard;
	int currentBalance;
	
	public void closeThisAct(View view){
		finish();
	}
	
	public void addBalance(View view){
		
	}
	
	public void checkCard(View view){
		cc = new CheckCard(this, editGiftcard.getText().toString());
		cc.execute("");
	}
	
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
		if(null != cc)
			cc.cancel(true);
		super.onDestroy();
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		verifyUI();
	}
	
	private void verifyUI(){
		if(fChecked){
			btnRefill.setEnabled(true);
			btnCheck.setEnabled(false);
			txtCardBalance.setTextColor(Color.BLUE);
			btnRefill.invalidate();
		}else{
			txtCardBalance.setTextColor(Color.RED);
			btnRefill.setEnabled(false);
			btnCheck.setEnabled(true);
		}
		btnRefill.invalidate();
		btnCheck.invalidate();
		txtCardBalance.invalidate();
	}
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_balance);   
        
        setTitle("Review Account Balance");
        txtCardBalance=(TextView)findViewById(R.id.txtCardBalance);
		txtBalance=(TextView) findViewById(R.id.txtBalance);
		btnRefill=(Button)findViewById(R.id.btnRefill);
		btnCheck=(Button)findViewById(R.id.btnCheck);
		
		editGiftcard=(EditText)findViewById(R.id.editGiftcard);
		
		editGiftcard.addTextChangedListener(new TextWatcher() {
		    @Override
		    public void onTextChanged(CharSequence s, int start, int before, int count) {
		    	
		    }
		    @Override
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		    	
		    }
		    @Override
		    public void afterTextChanged(Editable s) {
		    	fChecked = false;
		    	txtCardBalance.setText("");
		    	verifyUI();
		    }
		});
		
		currentBalance = ApplicationData.getBalance();
		double fAmt = (double)currentBalance / 100;
		txtBalance.setText(
	    		   String.format(Locale.CANADA, "%,d", currentBalance)
	    		    + " points\n"+" - equivalant to $ "+String.format(Locale.CANADA, "%,.2f", fAmt));

		gd = new GetData(this, ApplicationData.getUser().getUserid());
		gd.execute("");
    }
	
	private class CheckCard extends AsyncTask<String, String, String> {
		//private Context mContext;
		private String giftcode;
		private String balance;
		
		private CheckCard(Context context, String giftcode) {
			//this.mContext = context;
			this.giftcode = giftcode;
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
				result = request.checkGiftcard(giftcode);
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
				Log.e("onPost", result);
				try {
					Giftcard giftCard = new Parse().GetGiftcard(result);
					if(giftCard == null || giftCard.getGiftbalance() == null 
							|| giftCard.getGiftbalance().length() <=0){
						isFailed = true;
						Log.e("onPost", "Failed");
					}else if(!giftCard.canUse()){
						isFailed = true;
						Log.e("onPost", "Used card");
					}else{
						Log.e("onPost", "available card");
						balance =giftCard.getGiftbalance();
						fChecked = true;
						verifyUI();
						txtCardBalance.setText("Gift card amount is : "+
								String.format(Locale.CANADA, "%,d", Integer.parseInt(balance))
				    		    + " points");
					}
					
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
					Log.e("onPost", "exception, JsonSyntaxException");
					isFailed = true;
				} catch (Exception e) {
					Log.e("onPost", "exception, Exception");
					e.printStackTrace();
					isFailed = true;
				} 
				finally{
					
					if(isFailed){
						txtCardBalance.setText("Invalid gift card number");
					}
						
				}
			}
		}

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
						ApplicationData.setBalance(Integer.parseInt(balance));
						currentBalance = ApplicationData.getBalance();
						double fAmt = (double)currentBalance / 100;
						txtBalance.setText(
					    		   String.format(Locale.CANADA, "%,d", currentBalance)
					    		    + " points\n"+" - equivalant to $ "+String.format(Locale.CANADA, "%,.2f", fAmt));

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
