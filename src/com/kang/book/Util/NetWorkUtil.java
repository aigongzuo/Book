package com.kang.book.Util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetWorkUtil {

	public static String getReader(URL url) {
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
	}
}
