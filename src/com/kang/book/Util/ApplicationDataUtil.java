package com.kang.book.Util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class ApplicationDataUtil {
	public static String getData(Context context, String str) {
		ApplicationInfo appInfo;
		try {
			appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),PackageManager.GET_META_DATA);
			String msg = appInfo.metaData.getString(str);
			return msg;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}
