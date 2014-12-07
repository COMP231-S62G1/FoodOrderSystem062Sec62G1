package com.foodorder.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import android.app.Application;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.Location;
import android.os.Build;
import android.os.Vibrator;


public class ApplicationData extends Application{
	
	private static ArrayList<Object> listdata = new ArrayList<Object>();
	private static ArrayList<MenuModel> cartList = new ArrayList<MenuModel>();
	private static ArrayList<MenuModel> orderList = new ArrayList<MenuModel>();
	private static ArrayList<Rest> restList = new ArrayList<Rest>();
	private static HashMap<String, String> orderLineList = new HashMap<String, String>();
	private static UserInfo userinfo;
	
	public static UserInfo getUser(){
		return userinfo;
	}
	public static void setUser(UserInfo user){
		userinfo = user;
	}
	public static ArrayList<MenuModel> getCartList()
	{
		return cartList;
	}
	public static ArrayList<MenuModel> getOrderList()
	{
		return orderList;
	}
	public static void setOrderList(ArrayList<MenuModel> orderList){
		if(orderList == null){
			//ApplicationData.restList = null;
			ApplicationData.orderList = new ArrayList<MenuModel>();
		}else
			ApplicationData.orderList = orderList;
	}
	
	public static void setRestList(ArrayList<Rest> restList){
		if(restList == null){
			//ApplicationData.restList = null;
			ApplicationData.restList = new ArrayList<Rest>();
		}else
			ApplicationData.restList = restList;
	}
	
	public static ArrayList<Rest> getRestList(){
		return ApplicationData.restList;
	}
	
	public static void setBalance(int balance){
		if(userinfo == null)
			return;
		else{
			userinfo.setBalance(Integer.toString(balance));
		}
	}
	
	/*
	 * getBalance()
	 * 
	 * return balance of current user
	 * 
	 * if user does not logged in yet or some error occurred, return -1
	 * otherwise return remaining balance amount
	 * 
	 * return type is integer, 100 point is $1, 1 point is 1 cent
	 */
	public static int getBalance(){
		if(userinfo == null){
			return -1;
		}else if (userinfo.getBalance() == null || userinfo.getBalance().length() == 0){
			return -1;
		}
		int nBalance = Integer.parseInt(userinfo.getBalance());
		return nBalance;
	}
	
	public static void setCartList(ArrayList<MenuModel> listCart)
	{
		if(listCart == null){
			//cartList = null;
			cartList = new ArrayList<MenuModel>();
		}else
			cartList = listCart;
	}
	
	
	

	public static void setOrderLineList(HashMap<String, String> orderline)
	{
		if (orderline == null) {
			//orderLineList = null;
			orderLineList = new HashMap<String, String>();
		}else 
			orderLineList = orderline;
	}
	
	public static HashMap<String, String> getOrderLine()
	{
		return orderLineList;
	}
	
	public static HashMap<String, Boolean> newsReadFlag=new HashMap<String, Boolean>();
	
	public ArrayList<Object> getListdata() {
		return listdata;
	}
	public void setListdata(ArrayList<Object> listdata) {
		if(listdata == null){
			//this.listdata = null;
			ApplicationData.listdata = new ArrayList<Object>();
		}else
			ApplicationData.listdata = listdata;
	}
	
	public static final String TAB_WOCHACHA = "TAB_WOCHACHA";
	public static final String TAB_SHOPPING = "TAB_SHOPPING";
	public static final String TAB_SOCIAL_SECURITY = "TAB_SOCIALSECURITY";
	public static final String TAB_UTILITIES = "TAB_UTILITIES";
	public static final String TAB_MORE = "TAB_MORE";
	public static final String TAB_INSURANCE_POLICY = "TAB_INSURANCE_POLICY";
	public static final String TAB_PROVIDENT_FUND = "TAB_PROVIDENT_FUND";
	public static boolean TABHOSTCREAT = false;

	public static String mDid;// 
	public static String mDname;// 
	public static final String mLanguage = "zh";// 
	public static int mDeviceVersion;// 
	public static String mAppVersion;// 
	public static String mModel;// 
	public static String mDevicetoken = "fsjlkfs";// PUSH Token
	public static String mFrom;// 
	public static int mNetType;// 
	public static final String PLATFORM = "android";// 
	public static String mAppType = "xxxxx";// 
	public static final String mAppID = "2";// 

	public static final String EXTRAS_FILM_INFO = "com.itotem.kunshan.imovie.file.info";
	public static Location location;

	public Vibrator mVibrator01;
	public static String mAddrStr;// current address

	public static Double longitude;
	public static Double dimension;
	public static boolean isCancel = true;
	
	public static int NOTIFICATION_ID = 1;
	public static ArrayList<String> arrOrderId = new ArrayList<String>();

	@Override
	public void onCreate() {
		super.onCreate();
		initHeader();

	}

	private void initHeader() {
		// mMemberId = getUid();
		mAppVersion = getVersion();
		// mImei = getEncryptCode(getIMEI());
		// mDid = getEncryptCode("011472001975695");
		mDname = Build.BRAND;
		mDeviceVersion = Build.VERSION.SDK_INT;
		mModel = Build.MODEL;
	}
	private String getVersion() {
		try {
			return getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * IMEI
	 * */
	/*
	private String getIMEI() {
		TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId();
	}
	*/

	/**
	 * exitAcivity
	 */
	/*
	public static void exit(final Activity activity) {
		Builder dialog = new AlertDialog.Builder(activity);
		dialog.setTitle("Are you sure Exit?");
		dialog.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				int sdkVersion = Integer.valueOf(android.os.Build.VERSION.SDK);
				ActivityManager manager = (ActivityManager) activity
						.getSystemService(ACTIVITY_SERVICE);
				if (sdkVersion < 8) {
					manager.killBackgroundProcesses(activity.getPackageName());
					System.exit(0);
				} else {
					Intent startMain = new Intent(Intent.ACTION_MAIN);
					startMain.addCategory(Intent.CATEGORY_HOME);
					startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					activity.startActivity(startMain);
					System.exit(0);
				}
			}
		});
		dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
			}
		});
		dialog.show();
	}
	*/


	public static boolean isPicture(String pInput, String pImgeFlag) throws Exception {
		
		if (pInput == null || pInput.equals("")) {
			
			return false;
		}
	
		String tmpName = pInput.substring(pInput.lastIndexOf(".") + 1, pInput.length());
		
		String imgeArray[][] = { { "bmp", "0" }, { "dib", "1" }, { "gif", "2" }, { "jfif", "3" },
				{ "jpe", "4" }, { "jpeg", "5" }, { "jpg", "6" }, { "png", "7" }, { "tif", "8" },
				{ "tiff", "9" }, { "ico", "10" } };
		
		for (int i = 0; i < imgeArray.length; i++) {
			if (pImgeFlag != null && imgeArray[i][0].equals(tmpName.toLowerCase(Locale.CANADA))
					&& imgeArray[i][1].equals(pImgeFlag)) {
				return true;
			}
			if (pImgeFlag == null && imgeArray[i][0].equals(tmpName.toLowerCase(Locale.CANADA))) {
				return true;
			}
		}
		return false;
	}

}
