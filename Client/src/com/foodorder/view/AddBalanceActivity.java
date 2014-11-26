package com.foodorder.view;

import javax.security.auth.PrivateCredentialPermission;

import android.app.Activity;
import android.content.Intent;
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
import com.foodorder.client.R;
import com.foodorder.view.ShoppingCartActivity.MyBaseAdapter;

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
    }
}
