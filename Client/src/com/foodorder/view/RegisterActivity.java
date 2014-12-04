package com.foodorder.view;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.foodorder.client.R;
import com.foodorder.net.FoodOrderRequest;

public class RegisterActivity extends Activity {

	private DialogActivity dialog;
	private Button btnReg;
	private EditText username;
	private EditText password;
	private EditText phone;
	private EditText email;
	private SetData sd;
	
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
		super.onDestroy();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		username = (EditText) findViewById(R.id.editText2);
		password = (EditText) findViewById(R.id.editText3);
		phone = (EditText) findViewById(R.id.editText5);
		email = (EditText) findViewById(R.id.editText7);
		this.btnReg = (Button) findViewById(R.id.button1);
		this.btnReg.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				handler.sendEmptyMessage(0);
			}
		});

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
				result = request.getRegistration(username.getText().toString(),
						password.getText().toString(), phone.getText()
								.toString(), email.getText().toString());
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

			if (result == null || result.equals("")) {
				handler.sendEmptyMessage(3);
			} else {
				finish();
			}
		}

	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			new SetData(RegisterActivity.this, 1).execute("");
			
		}
	};
}
