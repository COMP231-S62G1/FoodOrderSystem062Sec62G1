package com.foodorder.beans;

import java.io.Serializable;

public class Giftcard  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String idgiftcard;
	private String giftcode;
	private String giftbalance;
	private String canuse;
	public String getIdgiftcard() {
		return idgiftcard;
	}
	public void setIdgiftcard(String idgiftcard) {
		this.idgiftcard = idgiftcard;
	}
	public String getGiftcode() {
		return giftcode;
	}
	public void setGiftcode(String giftcode) {
		this.giftcode = giftcode;
	}
	public String getGiftbalance() {
		return giftbalance;
	}
	public void setGiftbalance(String giftbalance) {
		this.giftbalance = giftbalance;
	}
	public String getCanuse() {
		return canuse;
	}
	public void setCanuse(String canuse) {
		this.canuse = canuse;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
