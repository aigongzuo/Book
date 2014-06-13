package com.kang.book.Activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.method.Touch;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.kang.book.BookViewFactory;
import com.kang.book.R;
import com.kang.book.Adapter.ViewPagerAdapter;
import com.kang.book.BookDotaProvide.BookProvide;
import com.kang.book.Util.SharedPreferencesUtil;

public class MainActivity extends Activity implements OnPageChangeListener, OnClickListener, OnTouchListener {
	SharedPreferencesUtil sharedPreferencesUtil;
	BookViewFactory bookViewFactory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);
		initViewPage();

	}

	ViewPager viewpager;
	ViewPagerAdapter adapter;
	RelativeLayout rl_tittle;
	LinearLayout fond_layout, menu_layout;
	ImageView back_button, show_menu;
	ImageButton font_minus, font_plus;
	Button all_chaption;

	public void initViewPage() {
		rl_tittle = (RelativeLayout) findViewById(R.id.rl_tittle);
		menu_layout = (LinearLayout) findViewById(R.id.menu_layout);
		fond_layout = (LinearLayout) findViewById(R.id.fond_layout);
		back_button = (ImageView) findViewById(R.id.back_button);
		back_button.setOnClickListener(this);
		show_menu = (ImageView) findViewById(R.id.show_menu);
		show_menu.setOnClickListener(this);
		font_minus = (ImageButton) findViewById(R.id.font_minus);
		font_minus.setOnClickListener(this);
		font_plus = (ImageButton) findViewById(R.id.font_plus);
		font_plus.setOnClickListener(this);
		all_chaption = (Button) findViewById(R.id.all_chaption);
		all_chaption.setOnClickListener(this);

		sharedPreferencesUtil = new SharedPreferencesUtil(this);
		BookProvide bookfile = new BookProvide(this);
		bookfile.LoadCaption();
		bookViewFactory = new BookViewFactory(this);
		List<View> viewlist = bookViewFactory.reInitListView();

		viewpager = (ViewPager) findViewById(R.id.viewpager);
		viewpager.setOnPageChangeListener(this);
		adapter = new ViewPagerAdapter(viewlist);
		viewpager.setAdapter(adapter);
		viewpager.setCurrentItem(bookViewFactory.getCountVideo(),false);
		viewpager.setOnTouchListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_button:
			finish();
			break;
		case R.id.show_menu:
			if (menu_layout.getVisibility() == View.GONE)
				menu_layout.setVisibility(View.VISIBLE);
			else
				menu_layout.setVisibility(View.GONE);
			break;
		case R.id.font_minus:
			sharedPreferencesUtil.SetFontSize((sharedPreferencesUtil.getFontSize() - 1));
			reInitAdapter(true);
			break;
		case R.id.font_plus:
			sharedPreferencesUtil.SetFontSize((sharedPreferencesUtil.getFontSize() + 1));
			reInitAdapter(true);
			break;
		case R.id.all_chaption:
			Intent intent = new Intent(this, BookChaptionActivity.class);
			startActivityForResult(intent, 200);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// super.onActivityResult(requestCode, resultCode, data);
		Log.i("kang", "resultCode:" + resultCode);
		if (resultCode != -1) {
			sharedPreferencesUtil.SetChapter(resultCode);
			reInitAdapter(false);
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		Log.i("kang", "onPageScrollStateChanged.arg0:" + arg0);
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		// Log.i("kang",
		// "onPageScrolled.arg0,arg1,arg2"+arg0+","+arg1+","+arg2);
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		Log.i("kang", "onPageSelected.arg0:" + arg0);

		int pNote = sharedPreferencesUtil.getChapterPNote();
		int nNote = sharedPreferencesUtil.getChapteNNoter();
		if (arg0 < pNote || arg0 >= nNote) {
			nextOrprivatePage(arg0, pNote, nNote);
		} else {
			sharedPreferencesUtil.SetReadNote(arg0);
		}
		parg0 = arg0;
	}

	int parg0;

	private void nextOrprivatePage(int arg0, int pNote, int nNote) {
		if (arg0 < pNote) {
			sharedPreferencesUtil.SetChapter(sharedPreferencesUtil.getChapter() - 1);
		} else if (arg0 >= nNote) {
			sharedPreferencesUtil.SetChapter(sharedPreferencesUtil.getChapter() + 1);
		}

		List<View> viewlist = bookViewFactory.reInitListView();
		adapter.setList(viewlist);
		// viewpager.setAdapter(adapter);
		adapter.notifyDataSetChanged();

		int mPNote;
		if (parg0 < arg0) {
			mPNote = sharedPreferencesUtil.getChapterPNote();
		} else {
			mPNote = sharedPreferencesUtil.getChapteNNoter() - 1;
		}
		mPNote = viewlist.size() < mPNote ? viewlist.size() : mPNote;
		viewpager.setCurrentItem(mPNote,false);
		sharedPreferencesUtil.SetReadNote(mPNote);
		
		if(arg0==adapter.getCount()-1){
			
		}
	}

	private void reInitAdapter(boolean isNowNote) {
		List<View> viewlist = bookViewFactory.reInitListView();
		adapter.setList(viewlist);
		adapter.notifyDataSetChanged();

		if (!isNowNote)
			sharedPreferencesUtil.SetReadNote(sharedPreferencesUtil.getChapterPNote());

		viewpager.setCurrentItem(bookViewFactory.getCountVideo(),false);
	}

	float x, y;

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			x = event.getX();
			y = event.getY();
			break;
		case MotionEvent.ACTION_UP:
			if (Math.abs(x - event.getX()) < 20) {
				if (rl_tittle.getVisibility() == View.GONE)
					rl_tittle.setVisibility(View.VISIBLE);
				else
					rl_tittle.setVisibility(View.GONE);
				menu_layout.setVisibility(View.GONE);
			}
			break;
		default:
			break;
		}
		return false;
	}
}
