//package com.zack.ui.manager;
//
//
//
//import com.zack.utils.GlobalConstants;
//import android.content.Context;
//import android.util.DisplayMetrics;
//import android.util.Log;
//import android.view.Display;
//import android.view.WindowManager;
//public class WindowPramsManager 
//
//{
//	
//	private final String TAG = "WindowManager";
//	private static WindowPramsManager instance;
//	private WindowManager windowManager;
//	private Context context;
//	private int baseWidth;
//	
//	public static int SCREEN_WIDTH;
//	public static int SCREEN_HEIGHT;
//	public static float SCREEN_DENSITY;
//
//
//	private WindowPramsManager(Context context){
//		this.context = context;
//
//	}
//	
//	
//	public static WindowPramsManager getInstance(Context context)
//	{
//		if(null == instance) instance = new WindowPramsManager(context);
//		return instance;
//	}
//	
//	
//	public void init()
//	{
//		initParams();
//	}
//	
//	
//	private void initParams()
//	{
//		DisplayMetrics dm = new DisplayMetrics();
//		windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
//		Display display = windowManager.getDefaultDisplay();
//		display.getMetrics(dm);
//		SCREEN_DENSITY = dm.density;
//		SCREEN_WIDTH = dm.widthPixels;
//		SCREEN_HEIGHT = dm.heightPixels;
//		
//		SCREEN_DENSITY = (float)SCREEN_WIDTH/(float)GlobalConstants.BASE_SCREEN_HEIGHT;
//		
//		Log.d(TAG, "SCREEN_WIDTH="+SCREEN_WIDTH);
//		Log.d(TAG, "SCREEN_HEIGHT="+SCREEN_HEIGHT);
//		Log.d(TAG, "SCREEN_DENSITY="+SCREEN_DENSITY);
//	}
//
//}
