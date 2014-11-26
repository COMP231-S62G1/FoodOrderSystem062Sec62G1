package com.foodorder.view;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import javax.security.auth.PrivateCredentialPermission;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.foodorder.beans.ApplicationData;
import com.foodorder.beans.CommonModel;
import com.foodorder.beans.UserInfo;
import com.foodorder.client.R;
import com.foodorder.net.FoodOrderRequest;
import com.foodorder.net.Parse;
import com.foodorder.view.ShoppingCartActivity.MyBaseAdapter;
import com.google.gson.JsonSyntaxException;

public class AddBalanceActivity extends Activity {
	
	private TextView txtBalance;
	private EditText txtCashcard;
	private Button btnRefill, btnBack;
	private Intent intentViewBalance;
	private Bundle b;
	double currentBalance;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_balance);   
        
        setTitle("Review Account Balance");

		intentViewBalance = getIntent();
		b = intentViewBalance.getExtras();
		b.get("ViewBalance");
		
		txtBalance=(TextView) findViewById(R.id.txtBalance);
		
		currentBalance = ApplicationData.getBalance();
		txtBalance.setText(String.valueOf(currentBalance));
		
		new GetData(this, ApplicationData.getUser().getUserid()).execute("");
    }
	
	private class GetData extends AsyncTask<String, String, String> {
		private Context mContext;
		private String userid;
		private String balance;
		
		private AlertDialog alertDialog1;

		private GetData(Context context, String userid) {
			this.mContext = context;
			this.userid = userid;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			String result = null;
			FoodOrderRequest request = new FoodOrderRequest(AddBalanceActivity.this);

			try {
				result = request.getBalance(result);
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
					CommonModel comm = new Parse().CommonPares(result);
					if(comm == null || comm.getResult() == null || comm.getResult().length() <= 0){
						alertDialog1=new AlertDialog.Builder(mContext).create();
						isFailed = true;
					}else{
						balance =comm.getResult();
					}
					
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
					alertDialog1=new AlertDialog.Builder(mContext).create();
					isFailed = true;
				} catch (Exception e) {
					e.printStackTrace();
					alertDialog1=new AlertDialog.Builder(mContext).create();
					isFailed = true;
				} 
				finally{
					if (balance != null) {
						ApplicationData.setBalance(Double.parseDouble(balance));
						txtBalance.setText("$ " + balance);
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
				                        // TODO do something when failed to retrieve new amount from server
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
