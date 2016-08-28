package com.example.flying_h;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.provider.DocumentsContract;

public class PhotoAct extends Activity {
    
	public static final int TAKE_PHOTO = 1;
	public static int i = 0;
	public static final int CROP_PHOTO=2;
	private Button takePhoto;
	private ImageView picture;
	private Uri imageUri;
	private static final int CHOOSE_PHOTO =3;
	private Button chooseFromAlbum;
    //λ��ͼƬ�е��ı����
    private TextView tvInImage;
    //ͼƬ���ı��ĸ����
    private View containerView;
    //������ĳߴ�
    private float imageWidth, imageHeight, imagePositionX, imagePositionY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_photo);
        takePhoto =(Button) findViewById(R.id.take_photo);
        chooseFromAlbum=(Button) findViewById(R.id.choose_from_album);
        picture = (ImageView) findViewById(R.id.picture);
        tvInImage = (TextView) findViewById(R.id.writeText_image_tv);
        containerView = findViewById(R.id.writeText_img_rl);
        EditText editText = (EditText) findViewById(R.id.writeText_et);

        takePhoto.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v){//����file�������ڴ洢���պ��ͼƬ
        		File outputImage = new File (Environment.getExternalStorageDirectory(),"output_image.jpg");
        		try{
        			if (outputImage.exists()){outputImage.delete();}
        			outputImage.createNewFile();
        		}catch(IOException e){
        			e.printStackTrace();
        		}
        		
       		
        		imageUri = Uri.fromFile(outputImage);
        		Intent intent = new Intent ("android.media.action.IMAGE_CAPTURE");
        	    intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        	    startActivityForResult(intent,TAKE_PHOTO);//�����������
        			}
        });
        chooseFromAlbum.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v){
        		Intent intent = new Intent("android.intent.action.GET_CONTENT");
        		intent.setType("image/*");
        		startActivityForResult(intent,CHOOSE_PHOTO);//�����
        	}
        });
        //�����
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    tvInImage.setVisibility(View.INVISIBLE);
                } else {
                    tvInImage.setVisibility(View.VISIBLE);
                    tvInImage.setText(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        final GestureDetector gestureDetector = new GestureDetector(this, new SimpleGestureListenerImpl());
        //�ƶ�
        tvInImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
        
        findViewById(R.id.y).setOnClickListener(new OnClickListener() {
        	@Override
			public void onClick(View v) {
        		Intent intent = getPackageManager().getLaunchIntentForPackage("com.android.soundrecorder");
        		
    			if (intent == null) {
    			 
    			    Toast.makeText(getApplicationContext(), "��Ǹ��û���ҵ�����¼����", Toast.LENGTH_SHORT).show();
    		}
    			else{
    		    startActivity(intent);}
    		
			}
    			});    
    
                
        findViewById(R.id.back).setOnClickListener(new OnClickListener() {
        	@Override
			public void onClick(View v) {
				finish();
			}
    			});    
    
        findViewById(R.id.share).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
			Intent intent = new Intent(Intent.ACTION_SEND);
				   Bitmap bm = loadBitmapFromView(containerView);
			       bm= comp(bm);
			   	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmmss",Locale.SIMPLIFIED_CHINESE);
			       String filePath = Environment.getExternalStorageDirectory()  +File.separator +sdf.format(new Date()) + ".jpg";
			    
			        try {
			            bm.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(filePath));
			            Toast.makeText(PhotoAct.this, "ͼƬ�ѱ���", Toast.LENGTH_SHORT).show();
			            intent.setType("image/jpg");  
			            File f=new File(filePath);
		                Uri u = Uri.fromFile(f);  
		               intent.putExtra(Intent.EXTRA_STREAM, u);  
		               startActivity(intent);
			        } catch (FileNotFoundException e) {
			            e.printStackTrace();
			        }
			}
			
		});            
            }

