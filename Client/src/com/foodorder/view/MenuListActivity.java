package com.foodorder.view;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;

import com.foodorder.beans.AppConstants;
import com.foodorder.beans.FoodListsViewImage;
import com.foodorder.beans.MenuModel;
import com.foodorder.beans.Rest;
import com.foodorder.client.R;
import com.foodorder.net.Parse;
import com.foodorder.net.FoodOrderRequest;
import com.foodorder.view.RestListActivity.MyBaseAdapter;
import com.foodorder.view.ShoppingCartActivity;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Note;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MenuListActivity extends Activity {

	private DialogActivity dialog;
	private Intent intent;
	private ArrayList<MenuModel> menuList;
	private MyBaseAdapter myBaseAdapter;
	static String path = AppConstants.path;
	private MenuModel nextPageNote;
	private int noteId;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_list);
		setTitle("Menu List");
		getMenuList();
		listView = (ListView) findViewById(R.id.rest_listview);
		myBaseAdapter = new MyBaseAdapter();
		listView.setAdapter(myBaseAdapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (menuList == null) {
					return;
				}
				MenuModel menuItem = menuList.get(position);
				noteId = Integer.parseInt(menuItem.getMenuid());
				Object obj = (Object) menuList.get(position);
				nextPageNote = new MenuModel();
				nextPageNote = (MenuModel) obj;
				// ShowMyDialog(1, null);
			//	handler.sendEmptyMessage(0);
				if (obj instanceof String) {
					return;
				}
			}
		});
	}

	@SuppressWarnings("unchecked")
	private void getMenuList() {
		menuList = (ArrayList<MenuModel>) getIntent().getSerializableExtra(
				"menuList");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_list, menu);
		return true;
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
			final View view = convertView.inflate(MenuListActivity.this,
					R.layout.subcate_listview, null);
			Object obj = menuList.get(position);
			ImageView menu_item_image = (ImageView) view.findViewById(R.id.img);
			TextView menu_item_title = (TextView) view.findViewById(R.id.info);
			ImageView right_flag = (ImageView) view.findViewById(R.id.favImg);
			if (obj instanceof MenuModel) {
				final MenuModel aMenuItem = (MenuModel) obj;
				menu_item_title.setText("Name: " + aMenuItem.getName() + "\n"
						+ "Description: " + aMenuItem.getDes());

				menu_item_image.setTag(aMenuItem.getPic());
				if (menuList.get(position).getPic() != null
						&& !menuList.get(position).getPic().equals("")) {
					try {
						new FoodListsViewImage(MenuListActivity.this)
						.loadingImage(menuList.get(position).getPic(),
								menu_item_image, R.drawable.computer, listView);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					menu_item_image.setImageResource(R.drawable.computer);
				}
				
			} 
			else {
			}
			return view;
		}
	}

	public static Bitmap getPicByPath(String picName) {
		picName = picName.substring(picName.lastIndexOf("/") + 1);
		String filePath = path + picName;
		Bitmap bitmap = BitmapFactory.decodeFile(filePath);
		return bitmap;
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
			FoodOrderRequest request = new FoodOrderRequest(MenuListActivity.this);
			
			try {
				result = request.getMenuByRestId(String.format("%d",1));
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
					Intent intent = new Intent(MenuListActivity.this,ShoppingCartActivity.class);
					intent.putExtra("menuList", (Serializable)menuList);
					startActivity(intent);
					finish();
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
			// get comment by note id
			case 0:
				Thread thread = new Thread() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						super.run();
						FoodOrderRequest request = new FoodOrderRequest(
								getApplicationContext());
						String result = null;
						
					}
				};
				thread.start();
				break;
			case 1:
				dialog.cancel();
				break;
			case 2:
				dialog.cancel();

				break;
			case 3:
				dialog.cancel();

				break;
			default:
				break;
			}
		}

	};

	}
}