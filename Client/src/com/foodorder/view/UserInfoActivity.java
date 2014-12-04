package com.foodorder.view;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.foodorder.beans.ApplicationData;
import com.foodorder.beans.UserInfo;
import com.foodorder.client.R;

public class UserInfoActivity extends Activity {
	
	private Button btnEdit;
	private TextView username;
	private TextView phone;
	private TextView email;
	private TextView balance;
	private UserInfo user;
	private double aBalance;
	private TextView error;
	//private Intent getUpdate;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);   
        
       user = ApplicationData.getUser();
       aBalance = ApplicationData.getBalance();
       
       username = (TextView) findViewById(R.id.info1);
       phone = (TextView) findViewById(R.id.info2);
       email = (TextView) findViewById(R.id.info3);
       balance = (TextView) findViewById(R.id.info4);
       error = (TextView) findViewById(R.id.textView9);
       
       if (user != null)
       {
	       username.setText(user.getName());
	       phone.setText(user.getPhone());
	       email.setText(user.getEmail());
	       balance.setText("$ " + String.valueOf(aBalance));
       }
       else
       {    	   
    	   error.setText("Please login");
       }
        
        btnEdit = (Button) findViewById(R.id.edit3);
		btnEdit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intentEdit = new Intent(UserInfoActivity.this,
						EditUserInfoActivity.class);
//				intentEdit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//						| Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intentEdit);		
			}
		});
		/*
		getUpdate = getIntent();
		Bundle b = getUpdate.getExtras();
		b.get("ViewUpdate");
		*/
    }
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rest_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		} else if(id == R.id.action_cart){
			Intent intentViewCart = new Intent(UserInfoActivity.this,
					ShoppingCartActivity.class);
			intentViewCart.putExtra("ViewCart", "View Cart Successful");
			startActivity(intentViewCart);
		}else if (id == R.id.action_order) {
			Intent intentViewOrder = new Intent(UserInfoActivity.this,
					OrderDetail.class);
			if (ApplicationData.arrOrderId.size() > 0) {
				intentViewOrder.putExtra("orderId",
						Integer.parseInt(ApplicationData.arrOrderId.get(0)));
			}
			startActivity(intentViewOrder);
		}else if (id == R.id.action_logout) {
			ApplicationData.setUser(null);
            Intent loginIntent = new Intent(this, LoginActivity.class);
            loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(loginIntent);
            return true;
        }
		return super.onOptionsItemSelected(item);
	}
}
