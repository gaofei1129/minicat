package com.fanfou.app.hd;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanfou.app.hd.ui.widget.GestureManager.SwipeListener;
import com.fanfou.app.hd.util.Linkify;

/**
 * @author mcxiaoke
 * @version 1.0 2011.08.26
 * @version 1.1 2011.10.26
 * @version 1.2 2011.11.17
 * 
 */
public class UIAbout extends UIBaseSupport implements OnClickListener,
		SwipeListener {
	public static final String COPYRIGHT = "\u00a9";
	public static final String REGISTERED = "\u00ae";

	private ImageView mLogo;

	private TextView mTitle;
	private TextView mVersion;
	private TextView mIntroduction;
	private TextView mSupport;
	private TextView mSupportText;
	private TextView mContact;
	private TextView mContactText;
	private TextView mCopyright;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void initialize() {
		setTitle("关于");
	}

	@Override
	protected void setLayout() {
		setContentView(R.layout.about);

		mLogo = (ImageView) findViewById(R.id.about_icon);
		mLogo.setOnClickListener(this);

		mTitle = (TextView) findViewById(R.id.about_title);
		mVersion = (TextView) findViewById(R.id.about_version);
		mIntroduction = (TextView) findViewById(R.id.about_intro);
		mSupport = (TextView) findViewById(R.id.about_support);
		mSupportText = (TextView) findViewById(R.id.about_support_text);
		mContact = (TextView) findViewById(R.id.about_contact);
		mContactText = (TextView) findViewById(R.id.about_contact_text);
		mCopyright = (TextView) findViewById(R.id.about_copyright);

		mTitle.setText("饭否Android客户端");
		TextPaint t1 = mTitle.getPaint();
		t1.setFakeBoldText(true);
		String version = App.versionName + "(Build" + App.versionCode + ")";
		mVersion.setText("版本：" + version);
		mIntroduction.setText(R.string.introduction_text);
		mSupport.setText("技术支持");
		TextPaint t2 = mSupport.getPaint();
		t2.setFakeBoldText(true);
		mSupportText.setText(R.string.support_text);
		linkifySupport(mSupportText);
		mContact.setText("联系方式");
		TextPaint t3 = mContact.getPaint();
		t3.setFakeBoldText(true);
		mContactText.setText(R.string.contact_text);
		mCopyright.setText("\u00a9 2007-2013 fanfou.com");

	}

	private void linkifySupport(final TextView textView) {
		textView.setMovementMethod(LinkMovementMethod.getInstance());
		Spannable span = (Spannable) textView.getText();
		String text = textView.getText().toString();
		String spanText = "@Android客户端";
		int start = text.indexOf(spanText);
		if (start > 0) {
			int end = start + spanText.length();
			span.setSpan(new Linkify.URLSpanNoUnderline(
					"fanfouhd://user/androidsupport"), start, end,
					Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.about_icon) {
			Uri uri = Uri.parse("market://details?id=" + getPackageName());
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(uri);
			startActivity(intent);
		}
	}

	@Override
	public boolean onSwipeLeft() {
		finish();
		return true;
	}

	@Override
	public boolean onSwipeRight() {
		return true;
	}

}