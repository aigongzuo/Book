package com.kang.book.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.kang.book.Bean.BookChaptherBean;
import com.kang.book.BookDotaProvide.BookProvide;
import com.kang.book.Util.ApplicationDataUtil;
import com.kang.book.Util.NetWorkUtil;
import com.kang.book.Util.SharedPreferencesUtil;

public class BookService extends Service {
	SharedPreferencesUtil sharedPreferencesUtil;
	public static int loadChaption =0;
	@Override
	public void onCreate() {
		sharedPreferencesUtil = new SharedPreferencesUtil(this);

		mThread.start();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	Thread mThread = new Thread() {
		public void run() {
			try {
				LoadBook(ApplicationDataUtil.getData(getApplicationContext(), "bool_url"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	public void LoadBook(String Path) {
		try {
			String str = NetWorkUtil.getReader(new URL(Path));
			parseDirectory(str);

			BookProvide bookPrivide = new BookProvide(this);
			bookPrivide.reLoadCaption();
			List<BookChaptherBean> booklist = bookPrivide.getAllChaption();

			for (int i = 0; i < booklist.size(); i++) {
				loadChaption = i;
				if (!bookPrivide.isHaveChaption(i)) {
					str = NetWorkUtil.getReader(new URL(Path + (booklist.get(i)).getPath()));
					try {
						sharedPreferencesUtil.saveChaptionText(booklist.get(i).getPath(), parseBookText(str));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		stopSelf();
	}

	public void parseDirectory(String str) {
		System.out.println("½âÎöÄ¿Â¼¡£¡£¡£");
		List<BookChaptherBean> booklist = new ArrayList<BookChaptherBean>();
		String retStr = "";
		Pattern p = Pattern.compile("<dd><a href=\"(.*?)\" title=\"(.*?)\">(.*?)</a></dd>");
		Matcher m = p.matcher(str);
		while (m.find()) {
			retStr += m.group();
			booklist.add(new BookChaptherBean(m.group(3), m.group(1)));
		}
		sharedPreferencesUtil.saveChaptionText("directory.html", retStr);
	}

	public String parseBookText(String str) {
		String retStr = "";

		Pattern p = Pattern.compile("<div id=\"content\">(.*?)</div>");
		Matcher m = p.matcher(str);
		if (m.find()) {
			retStr = m.group(1).replaceAll("<script>.*?</script>", "").replaceAll("<.*?>", "").replaceAll("&nbsp;", "");
		}
		return retStr;
	}
}
