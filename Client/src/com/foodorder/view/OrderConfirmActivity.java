package com.foodorder.view;

import java.io.IOException;
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
import android.os.Bundle;
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
import com.foodorder.beans.FoodListsViewImage;
import com.foodorder.beans.MenuModel;
import com.foodorder.beans.OrderLine;
import com.foodorder.client.R;
import com.foodorder.net.FoodOrderRequest;
import com.foodorder.view.MenuListActivity.MyBaseAdapter;

public class OrderConfirmActivity extends Activity {

	private DialogActivity dialog;
	private ArrayList<MenuModel> menuList;
	private ArrayList<HashMap<String, String>> orderLineList;
	private MyBaseAdapter myBaseAdapter;
	static String pathString = AppConstants.path;
	//private int orderId;
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
			
			HashMap<String, String> orderObject = orderLineList.get(position);
			Set<String> keys = orderObject.keySet();
			String itemId = null;
			for(String key: keys){
				itemId= key;
			}
			//String quantityString = orderObject.get(itemId);
			order_item_qty.setText("Qty: " +orderObject.get(itemId));		
			
			return view;
		}
	}


	public void cancelOrder(View view) {
		Intent returnMain = new Intent(this, ShoppingCartActivity.class);
		returnMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(returnMain);
	}

	public void submitOrder(View view) {

		String result = null;

		FoodOrderRequest request = new FoodOrderRequest(
				OrderConfirmActivity.this);

		try {
			result = request.createOrder("Alex","0001",orderLineList);

		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}

	}

}
