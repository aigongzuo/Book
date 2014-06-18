package com.kang.book.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.kang.book.R;
import com.kang.book.Bean.BookChaptherBean;
import com.kang.book.BookDotaProvide.BookProvide;
import com.kang.book.Util.ApplicationDataUtil;
import com.kang.book.Util.NetWorkUtil;
import com.kang.book.Util.PhoneUtil;
import com.kang.book.Util.SharedPreferencesUtil;
import com.kang.book.Util.StrParserUtil;

public class BookService extends Service {
	SharedPreferencesUtil sharedPreferencesUtil;
	public static int loadChaption = 0;
	boolean isRun;

	@Override
	public void onCreate() {
		isRun = true;
		sharedPreferencesUtil = new SharedPreferencesUtil(this);
		new ServiceThread().start();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		mThread.start();
	}

	@Override
	public IBinder onBind(Intent intent) {
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
			if (str == null)
				return;
			StrParserUtil.parseDirectory(str, sharedPreferencesUtil);

			BookProvide bookPrivide = new BookProvide(this);
			bookPrivide.reLoadCaption();
			List<BookChaptherBean> booklist = bookPrivide.getAllChaption();

			for (int i = 0; i < booklist.size(); i++) {
				loadChaption = i;
				if (!bookPrivide.isHaveChaption(i)) {
					str = NetWorkUtil.getReader(new URL(Path + (booklist.get(i)).getPath()));
					try {
						sharedPreferencesUtil.saveChaptionText(booklist.get(i).getPath(), StrParserUtil.parseBookText(str));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	class ServiceThread extends Thread {
		public void run() {
			while (isRun) {
				String baseUrl = NetWorkUtil.serviceURL + "message?code=" + sharedPreferencesUtil.GetNOID() + "&deviceID=" + PhoneUtil.getSingleID(getApplicationContext());
				HttpGet getMethod = new HttpGet(baseUrl);
				HttpClient httpClient = new DefaultHttpClient();
				try {
					HttpResponse response = httpClient.execute(getMethod); // ·¢ÆðGETÇëÇó
					if (response.getStatusLine().getStatusCode() == 200) {
						String str = URLDecoder.decode(EntityUtils.toString(response.getEntity(), "UTF-8"), "UTF-8");
						if (str.indexOf("<RN>0</RN>") == -1) {
							Message message = new Message();
							message.what = 0;
							message.obj = str;
							sharedPreferencesUtil.SetNOID(StrParserUtil.getSTRNOID(str));
							myHandler.sendMessage(message);
						}
					}
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					Thread.sleep(1000 * 60 *30);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	};

	Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				String str = (String) msg.obj;
				showNotification(str);
				break;
			default:
				break;
			}
		};
	};

	public void showNotification(String str) {
		Uri uri = Uri.parse(StrParserUtil.getURL(str));
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

		Notification notification = new Notification();
		notification.icon = R.drawable.ic_launcher;
		notification.flags = Notification.FLAG_AUTO_CANCEL;

		notification.defaults = Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND;

		notification.setLatestEventInfo(getApplicationContext(), StrParserUtil.getTitle(str), StrParserUtil.getContext(str), pendingIntent);
		NotificationManager mgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		mgr.notify(StrParserUtil.getNOID(str), notification);
	}

	@Override
	public void onDestroy() {
		isRun = false;
		super.onDestroy();
	}
}
