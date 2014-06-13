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
}
