package com.kang.book.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.kang.book.R;
import com.kang.book.Adapter.ChaptionListAdapter;
import com.kang.book.BookDotaProvide.BookProvide;

public class BookChaptionActivity extends Activity implements OnItemClickListener {
	ListView chaption_list;
	BookProvide bookProvide;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.book_chaption);
		bookProvide = new BookProvide(this);
		chaption_list = (ListView) findViewById(R.id.chaption_list);

		TextView tv = new TextView(this);
		tv.setText(R.string.other_chaption);
		tv.setTextSize(27);
		chaption_list.addFooterView(tv, null, false);

		ChaptionListAdapter chaptionAdapter = new ChaptionListAdapter(this, bookProvide.getAllChaption());
		chaption_list.setAdapter(chaptionAdapter);
		chaption_list.setOnItemClickListener(this);

		setResult(-1);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		 if (bookProvide.isHaveChaption(position)) {
			setResult(position);
			finish();
		} else {
			showDialog(this, bookProvide.getNowLoadChaption());
		}
	}

	private void showDialog(Context context, int number) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setIcon(R.drawable.ic_launcher);
		builder.setTitle("提示");
		builder.setMessage("该章节正在下载请保持网络畅通，当前正在下载第" + (number+1) + "章！");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
			}
		});
		builder.show();
	}

}