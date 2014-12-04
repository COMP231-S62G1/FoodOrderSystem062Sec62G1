package com.foodorder.utils;

import android.view.Menu;
import android.view.MenuItem;

import com.foodorder.client.R;

public class LogInOut {
	public static void setLogin(boolean isLogin, Menu menu){
		if(menu != null){
			MenuItem itemLogin = menu.findItem(R.id.action_login);
			MenuItem itemLogout = menu.findItem(R.id.action_logout);
			MenuItem itemUserInfo = menu.findItem(R.id.action_userinfo);
			
			if(isLogin){
				itemLogout.setVisible(true);
				itemLogin.setVisible(false);
				itemUserInfo.setVisible(true);
			}else{
				itemLogout.setVisible(false);
				itemLogin.setVisible(true);
				itemUserInfo.setVisible(false);
			}
		}
	}
}
