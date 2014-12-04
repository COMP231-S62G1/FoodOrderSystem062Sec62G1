package com.foodorder.view;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;

import com.foodorder.beans.ApplicationData;
import com.foodorder.beans.MenuModel;
import com.foodorder.beans.UserInfo;
import com.foodorder.client.R;
import com.foodorder.client.R.id;
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
		new GetData(mCtx).execute("");
	}
	
	private void doRegister(){
		new AlertDialog.Builder(LoginActivity.this)
		.setTitle("Sign Up")
		.setMessage("Register?")
		.setPositiveButton("yes",
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
		.setNegativeButton("no",
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
		private Context mContext;
		private UserInfo user;

		private AlertDialog alertDialog1;

		private GetData(Context context) {
			this.mContext = context;
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
				alertDialog1 = new AlertDialog.Builder(mContext).create();
				isFailed = true;
			} else {
				try {
					user = new Parse().GetLoginInfo(result);

				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				} finally {
					alertDialog1 = new AlertDialog.Builder(mContext).create();
					if (user != null) {
						ApplicationData.setUser(user);
						// TODO do something when success login
						
					} else {
						isFailed = true;
					}

					if (isFailed && alertDialog1 != null) {
						// Setting Dialog Title
						alertDialog1.setTitle("Log in failed");
						// Setting Dialog Message
						alertDialog1.setMessage("User name and password are not matched");
						// Setting Icon to Dialog
						alertDialog1.setIcon(R.drawable.login);
						// Setting OK Button
						alertDialog1.setButton("OK",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(
											final DialogInterface dialog,
											final int which) {
										// TODO do something when failed to
										// log-in
										// eg. remove user name or password edit
										// text field
										if (txtPassword != null)
											txtPassword.setText("");
									}
								});
						// Showing Alert Message
						alertDialog1.show();
					}else if(!isFailed && alertDialog1 != null){
						// Setting Dialog Title
						alertDialog1.setTitle("Log in successful");
						// Setting Dialog Message
						alertDialog1.setMessage("Welcome " + user.getName());
						// Setting Icon to Dialog
						alertDialog1.setIcon(R.drawable.login);
						// Setting OK Button
						alertDialog1.setButton("OK",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(
											final DialogInterface dialog,
											final int which) {
										finish();
									}
								});
						// Showing Alert Message
						alertDialog1.show();
					}

				}
			}
		}

	}

}