protected void onActivityResult(int requestCode,int resultCode,Intent data){
	   switch (requestCode){
	   case TAKE_PHOTO:
		   if(resultCode ==RESULT_OK){
			   Intent intent= new Intent("com.android.camera.action.CROP");
			   intent.setDataAndType(imageUri, "image/*");
			   intent.putExtra("scale",true);
			   intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
			   startActivityForResult(intent,CROP_PHOTO);//�����ü�����
		   }break;
	   case CROP_PHOTO:
		   if(resultCode ==RESULT_OK){
			   try{
				   Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
				   picture.setImageBitmap(bitmap);//���ü������Ƭ��ʾ����
				   picture.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
       	            public void onGlobalLayout() {
       	            	picture.getViewTreeObserver().removeGlobalOnLayoutListener(this);

       	                imagePositionX = picture.getX();
       	                imagePositionY = picture.getY();
       	                imageWidth =picture.getWidth();
       	                imageHeight =picture.getHeight();

       	                //�����ı���С
       	                tvInImage.setMaxWidth((int) imageWidth);
       	            }
       	        });
				   
				   
			   }catch(FileNotFoundException e ){
				   e.printStackTrace();
			   }
		   }
		   break;
	   case CHOOSE_PHOTO:
		   if(resultCode == RESULT_OK){//�ж��ֻ�ϵͳ�汾��
			   if(Build.VERSION.SDK_INT>=19){//4.4������ϵͳʹ�������������ͼƬ
				   handleImageOnKitKat(data);
			   }else{//4.4������ϵͳʹ�������������ͼƬ
				   handleImageBeforeKitKat(data);
			   }		   
			   }break;
	 default:break;
	   }
}

@TargetApi(19)
private void handleImageOnKitKat(Intent data){
	String imagePath = null;
	Uri uri = data.getData();
	if(DocumentsContract.isDocumentUri(PhotoAct.this, uri)){//�����document���͵�Uri,��ͨ��document id����
		String docId = DocumentsContract.getDocumentId(uri);
		if("com.android.providers.media.documents".equals(uri.getAuthority())){
			String id = docId.split(":")[1];//���������ָ�ʽ��Id
			String selection = MediaStore.Images.Media._ID + "=" +id;
			imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
		}else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
			Uri contentUri =ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
			 imagePath = getImagePath(contentUri,null);
		}
	}else if("content".equalsIgnoreCase(uri.getScheme())){//�������document���͵�Uri��ʹ����ͨ��ʽ����
		imagePath = getImagePath(uri,null);
	}
	displayImage(imagePath);//����ͼƬ·����ʾͼƬ///////////////////////////////////////////////////////////////////////////////////////
	picture.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
        public void onGlobalLayout() {
        	picture.getViewTreeObserver().removeGlobalOnLayoutListener(this);

            imagePositionX = picture.getX();
            imagePositionY = picture.getY();
            imageWidth =picture.getWidth();
            imageHeight =picture.getHeight();

            //�����ı���С
            tvInImage.setMaxWidth((int) imageWidth);
        }
    });}

private void handleImageBeforeKitKat(Intent data){
	Uri uri = data.getData();
	String imagePath = getImagePath(uri,null);
	displayImage(imagePath);//////////////////////////////////////////////////////////////////////////////////////////////////////
	picture.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
        public void onGlobalLayout() {
        	picture.getViewTreeObserver().removeGlobalOnLayoutListener(this);

            imagePositionX = picture.getX();
            imagePositionY = picture.getY();
            imageWidth =picture.getWidth();
            imageHeight =picture.getHeight();

            //�����ı���С
            tvInImage.setMaxWidth((int) imageWidth);
        }
    });}

