package com.kang.book.Adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kang.book.Bean.BookChaptherBean;

public class ChaptionListAdapter extends BaseAdapter {
	Context context;
	List<BookChaptherBean> list;

	public ChaptionListAdapter(Context context, List<BookChaptherBean> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView textView;
		if (convertView == null) {
			textView = new TextView(context);
			textView.setTextSize(27);
			textView.layout(0, 10, 0, 10);
			
		} else {
			textView = (TextView) convertView;
		}
		textView.setText(list.get(position).getChapther());
		return textView;
	}

}
