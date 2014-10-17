package com.foodorder.view;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
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

import com.foodorder.beans.Rest;
import com.foodorder.beans.FoodListsViewImage;

public class RestListActivity extends Activity {
	
	private DialogActivity dialog;
	private ArrayList<Rest> restList;
	private MyBaseAdapter myBaseAdapter;
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
				// Victor_Todo; implement something to get data from server
				//GetData dataGet = new GetData(RestListActivity.this,0);
				//dataGet.execute("");
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
/*
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
*/

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
			super.onPreExecute();
		}
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
		}
}