private String getImagePath(Uri uri,String selection){
	String path =null;//ͨ��Uri ��selection ����ȡ��ʵ��ͼƬ·��
	Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
	if(cursor != null){
		if(cursor.moveToFirst()){
			path = cursor.getString(cursor.getColumnIndex(Media.DATA));
			}
		cursor.close();
		}
	    return path;
		}
	
    private void displayImage(String imagePath){
    	if(imagePath != null){
    		Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
    		picture.setImageBitmap(bitmap);
    	}else{
    	Toast.makeText(PhotoAct.this, "����ʧ��", Toast.LENGTH_SHORT).show();
    	}
    		
    }



    //��ͼƬ��ʽ��ȡView��ʾ�����ݣ������ڽ�ͼ��
    public static Bitmap loadBitmapFromView(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    private int count = 0;
    //tvInImage��x�����y�����ƶ���
    private float mDx, mDy;

    //�ƶ�
    private class SimpleGestureListenerImpl extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            //�����ƶ�ʱ��distanceXΪ���������ƶ�ʱ��distanceXΪ��
            //�����ƶ�ʱ��distanceYΪ���������ƶ�ʱ��distanceYΪ��

            count++;
            mDx -= distanceX;
            mDy -= distanceY;

            //�߽���
            mDx = calPosition(imagePositionX - tvInImage.getX(), imagePositionX + imageWidth - (tvInImage.getX() + tvInImage.getWidth()), mDx);
            mDy = calPosition(imagePositionY - tvInImage.getY(), imagePositionY + imageHeight - (tvInImage.getY() + tvInImage.getHeight()), mDy);

            //����ˢ��Ƶ��
            if (count % 5 == 0) {
                tvInImage.setX(tvInImage.getX() + mDx);
                tvInImage.setY(tvInImage.getY() + mDy);
            }

            return true;
        }
    }

    //������ȷ����ʾλ�ã����ܳ����߽磩
    private float calPosition(float min, float max, float current) {
        if (current < min) {
            return min;
        }

        if (current > max) {
            return max;
        }

        return current;
    }

    //��ȡѹ�����bitmap
    	private Bitmap comp(Bitmap image) {  
    	      
    	    ByteArrayOutputStream baos = new ByteArrayOutputStream();         
    	    image.compress(Bitmap.CompressFormat.JPEG, 100, baos);  
    	    if( baos.toByteArray().length / 1024>1024) {//�ж����ͼƬ����1M,����ѹ������������ͼƬ��BitmapFactory.decodeStream��ʱ���    
    	        baos.reset();//����baos�����baos  
    	        image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//����ѹ��50%����ѹ��������ݴ�ŵ�baos��  
    	    }  
    	    ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());  
    	    BitmapFactory.Options newOpts = new BitmapFactory.Options();  
    	    //��ʼ����ͼƬ����ʱ��options.inJustDecodeBounds ���true��  
    	    newOpts.inJustDecodeBounds = true;  
    	    Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);  
    	    newOpts.inJustDecodeBounds = false;  
    	    int w = newOpts.outWidth;  
    	    int h = newOpts.outHeight;  
    	    //���������ֻ��Ƚ϶���800*480�ֱ��ʣ����ԸߺͿ���������Ϊ  
    	    float hh = 800f;//�������ø߶�Ϊ800f  
    	    float ww = 480f;//�������ÿ��Ϊ480f  
    	    //���űȡ������ǹ̶��������ţ�ֻ�ø߻��߿�����һ�����ݽ��м��㼴��  
    	    int be = 1;//be=1��ʾ������  
    	    if (w > h && w > ww) {//�����ȴ�Ļ����ݿ�ȹ̶���С����  
    	        be = (int) (newOpts.outWidth / ww);  
    	    } else if (w < h && h > hh) {//����߶ȸߵĻ����ݿ�ȹ̶���С����  
    	        be = (int) (newOpts.outHeight / hh);  
    	    }  
    	    if (be <= 0)  
    	        be = 1;  
    	    newOpts.inSampleSize = be;//�������ű���  
    	    //���¶���ͼƬ��ע���ʱ�Ѿ���options.inJustDecodeBounds ���false��  
    	    isBm = new ByteArrayInputStream(baos.toByteArray());  
    	    bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);  
    	    //return comp(bitmap)//ѹ���ñ�����С���ٽ�������ѹ��  \
    	    return bitmap;
    	}  
    }

