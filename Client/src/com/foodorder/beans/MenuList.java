package com.foodorder.beans;

public class MenuList {
	private MenuModel menu;
	private int qty;
	
	public MenuList(){
		setMenu(null);
	}
	
	public MenuList(MenuModel menu){
		setMenu(menu);
	}
	
	public void setMenu(MenuModel menu){
		this.menu=menu;
		this.qty = 0;
	}
	public void setQty(int qty){
		this.qty = qty;
	}
	public MenuModel getMenu(){
		return menu;
	}
	public int getQty(){
		return qty;
	}
}
