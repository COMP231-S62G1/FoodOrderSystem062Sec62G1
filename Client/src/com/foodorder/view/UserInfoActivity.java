package com.foodorder.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import com.foodorder.beans.ApplicationData;
import com.foodorder.client.R;

public class UserInfoActivity extends Activity {
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);   
    }
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_info, menu);
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
			/*ApplicationData.setUser(null);
            Intent loginIntent = new Intent(this, LoginActivity.class);
            loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(loginIntent);*/
			comfirmlogout();
            return true;
        }
		return super.onOptionsItemSelected(item);
	}
	
	public void comfirmlogout() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(false);
		builder.setMessage("Do you want to logout?");
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// if user pressed "OK", then he is allowed to exit from
				// application
				ApplicationData.setUser(null);
				Intent loginIntent = new Intent(UserInfoActivity.this,
						LoginActivity.class);
				loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				finish();
				startActivity(loginIntent);
			}
		});
		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// if user select"Cancel", just cancel this dialog and
						// continue with app
						dialog.cancel();

					}
				});
		AlertDialog exitAlertDialog = builder.create();
		exitAlertDialog.show();
	}

}
