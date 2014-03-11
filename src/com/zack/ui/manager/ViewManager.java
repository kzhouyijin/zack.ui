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
//	 * ��Ҫ�ֶ���ҳ���еĿؼ���ViewManager��������
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
//	 * ��ʼ��ViewManager
//	 * @param context
//	 * @param layoutId ҳ���Ӧ��layout
//	 * @param containId ���ݿؼ���Ӧ��id
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
//			//�����ǰҳ�������ӵ�ҳ����ͬ
//			if (view.getClass()==subView.getClass())
//			{
//				return;
//			}
//			// ��ǰ��container�����¸�viewʱ�������ڵ�view����Viewջ
//			addToStackView(view);
//			container.removeView(view);
//		}
//		//չʾ�¼����ҳ��
//		container.addView(subView, params);
//		System.gc();
//		System.runFinalization();
//	}
//	
//	
//	public void addToStackView(BasePage view)
//	{
//		//ҳ����Ҫ����
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
//	//------------------------����Ķ��ǽ�����л�����-----------------------------
//
//	//�޲��л���
//	public <T extends BasePage> void switch2CacheView(Class<T> clazz)
//	{
//		addSubView(factory.createCacheView(clazz));
//	}
//	//�޲��޻���
//	public <T extends BasePage> void switch2View(Class<T> clazz)
//	{
//		addSubView(factory.createNewView(clazz));
//	}
//}
