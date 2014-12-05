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
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.foodorder.beans.ApplicationData;
import com.foodorder.beans.CommonModel;
import com.foodorder.beans.UserInfo;
import com.foodorder.client.R;
import com.foodorder.net.FoodOrderRequest;
import com.foodorder.net.Parse;

public class EditUserInfoActivity extends Activity {
	private DialogActivity dialog;
	
	private UserInfo user;
	private EditText txtPassword1;
	private EditText txtPassword2;
	private EditText txtEmail;
	private EditText txtPhone;
	private GetData gd;
	
	@Override
	protected void onStop(){
		Log.e("EditUserInfoActivity", "onStop");
		super.onStop();
	}
	
	@Override
	protected void onDestroy (){
		Log.e("EditUserInfoActivity", "onDestroy");
		if(null != gd)
			gd.cancel(true);
		super.onDestroy();
	}
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
		user = ApplicationData.getUser();   
		final Button btnUpdate = (Button)findViewById(R.id.btnUpdate);
		Button btnCancel = (Button)findViewById(R.id.btnCancel);
		final TextView txtInvalid  = (TextView)findViewById(R.id.txtInvalid);
		btnUpdate.setEnabled(false);
		
		TextView txtUser  = (TextView)findViewById(R.id.txtUser);
		txtUser.setEnabled(false);
		
		txtPassword1  = (EditText)findViewById(R.id.txtPassword1);
		txtPassword2  = (EditText)findViewById(R.id.txtPassword2);
		txtEmail  = (EditText)findViewById(R.id.txtEmail);
		txtPhone  = (EditText)findViewById(R.id.txtPhone);
		
		if (user != null)
		{
			txtUser.setText(user.getName());
			txtPhone.setText(user.getPhone());
			txtEmail.setText(user.getEmail());
		}
		else
		{    	   
			finish();
		}
		txtPassword1.addTextChangedListener(new TextWatcher() {
		    @Override
		    public void onTextChanged(CharSequence s, int start, int before, int count) {}
		    @Override
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
		    @Override
		    public void afterTextChanged(Editable s) {
		    	Log.e("addTextChangedListener","pwd1: " + txtPassword1.getText());
		    	Log.e("addTextChangedListener","pwd2: " + txtPassword2.getText());
		    	if(txtPassword1.getText().toString().equals(txtPassword2.getText().toString())){
		    		txtInvalid.setVisibility(View.GONE);
		    		txtInvalid.invalidate();
		    		if(s.toString().length() <=0)
		    			btnUpdate.setEnabled(false);
		    		else
		    			btnUpdate.setEnabled(true);
		    	}else{
		    		txtInvalid.setVisibility(View.VISIBLE);
		    		txtInvalid.invalidate();
		    		btnUpdate.setEnabled(false);
		    	}
		    }
		});
		txtPassword2.addTextChangedListener(new TextWatcher() {
		    @Override
		    public void onTextChanged(CharSequence s, int start, int before, int count) {}
		    @Override
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
		    @Override
		    public void afterTextChanged(Editable s) {
		    	Log.e("addTextChangedListener","pwd1: " + txtPassword1.getText());
		    	Log.e("addTextChangedListener","pwd2: " + txtPassword2.getText());
		    	if(txtPassword1.getText().toString().equals(txtPassword2.getText().toString())){
		    		txtInvalid.setVisibility(View.GONE);
		    		txtInvalid.invalidate();
		    		if(s.toString().length() <=0)
		    			btnUpdate.setEnabled(false);
		    		else
		    			btnUpdate.setEnabled(true);
		    	}else{
		    		txtInvalid.setVisibility(View.VISIBLE);
		    		txtInvalid.invalidate();
		    		btnUpdate.setEnabled(false);
		    	}
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
		private int mType;

		private GetData(Context context, int type) {
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
			FoodOrderRequest request = new FoodOrderRequest(EditUserInfoActivity.this);
			
			try {
				Log.e("updateUserInfo", "Request Started");
				result = request.updateUserInfo( 
						Integer.parseInt(user.getUserid())
						,user.getName()
						,txtPhone.getText().toString().trim()
						,txtEmail.getText().toString().trim()
						,txtPassword1.getText().toString()
						);
				Log.e("updateUserInfo", "Got result: " + result);
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
				Log.e("onPostExecute", "Failed, result is null or empty " );
				isFailed = true;
			}else{
				CommonModel comm = new Parse().CommonPares(result);
				if(comm == null || comm.getResult() == null || comm.getResult().length() <= 0){
					isFailed = true;
					Log.e("onPostExecute", "Failed, CommonModel is null or empty " );
				}else if(comm.getResult().equals("succ")){
					user.setEmail(txtEmail.getText().toString().trim());
					user.setPhone(txtPhone.getText().toString().trim());				
					user.setPwd(txtPassword1.getText().toString());
					ApplicationData.setUser(user);
					finish();
				}else{
					isFailed = true;
				}
			}
			if(isFailed){
				AlertDialog.Builder adbLogin = new AlertDialog.Builder( EditUserInfoActivity.this);
				adbLogin.setTitle("Network Error");
				adbLogin.setMessage("Error while access to server to update account information.");
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

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				gd = new GetData(EditUserInfoActivity.this, 1);
				gd.execute("");
				break;
			default:
				break;
			}
		}
	};
}
