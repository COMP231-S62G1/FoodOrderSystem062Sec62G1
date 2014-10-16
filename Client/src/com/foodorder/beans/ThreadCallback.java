package com.foodorder.beans;

import java.io.Serializable;

/**
 * Call back
 * @author Alex.Liu
 * @email alexliubo@gmail.com
 */
public interface ThreadCallback extends Serializable{
	public void onCallbackFromThread(int callid);
}
