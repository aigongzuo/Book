package com.kang.book.Adapter;

import java.util.List;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

public class ViewPagerAdapter extends PagerAdapter {

	List<View> list;

	public ViewPagerAdapter(List<View> list) {
		this.list = list;
	}
	public void setList(List<View> list) {
		this.list = list;
	}
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object instantiateItem(View collection, int position) {
		Log.i("kang", "instantiateItem");
		// ((ViewPager) collection).removeView(bookView);
		((ViewPager) collection).addView(list.get(position));
		return list.get(position);
	}

	@Override
	public void destroyItem(View collection, int position, Object view) {
		Log.i("kang", "destroyItem");
		 ((ViewPager) collection).removeView((View) view);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		Log.i("kang", "isViewFromObject");
		return view == (object);
	}

	@Override
	public void finishUpdate(View arg0) {
		Log.i("kang", "finishUpdate");
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
		Log.i("kang", "restoreState");
	}

	@Override
	public Parcelable saveState() {
		Log.i("kang", "saveState");
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
		Log.i("kang", "startUpdate");
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

}
