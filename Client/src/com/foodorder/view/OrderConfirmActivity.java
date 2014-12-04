package com.foodorder.view;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.foodorder.beans.AppConstants;
import com.foodorder.beans.ApplicationData;
import com.foodorder.beans.CommonModel;
import com.foodorder.beans.FoodListsViewImage;
import com.foodorder.beans.MenuModel;
import com.foodorder.client.R;
import com.foodorder.net.FoodOrderRequest;
import com.foodorder.net.Parse;
import com.foodorder.services.UpdateOrderStatus;
import com.foodorder.utils.AmountCalculator;

public class OrderConfirmActivity extends Activity {

	private DialogActivity dialog;
	private ArrayList<MenuModel> menuList;
	private HashMap<String, String> orderLineList;
	private MyBaseAdapter myBaseAdapter;
	static String pathString = AppConstants.path;
	private ListView orderListView;
	private Intent intentViewOrder;
	private Bundle b;
	private SendData sd;
	
	@Override
	protected void onResume(){
		Log.e("OrderConfirm", "onResume");
		super.onResume();
	}
	
	@Override
	protected void onStop(){
		Log.e("OrderConfirm", "onStop");
		super.onStop();
	}
	
	@Override
	protected void onDestroy (){
		Log.e("OrderConfirm", "onDestroy");
		if(null != sd)
			sd.cancel(true);
		super.onDestroy();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.e("OrderConfirm", "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirm);
		setTitle("Order Confirmation");

		intentViewOrder = getIntent();
		b = intentViewOrder.getExtras();
		b.get("OrderConfirm");         

		orderListView = (ListView) findViewById(R.id.orderlist);
		menuList = ApplicationData.getCartList();
		orderLineList=ApplicationData.getOrderLine();
		myBaseAdapter = new MyBaseAdapter();
		orderListView.setAdapter(myBaseAdapter);
		
		TextView txtNet = (TextView) findViewById(R.id.txtNetAmt);
		TextView txtHst = (TextView) findViewById(R.id.txtHstAmt);
		TextView txtTotal = (TextView) findViewById(R.id.txtTotalAmt);
		
		double fNet=0;
		double fHst=0;
		double fGross=0;
		
		fNet = AmountCalculator.getNetAmount(menuList, orderLineList);
		fHst = AmountCalculator.getHstAmount(fNet);
		fGross = AmountCalculator.getGrossAmount(fNet);
		
		
		txtNet.setText( AmountCalculator.getAmountString(fNet));
		txtHst.setText( AmountCalculator.getAmountString(fHst));
		txtTotal.setText( AmountCalculator.getAmountString(fGross));

	}


	class MyBaseAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return menuList.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@SuppressLint("ViewHolder")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (menuList == null) {
				return convertView;
			}
			if (orderLineList == null) {
				return convertView;
			}
			
			@SuppressWarnings("static-access")
			final View view = convertView.inflate(OrderConfirmActivity.this, R.layout.sub_order_item, null);
			Object menuObject = menuList.get(position);

			
			ImageView order_item_image = (ImageView) view.findViewById(R.id.itemimg);
			TextView order_item_title = (TextView) view.findViewById(R.id.itemname);
			TextView order_item_qty = (TextView) view.findViewById(R.id.itemqty);
			
			if (menuObject instanceof MenuModel) {
				final MenuModel aMenuItem = (MenuModel) menuObject;
				order_item_title.setText("Name: " + aMenuItem.getName() + "\n"
						+ "Description: " + aMenuItem.getDes());

				order_item_image.setTag(aMenuItem.getPic());
				if (menuList.get(position).getPic() != null
						&& !menuList.get(position).getPic().equals("")) {
					try {
						new FoodListsViewImage(OrderConfirmActivity.this)
						.loadingImage(menuList.get(position).getPic(),
								order_item_image, R.drawable.computer, orderListView);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					order_item_image.setImageResource(R.drawable.computer);
				}
				
			} 
			else {
			}
			
			Set<String> keys = orderLineList.keySet();
			String itemId = null;
			MenuModel aModel = menuList.get(position);

			for(String key: keys){
				if(aModel.getMenuid()== key)
					itemId=key;
			}
			if(itemId!=null){
				//String quantityString = orderObject.get(itemId);
				order_item_qty.setText("Qty: " +orderLineList.get(itemId));		
			}
			return view;
		}
	}


	public void cancelOrder(View view) {
		finish();	
	}

	public void submitOrder(View view) {
		
		new SendData(OrderConfirmActivity.this,0).execute("");

	}

	
	private class SendData extends AsyncTask<String, String, String>{
		//private Context mContext;
		private int mType;
		

		private SendData(Context context, int type) {
			//this.mContext = context;
			this.mType = type;
			dialog = new DialogActivity(context, type);
		}
		
		@Override
		protected void onPreExecute() {
			if (mType == 1) {
				if (null != dialog && !dialog.isShowing()) {
					dialog.show();
				}
			}
			super.onPreExecute();
		}
		
		@Override
		protected String doInBackground(String... params) {
			String result = null;
			FoodOrderRequest request = new FoodOrderRequest(OrderConfirmActivity.this);
			
			try {
				result = request.createOrder("Alex","0001",orderLineList);
				Log.e("OrderConfirmActivity", "request result is "+ result);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TimeoutException e) {
				e.printStackTrace();
			}
			
			return result;
		}
		
		@Override
		protected void onPostExecute(String result) {
			if (null != dialog) {
				dialog.dismiss();
			}
			
			if (result == null || result.equals("")) {
				handler.sendEmptyMessage(3);
			} else {
				Log.e("OrderConfirmActivity", "result is "+ result);
				CommonModel commonResult = new Parse().CommonPares(result);
				String orderId = commonResult.getResult();
				
				// Start service to check order status update
				
				Intent myIntent = new Intent(OrderConfirmActivity.this,UpdateOrderStatus.class);
				Log.e("OrderConfirmActivity", "order id is "+orderId);
				myIntent.putExtra("NEW_MENU", orderId);
				startService(myIntent);			
				
				AlertDialog.Builder adbSubmit = new AlertDialog.Builder( OrderConfirmActivity.this);
				adbSubmit.setTitle("Order Submition");
				adbSubmit.setMessage("Your order has been submitted.?");
				
				adbSubmit.setPositiveButton("Ok",
						new AlertDialog.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								Intent intent = new Intent(OrderConfirmActivity.this,RestListActivity.class);
								intent.putExtra("restList", (Serializable)ApplicationData.getRestList());
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								ApplicationData.setOrderLineList(null);
								ApplicationData.setCartList(null);
								startActivity(intent);
							}
						});
				adbSubmit.setIcon(R.drawable.ic_launcher);
				adbSubmit.show();
				
				
			}
		}
		
		@SuppressLint("HandlerLeak")
		private Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case 0:
					sd = new SendData(OrderConfirmActivity.this, 1);
					sd.execute("");
					break;
				default:
					break;
				}
			}
		};
	}

}
