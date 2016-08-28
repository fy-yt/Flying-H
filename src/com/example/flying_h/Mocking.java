package com.example.flying_h;
import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class Mocking extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.mocking);
	findViewById(R.id.wb).setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
		Intent intent = getPackageManager().getLaunchIntentForPackage("net.doyouhike.app.wildbird");
	
			if (intent == null) {
			 
			    Toast.makeText(getApplicationContext(), "��Ǹ��û���ҵ������й�Ұ���ٲ�", Toast.LENGTH_SHORT).show();
		}else{
	
		    startActivity(intent);}
		}
	});
	findViewById(R.id.mb).setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
		Intent intent = getPackageManager().getLaunchIntentForPackage("com.MockingBird");
	
			if (intent == null) {
			 
			    Toast.makeText(getApplicationContext(), "��Ǹ��û���ҵ�����MockingBird", Toast.LENGTH_SHORT).show();
		}else{
	
		    startActivity(intent);}
		}
	});
	findViewById(R.id.eb).setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
		Intent intent = getPackageManager().getLaunchIntentForPackage("edu.cornell.birds.ebird");
	
			if (intent == null) {
			 
			    Toast.makeText(getApplicationContext(), "��Ǹ��û���ҵ�����eBird", Toast.LENGTH_SHORT).show();
		}
			else{
		    startActivity(intent);}
		}
	});
	findViewById(R.id.ma_1).setOnClickListener(new OnClickListener() {
		@Override
	/*	public void onClick(View v ){
			
		}*/
	public void onClick(View v) {
			 Intent intent = new Intent("android.intent.action.VIEW");   
		        intent.addCategory("android.intent.category.DEFAULT");   
		       intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
		 			File	file= new File(Environment.getExternalStorageDirectory(),  "��ͼƬ�����ү.chm");
		 			
		 			 Uri uri=Uri.fromFile(file);  
					 intent.setDataAndType(uri, "application/x-chm");   startActivity(intent);
		}
	});
	findViewById(R.id.back).setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {

			finish();
			}
			});
	}
}

