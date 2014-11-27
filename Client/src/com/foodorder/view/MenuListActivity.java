package com.foodorder.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.foodorder.beans.AppConstants;
import com.foodorder.beans.ApplicationData;
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
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MenuListActivity extends Activity {

	private DialogActivity dialog;
	private Intent intent;
	private ArrayList<MenuModel> menuList;
	private MyBaseAdapter myBaseAdapter;
	static String path = AppConstants.path;
	private int menuId;
	private ListView listView;
	private Button btnviewCart;
	private Intent intentViewCart;
	private Intent intentBack;
	private Bundle b;
	private MenuModel aMenu;

	protected void generateOrderlineList() {
		View v;
		EditText et;
		for (int i = 0; i < listView.getCount(); i++) {
			v = listView.getAdapter().getView(i, null, null);
			et = (EditText) v.findViewById(i);
			// if(et.getText().toString() )
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.e("MenuList", "onResume()");
		// Toast.makeText(this, "onResume()", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.e("MenuList", "onDestroy");
		// Toast.makeText(this, "onDestory()", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_list);
		setTitle("Menu List");

		Log.e("MenuList", "onCreate()");

		this.btnviewCart = (Button) findViewById(R.id.btnViewCart1);
		this.btnviewCart.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				intentViewCart = new Intent(MenuListActivity.this,
						ShoppingCartActivity.class);
				// intentViewCart.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
				// Intent.FLAG_ACTIVITY_SINGLE_TOP);
				intentViewCart.putExtra("ViewCart", "View Cart Successful");
				startActivity(intentViewCart);
			}

		});

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
				menuId = Integer.parseInt(menuItem.getMenuid());

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
		if(ApplicationData.getUser() != null)
			getMenuInflater().inflate(R.menu.menu_list, menu);
		else
			getMenuInflater().inflate(R.menu.menu_list_login, menu);
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
			Intent intentViewCart = new Intent(MenuListActivity.this,
					ShoppingCartActivity.class);
			intentViewCart.putExtra("ViewCart", "View Cart Successful");
			startActivity(intentViewCart);
		}else if (id == R.id.action_order){
			Intent intentViewOrder = new Intent(MenuListActivity.this,
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
            return true;
        }else if (id == R.id.action_register) {
			Intent intentRegister = new Intent(MenuListActivity.this,
					RegisterActivity.class);
					intentRegister.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intentRegister);
		
		}
        else if (id == R.id.action_user_info) {
			Intent intentUser = new Intent(MenuListActivity.this,
					UserInfoActivity.class);
					intentUser.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intentUser);
		
		}
		else if (id == R.id.action_login) {
			ApplicationData.setUser(null);
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
            return true;
        }
		return super.onOptionsItemSelected(item);
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
			Button cartAdd = (Button) view.findViewById(R.id.btnAdd);
			final EditText txtQuanty = (EditText) view
					.findViewById(R.id.txtQty);
			final int pos = position;
			cartAdd.setTag(position);

			cartAdd.setOnClickListener(new AdapterView.OnClickListener() {
				public void onClick(View v) {

					// validate 000000
					if (txtQuanty.getText().toString().matches("")
							|| txtQuanty.getText().toString().matches("0") ||
							Integer.parseInt(txtQuanty.getText().toString()) == 0) {
						int position = (Integer) v.getTag();
						AlertDialog.Builder adb = new AlertDialog.Builder(
								MenuListActivity.this);
						adb.setTitle("Quantity Error");
						adb.setMessage("Please enter a quantity.");
						adb.setPositiveButton("Ok", null);
						adb.show();

					}

					else {

						ArrayList<MenuModel> listMenuApp = ApplicationData
								.getCartList();
						MenuModel aMenu = menuList.get(pos);
						boolean isExistInList = false;
						for (MenuModel m : listMenuApp) {
							if (m.getMenuid().equals(aMenu.getMenuid()))
								isExistInList = true;
						}
						if (!isExistInList)
							listMenuApp.add(aMenu);
						ApplicationData.setCartList(listMenuApp);

						HashMap<String, String> currentOrderline = ApplicationData
								.getOrderLine();
						if (!currentOrderline.isEmpty()) {
							if (currentOrderline.containsKey(aMenu.getMenuid())) {
								currentOrderline.remove(aMenu.getMenuid());
							}
						}
						currentOrderline.put(aMenu.getMenuid(), String.valueOf(Integer.parseInt(txtQuanty
								.getText().toString())));
						ApplicationData.setOrderLineList(currentOrderline);
						txtQuanty.setText("");
					}
				}
			});

			// ImageView right_flag = (ImageView)
			// view.findViewById(R.id.favImg);
			if (obj instanceof MenuModel) {
				final MenuModel aMenuItem = (MenuModel) obj;
				menu_item_title.setText("Name: " + aMenuItem.getName() + "\n"
						+ "Description: " + aMenuItem.getDes() + "\n" + "Price: $" + aMenuItem.getPrice());

				menu_item_image.setTag(aMenuItem.getPic());
				if (menuList.get(position).getPic() != null
						&& !menuList.get(position).getPic().equals("")) {
					try {
						new FoodListsViewImage(MenuListActivity.this)
								.loadingImage(menuList.get(position).getPic(),
										menu_item_image, R.drawable.computer,
										listView);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					menu_item_image.setImageResource(R.drawable.computer);
				}

			} else {
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
			FoodOrderRequest request = new FoodOrderRequest(
					MenuListActivity.this);

			try {
				result = request.getMenuByRestId(String.format("%d", 1));
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
					Intent intent = new Intent(MenuListActivity.this,
							ShoppingCartActivity.class);
					intent.putExtra("menuList", (Serializable) menuList);
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
