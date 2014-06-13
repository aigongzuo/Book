package com.kang.book.Util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.util.EncodingUtils;

import android.os.Environment;

public class FileUtil {
	// д���ݵ�SD�е��ļ�
	public static void writeFileSdcardFile(String fileName, String write_str) throws IOException {
		try {
			FileOutputStream fout = new FileOutputStream(Environment.getExternalStorageDirectory().toString() + "/" + fileName);
			byte[] bytes = write_str.getBytes();

			fout.write(bytes);
			fout.close();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ��SD�е��ļ�
	public String readFileSdcardFile(String fileName) throws IOException {
		String res = "";
		try {
			FileInputStream fin = new FileInputStream(fileName);

			int length = fin.available();

			byte[] buffer = new byte[length];
			fin.read(buffer);

			res = EncodingUtils.getString(buffer, "UTF-8");

			fin.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
}