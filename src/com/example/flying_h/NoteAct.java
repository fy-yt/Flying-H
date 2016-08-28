package com.example.flying_h;

import java.util.ArrayList;

import com.example.beans.Cuns;

import com.example.luoji.MyAdapter;
import com.example.luoji.MyDataBase;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class NoteAct extends Activity {
	Button bt;
	ListView lv;
	LayoutInflater inflater;
	ArrayList<Cuns> array;
	MyDataBase mdb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.noteact);
		
		lv=(ListView) findViewById(R.id.listView1);
		bt=(Button) findViewById(R.id.button1);
		inflater=getLayoutInflater();
		
		mdb=new MyDataBase(this);
		array=mdb.getArray();
		MyAdapter adapter=new MyAdapter(inflater,array);
		lv.setAdapter(adapter);
		/*
		 * ���listView�����item,���뵽�ڶ���ҳ�棬�����޸��ռ�
		 */
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub				
				Intent intent=new Intent(getApplicationContext(),NoteSecondAct.class);
				intent.putExtra("ids",array.get(position).getIds() );
				startActivity(intent);
				NoteAct.this.finish();
			}
		});
		/*
		 * ��������ж��Ƿ�ɾ������
		 */
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long id) {
				// TODO Auto-generated method stub
				//AlertDialog,���ж��Ƿ�ɾ���ռǡ�
				new AlertDialog.Builder(NoteAct.this)
				.setTitle("ɾ��")
				.setMessage("�Ƿ�ɾ���ʼ�")
				.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				})
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						mdb.toDelete(array.get(position).getIds());
						array=mdb.getArray();
						MyAdapter adapter=new MyAdapter(inflater,array);
						lv.setAdapter(adapter);
					}
				})
				.create().show();
				return true;
			}
		});
		/*
		 * ��ť����¼��������½��ռ�
		 */
		bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getApplicationContext(),NoteSecondAct.class);
				startActivity(intent);
				NoteAct.this.finish();
			}
		});

	}
}
