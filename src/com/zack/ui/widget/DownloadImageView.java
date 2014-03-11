package com.zack.ui.widget;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.PublicKey;

import com.zack.ui.R;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

//�Զ�����ImageView
public class DownloadImageView extends ImageView
{

	//ͼƬ����·��
	private String url;
	//���غ��ͼƬ
	private Bitmap bitmap;
	
	private DownloadImageThread thread;
	
	private Handler handler=new Handler()
	{
		public void handleMessage(android.os.Message msg) 
		{
			//����ͼƬ�ɹ�
			if(bitmap!=null)
			{
				DownloadImageView.this.setImageBitmap(bitmap);
				thread.stop();
				thread=null;
			}
		};
	};
	
	public DownloadImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setImageResource(R.drawable.promotion_default);
	}
	
	
	//��ʼ����ͼƬ
	public void startDownload(String url)
	{
	
		this.url=url;
		thread=new DownloadImageThread();
		thread.start();
	}
		
	public class DownloadImageThread extends Thread
	{
		
		@Override
		public void run()
		{
			
			try
			{
				//����ͼƬ	
				bitmap = BitmapFactory.decodeStream(getImageStream(url));  
				handler.sendEmptyMessage(0);
				
			}
			catch (Exception e) 
			{
				
				// TODO: handle exception
			}
		}
		
		//��ȡ�ļ���
		public InputStream getImageStream(String path) throws Exception
		{  
		        
			URL url = new URL(path);  
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();   
			conn.setConnectTimeout(5 * 1000);  
			conn.setRequestMethod("GET");  
		        
			if(conn.getResponseCode() == HttpURLConnection.HTTP_OK)
			{  
		            
				return conn.getInputStream();  
			}    
			return null;   
		}  
	}
}
