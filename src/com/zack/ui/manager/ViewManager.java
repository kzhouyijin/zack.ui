//package com.zack.ui.manager;
//
//import java.util.Stack;
//import com.zack.utils.GlobalConstants;
//
//import android.content.Context;
//import android.util.Log;
//import android.view.View;
//import android.widget.RelativeLayout;
//import android.widget.RelativeLayout.LayoutParams;
//import android.widget.TextView;
//
//
//
//
//
//public class ViewManager 
//{
//	
//	/**
//	 * 需要手动把页面中的控件和ViewManager关联起来
//	 */
//	public RelativeLayout main, container;
//	public OnFooterListener footerListener;
//	private LayoutParams params;
//	private static ViewManager instance;
//	private static ViewFactory factory;
//	private Stack<BasePage> viewStack;
//	private Context context;
//	
//	
//	private ViewManager(Context context)
//	{
//		this.context=context;
//		params = new LayoutParams(LayoutParams.FILL_PARENT,
//				LayoutParams.FILL_PARENT);
//
//
//		if (null == factory)
//			factory = ViewFactory.getInstance(context);
//		viewStack = new Stack<BasePage>();
//	}
//	
//	
//	/***
//	 * 初始化ViewManager
//	 * @param context
//	 * @param layoutId 页面对应的layout
//	 * @param containId 内容控件对应的id
//	 * @return
//	 */
//	public static ViewManager getInstance(Context context)
//	{
//		if (null == instance)
//			instance = new ViewManager(context);
//		return instance;
//	}
//	
//
//	
//	private void addSubView(BasePage subView)
//	{
//		if (container.getChildCount() > 0)
//		{
//			BasePage view = (BasePage) container.getChildAt(0);
//			Log.i(GlobalConstants.PKG, "add view:"+view.getClass().getSimpleName());
//			//如果当前页面和新添加的页面相同
//			if (view.getClass()==subView.getClass())
//			{
//				return;
//			}
//			// 当前的container加入下个view时，将现在的view加入View栈
//			addToStackView(view);
//			container.removeView(view);
//		}
//		//展示新加入的页面
//		container.addView(subView, params);
//		System.gc();
//		System.runFinalization();
//	}
//	
//	
//	public void addToStackView(BasePage view)
//	{
//		//页面需要保存
//		if (view.isSave)
//		{
//			viewStack.push(view);
//		}
//	}
//	
//	
//	
//	public BasePage popStackView()
//	{
//		if(!viewStack.isEmpty())
//			return viewStack.pop();
//		return null;
//	}
//	
//	
//	//------------------------下面的都是界面的切换方法-----------------------------
//
//	//无参有缓存
//	public <T extends BasePage> void switch2CacheView(Class<T> clazz)
//	{
//		addSubView(factory.createCacheView(clazz));
//	}
//	//无参无缓存
//	public <T extends BasePage> void switch2View(Class<T> clazz)
//	{
//		addSubView(factory.createNewView(clazz));
//	}
//}
