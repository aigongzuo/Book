package com.kang.book.BookDotaProvide;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;

import com.kang.book.Bean.BookChaptherBean;
import com.kang.book.Util.AssetsUtil;
import com.kang.book.Util.SharedPreferencesUtil;
import com.kang.book.Util.StrParserUtil;
import com.kang.book.service.BookService;

public class BookProvide {

	Context context;
	SharedPreferencesUtil sharedPreferencesUtil;
	public static List<BookChaptherBean> listCaption;

	public BookProvide(Context context) {
		this.context = context;
		sharedPreferencesUtil = new SharedPreferencesUtil(context);
	}

	private String chaptionDirectoryFile = "directory.html";

	public void LoadCaption() {
		if (listCaption == null) {
			String str = sharedPreferencesUtil.getChaptionText(chaptionDirectoryFile);
			if (str.equals("")) {
				str = AssetsUtil.getStringFromAssetsFile(context, chaptionDirectoryFile);
			}
			listCaption = StrParserUtil.parseDirectory(str);
		} else {
			return;
		}
	}

	public void reLoadCaption() {
		String str = sharedPreferencesUtil.getChaptionText(chaptionDirectoryFile);
		if (str.equals("")) {
			str = AssetsUtil.getStringFromAssetsFile(context, chaptionDirectoryFile);
		}
		listCaption = StrParserUtil.parseDirectory(str);
	}

	public List<BookChaptherBean> getAllChaption() {
		LoadCaption();
		return listCaption;
	}

	public String LoadBookText(int chaption) {
		if (chaption < 0 || chaption > listCaption.size())
			return "";
		String retStr = "";
		try {
			LoadCaption();
			retStr = sharedPreferencesUtil.getChaptionText(listCaption.get(chaption).getPath());
			if (retStr.equals("")) {
				retStr = AssetsUtil.getStringFromAssetsFile(context, listCaption.get(chaption).getPath());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return retStr;
	}

	private boolean isLoadFile() {
		if (listCaption == null || listCaption.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public int getNowLoadChaption() {
		return BookService.loadChaption;
	}

	public boolean isHaveChaption(int chaption) {
		LoadCaption();
		if(!sharedPreferencesUtil.isContainsChaption(listCaption.get(chaption).getPath())){
			if (!AssetsUtil.isContains(context, listCaption.get(chaption).getPath())) {
				return false;
			}
		}
		return true;
	}

	/*
	 * public String getFirstChaption() { if (isLoadFile()) return ""; return
	 * AssetsUtil.getStringFromAssetsFile(context,
	 * listCaption.get(0).getPath()); }
	 * 
	 * public boolean haveNextChaption(int nowChaption) { if (nowChaption <
	 * listCaption.size()) { return true; } else { return false; } }
	 */
}
