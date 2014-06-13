package com.kang.book.UI;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

public class BookView extends View {

	Context context;
	Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
	String drawText = "";
	int fontWidth;
	int fontHeight;
	int MAXLineSize;
	int MAXLine;

	public BookView(Context context) {
		super(context);
		this.context = context;

	}

	public BookView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public void initPaint(int TextSize) {
		p.setColor(0xFF000000);
		p.setAntiAlias(true);// 去除锯齿
		p.setFilterBitmap(true);// 对位图进行滤波处理
		p.setTextSize(TextSize);
		
		FontMetrics fm = p.getFontMetrics();
		fontHeight = (int) (fm.bottom - fm.top);
		fontWidth = (int) p.measureText("康");

		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		MAXLineSize = dm.widthPixels / fontWidth;
		MAXLine = dm.heightPixels / fontHeight;
	}

	public int getFontHeight() {
		return fontHeight;
	}

	public int getFontWidth() {
		return fontWidth;
	}

	public int getLineSize() {
		return MAXLineSize;
	}

	public int getOnePageALLFontSize() {
		return MAXLineSize * MAXLine;
	}

	public void setDrawString(String str) {
		drawText = str;
//		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (drawText == null) {
			throw new NullPointerException("drawText is null");
		}
		int line_number = drawText.length() / MAXLineSize;
		if ((drawText.length() % MAXLineSize) > 0) {
			line_number++;
		}

		for (int i = 0; i < line_number; i++) {
			int subend = (i + 1) * MAXLineSize;
			if (subend > drawText.length())
				subend = drawText.length();
			String str = drawText.substring(i * MAXLineSize, subend);
			canvas.drawText(str, 0, fontHeight * (i+1), p);
		}
	}
}
