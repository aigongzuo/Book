package com.kang.book;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;

import com.kang.book.BookDotaProvide.BookProvide;
import com.kang.book.UI.BookView;
import com.kang.book.Util.SharedPreferencesUtil;

public class BookViewFactory {
	111
	String strTest;
	Context context;
	String bookcontext;
	BookProvide bookProvide;
	SharedPreferencesUtil sharedPreferencesUtil;

	public BookViewFactory(Context context) {
		this.context = context;
		sharedPreferencesUtil = new SharedPreferencesUtil(context);
		bookProvide = new BookProvide(context);
	}

	public List<View> reInitListView() {
		List<View> list = new ArrayList<View>();

		String pcs, ncs, ccs;
		pcs = bookProvide.LoadBookText(sharedPreferencesUtil.getChapter() - 1);
		ccs = bookProvide.LoadBookText(sharedPreferencesUtil.getChapter());
		ncs = bookProvide.LoadBookText(sharedPreferencesUtil.getChapter() + 1);
		if (!pcs.equals("")) {
			StringToView(list, pcs);
		}
		sharedPreferencesUtil.SetChapterPNote(list.size());
		if (!ccs.equals("")) {
			StringToView(list, ccs);
		}
		sharedPreferencesUtil.SetChapterNNote(list.size());
		if (!ncs.equals("")) {
			StringToView(list, ncs);
		}
		return list;
	}

	public int getCountVideo() {

		if (sharedPreferencesUtil.getReadNoter() >= sharedPreferencesUtil.getChapteNNoter() || sharedPreferencesUtil.getReadNoter() < sharedPreferencesUtil.getChapterPNote()) {
			return sharedPreferencesUtil.getChapterPNote();
		} else {
			return sharedPreferencesUtil.getReadNoter();// getChapterPNote();
		}
	}

	public void StringToView(List<View> list, String str) {

		int startNumber = 0;
		int endNumber = 0;

		while (str.length() > endNumber) {
			BookView bookView = new BookView(context);
			bookView.initPaint(sharedPreferencesUtil.getFontSize());
			endNumber = (startNumber + bookView.getOnePageALLFontSize() > str.length()) ? str.length() : (startNumber + bookView.getOnePageALLFontSize());
			bookView.setDrawString(str.substring(startNumber, endNumber));
			startNumber += bookView.getOnePageALLFontSize();
			list.add(bookView);
		}
	}

	public View getPrivateView() {
		BookView bookView = new BookView(context);
		bookView.initPaint(sharedPreferencesUtil.getFontSize());
		if (sharedPreferencesUtil.getNowNumber() - bookView.getOnePageALLFontSize() == 0) {
			sharedPreferencesUtil.SetChapter(sharedPreferencesUtil.getChapter() - 1);
			loadChapter(sharedPreferencesUtil.getChapter());
			sharedPreferencesUtil.SetNowReadNumber(0);

		} else if (sharedPreferencesUtil.getNowNumber() - bookView.getOnePageALLFontSize() < 0) {
			sharedPreferencesUtil.SetNowReadNumber(0);
		}

		int startNumber = sharedPreferencesUtil.getNowNumber() - bookView.getOnePageALLFontSize() > 0 ? sharedPreferencesUtil.getNowNumber() - bookView.getOnePageALLFontSize() : 0;
		int endNumber = (startNumber + bookView.getOnePageALLFontSize() > bookcontext.length()) ? bookcontext.length() : (startNumber + bookView.getOnePageALLFontSize());
		bookView.setDrawString(bookcontext.substring(startNumber, endNumber));
		sharedPreferencesUtil.SetNowReadNumber(endNumber);
		return bookView;
	}

	public View getNextView(Context context) {
		if (bookcontext.length() <= sharedPreferencesUtil.getNowNumber()) {
			sharedPreferencesUtil.SetChapter(sharedPreferencesUtil.getChapter() + 1);
			loadChapter(sharedPreferencesUtil.getChapter());
			sharedPreferencesUtil.SetNowReadNumber(0);
		}

		BookView bookView = new BookView(context);
		bookView.initPaint(sharedPreferencesUtil.getFontSize());
		int startNumber = sharedPreferencesUtil.getNowNumber();
		int endNumber = (startNumber + bookView.getOnePageALLFontSize() > bookcontext.length()) ? bookcontext.length() : (startNumber + bookView.getOnePageALLFontSize());
		bookView.setDrawString(bookcontext.substring(startNumber, endNumber));
		sharedPreferencesUtil.SetNowReadNumber(endNumber);
		return bookView;
	}

	public boolean canGetPrivate() {
		return false;
	}

	public boolean canGetNext() {
		return false;
	}

	public static boolean isHaveChapter(int chapter) {
		return true;
	}

	public void loadChapter(int chapter) {

	}

}
