package com.kang.book.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesUtil {
	Context context;

	public SharedPreferencesUtil(Context context) {
		this.context = context;
	}

	public void SetChapter(int chapter) {
		SharedPreferences sp = context.getSharedPreferences("SP", context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putInt("CHAPTER", chapter);
		editor.commit();
	}

	public void SetNowReadNumber(int now_number) {
		SharedPreferences sp = context.getSharedPreferences("SP", context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putInt("NOW_NUMBER", now_number);
		editor.commit();
	}

	public void SetFontSize(int font_size) {
		SharedPreferences sp = context.getSharedPreferences("SP", context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putInt("FONT_SIZE", font_size);
		editor.commit();
	}

	public int getFontSize() {
		SharedPreferences sp = context.getSharedPreferences("SP", context.MODE_PRIVATE);
		return sp.getInt("FONT_SIZE", 24);
	}

	public int getChapter() {
		SharedPreferences sp = context.getSharedPreferences("SP", context.MODE_PRIVATE);
		return sp.getInt("CHAPTER", 0);
	}

	public int getNowNumber() {
		SharedPreferences sp = context.getSharedPreferences("SP", context.MODE_PRIVATE);
		return sp.getInt("NOW_NUMBER", 0);
	}

	public void SetChapterPNote(int chapter) {
		SharedPreferences sp = context.getSharedPreferences("SP", context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putInt("PNote", chapter);
		editor.commit();
	}

	public void SetChapterNNote(int chapter) {
		SharedPreferences sp = context.getSharedPreferences("SP", context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putInt("NNote", chapter);
		editor.commit();
	}

	public int getChapterPNote() {
		SharedPreferences sp = context.getSharedPreferences("SP", context.MODE_PRIVATE);
		return sp.getInt("PNote", 0);
	}

	public int getChapteNNoter() {
		SharedPreferences sp = context.getSharedPreferences("SP", context.MODE_PRIVATE);
		return sp.getInt("NNote", 0);
	}

	public void SetReadNote(int note) {
		SharedPreferences sp = context.getSharedPreferences("SP", context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putInt("read_note", note);
		editor.commit();
	}

	public int getReadNoter() {
		SharedPreferences sp = context.getSharedPreferences("SP", context.MODE_PRIVATE);
		return sp.getInt("read_note", getChapterPNote());
	}

	public void saveChaptionText(String name, String text) {
		SharedPreferences sp = context.getSharedPreferences("ChaptionText", context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString(name, text);
		editor.commit();
	}

	public String getChaptionText(String name) {
		SharedPreferences sp = context.getSharedPreferences("ChaptionText", context.MODE_PRIVATE);
		return sp.getString(name, "");
	}
}
