package com.kang.book.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.kang.book.Bean.BookChaptherBean;

public class StrParserUtil {
	
	public static List<BookChaptherBean> parseDirectory(String str) {
		List<BookChaptherBean> listCaption = new ArrayList<BookChaptherBean>();

		Pattern p = Pattern.compile("<dd><a href=\"(.*?)\" title=\"(.*?)\">(.*?)</a></dd>");
		Matcher m = p.matcher(str);
		while (m.find()) {
			listCaption.add(new BookChaptherBean(m.group(2), m.group(1)));
		}
		return listCaption;
	}
	
	public  static void parseDirectory(String str,SharedPreferencesUtil sharedPreferencesUtil) {
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

	public static String parseBookText(String str) {
		String retStr = "";

		Pattern p = Pattern.compile("<div id=\"content\">(.*?)</div>");
		Matcher m = p.matcher(str);
		if (m.find()) {
			retStr = m.group(1).replaceAll("<script>.*?</script>", "").replaceAll("<.*?>", "").replaceAll("&nbsp;", "");
		}
		return retStr;
	}
	
	public static int getNOID(String str) {
		try {
			Pattern p = Pattern.compile("<NOID>(.*?)</NOID>");
			Matcher m = p.matcher(str);
			if (m.find()) {
				return Integer.parseInt(m.group(1));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	public static String getSTRNOID(String str) {
		try {
			Pattern p = Pattern.compile("<NOID>(.*?)</NOID>");
			Matcher m = p.matcher(str);
			if (m.find()) {
				return m.group(1);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "0";
	}

	public static String getTitle(String str) {
		Pattern p = Pattern.compile("<Title>(.*?)</Title>");
		Matcher m = p.matcher(str);
		if (m.find()) {
			return m.group(1);
		}
		return "";
	}

	public static String getContext(String str) {
		Pattern p = Pattern.compile("<Context>(.*?)</Context>");
		Matcher m = p.matcher(str);
		if (m.find()) {
			return m.group(1);
		}
		return "";
	}

	public static String getURL(String str) {
		Pattern p = Pattern.compile("<URL>(.*?)</URL>");
		Matcher m = p.matcher(str);
		if (m.find()) {
			return m.group(1);
		}
		return "";
	}
}
