package com.example.flying_h;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Guanniao extends Activity {
	private WebView webView;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.guanniao);
		webView = (WebView) findViewById(R.id.guanniao);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient());
		webView.loadUrl("http://www.cbw.org.cn/main.jsp");
		webView.getSettings().setSupportZoom(true);//������Ļ����
		webView.getSettings().setBuiltInZoomControls(true);//������Ļ����
		webView. getSettings().setDisplayZoomControls(false);//�������Ű�ť
		webView.getSettings().setDefaultFontSize(16);
	}
}
