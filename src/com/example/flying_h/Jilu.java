package com.example.flying_h;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Jilu extends Activity {
	private WebView webView;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.jilu);
		webView = (WebView) findViewById(R.id.jilu);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient());
		webView.loadUrl("http://www.birdreport.cn/Index");
		webView.getSettings().setSupportZoom(true);//������Ļ����
		webView.getSettings().setBuiltInZoomControls(true);//������Ļ����
		webView. getSettings().setDisplayZoomControls(false);//�������Ű�ť
		webView.getSettings().setDefaultFontSize(16);
	}
}
