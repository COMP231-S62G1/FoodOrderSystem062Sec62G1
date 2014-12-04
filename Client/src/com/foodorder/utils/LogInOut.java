package com.foodorder.utils;

import java.io.Serializable;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.foodorder.beans.ApplicationData;
import com.foodorder.client.R;
import com.foodorder.view.LoginActivity;
import com.foodorder.view.OrderDetail;
import com.foodorder.view.RegisterActivity;
import com.foodorder.view.RestListActivity;
import com.foodorder.view.ShoppingCartActivity;
import com.foodorder.view.UserInfoActivity;

public class LogInOut {
	public static void setLogin(boolean isLogin, Menu menu){
		if(menu != null){
			MenuItem itemLogin = menu.findItem(R.id.action_login);
			MenuItem itemLogout = menu.findItem(R.id.action_logout);
			MenuItem itemUserInfo = menu.findItem(R.id.action_userinfo);
			MenuItem itemRegister = menu.findItem(R.id.action_register);
			
			if(isLogin){
				itemLogout.setVisible(true);
				itemLogin.setVisible(false);
				itemUserInfo.setVisible(true);
				itemRegister.setVisible(false);
			}else{
				itemLogout.setVisible(false);
				itemLogin.setVisible(true);
				itemUserInfo.setVisible(false);
				itemRegister.setVisible(true);
			}
		}
	}
	
	public static void handleOptionItem(Context mCtx, MenuItem item){
		int id = item.getItemId();
		if(id == R.id.action_cart){
			Intent intentViewCart = new Intent(mCtx, ShoppingCartActivity.class);
			intentViewCart.putExtra("ViewCart", "View Cart Successful");
			mCtx.startActivity(intentViewCart);
		}else if (id == R.id.action_order){
			Intent intentViewOrder = new Intent(mCtx, OrderDetail.class);
			if(ApplicationData.arrOrderId.size()>0){
				intentViewOrder.putExtra("orderId", Integer.parseInt(ApplicationData.arrOrderId.get(0)));
			}
			mCtx.startActivity(intentViewOrder);
		}else if (id == R.id.action_logout) {
			ApplicationData.setUser(null);
			Intent intent = new Intent(mCtx, RestListActivity.class);
			intent.putExtra("restList", (Serializable)ApplicationData.getRestList());
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			ApplicationData.setOrderLineList(null);
			ApplicationData.setCartList(null);
			mCtx.startActivity(intent);
        }else if (id == R.id.action_login) {
			ApplicationData.setUser(null);
            Intent loginIntent = new Intent(mCtx, LoginActivity.class);
            mCtx.startActivity(loginIntent);
        }else if (id == R.id.action_userinfo) {
			Intent intentUser = new Intent(mCtx, UserInfoActivity.class);
			intentUser.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			mCtx.startActivity(intentUser);
		}else if (id == R.id.action_register) {
			Intent intentRegister = new Intent(mCtx, RegisterActivity.class);
			intentRegister.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			mCtx.startActivity(intentRegister);
		}
	}
}
