package com.foodorder.beans;

import java.io.Serializable;

public class OrderLine  implements Serializable {

	
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
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	private String idorderline;
	private String qty;
	private String menuid;
	private String des;
	private String orderid;
	
	public String toString(){
		return "qty:"+qty+", menuid:"+menuid+", des"+des+", idorderline"+idorderline+", orderid:"+orderid;
	}
	
}
