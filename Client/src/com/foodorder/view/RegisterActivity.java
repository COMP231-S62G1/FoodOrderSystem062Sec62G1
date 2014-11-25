package com.foodorder.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import com.foodorder.client.R;

public class RegisterActivity extends Activity {
	
	private Button btnReg;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);   
        
        this.btnReg = (Button) findViewById(R.id.button1);
		this.btnReg.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				
				
					Intent intentMenu = new Intent(RegisterActivity.this,
											LoginActivity.class);
					intentMenu.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
					intentMenu.putExtra("Viewregister","Registration Successful");
					startActivity(intentMenu);

								}
							});
        
    }
			}
