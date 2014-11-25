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
import com.foodorder.net.FoodOrderRequest;
import com.foodorder.net.Parse;
import com.google.gson.JsonSyntaxException;

public class LoginActivity extends Activity {
	
	private String name;
	private String pwd;
	private EditText txtPassword;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);  
        txtPassword = (EditText) findViewById(R.id.editPassword);
    }
	
	
	
	
	
	private class GetData extends AsyncTask<String, String, String> {
		private Context mContext;
		private UserInfo user;
		
		private AlertDialog alertDialog1;

		private GetData(Context context) {
			this.mContext = context;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			String result = null;
			FoodOrderRequest request = new FoodOrderRequest(LoginActivity.this);

			try {
				result = request.login(name, pwd);
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
			if (result == null || result.equals("")) {
				alertDialog1=new AlertDialog.Builder(mContext).create();
				isFailed = true;
			} else {
				try {
					user = new Parse().GetUserInfo(result);
					
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				} finally{
					if (user != null) {
						ApplicationData.setUser(user);
						// TODO do something when success login
						finish();
					} else {
						isFailed = true;
						alertDialog1=new AlertDialog.Builder(mContext).create();
					}
					
					if(isFailed && alertDialog1!=null){
						//Setting Dialog Title
		                alertDialog1.setTitle("Log in failed");
		                //Setting Dialog Message
		                alertDialog1.setMessage("User name and password are not matched");
		                //Setting Icon to Dialog
		                alertDialog1.setIcon(R.drawable.login);
		                //Setting OK Button
		                alertDialog1.setButton("OK", 
		                		new DialogInterface.OnClickListener() {
				                    @Override
				                    public void onClick(final DialogInterface dialog, final int which) {
				                        // TODO do something when failed to log-in
				                    	// eg. remove user name or password edit text field
				                    	if(txtPassword != null)
				                    		txtPassword.setText("");
				                    }
				                });
		                //Showing Alert Message
		                alertDialog1.show();
					}
						
				}
			}
		}

		

	}
}
