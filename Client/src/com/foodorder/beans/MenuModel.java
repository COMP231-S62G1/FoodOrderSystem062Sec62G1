package com.foodorder.beans;

import java.io.Serializable;

public class MenuModel  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 665009315158163676L;
	private String menuid;
	private String restid;
	private String name;
	private String pic;
	private String des;
	private String price;
	
	public void setPrice(String price){
		this.price = price;
	}
	public String getPrice(){
		return this.price;
	}
	public String getMenuid() {
		return menuid;
	}
	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}
	public String getRestid() {
		return restid;
	}
	public void setRestid(String restid) {
		this.restid = restid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
}
