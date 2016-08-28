package com.example.flying_h;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;



public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.activity_main);
	ImageButton buttonmap=(ImageButton)findViewById(R.id.button_map);
	ImageButton buttoncompass=(ImageButton)findViewById(R.id.button_compass);
	buttonmap.setOnClickListener(new OnClickListener(){
		@Override 
		public void onClick(View v){
			Intent intent =new Intent (MainActivity.this,PhotoAct.class);
			startActivity(intent);
				}
	});
	buttoncompass.setOnClickListener(new OnClickListener(){
		@Override 
		public void onClick(View v){
			 Toast.makeText(getApplicationContext(), "请注意保持手机的水平", Toast.LENGTH_LONG).show();
			Intent intent =new Intent (MainActivity.this,Compass.class);
			startActivity(intent);
		}
	});
	findViewById(R.id.button_mocking).setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
		//	Intent intent = getPackageManager().getLaunchIntentForPackage("com.MockingBird");//
		 //   startActivity(intent);
			//if (intent == null) {
			 
			//    Toast.makeText(getApplicationContext(), "抱歉，没有找到您的MockingBird", Toast.LENGTH_LONG).show();
		//	}
			Intent intent = new Intent(MainActivity.this,Mocking.class);
		    startActivity(intent);
		}
	});
	
	findViewById(R.id.button_intent).setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
		
			Intent intent = new Intent(MainActivity.this,WebMainActivity.class);
			startActivity(intent);
			}
		});
	findViewById(R.id.button_sort).setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
		
			Intent intent = new Intent(MainActivity.this,NoteAct.class);
			startActivity(intent);
			}
		});
	findViewById(R.id.button_help).setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
		
			Intent intent = new Intent(MainActivity.this,Help.class);
			startActivity(intent);
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
		System.exit(0);
	}
	
}