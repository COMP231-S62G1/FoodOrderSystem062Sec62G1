package com.foodorder.view;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;

import com.foodorder.beans.MenuModel;
import com.foodorder.client.R;
import com.foodorder.net.FoodOrderRequest;
import com.foodorder.net.Parse;
import com.google.gson.JsonSyntaxException;

public class RegisterActivity extends Activity {

	private DialogActivity dialog;
	private Button btnReg;
	private EditText username;
	private EditText password;
	private EditText phone;
	private EditText email;

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

	private class GetData extends AsyncTask<String, String, String> {
		private Context mContext;
		private int mType;

		private GetData(Context context, int type) {
			this.mContext = context;
			this.mType = type;
			dialog = new DialogActivity(context, type);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			if (mType == 1) {
				if (null != dialog && !dialog.isShowing()) {
					dialog.show();
				}
			}
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String result = null;
			FoodOrderRequest request = new FoodOrderRequest(
					RegisterActivity.this);

			try {
				result = request.getRegistration(username.getText().toString(),
						password.getText().toString(), phone.getText()
								.toString(), email.getText().toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			if (null != dialog && dialog.isShowing()) {
				dialog.dismiss();
			}

			if (result == null || result.equals("")) {
				handler.sendEmptyMessage(3);
			} else {
				// alert and goto login page

				
				Intent intentMenu = new Intent(RegisterActivity.this,
						LoginActivity.class);
				intentMenu.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_SINGLE_TOP);
				intentMenu.putExtra("Viewregister", "Registration Successful");
				startActivity(intentMenu);
			}
		}

	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				new GetData(RegisterActivity.this, 1).execute("");
				break;
			default:
				break;
			}
		}
	};
}
