package com.example.flying_h;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;


public class Help extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.logo2);
        findViewById(R.id.back).setOnClickListener(new OnClickListener() {
        	@Override
			public void onClick(View v) {
				finish();
			}
    			});
}
}