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
    //位于图片中的文本组件
    private TextView tvInImage;
    //图片和文本的父组件
    private View containerView;
    //父组件的尺寸
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
        	public void onClick(View v){//创建file对象用于存储拍照后的图片
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
        	    startActivityForResult(intent,TAKE_PHOTO);//启动相机程序
        			}
        });
        chooseFromAlbum.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v){
        		Intent intent = new Intent("android.intent.action.GET_CONTENT");
        		intent.setType("image/*");
        		startActivityForResult(intent,CHOOSE_PHOTO);//打开相册
        	}
        });
        //输入框
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
        //移动
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
    			 
    			    Toast.makeText(getApplicationContext(), "抱歉，没有找到您的录音机", Toast.LENGTH_SHORT).show();
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
			            Toast.makeText(PhotoAct.this, "图片已保存", Toast.LENGTH_SHORT).show();
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
			   startActivityForResult(intent,CROP_PHOTO);//启动裁剪程序
		   }break;
	   case CROP_PHOTO:
		   if(resultCode ==RESULT_OK){
			   try{
				   Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
				   picture.setImageBitmap(bitmap);//将裁剪后的照片显示出来
				   picture.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
       	            public void onGlobalLayout() {
       	            	picture.getViewTreeObserver().removeGlobalOnLayoutListener(this);

       	                imagePositionX = picture.getX();
       	                imagePositionY = picture.getY();
       	                imageWidth =picture.getWidth();
       	                imageHeight =picture.getHeight();

       	                //设置文本大小
       	                tvInImage.setMaxWidth((int) imageWidth);
       	            }
       	        });
				   
				   
			   }catch(FileNotFoundException e ){
				   e.printStackTrace();
			   }
		   }
		   break;
	   case CHOOSE_PHOTO:
		   if(resultCode == RESULT_OK){//判断手机系统版本号
			   if(Build.VERSION.SDK_INT>=19){//4.4及以上系统使用这个方法处理图片
				   handleImageOnKitKat(data);
			   }else{//4.4及以下系统使用这个方法处理图片
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
	if(DocumentsContract.isDocumentUri(PhotoAct.this, uri)){//如果是document类型的Uri,则通过document id处理
		String docId = DocumentsContract.getDocumentId(uri);
		if("com.android.providers.media.documents".equals(uri.getAuthority())){
			String id = docId.split(":")[1];//解析出数字格式的Id
			String selection = MediaStore.Images.Media._ID + "=" +id;
			imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
		}else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
			Uri contentUri =ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
			 imagePath = getImagePath(contentUri,null);
		}
	}else if("content".equalsIgnoreCase(uri.getScheme())){//如果不是document类型的Uri则使用普通方式处理
		imagePath = getImagePath(uri,null);
	}
	displayImage(imagePath);//根据图片路径显示图片///////////////////////////////////////////////////////////////////////////////////////
	picture.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
        public void onGlobalLayout() {
        	picture.getViewTreeObserver().removeGlobalOnLayoutListener(this);

            imagePositionX = picture.getX();
            imagePositionY = picture.getY();
            imageWidth =picture.getWidth();
            imageHeight =picture.getHeight();

            //设置文本大小
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

            //设置文本大小
            tvInImage.setMaxWidth((int) imageWidth);
        }
    });}

private String getImagePath(Uri uri,String selection){
	String path =null;//通过Uri 和selection 来获取真实的图片路径
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
    	Toast.makeText(PhotoAct.this, "操作失败", Toast.LENGTH_SHORT).show();
    	}
    		
    }



    //以图片形式获取View显示的内容（类似于截图）
    public static Bitmap loadBitmapFromView(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    private int count = 0;
    //tvInImage的x方向和y方向移动量
    private float mDx, mDy;

    //移动
    private class SimpleGestureListenerImpl extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            //向右移动时，distanceX为负；向左移动时，distanceX为正
            //向下移动时，distanceY为负；向上移动时，distanceY为正

            count++;
            mDx -= distanceX;
            mDy -= distanceY;

            //边界检查
            mDx = calPosition(imagePositionX - tvInImage.getX(), imagePositionX + imageWidth - (tvInImage.getX() + tvInImage.getWidth()), mDx);
            mDy = calPosition(imagePositionY - tvInImage.getY(), imagePositionY + imageHeight - (tvInImage.getY() + tvInImage.getHeight()), mDy);

            //控制刷新频率
            if (count % 5 == 0) {
                tvInImage.setX(tvInImage.getX() + mDx);
                tvInImage.setY(tvInImage.getY() + mDy);
            }

            return true;
        }
    }

    //计算正确的显示位置（不能超出边界）
    private float calPosition(float min, float max, float current) {
        if (current < min) {
            return min;
        }

        if (current > max) {
            return max;
        }

        return current;
    }

    //获取压缩后的bitmap
    	private Bitmap comp(Bitmap image) {  
    	      
    	    ByteArrayOutputStream baos = new ByteArrayOutputStream();         
    	    image.compress(Bitmap.CompressFormat.JPEG, 100, baos);  
    	    if( baos.toByteArray().length / 1024>1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出    
    	        baos.reset();//重置baos即清空baos  
    	        image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中  
    	    }  
    	    ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());  
    	    BitmapFactory.Options newOpts = new BitmapFactory.Options();  
    	    //开始读入图片，此时把options.inJustDecodeBounds 设回true了  
    	    newOpts.inJustDecodeBounds = true;  
    	    Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);  
    	    newOpts.inJustDecodeBounds = false;  
    	    int w = newOpts.outWidth;  
    	    int h = newOpts.outHeight;  
    	    //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为  
    	    float hh = 800f;//这里设置高度为800f  
    	    float ww = 480f;//这里设置宽度为480f  
    	    //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可  
    	    int be = 1;//be=1表示不缩放  
    	    if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放  
    	        be = (int) (newOpts.outWidth / ww);  
    	    } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放  
    	        be = (int) (newOpts.outHeight / hh);  
    	    }  
    	    if (be <= 0)  
    	        be = 1;  
    	    newOpts.inSampleSize = be;//设置缩放比例  
    	    //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了  
    	    isBm = new ByteArrayInputStream(baos.toByteArray());  
    	    bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);  
    	    //return comp(bitmap)//压缩好比例大小后再进行质量压缩  \
    	    return bitmap;
    	}  
    }

