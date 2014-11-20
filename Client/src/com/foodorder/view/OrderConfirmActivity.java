package com.foodorder.view;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import android.R.string;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.foodorder.beans.AppConstants;
import com.foodorder.beans.ApplicationData;
import com.foodorder.beans.CommonModel;
import com.foodorder.beans.FoodListsViewImage;
import com.foodorder.beans.MenuModel;
import com.foodorder.beans.OrderLine;
import com.foodorder.client.R;
import com.foodorder.net.FoodOrderRequest;
import com.foodorder.net.Parse;
import com.foodorder.services.UpdateOrderStatus;
import com.foodorder.utils.AmountCalculator;
import com.foodorder.view.MenuListActivity.MyBaseAdapter;
import com.google.gson.JsonSyntaxException;

public class OrderConfirmActivity extends Activity {

	private DialogActivity dialog;
	private ArrayList<MenuModel> menuList;
	private HashMap<String, String> orderLineList;
	private MyBaseAdapter myBaseAdapter;
	static String pathString = AppConstants.path;
	private ListView orderListView;
	private Intent intentViewOrder;
	private Button btnCancel;
	private Button btnSubmit;
	private Bundle b;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
			// TODO Auto-generated method stub
			return menuList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (menuList == null) {
				return convertView;
			}
			if (orderLineList == null) {
				return convertView;
			}
			
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
						// TODO Auto-generated catch block
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
		//Intent returnMain = new Intent(this, ShoppingCartActivity.class);
		//returnMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		//startActivity(returnMain);
		
		Intent returnMain = new Intent(OrderConfirmActivity.this,ShoppingCartActivity.class);
		//intentViewCart.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		returnMain.putExtra("ViewCart","View Cart Successful");
		startActivity(returnMain);	
	}

	public void submitOrder(View view) {
		
		new SendData(OrderConfirmActivity.this,0).execute("");

	}

	
	private class SendData extends AsyncTask<String, String, String>{
		private Context mContext;
		private int mType;

		private SendData(Context context, int type) {
			this.mContext = context;
			this.mType = type;
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			if (mType == 0) {
				if (null != dialog && !dialog.isShowing()) {
					dialog.show();
				}
			}
			super.onPreExecute();
		}
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String result = null;
			FoodOrderRequest request = new FoodOrderRequest(OrderConfirmActivity.this);
			
			try {
				result = request.createOrder("Alex","0001",orderLineList);
				Log.e("OrderConfirmActivity", "request result is "+ result);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return result;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			if (null != dialog && dialog.isShowing()) {
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
				
				
				AlertDialog alertDialog1=new AlertDialog.Builder(OrderConfirmActivity.this).create();
				
				//Setting Dialog Title
				alertDialog1.setTitle("Order Submition");
				
				//Setting Dialog Message
				alertDialog1.setMessage("Your order has been submitted.");
				
				//Setting Icon to Dialog
				alertDialog1.setIcon(R.drawable.ic_launcher);
				
				//Setting OK Button
				alertDialog1.setButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						//Start service to check order status
						
						//Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(OrderConfirmActivity.this,MenuListActivity.class);
						intent.putExtra("menuList", (Serializable)menuList);
						startActivity(intent);			
					}
				}); 
					
				//Showing Alert Message
				alertDialog1.show();
			}
		}
		
		private Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch (msg.what) {
				case 0:
					new SendData(OrderConfirmActivity.this, 1).execute("");
					break;
				default:
					break;
				}
			}
		};
	}

}
