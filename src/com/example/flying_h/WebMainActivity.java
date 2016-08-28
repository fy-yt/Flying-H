package com.example.flying_h;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;


public class WebMainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_web_main);
        Button bniaowang=(Button) findViewById(R.id.niaowang);
        Button bxeno=(Button) findViewById(R.id.xeno);
        Button bjilu=(Button) findViewById(R.id.jilu);
        Button bguanniao=(Button) findViewById(R.id.guanniao);
        Button bobi=(Button) findViewById(R.id.obi);
        findViewById(R.id.back).setOnClickListener(new OnClickListener() {
        	@Override
			public void onClick(View v) {
				finish();
			}
    			});    
        bniaowang.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v){
        		Intent intent=new Intent(WebMainActivity.this,Niaowang.class);
        		startActivity(intent);
        	}
        });/////////////////////
        bxeno.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v){
             	Intent intent=new Intent(Intent.ACTION_VIEW);
        		intent.setData(Uri.parse("http://test.xeno-canto.org/"));
        		startActivity(intent);
        	}
        });
        bjilu.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v){
        		Intent intent=new Intent(WebMainActivity.this,Jilu.class);
        		startActivity(intent);
        	}
        });
        bobi.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v){
             	Intent intent=new Intent(Intent.ACTION_VIEW);
        		intent.setData(Uri.parse("http://orientalbirdimages.org/"));
        		startActivity(intent);
        	}
        });
        bguanniao.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v){
        		Intent intent=new Intent(WebMainActivity.this,Guanniao.class);
        		startActivity(intent);
        	}
        });
    }
}