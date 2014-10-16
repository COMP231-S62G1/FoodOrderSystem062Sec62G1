package com.foodorder.beans;

import java.io.Serializable;

public class Rest  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	public String toString() {
		return "Rest [idrest=" + idrest + ", name=" + name + ", pic=" + pic
				+ ", des=" + des + "]";
	}
	public String getIdrest() {
		return idrest;
	}
	public void setIdrest(String idrest) {
		this.idrest = idrest;
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
	private String idrest;
	private String name;
	private String pic;
	private String des;
}
