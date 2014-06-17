package com.kang.book.Application;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.kang.book.R;
import com.kang.book.service.BookService;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class BookApplication extends Application {
	Context mContext;
	static BookApplication duckApplication;

	@Override
	public void onCreate() {
		super.onCreate();
		mContext = this;
		duckApplication = this;
		
//		mThread.start();
		
		startService(new Intent(this,BookService.class));
	}

	Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			String str = (String) msg.obj;
			// Toast.makeText(mContext, "收到�?个消�?"+str,
			// Toast.LENGTH_SHORT).show();

			showNotification(str);
		};
	};

	public void showNotification(String url) {
		// Intent intent = new Intent(Intent.ACTION_MAIN);
		// intent.addCategory(Intent.CATEGORY_LAUNCHER);
		// intent.setClass(this, TabFragmentActivity.class);
		// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
		// Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		Uri uri = Uri.parse(url);
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,intent, 0);

		Notification notification = new Notification();
		notification.icon = R.drawable.ic_launcher;
		notification.flags=Notification.FLAG_AUTO_CANCEL;
		
		notification.defaults = Notification.DEFAULT_VIBRATE|Notification.DEFAULT_SOUND; 
		
		notification.setLatestEventInfo(getApplicationContext(),getString(R.string.app_name), "���롣����",pendingIntent);
		NotificationManager mgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		mgr.cancel(123000);
		mgr.notify(123000, notification);
	}

	public BookApplication getInstals() {
		return duckApplication;
	}

	
	Thread mThread = new Thread() {
		String str = "";

		public void run() {
			if (str.equals("")) {
				str = GetSaveString();
			}
			while (mContext != null) {
				// 先将参数放入List，再对参数进行URL编码
				// List<BasicNameValuePair> params = new
				// LinkedList<BasicNameValuePair>();
				// params.add(new BasicNameValuePair("param1", "中国"));
				// params.add(new BasicNameValuePair("param2", "value2"));

				// 对参数编�?
				// String param = URLEncodedUtils.format(params, "UTF-8");
				// baseUrl
				// String baseUrl =
				// "http://192.168.18.132:8080/ServiceDemo/hello";
				String baseUrl = null;
				try {
					SharedPreferences sp = getSharedPreferences("SP", MODE_PRIVATE);
					baseUrl = ("http://kangdemo.jd-app.com/hello?step=" + sp.getInt("Steps", 0)+"&local="+URLEncoder.encode(sp.getString("Local", "暂为获取到（请到设置页面获取�?"),"GBK"));
//					baseUrl = ("http://192.168.18.132:8080/ServiceDemo/hello?step=" + step+"&local="+URLEncoder.encode(local,"GBK"));
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				Log.i("kang", baseUrl);
				// 将URL与参数拼�?
				// HttpGet getMethod = new HttpGet(baseUrl + "?" + param);
				HttpGet getMethod = new HttpGet(baseUrl);

				HttpClient httpClient = new DefaultHttpClient();

				try {
					HttpResponse response = httpClient.execute(getMethod); // 发起GET请求

					Log.i("kang", "resCode = "
							+ response.getStatusLine().getStatusCode()); // 获取响应�?
					String str1 = EntityUtils.toString(response.getEntity(),
							"utf-8");
					Log.i("kang", "result = " + str1);// 获取服务器响应内�?
					if (str1 != null && !str1.equals(str)
							&& !str1.equals("null")) {
						Message message = new Message();
						str = str1;
						message.obj = str;
						myHandler.sendMessage(message);
						SaveString(str);
					}
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					Thread.sleep(1000 * 5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Log.i("kang", "Conn service... ");
			}
		};
	};

	public void SaveString(String str) {
		SharedPreferences sp = this.getSharedPreferences("SP", MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString("STRING_KEY", str);
		editor.commit();
	}

	public String GetSaveString() {
		SharedPreferences sp = this.getSharedPreferences("SP", MODE_PRIVATE);
		return sp.getString("STRING_KEY", "");
	}
}
