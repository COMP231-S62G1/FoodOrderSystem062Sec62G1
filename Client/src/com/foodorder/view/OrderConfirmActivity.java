package com.foodorder.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeoutException;

import android.R.string;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.foodorder.beans.AppConstants;
import com.foodorder.beans.OrderLine;
import com.foodorder.client.R;
import com.foodorder.net.FoodOrderRequest;

public class OrderConfirmActivity extends Activity {

	private DialogActivity dialog;
	private ArrayList<OrderLine> orderList;
	private MyBaseAdapter myBaseAdapter;
	static String pathString = AppConstants.path;
	private int orderId;
	private ListView orderListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirm);
		setTitle("Order Confirmation");

		orderListView = (ListView) findViewById(R.id.orderlist);
		myBaseAdapter = new MyBaseAdapter(this,orderList);
		orderListView.setAdapter(myBaseAdapter);

	}

	class MyBaseAdapter extends BaseAdapter {

		Context context;
		protected List<OrderLine> orderLines;
		LayoutInflater inflater;

		public MyBaseAdapter(Context context, List<OrderLine> orderLines) {
			this.orderLines = orderLines;
			this.inflater = LayoutInflater.from(context);
			this.context = context;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return orderLines.size();
		}

		@Override
		public OrderLine getItem(int position) {
			// TODO Auto-generated method stub
			// return position;
			return orderLines.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		private class ViewHolder {
			TextView txtName;
			TextView txtNumber;
			// TextView txtPrice;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;

			if (convertView == null) {
				holder = new ViewHolder();
				convertView = this.inflater.inflate(
						R.layout.layout_list_orderitem, parent, false);

				holder.txtName = (TextView) convertView
						.findViewById(R.id.txt_item_name);
				holder.txtNumber = (TextView) convertView
						.findViewById(R.id.txt_item_number);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			OrderLine orderitem = orderLines.get(position);
			holder.txtName.setText(orderitem.getMenuId());
			holder.txtNumber.setText(orderitem.getQty());

			return convertView;
		}
	}

	public void cancelOrder(View view) {
		Intent returnMain = new Intent(this, MenuListActivity.class);
		returnMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(returnMain);
	}

	public void submitOrder(View view) {

		String result = null;

		FoodOrderRequest request = new FoodOrderRequest(
				OrderConfirmActivity.this);

		/**try {
			result = request.createOrder("Alex","0001",orderList);

		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}**/

		Intent returnMain = new Intent(this, MenuListActivity.class);
		returnMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(returnMain);
	}

}
