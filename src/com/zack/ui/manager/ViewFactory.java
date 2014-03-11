//package com.zack.ui.manager;
//
//import java.util.HashMap;
//
//import com.zack.utils.GlobalConstants;
//
//import android.content.Context;
//import android.util.Log;
//
//public class ViewFactory 
//{
//	private static ViewFactory instance;
//	private Context context;
//	private HashMap<String, BasePage> views;
//	
//	private ViewFactory(Context context)
//	{
//		this.context=context;
//		views=new HashMap<String, BasePage>();
//	}
//	
//	public static ViewFactory getInstance(Context context)
//	{
//		if(instance==null)
//			instance=new ViewFactory(context);
//		return instance;
//	}
//	
//	
//	
//	/***
//	 * ����һ���Ե�view
//	 * @param <T>
//	 * @param clazz
//	 * @return
//	 */
//	public <T extends BasePage>T  createNewView(Class<T> clazz)
//	{
//		try
//		{
//			return (T)clazz.getConstructor(Context.class).newInstance(context);
//		}
//		catch (Exception e) {
//			Log.e(GlobalConstants.TAG, "create error:"+clazz.getSimpleName());
//		}
//		return null;
//	}
//	
//	
//	/***
//	 * �������õ�View
//	 * @param <T>
//	 * @param clazz
//	 * @return
//	 */
//	public <T extends BasePage>T createCacheView(Class<T> clazz)
//	{
//		try
//		{
//			String name=clazz.getSimpleName();
//			BasePage baseView=views.get(name);
//			if(baseView==null)
//			{
//				baseView=clazz.getConstructor(Context.class).newInstance(context);
//				views.put(name, baseView);
//			}
//			return (T)baseView;
//		}
//		catch(Exception e)
//		{
//			Log.e(GlobalConstants.TAG, "create error:"+clazz.getSimpleName());
//		}
//		return null;
//	}
//
//	
//	/***
//	 * ��������VIEW
//	 */
//	public void recycle()
//	{
//		if(views!=null)
//			views.clear();
//		context=null;
//		instance=null;
//	}
//}
