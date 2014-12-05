package com.foodorder.view;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeoutException;

import android.os.Handler;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.foodorder.beans.ApplicationData;
import com.foodorder.beans.CommonModel;
import com.foodorder.beans.UserInfo;
import com.foodorder.client.R;
import com.foodorder.net.FoodOrderRequest;
import com.foodorder.net.Parse;
import com.google.gson.JsonSyntaxException;

public class RegisterActivity extends Activity {
	private boolean isValidName = false;
	private DialogActivity dialog;
	private EditText txtUser;
	private EditText txtPassword1;
	private EditText txtPassword2;
	private EditText txtPhone;
	private EditText txtEmail;
	private TextView txtInvalid;
	private Button btnCheck;
	private Button btnUpdate;
	private SetData sd;
	private GetData gd;
	private CheckData cd;
	
	private UserInfo ui;
	
	@Override
	protected void onStop(){
		Log.e("RegisterActivity", "onStop");
		super.onStop();
	}
	
	@Override
	protected void onDestroy (){
		Log.e("RegisterActivity", "onDestroy");
		if(sd !=null)
			sd.cancel(true);
		if(gd != null)
			gd.cancel(true);
		super.onDestroy();
	}
	
	private void applyUI(){
		boolean isPwdOkay = false;
		if(txtPassword1.getText().toString().equals(txtPassword2.getText().toString())){
    		txtInvalid.setVisibility(View.GONE);
    		txtInvalid.invalidate();
    		if(txtPassword1.getText().toString().length() <=0){
    			isPwdOkay = false;
    		}else{
    			isPwdOkay = true;
    		}
    	}else{
    		isPwdOkay = false;
    		txtInvalid.setVisibility(View.VISIBLE);
    		txtInvalid.invalidate();
    	}
		
		if(isValidName && isPwdOkay){
			btnUpdate.setEnabled(true);
		}else{
			btnUpdate.setEnabled(false);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		btnUpdate = (Button)findViewById(R.id.btnUpdate);
		txtInvalid  = (TextView)findViewById(R.id.txtInvalid);
		Button btnCancel = (Button)findViewById(R.id.btnCancel);
		btnCheck =(Button)findViewById(R.id.btnCheck);

		txtUser = (EditText) findViewById(R.id.txtUser);
		txtPassword1 = (EditText) findViewById(R.id.txtPassword1);
		txtPassword2 = (EditText) findViewById(R.id.txtPassword2);
		txtPhone = (EditText) findViewById(R.id.txtPhone);
		txtEmail = (EditText) findViewById(R.id.txtEmail);

		
		
		ui = new UserInfo();
		btnCheck.setEnabled(false);
		btnUpdate.setEnabled(false);
		
		btnCheck.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				handler.sendEmptyMessage(3);						
			}
		});
		
		txtUser.addTextChangedListener(new TextWatcher() {
		    @Override
		    public void onTextChanged(CharSequence s, int start, int before, int count) {}
		    @Override
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
		    @Override
		    public void afterTextChanged(Editable s) {
		    	isValidName = false;
		    	btnUpdate.setEnabled(false);
		    	if(s.toString().length()>0){
		    		btnCheck.setEnabled(true);
		    	}else{
		    		btnCheck.setEnabled(false);
		    	}
		    }
		});
		
		txtPassword1.addTextChangedListener(new TextWatcher() {
		    @Override
		    public void onTextChanged(CharSequence s, int start, int before, int count) {}
		    @Override
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
		    @Override
		    public void afterTextChanged(Editable s) {
		    	applyUI();
		    }
		});
		txtPassword2.addTextChangedListener(new TextWatcher() {
		    @Override
		    public void onTextChanged(CharSequence s, int start, int before, int count) {}
		    @Override
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
		    @Override
		    public void afterTextChanged(Editable s) {
		    	applyUI();
		    }
		});
		
		
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//save function
				handler.sendEmptyMessage(0);						
			}
		});
		btnCancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();						
			}
		});

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
			FoodOrderRequest request = new FoodOrderRequest(RegisterActivity.this);

			try {
				result = request.login( ui.getName(), ui.getPwd());
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
					AlertDialog.Builder adbLogin = new AlertDialog.Builder( RegisterActivity.this);
					if (user != null) {
						ApplicationData.setUser(user);
					} else {
						isFailed = true;
					}

					if (isFailed && adbLogin != null) {
						adbLogin.setTitle("Network Error");
						adbLogin.setMessage("Error while access to server to register account");
						adbLogin.setPositiveButton("Ok",
								new AlertDialog.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										
									}
								});
						adbLogin.setIcon(R.drawable.ic_launcher);
						adbLogin.show();
					}else if(!isFailed && adbLogin != null){
						adbLogin.setTitle("Regstration");
						adbLogin.setMessage("Welcome to join FoodOrder, " + user.getName());
						adbLogin.setPositiveButton("Ok",
								new AlertDialog.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										finish();
									}
								});
						adbLogin.setIcon(R.drawable.ic_launcher);
						adbLogin.show();
					}

				}
			}
		}

	}

	private class SetData extends AsyncTask<String, String, String> {
		//private Context mContext;
		private int mType;

		private SetData(Context context, int type) {
			//this.mContext = context;
			this.mType = type;
			dialog = new DialogActivity(context, type);
		}

		@Override
		protected void onPreExecute() {
			if (mType == 1) {
				if (null != dialog && !dialog.isShowing()) {
					dialog.show();
				}
			}
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			String result = null;
			FoodOrderRequest request = new FoodOrderRequest(
					RegisterActivity.this);

			try {
				Log.e("Register Account", "request started");
				result = request.getRegistration(txtUser.getText().toString(),
						txtPassword1.getText().toString(), txtPhone.getText()
								.toString(), txtEmail.getText().toString());
				Log.e("Register Account", "got result: "+ result);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TimeoutException e) {
				e.printStackTrace();
			}

			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			if (null != dialog && dialog.isShowing()) {
				dialog.dismiss();
			}
			boolean isFailed=false;

			if (result == null || result.equals("")) {
				isFailed=true;
			} else {
				try{
					CommonModel comm = new Parse().CommonPares(result);
					if(comm == null || comm.getResult() == null || comm.getResult().length() <= 0){
						isFailed = true;
					}else if(!comm.getResult().equals("succ")){
						isFailed = true;
					}
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
					Log.e("onPost", "exception, JsonSyntaxException");
					isFailed = true;
				} catch (Exception e) {
					Log.e("onPost", "exception, Exception");
					e.printStackTrace();
					isFailed = true;
				} finally{
					if(isFailed){
						AlertDialog.Builder adbLogin = new AlertDialog.Builder( RegisterActivity.this);
						adbLogin.setTitle("Network Error");
						adbLogin.setMessage("Error while access to server to register account");
						adbLogin.setPositiveButton("Ok",
								new AlertDialog.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										 // TODO do something when failed to retrieve new amount from server
									}
								});
						adbLogin.setIcon(R.drawable.ic_launcher);
						adbLogin.show();
					}else{
						ui.setName(txtUser.getText().toString());
						ui.setPwd(txtPassword1.getText().toString());
						gd = new GetData(RegisterActivity.this);
						gd.execute("");
					}
						
				}
					
			}
		}
	}
	
	private class CheckData extends AsyncTask<String, String, String> {
		//private Context mContext;
		private int mType;

		private CheckData(Context context, int type) {
			//this.mContext = context;
			this.mType = type;
			dialog = new DialogActivity(context, type);
		}

		@Override
		protected void onPreExecute() {
			if (mType == 1) {
				if (null != dialog && !dialog.isShowing()) {
					dialog.show();
				}
			}
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			String result = null;
			FoodOrderRequest request = new FoodOrderRequest(RegisterActivity.this);

			try {
				Log.e("Check Account", "request started");
				result = request.checkUser(txtUser.getText().toString());
				Log.e("Check Account", "got result: "+ result);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TimeoutException e) {
				e.printStackTrace();
			}

			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			if (null != dialog && dialog.isShowing()) {
				dialog.dismiss();
			}
			boolean isFailed=false;

			if (result == null || result.length()<=0) {
				isFailed=true;
			} else {
				try{
					CommonModel comm = new Parse().CommonPares(result);
					int count = Integer.parseInt(comm.getResult());
					if(count == 0){
						isFailed = false;
					}else{
						isFailed=true;
					}
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
					Log.e("onPost", "exception, JsonSyntaxException");
					isFailed = true;
				} catch (Exception e) {
					Log.e("onPost", "exception, Exception");
					e.printStackTrace();
					isFailed = true;
				} finally{
					if(isFailed){
						isValidName = false;
						applyUI();
						AlertDialog.Builder adbLogin = new AlertDialog.Builder( RegisterActivity.this);
						adbLogin.setTitle("User name error");
						adbLogin.setMessage("User name " + txtUser.getText().toString() + " already exis in the system.\nPlease use other server");
						adbLogin.setPositiveButton("Ok",
								new AlertDialog.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										 // TODO do something when failed to retrieve new amount from server
										txtUser.setText("");
									}
								});
						adbLogin.setIcon(R.drawable.ic_launcher);
						adbLogin.show();
					}else{
						isValidName = true;
						applyUI();
						AlertDialog.Builder adbLogin = new AlertDialog.Builder( RegisterActivity.this);
						adbLogin.setTitle("User name okay");
						adbLogin.setMessage("You can use user name, " + txtUser.getText().toString());
						adbLogin.setPositiveButton("Ok",
								new AlertDialog.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										 // TODO do something when failed to retrieve new amount from server
										btnCheck.setEnabled(false);
										
									}
								});
						adbLogin.setIcon(R.drawable.ic_launcher);
						adbLogin.show();
					}
						
				}
					
			}
		}

	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what){
				case 0:
					sd = new SetData(RegisterActivity.this, 1);
					sd.execute("");
					break;
				default:
					cd = new CheckData(RegisterActivity.this, 1);
					cd.execute("");
					break;
			}
			
			
		}
	};
}
