package com.kang.book.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.util.Log;

import com.kang.book.Util.ApplicationDataUtil;
import com.kang.book.Util.FileUtil;
import com.kang.book.Util.NetWorkUtil;

public class LoadDateService {
	Context context;

	LoadDateService(Context context) {
		this.context = context;
	}

	Thread mThread = new Thread() {
		public void run() {
			try {
				String str = NetWorkUtil.getReader(new URL(ApplicationDataUtil.getData(context, "bool_url")));
				// String str = getReader(new
				// URL("http://www.siluke.com/0/73/73503/9887550.html"));
				// str =parseDirectory(str);
				try {
					FileUtil.writeFileSdcardFile("kang.txt", str);
				} catch (IOException e) {
					e.printStackTrace();
				}
				Log.i("kang", str);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
	};
}
