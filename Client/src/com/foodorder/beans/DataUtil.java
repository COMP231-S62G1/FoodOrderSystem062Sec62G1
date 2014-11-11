package com.foodorder.beans;

import android.location.Location;

/**
 * Data collection
 * @author Alex.Liu
 * @email alexliubo@gmail.com
 */
public class DataUtil {
	
	public static boolean ProgressBarDialogShowFlag = false;
	public static ThreadLocal<Boolean> localTimeout = new ThreadLocal<Boolean>();
	
	public static boolean isProgressBarDialogShowFlag() {
		return ProgressBarDialogShowFlag;
	}
	public static void setProgressBarDialogShowFlag(
			boolean progressBarDialogShowFlag) {
		ProgressBarDialogShowFlag = progressBarDialogShowFlag;
	}
}
