package com.example.flying_h;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Niaowang extends Activity {
	private WebView webView;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	  requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.niaowang);
		webView = (WebView) findViewById(R.id.niaowang);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient());
		webView.loadUrl("http://www.birdnet.cn/portal.php");
		webView.getSettings().setSupportZoom(true);//调节屏幕缩放
		webView.getSettings().setBuiltInZoomControls(true);//调节屏幕缩放
		webView. getSettings().setDisplayZoomControls(false);//隐藏缩放按钮
		webView.getSettings().setDefaultFontSize(16);
	}
}
