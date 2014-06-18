package com.kang.book.Util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;

public class AssetsUtil {

	public static String getStringFromAssetsFile(Context context, String fileName) {
		AssetManager am = context.getResources().getAssets();
		try {
			InputStream is = am.open(fileName);
			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			byte[] b = new byte[256];
			int number = -1;
			while ((number = is.read(b)) != -1) {
				bytes.write(b, 0, number);
			}
			if (is != null)
				is.close();
			return new String(bytes.toByteArray(), "gbk");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static boolean isContains(Context context, String fileName) {
		AssetManager am = context.getResources().getAssets();
		try {
			InputStream is = am.open(fileName);
			if (is != null)
				is.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
