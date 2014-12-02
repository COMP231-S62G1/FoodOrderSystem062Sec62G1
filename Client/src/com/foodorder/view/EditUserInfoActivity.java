package com.foodorder.view;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.TextView;

import com.foodorder.beans.ApplicationData;
import com.foodorder.beans.UserInfo;
import com.foodorder.client.R;
import com.foodorder.net.FoodOrderRequest;

public class EditUserInfoActivity extends Activity {
	private DialogActivity dialog;
	private Button btnSave;
	private TextView username;
	private EditText phone;
	private EditText email;
	private UserInfo user;
	private TextView error;
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);   
        
        user = ApplicationData.getUser();        
        
        username = (TextView) findViewById(R.id.editText2);
        phone = (EditText) findViewById(R.id.editText5);
        email = (EditText) findViewById(R.id.editText7);
        error = (TextView) findViewById(R.id.textView9);
        
        if (user != null)
        {
 	       username.setText(user.getName());
 	       phone.setText(user.getPhone());
 	       email.setText(user.getEmail());
        }
        else
        {    	   
     	   error.setText("Please login");
        }
        
        this.btnSave = (Button) findViewById(R.id.button1);
		this.btnSave.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//save function
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
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			if (mType == 0) {
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
					EditUserInfoActivity.this);

			try {
				result = request.updateUserInfo(phone.getText()
								.toString(), email.getText().toString(),
								Integer.parseInt(ApplicationData.getUser().getUserid()));
				
				user.setEmail(email.getText().toString());
				user.setPhone(phone.getText().toString());				
				
				ApplicationData.setUser(user);
				
				
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
				Intent intentMenu = new Intent(EditUserInfoActivity.this,
						UserInfoActivity.class);
				intentMenu.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_SINGLE_TOP);
				intentMenu.putExtra("Viewregister", "Update Successful");
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
				new GetData(EditUserInfoActivity.this, 1).execute("");
				break;
			default:
				break;
			}
		}
	};
}
