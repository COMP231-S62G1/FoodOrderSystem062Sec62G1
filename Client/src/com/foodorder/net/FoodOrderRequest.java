package com.foodorder.net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.TimeoutException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.foodorder.beans.*;
import com.foodorder.view.OrderConfirmActivity;

public class FoodOrderRequest {
	
	private final String url = "http://husion.ca/comp231/Interface/";

	protected Context mContext;

	private BaseRequest baseRequest;

	public FoodOrderRequest(Context context) {
		baseRequest = new BaseRequest();
		mContext = context;
	}

	public String getUrl(String u, String a) {
		StringBuilder sb = new StringBuilder();
		sb.append(url).append(u).append("/").append(a);
		return sb.toString();
	}

	
	public String getRestList() throws IOException, TimeoutException {
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		return baseRequest.postRequestByHttpClient(strParams,
				getUrl("GetRestList.php", ""));
	}
	
	public String getMenuByRestId(String restid)
			throws IOException, TimeoutException {
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		if (!TextUtils.isEmpty(restid)) {
			strParams.add(new BasicNameValuePair("restid", restid));
		}
		return baseRequest.postRequestByHttpClient(strParams, getUrl("GetMenuByRest.php", ""));
	}
	
	//HashMap<String, String>
	//		itemId
	//		item Quantity
	public String createOrder(String username,String userid, HashMap<String, String> orderline)
			throws IOException, TimeoutException {
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		if (!TextUtils.isEmpty(username)) {
			strParams.add(new BasicNameValuePair("username", username));
		}
		if (!TextUtils.isEmpty(userid)) {
			strParams.add(new BasicNameValuePair("userid", userid));
		}
		
		if(!orderline.isEmpty())
		{
		     for (Entry<String, String> entry : orderline.entrySet())
		     {
		    	 strParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		     }
		}
//		if (!orderline.isEmpty()) {
//			for (HashMap<String, String> map : orderline)
//			{
//			}
//		}
		Log.e("strParams", ":" + strParams.toString());
		//Toast.makeText(mContext , strParams.toString(),  Toast.LENGTH_SHORT).show();
		return baseRequest.postRequestByHttpClient(strParams, getUrl("CreateOrder.php", ""));
	}
	
	public String checkStatus(String orderid) throws IOException, TimeoutException {
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		if (!TextUtils.isEmpty(orderid)) {
			strParams.add(new BasicNameValuePair("orderid", orderid));
		}else{
			return null;
		}
		return baseRequest.postRequestByHttpClient(strParams, getUrl("GetOrderStatus.php", ""));
	}
	
	public String getOrderDetail(String orderid) throws IOException, TimeoutException {
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		if (!TextUtils.isEmpty(orderid)) {
			strParams.add(new BasicNameValuePair("orderid", orderid));
		}else{
			return null;
		}
		Log.e("getOrderDetial", "param: " + strParams);
		Log.e("getOrderDetial", "url: " + getUrl("GetOrderDetail.php", ""));
		return baseRequest.postRequestByHttpClient(strParams, getUrl("GetOrderDetail.php", ""));
	}
	
	public String getReason(String orderid) throws IOException, TimeoutException{
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		if(!TextUtils.isEmpty(orderid)){
			strParams.add(new BasicNameValuePair("orderid", orderid));
		}else{
			return null;
		}
		Log.e("getReason", "param: " + strParams);
		Log.e("getReason", "url: " + getUrl("GetReason.php", ""));
		return baseRequest.postRequestByHttpClient(strParams, getUrl("GetReason.php", ""));
	}
	
	public String login(String name, String pwd) throws IOException, TimeoutException{
		ArrayList<NameValuePair> strParams = new ArrayList<NameValuePair>();
		if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(pwd)){
			strParams.add(new BasicNameValuePair("name", name));
			strParams.add(new BasicNameValuePair("pwd", pwd));
		}else{
			return null;
		}
		Log.e("getReason", "param: " + strParams);
		Log.e("getReason", "url: " + getUrl("login.php", ""));
		return baseRequest.postRequestByHttpClient(strParams, getUrl("login.php", ""));
	}
}
