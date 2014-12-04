package com.foodorder.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Set;

import com.foodorder.beans.MenuModel;

public class AmountCalculator {
	
	public static double getHstAmount(double netAmount){
		double hstRate = 0.13;
		double hstAmount = (netAmount * hstRate)*100;
		hstAmount = Math.round(hstAmount);
		hstAmount /= 100;
		return hstAmount;
	}
	
	public static double getGrossAmount ( double netAmount){
		double hstAmount = getHstAmount(netAmount);
		return netAmount + hstAmount;
	}
	
	public static double getNetAmount(ArrayList<MenuModel> menuList, HashMap<String, String> orderLineList){
		double netAmt = 0;
		double unitPrice = 0;
		double quantity = 0;
		
		
		Set<String> keys = orderLineList.keySet();
		for(MenuModel aModel : menuList){
			String itemId = null;
			unitPrice = Double.parseDouble(aModel.getPrice());
			for(String key:keys){
				if(aModel.getMenuid() == key){
					itemId=key;
					quantity = Double.parseDouble(orderLineList.get(itemId));
					netAmt += unitPrice*quantity;
				}
			}
		}
		
		return netAmt;
	}
	
	public static String getAmountString(double amount){
		double amt = amount;
		return String.format(Locale.CANADA,"$%6.2f", amt);
	}
}
