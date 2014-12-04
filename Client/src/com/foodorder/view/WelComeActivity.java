package com.foodorder.view;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;

import com.foodorder.client.R;
import com.foodorder.net.FoodOrderRequest;
import com.foodorder.net.Parse;
import com.google.gson.JsonSyntaxException;
import com.foodorder.beans.Rest;


public class WelComeActivity extends Activity {

	private ArrayList<Rest> restList;
	private DialogActivity dialog;
	private GetData gd;

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				startActivity(new Intent(WelComeActivity.this, RestListActivity.class));
				WelComeActivity.this.finish();
				break;
			case 2:
			case 3:
				Log.e("WelcomeActivity", "GetData start");
				gd = new GetData(WelComeActivity.this, 1);
				gd.execute("");
				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		Message msg = new Message();
		msg.what = 2;
		handler.sendMessageDelayed(msg, 500);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			handler.removeMessages(1);
			handler.removeMessages(2);
			handler.removeMessages(3);
			if(gd != null){
				gd.cancel(true);
			}
			openConfirmDialog();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void openConfirmDialog() {
		final Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.quit);
		builder.setIcon(R.drawable.ic_launcher);
		builder.setMessage(R.string.exit);

		builder.setNeutralButton(R.string.cancel,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						builder.create().dismiss();
						startActivity(new Intent(WelComeActivity.this, RestListActivity.class));
						WelComeActivity.this.finish();
					}
				});

		builder.setPositiveButton(R.string.confirm,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						builder.create().dismiss();
						WelComeActivity.this.finish();
					}
				});
		builder.show();
	}

	private class GetData extends AsyncTask<String, String, String> {
		private Context mContext;
		private int mType;

		private GetData(Context context, int type) {
			this.mContext = context;
			this.mType = type;
			//myDialog = new LoadingDiagActivity(context);
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
			//myDialog.show();
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String result = null;
			FoodOrderRequest request = new FoodOrderRequest(WelComeActivity.this);

			try {
				Log.e("Welcome Activity", "request started");
				result = request.getRestList();
				Log.e("Welcome Activity", "request done :" +result);
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
			Log.e("Welcome Activity", "onPostExecute: "+result);
			if (null != dialog ) {
				dialog.dismiss();
			}

			if (result == null || result.equals("")) {
				Message msg = new Message();
				msg.what = 2;
				Log.e("Welcome Activity", "onPostExecute: result is null");
				handler.sendMessageDelayed(msg, 200);
			} else {

				restList = new ArrayList<Rest>();
				try {
					restList = new Parse().getRestList(result);
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
					Log.e("Welcome Activity", "onPostExecute: failed to parse result");
				} finally{
					if (restList != null) {
						Intent intent = new Intent(WelComeActivity.this,
								RestListActivity.class);
						intent.putExtra("restList", (Serializable) restList);
						startActivity(intent);
						WelComeActivity.this.finish();
					} else {
						Log.e("Welcome Activity", "onPostExecute: restart request");
						Message msg = new Message();
						msg.what = 2;
						handler.sendMessageDelayed(msg, 500);
					}
				}
					
			}
		}
	}
}
