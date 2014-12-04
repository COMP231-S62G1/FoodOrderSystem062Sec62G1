package com.foodorder.view;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import android.annotation.SuppressLint;
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
import android.util.Log;
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
import com.foodorder.net.Parse;
import com.foodorder.beans.MenuModel;
import com.foodorder.net.FoodOrderRequest;
import com.google.gson.JsonSyntaxException;
import com.foodorder.utils.LogInOut;
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
	private Menu menu;
	
	private GetData gd;
	
	@Override
	protected void onStop(){
		Log.e("RestListActivity", "onStop");
		super.onStop();
	}
	
	@Override
	protected void onDestroy (){
		Log.e("RestListActivity", "onDestroy");
		if(gd !=null)
			gd.cancel(true);
		super.onDestroy();
	}
	
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
				gd = new GetData(RestListActivity.this,1);
				gd.execute("");
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
		ApplicationData.setRestList(restList);
	}
	
	@Override
	public void onResume(){
		super.onResume();
		if(ApplicationData.getUser()!=null){
			LogInOut.setLogin(true, menu);
		}else{
			LogInOut.setLogin(false, menu);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		this.menu = menu;
		getMenuInflater().inflate(R.menu.rest_list, menu);
		if(ApplicationData.getUser()!=null){
			LogInOut.setLogin(true, menu);
		}else{
			LogInOut.setLogin(false, menu);
		}
		
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
		}else if (id == R.id.action_logout) {
			ApplicationData.setUser(null);
            Intent loginIntent = new Intent(this, LoginActivity.class);
            loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(loginIntent);
        }else if (id == R.id.action_login) {
			ApplicationData.setUser(null);
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }else if (id == R.id.action_userinfo) {
			Intent intentUser = new Intent(this, UserInfoActivity.class);
					intentUser.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intentUser);
		}else if (id == R.id.action_register) {
			Intent intentRegister = new Intent(RestListActivity.this,
					RegisterActivity.class);
					intentRegister.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intentRegister);
		
		}
		return super.onOptionsItemSelected(item);
	}


	class MyBaseAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return restList.size();
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
			if(restList==null){
				return convertView;
			}
			@SuppressWarnings("static-access")
			final View view=convertView.inflate(RestListActivity.this, R.layout.cate_item,null);
			
			Object obj=restList.get(position);
			final ImageView rest_item_image=(ImageView)view.findViewById(R.id.rest_item_image);
			TextView rest_item_title=(TextView)view.findViewById(R.id.rest_item_title);
			TextView rest_item_content=(TextView)view.findViewById(R.id.rest_item_content);
			//ImageView right_flag=(ImageView)view.findViewById(R.id.right_flag);
			
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
				params.width=LayoutParams.MATCH_PARENT;
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
		//private Context mContext;
		private int mType;

		private GetData(Context context, int type) {
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
			FoodOrderRequest request = new FoodOrderRequest(RestListActivity.this);
			
			try {
				Log.d("doInBackground", "start request");
				result = request.getMenuByRestId(String.format("%d",restId));
				Log.d("doInBackground", "returned");
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
			Log.d("onPostExecute", "postExec state");
			if (result == null || result.equals("")) {
				handler.sendEmptyMessage(1);
			} else {
				//Log.d("get result is done", result);
				menuList = new ArrayList<MenuModel>();
				try {
					menuList = new Parse().GetMenuByRestId(result);
					//Log.d("Parse is done", Integer.toString(menuList.size()));
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


	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				new GetData(RestListActivity.this, 1).execute("");
				break;
			default:
				break;
			}
		}
	};
	}
}
