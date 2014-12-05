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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.foodorder.beans.ApplicationData;
import com.foodorder.beans.UserInfo;
import com.foodorder.client.R;
import com.foodorder.net.FoodOrderRequest;
import com.foodorder.net.Parse;
import com.google.gson.JsonSyntaxException;

public class LoginActivity extends Activity {
	private DialogActivity dialog;
	private EditText txtPassword;
	private Button btnLogin;
	private Button Register1;
	private EditText txtUsername;
	private Context mCtx;
	private GetData gd;
	
	@Override
	protected void onStop(){
		Log.e("LoginActivity", "onStop");
		super.onStop();
	}
	
	@Override
	protected void onDestroy (){
		Log.e("LoginActivity", "onDestroy");
		if(null != gd)
			gd.cancel(true);
		super.onDestroy();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		txtPassword = (EditText) findViewById(R.id.editPassword);
		txtUsername = (EditText) findViewById(R.id.username);

		btnLogin = (Button) findViewById(R.id.btnLogin);
		mCtx = this;
		btnLogin.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				doLogin();
			}
		});

		Register1 = (Button) findViewById(R.id.btnSignup);
		Register1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				doRegister();
			}
		});

	}
	
	private void doLogin(){
		gd = new GetData(mCtx);
		gd.execute("");
	}
	
	private void doRegister(){
		new AlertDialog.Builder(LoginActivity.this)
		.setTitle("Sign Up")
		.setMessage("Register?")
		.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int which) {
						Intent intentR = new Intent(LoginActivity.this,
								RegisterActivity.class);
						intentR.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
								| Intent.FLAG_ACTIVITY_SINGLE_TOP);
						startActivity(intentR);				
						}
				})
		.setNegativeButton("No",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int which) {

					}
				}).show();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_login) {
			doLogin();
		}
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
			FoodOrderRequest request = new FoodOrderRequest(LoginActivity.this);

			try {
				result = request.login(txtUsername.getText().toString(),
						txtPassword.getText().toString());
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
					AlertDialog.Builder adbLogin = new AlertDialog.Builder( LoginActivity.this);
					if (user != null) {
						ApplicationData.setUser(user);
					} else {
						isFailed = true;
					}

					if (isFailed && adbLogin != null) {
						adbLogin.setTitle("Log in Error");
						adbLogin.setMessage("User name and password are not matched");
						adbLogin.setPositiveButton("Ok",
								new AlertDialog.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										if (txtPassword != null)
											txtPassword.setText("");
									}
								});
						adbLogin.setIcon(R.drawable.ic_launcher);
						adbLogin.show();
					}else if(!isFailed && adbLogin != null){
						adbLogin.setTitle("Log in");
						adbLogin.setMessage("Welcome " + user.getName());
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

}
