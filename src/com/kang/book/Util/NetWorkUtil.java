package com.kang.book.Util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetWorkUtil {
//	public static String serviceURL = "http://bdservice.jd-app.com/";
	public static String serviceURL = "http://192.168.18.132:8080/BookService/";
	
	/*public static String getReader(URL url) {
		try {
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 5.0; Windows XP; DigExt)");
			connection.connect();
			InputStream is = connection.getInputStream();
			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			byte[] b = new byte[256];
			int number = -1;

			while ((number = is.read(b)) != -1) {
				bytes.write(b, 0, number);
			}
			if (is != null)
				is.close();
			return new String(bytes.toByteArray(), "gbk").replaceAll((""+((char)0)),"").replaceAll("\b|\t|\n|\f|\r","");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}*/
	
	public static String getReader(URL url) {
		try {
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 5.0; Windows XP; DigExt)");
			connection.connect();

			String ss = connection.getContentType();

			InputStream is = connection.getInputStream();
			String enc = connection.getContentEncoding();
			if(enc!=null && enc.equals("gzip")) {    //◊¢“‚’‚¿Ô
				java.util.zip.GZIPInputStream gzin = new java.util.zip.GZIPInputStream(is);
				
				ByteArrayOutputStream bytes = new ByteArrayOutputStream();
				byte[] b = new byte[256];
				int number = -1;

				while ((number = gzin.read(b)) != -1) {
					bytes.write(b, 0, number);
				}
				if (gzin != null)
					gzin.close();
				String str =new String(bytes.toByteArray(), "GBK").replaceAll(("" + ((char) 0)), "").replaceAll("\b|\t|\n|\f|\r", "");
				System.out.println(str);
				return str;
			} else {
				ByteArrayOutputStream bytes = new ByteArrayOutputStream();
				byte[] b = new byte[256];
				int number = -1;

				while ((number = is.read(b)) != -1) {
					bytes.write(b, 0, number);
				}
				if (is != null)
					is.close();
				String str =new String(bytes.toByteArray(), "GBK").replaceAll(("" + ((char) 0)), "").replaceAll("\b|\t|\n|\f|\r", "");
				System.out.println(str);
				return str;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
