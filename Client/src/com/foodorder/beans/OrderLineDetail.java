package com.foodorder.beans;

import java.io.Serializable;

public class OrderLineDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String getIdorderline() {
		return idorderline;
	}
	public void setIdorderline(String idorderline) {
		this.idorderline = idorderline;
	}
	public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
	public String getMenuid() {
		return menuid;
	}
	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	/*
	 * "idorderline" => '',
        "menuid"=> '',
        "name" => '',
        "pic"=> '',
        "price"=> '',
        "qty"=> '',
	 */
	
	private String idorderline;
	private String menuid;
	private String name;
	private String pic;
	private String price;
	private String qty;


}
