package com.foodorder.beans;

import java.io.Serializable;

public class Orders  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String getIdorder() {
		return idorder;
	}
	public void setIdorder(String idorder) {
		this.idorder = idorder;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	private String idorder;
	private String userid;
	private String orderTime;
	private String username;
	private String status;
	
}
