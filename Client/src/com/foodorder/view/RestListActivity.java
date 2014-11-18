package com.foodorder.view;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.foodorder.client.R;
import com.foodorder.beans.AppConstants;
import com.foodorder.beans.ApplicationData;
import com.foodorder.beans.Rest;
import com.foodorder.client.R;
import com.foodorder.net.Parse;
import com.foodorder.beans.MenuModel;
import com.foodorder.net.FoodOrderRequest;
import com.google.gson.JsonSyntaxException;
import com.foodorder.view.MenuListActivity;
import com.foodorder.beans.FoodListsViewImage;

public class RestListActivity extends Activity {
	
	private DialogActivity dialog;
	private ArrayList<Rest> restList;
	private ArrayList<MenuModel> menuList;
	private MyBaseAdapter myBaseAdapter;
	static String path=AppConstants.path;
	private int restId;
	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rest_list);   
		setTitle("Restaurants");
		getRestList();
		listView=(ListView)findViewById(R.id.rest_listview);
		myBaseAdapter=new MyBaseAdapter();
		listView.setAdapter(myBaseAdapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(restList==null){
					return;
				}
				Rest cateItem = restList.get(position);
				restId = Integer.parseInt(cateItem.getIdrest());
				Object obj=(Object)restList.get(position);
				new GetData(RestListActivity.this,0).execute("");
				if(obj instanceof String){
					return;
				}
			}
		});
		
	}
	
	@SuppressWarnings("unchecked")
	private void getRestList()
	{
		restList = (ArrayList<Rest>)getIntent().getSerializableExtra("restList") ;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rest_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}else if(id == R.id.action_cart){
			Intent intentViewCart = new Intent(RestListActivity.this,
					ShoppingCartActivity.class);
			intentViewCart.putExtra("ViewCart", "View Cart Successful");
			startActivity(intentViewCart);
		}else if (id == R.id.action_order){
			Intent intentViewOrder = new Intent(RestListActivity.this,
					OrderDetail.class);
			if(ApplicationData.arrOrderId.size()>0){
				intentViewOrder.putExtra("orderId", Integer.parseInt(ApplicationData.arrOrderId.get(0)));
			}
			startActivity(intentViewOrder);
		}
		return super.onOptionsItemSelected(item);
	}


	class MyBaseAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return restList.size();
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
			if(restList==null){
				return convertView;
			}
			final View view=convertView.inflate(RestListActivity.this, R.layout.cate_item,null);
			
			Object obj=restList.get(position);
			final ImageView rest_item_image=(ImageView)view.findViewById(R.id.rest_item_image);
			TextView rest_item_title=(TextView)view.findViewById(R.id.rest_item_title);
			TextView rest_item_content=(TextView)view.findViewById(R.id.rest_item_content);
			ImageView right_flag=(ImageView)view.findViewById(R.id.right_flag);
			
			if(obj instanceof Rest){
				final Rest aRest=(Rest)obj;
				rest_item_image.setTag(aRest.getPic());
				if (restList.get(position).getPic() != null
						&& !restList.get(position).getPic().equals("")) {
					try {
						new FoodListsViewImage(RestListActivity.this)
						.loadingImage(restList.get(position).getPic(),
								rest_item_image, R.drawable.computer, listView);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					rest_item_image.setImageResource(R.drawable.computer);
				}

				rest_item_title.setText(aRest.getName());
				
			}else{
				view.setEnabled(false);
				rest_item_title.setText(obj.toString());
				LayoutParams params = rest_item_title.getLayoutParams();
				params.width=LayoutParams.FILL_PARENT;
				params.height=25;
				rest_item_title.setLayoutParams(params);
				rest_item_title.setGravity(Gravity.CENTER_VERTICAL);
				rest_item_title.setTextSize(15);
				rest_item_image.setVisibility(View.GONE);
				rest_item_content.setVisibility(View.GONE);
			}
			return view;
		}
	}
	
 	public static Bitmap getPicByPath(String picName){
 			picName=picName.substring(picName.lastIndexOf("/")+1);
 			String filePath=path+picName;
 			Bitmap bitmap=BitmapFactory.decodeFile(filePath);
 			return bitmap;
 	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			//Intent myIntent = new Intent();  
            //myIntent = new Intent(RestListActivity.this, SelectRoleActivity.class);  
            //startActivity(myIntent);  
            //this.finish();  
			openConfirmDialog();

			break;
		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private void openConfirmDialog() {
		final Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.quit);
		builder.setIcon(R.drawable.ic_launcher);
		builder.setMessage(R.string.exit);

		builder.setNeutralButton(R.string.cancel,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						builder.create().dismiss();
						//startActivity(new Intent(RestListActivity.this,
						//		RestListActivity.class));
						//RestListActivity.this.finish();
					}
				});

		builder.setPositiveButton(R.string.confirm,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						builder.create().dismiss();
						RestListActivity.this.finish();
					}
				});
		builder.show();
	}

	private class GetData extends AsyncTask<String, String, String> {
		private Context mContext;
		private int mType;

		private GetData(Context context, int type) {
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
			FoodOrderRequest request = new FoodOrderRequest(RestListActivity.this);
			
			try {
				result = request.getMenuByRestId(String.format("%d",restId));
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

				menuList = new ArrayList<MenuModel>();
				try {
					menuList = new Parse().GetMenuByRestId(result);
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				}
				if (menuList != null) {
					Intent intent = new Intent(RestListActivity.this,MenuListActivity.class);
					intent.putExtra("menuList", (Serializable)menuList);
					startActivity(intent);
					//finish();
				} else {
					handler.sendEmptyMessage(1);
				}
		}
	}


	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				new GetData(RestListActivity.this, 1).execute("");
				break;
			default:
				break;
			}
		}
	};
	}
}
