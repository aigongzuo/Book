package com.kang.book.Util;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;

public class PhoneUtil {
	public static String getPhoneNumber(Context context) {
		TelephonyManager mTelephonyMgr;
		mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return mTelephonyMgr.getLine1Number();
	}

	public static String getSingleID(Context context) {
		String DEVICE_ID = "";
		try {
			TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			DEVICE_ID = tm.getDeviceId();
			if (DEVICE_ID == null) {
				DEVICE_ID = tm.getSimSerialNumber();
				if (DEVICE_ID == null) {
					DEVICE_ID = Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
					if (DEVICE_ID == null)
						DEVICE_ID = android.os.Build.SERIAL;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (DEVICE_ID == null)
			DEVICE_ID = "";
		return DEVICE_ID;
	}
}
