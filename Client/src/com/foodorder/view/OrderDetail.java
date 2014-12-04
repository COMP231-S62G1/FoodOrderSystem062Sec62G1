package com.foodorder.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import com.foodorder.beans.AppConstants;
import com.foodorder.beans.ApplicationData;
import com.foodorder.beans.FoodListsViewImage;
import com.foodorder.beans.MenuModel;
import com.foodorder.beans.OrderLine;
import com.foodorder.client.R;
import com.foodorder.net.FoodOrderRequest;
import com.foodorder.net.Parse;
import com.foodorder.utils.LogInOut;
import com.google.gson.JsonSyntaxException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class OrderDetail extends Activity {
	private ArrayList<MenuModel> menuList;
	private ArrayList<OrderLine> orderLine;
	private MyBaseAdapter myBaseAdapter;
	static String pathString = AppConstants.path;
	private ListView orderListView;
	//private Button btnCancel;
	private int orderId;
	private int orderStatus;
	//private int requestType =0;
	private Menu menu;
	//private DialogActivity dialog;
	
	private TextView txtTitle;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_detail);
		setTitle("Your order status");
		
		Bundle extras = getIntent().getExtras();
		txtTitle = (TextView)findViewById(R.id.textView1);
		
		if(extras == null){
			txtTitle.setText("No stored order yet");
			return;
		}
		
		int id = extras.getInt("notiId");
		orderId = extras.getInt("orderId");
		orderStatus = extras.getInt("orderStatus");
		
		
		
		if(orderId <= 0){
			txtTitle.setText("No stored order yet");
			return;
		}
		
		if(orderStatus > 0){
			txtTitle.setText(txtTitle.getText().toString() + " " + getStatusString(orderStatus));
			if(orderStatus == 3){
				//new GetReason(OrderDetail.this).execute("");
				handler.sendEmptyMessage(2);
			}
		}else{
			new GetData(OrderDetail.this, 1).execute("");
		}
		

		NotificationManager myNotificationManager =
				(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		// remove the notification with the specific id
		myNotificationManager.cancel(id);
		
		orderListView = (ListView) findViewById(R.id.orderlist);
		menuList = ApplicationData.getCartList();
		//orderLine = 
		
		myBaseAdapter = new MyBaseAdapter();
		orderListView.setAdapter(myBaseAdapter);
		
		new GetData(OrderDetail.this, 1).execute("");
	}

	private String getStatusString(int statusId){
		switch(statusId){
			case 0:
				return "submitted";
			case 1:
				return "under processing";
			case 2:
				return "completed";
			case 3:
				return "rejected";
			default:
				return "unkonwn";
				
		}
	}
	
	/*
	private class GetReason extends AsyncTask<String, String, String> {
		//private Context mContext;
		
		private GetReason(Context context) {
			//this.mContext = context;
		}
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		@Override
		protected String doInBackground(String... params) {
			String result = null;
			FoodOrderRequest request = new FoodOrderRequest(OrderDetail.this);
			
			try {
				result = request.getReason(Integer.toString(orderId));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TimeoutException e) {
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result == null || result.equals("")) {
				handler.sendEmptyMessage(3);
			} else {
				txtTitle.setText(txtTitle.getText().toString() + "\nDue to " + result);
				txtTitle.invalidate();
			}
		}
	}
	*/
	
	/*
	private class GetStatus extends AsyncTask<String, String, String> {
		private Context mContext;
		//private int mType;

		private GetStatus(Context context, int type) {
			this.mContext = context;
			//this.mType = type;
			dialog = new DialogActivity(context, type);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		@Override
		protected String doInBackground(String... params) {
			String result = null;
			FoodOrderRequest request = new FoodOrderRequest(OrderDetail.this);
			
			try {
				result = request.checkStatus(Integer.toString(orderId));

			} catch (IOException e) {
				e.printStackTrace();
			} catch (TimeoutException e) {
				e.printStackTrace();
			}
			
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			if (null != dialog ) {
				dialog.dismiss();
			}
			if (result == null || result.equals("")) {
				handler.sendEmptyMessage(3);
			} else {
				CommonModel resultCom = new Parse().CommonPares(result);
				String status =  resultCom.getResult();
				int orderStatus = Integer.parseInt(status);
				txtTitle.setText(txtTitle.getText().toString() + " " + getStatusString(orderStatus));
				txtTitle.invalidate();
				if(orderStatus == 3){
					new GetReason(mContext).execute("");;
				}
			}
		}
	}
	*/
	
	private class GetData extends AsyncTask<String, String, String> {
		//private Context mContext;
		private int mType;

		private GetData(Context context, int type) {
			//this.mContext = context;
			this.mType = type;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		@Override
		protected String doInBackground(String... params) {
			String result = null;
			FoodOrderRequest request = new FoodOrderRequest(OrderDetail.this);
			
			try {
				if(this.mType==1)
				{
				result = request.getOrderDetail(String.format("%d",orderId));
				}
				else if(this.mType == 2)
				{
					result = request.getReason(Integer.toString(orderId));

				}
				else if (this.mType ==3)
				{
					result = request.checkStatus(Integer.toString(orderId));

				}
				Log.e("doInBackground", "result: " + result);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TimeoutException e) {
				e.printStackTrace();
			}
			
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			
			if (result == null || result.equals("")) {
				handler.sendEmptyMessage(3);
			} else {

				if(this.mType == 0)
				{
					
				}
				else if(this.mType == 1)
				{}
				//orderLine = new ArrayList<OrderLine>();
				try {
					orderLine = new Parse().GetOrderLine(result);
					for(OrderLine aLine : orderLine){
						Log.e("onPost", "Menu ID " + aLine.getMenuid());
						Log.e("onPost", "Qty " + aLine.getQty());
					}
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				}
				if (orderLine != null) {
					myBaseAdapter.notifyDataSetChanged();
					//finish();
				} else {
					handler.sendEmptyMessage(1);
				}
			}
		}
	}
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				new GetData(OrderDetail.this, 1).execute("");
				break;
			case 1:
				new GetData(OrderDetail.this, 2).execute("");
				break;
			case 2:
				new GetData(OrderDetail.this, 3).execute("");
				break;
			default:
				break;
			}
		}
	};
	
	
	
	class MyBaseAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			if(orderLine == null)
				return 0;
			Log.w("getCount()", "size of array is:" +orderLine.size());
			return orderLine.size();
		}

		@Override
		public Object getItem(int position) {
			if(orderLine == null)
				return null;
			Log.w("getItem()", "Requested position is :" +position);
			return orderLine.get(position);
		}

		@Override
		public long getItemId(int position) {
			if(orderLine == null)
				return 0;
			Log.w("getItemId()", "Requested position is :" +position);
			Log.w("getItemId()", "And its menu ID is  :" + orderLine.get(position).getMenuid());
			return Long.parseLong(orderLine.get(position).getMenuid()) ;
		}

		@SuppressLint("ViewHolder")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (menuList == null) {
				return convertView;
			}
			if (orderLine == null) {
				return convertView;
			}
			
			@SuppressWarnings("static-access")
			final View view = convertView.inflate(OrderDetail.this, R.layout.sub_order_item, null);
			
			ImageView order_item_image = (ImageView) view.findViewById(R.id.itemimg);
			TextView order_item_title = (TextView) view.findViewById(R.id.itemname);
			TextView order_item_qty = (TextView) view.findViewById(R.id.itemqty);
			
			for(MenuModel aMenuItem : menuList){
				if(Integer.parseInt(aMenuItem.getMenuid()) == getItemId(position)){
					order_item_title.setText("Name: " + aMenuItem.getName() + "\n"
							+ "Description: " + aMenuItem.getDes());
					order_item_image.setTag(aMenuItem.getPic());
					if (menuList.get(position).getPic() != null
							&& !menuList.get(position).getPic().equals("")) {
						try {
							new FoodListsViewImage(OrderDetail.this)
							.loadingImage(menuList.get(position).getPic(),
									order_item_image, R.drawable.computer, orderListView);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						order_item_image.setImageResource(R.drawable.computer);
					}
					break;
				}
			}
			
			order_item_qty.setText("Qty: " +  orderLine.get(position).getQty() );		
			
			return view;
		}
	}
	
	public void closeThis(View view){
		finish();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Log.e("OrderDetail", "onResume()");
		if(ApplicationData.getUser()!=null){
			LogInOut.setLogin(true, menu);
		}else{
			LogInOut.setLogin(false, menu);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.menu = menu;
		getMenuInflater().inflate(R.menu.rest_list, menu);
		if(ApplicationData.getUser()!=null){
			LogInOut.setLogin(true, menu);
		}else{
			LogInOut.setLogin(false, menu);
		}
		menu.findItem(R.id.action_order).setVisible(false);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		LogInOut.handleOptionItem(this, item);
		return super.onOptionsItemSelected(item);
	}
}
