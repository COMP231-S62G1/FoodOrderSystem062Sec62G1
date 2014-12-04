package com.foodorder.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
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
import com.foodorder.beans.MenuList;
import com.foodorder.beans.MenuModel;
import com.foodorder.client.R;
import com.foodorder.utils.LogInOut;
import com.foodorder.view.ShoppingCartActivity;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
//import android.widget.Toast;

public class MenuListActivity extends Activity {

	private ArrayList<MenuModel> menuList;
	private ArrayList<MenuList> itemList;
	private MyBaseAdapter myBaseAdapter;
	final static String path = AppConstants.path;
	private int menuId;
	private ListView listView;
	private Button btnviewCart;
	private Button btnAddCart;
	private Intent intentViewCart;
	private MenuModel aMenu;
	private Menu menu;
	private AlertDialog.Builder adbCart;

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
		if(ApplicationData.getUser()!=null){
			LogInOut.setLogin(true, menu);
		}else{
			LogInOut.setLogin(false, menu);
		}
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
				intentViewCart = new Intent(MenuListActivity.this, ShoppingCartActivity.class);
				//intentViewCart.putExtra("ViewCart", "View Cart Successful");
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
				if (itemList == null) {
					return;
				}
				MenuModel menuItem = itemList.get(position).getMenu();
				menuId = Integer.parseInt(menuItem.getMenuid());

			}
		});
		
		this.btnAddCart = (Button) findViewById(R.id.btnAddCart);
		setAddCart();
		
		adbCart = new AlertDialog.Builder( this);
		adbCart.setTitle("Items were added to cart");
		adbCart.setMessage("Are you want to go to your shopping cart?");
		adbCart.setPositiveButton("Ok",
				new AlertDialog.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						intentViewCart = new Intent(MenuListActivity.this, ShoppingCartActivity.class);
						//intentViewCart.putExtra("ViewCart", "View Cart Successful");
						startActivity(intentViewCart);
					}
				});
		adbCart.setNegativeButton("Cancel", null);
	}
	
	private void setAddCart(){
		this.btnAddCart.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ArrayList<MenuModel> listMenuApp = ApplicationData.getCartList();
				int nTotalQty = 0;
				for(int nCnt=0 ; nCnt < itemList.size(); nCnt++){
					if(itemList.get(nCnt).getQty() <= 0)
						continue;
					nTotalQty += itemList.get(nCnt).getQty();
					MenuModel aMenu = itemList.get(nCnt).getMenu();
					boolean isExistInList = false;
					for (MenuModel m : listMenuApp) {
						if (m.getMenuid().equals(aMenu.getMenuid()))
							isExistInList = true;
					}
					if (!isExistInList)
						listMenuApp.add(aMenu);
					ApplicationData.setCartList(listMenuApp);
					HashMap<String, String> currentOrderline = ApplicationData.getOrderLine();
					int nCurrQty = 0;
					if (!currentOrderline.isEmpty()) {
						if (currentOrderline.containsKey(aMenu.getMenuid())) {
							nCurrQty = Integer.parseInt(currentOrderline.get(aMenu.getMenuid()) );
							currentOrderline.remove(aMenu.getMenuid());
						}
					}
					currentOrderline.put(aMenu.getMenuid(), Integer.toString(nCurrQty+itemList.get(nCnt).getQty()));
					ApplicationData.setOrderLineList(currentOrderline);
					itemList.get(nCnt).setQty(0);
				}
				if(nTotalQty <= 0){
					AlertDialog.Builder adbError = new AlertDialog.Builder(MenuListActivity.this);
					adbError.setTitle("No selected items");
					adbError.setMessage("Nothing selected on the list");
					adbError.setPositiveButton("Ok", null);
					adbError.show();
				}else{
					listView.invalidateViews();
					adbCart.show();
				}
			}

		});
	}
	

	@SuppressWarnings("unchecked")
	private void getMenuList() {
		menuList = (ArrayList<MenuModel>) getIntent().getSerializableExtra("menuList");
		itemList = new ArrayList<MenuList>();
		for(int nCnt=0; nCnt<menuList.size() ; nCnt++){
			itemList.add( new MenuList( menuList.get(nCnt) )  );
		}
		menuList = null;
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
			//intentViewCart.putExtra("ViewCart", "View Cart Successful");
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
        }else if (id == R.id.action_register) {
			Intent intentRegister = new Intent(MenuListActivity.this,
					RegisterActivity.class);
			intentRegister.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intentRegister);
		}
        else if (id == R.id.action_userinfo) {
			Intent intentUser = new Intent(MenuListActivity.this,
					UserInfoActivity.class);
					intentUser.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intentUser);
		}
		else if (id == R.id.action_login) {
			ApplicationData.setUser(null);
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }
		return super.onOptionsItemSelected(item);
	}
	

	class MyBaseAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if(itemList == null)
				return 0;
			return itemList.size();
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
			if (itemList == null) {
				return convertView;
			}
			//Log.e("getView", "pos: "+position);
			final View view = convertView.inflate(MenuListActivity.this,
					R.layout.subcate_listview, null);
			final MenuList obj = itemList.get(position);
			ImageView menu_item_image = (ImageView) view.findViewById(R.id.img);
			TextView menu_item_title = (TextView) view.findViewById(R.id.info);
			ImageButton btnAdd = (ImageButton) view.findViewById(R.id.btnAdd);
			ImageButton btnRemove = (ImageButton) view.findViewById(R.id.btnRemove);
			final TextView txtQty = (TextView) view.findViewById(R.id.txtQty);
			txtQty.setText(Integer.toString(obj.getQty()));
			txtQty.setPaintFlags(txtQty.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
			final int pos = position;
			btnAdd.setTag(pos);
			btnRemove.setTag(pos);
			btnAdd.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					int nQty = obj.getQty();
					obj.setQty(++nQty);
					txtQty.setText(Integer.toString(nQty));
				}
			});
			
			btnRemove.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					int nQty = obj.getQty();
					if(nQty <= 0)
					{
						return;
					}
					obj.setQty(--nQty);
					txtQty.setText(Integer.toString(nQty));
				}
			});
			
			//Button cartAdd = (Button) view.findViewById(R.id.btnAdd);
			//final EditText txtQuanty = (EditText) view.findViewById(R.id.txtQty);
			//txtQuanty.setInputType(InputType.TYPE_CLASS_NUMBER);
			
			//cartAdd.setTag(position);

			/*cartAdd.setOnClickListener(new AdapterView.OnClickListener() {
				public void onClick(View v) {

					// validate 000000
					if (txtQuanty.getText().toString().matches("")
							|| txtQuanty.getText().toString().matches("0") ||
							Integer.parseInt(txtQuanty.getText().toString()) == 0) {
						int position = (Integer) v.getTag();
						AlertDialog.Builder adb = new AlertDialog.Builder(
								MenuListActivity.this);
						adbCart.setTitle("Quantity Error");
						adbCart.setMessage("Please enter a quantity.");
						adbCart.setPositiveButton("Ok", null);
						adbCart.show();

					}

					else {

						ArrayList<MenuModel> listMenuApp = ApplicationData
								.getCartList();
						MenuModel aMenu = itemList.get(pos).getMenu();
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
			});*/

			// ImageView right_flag = (ImageView)
			// view.findViewById(R.id.favImg);
			if (obj.getMenu() instanceof MenuModel) {
				final MenuModel aMenuItem = (MenuModel) obj.getMenu();
				menu_item_title.setText(aMenuItem.getName() + "\n\n"
						+ "Description:\n" + aMenuItem.getDes() + "\n\n" + "Price: $" + aMenuItem.getPrice());

				menu_item_image.setTag(aMenuItem.getPic());
				if (itemList.get(position).getMenu().getPic() != null
						&& !itemList.get(position).getMenu().getPic().equals("")) {
					try {
						new FoodListsViewImage(MenuListActivity.this)
								.loadingImage(itemList.get(position).getMenu().getPic(),
										menu_item_image, R.drawable.computer,
										listView);
					} catch (Exception e) {
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

	
}
