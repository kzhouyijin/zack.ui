package com.zack.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/***
 * 自定义控件基础类
 * @author 周艺津
 *
 */
public abstract class BaseView extends RelativeLayout
{
	
	protected Context context;
	
	public BaseView(Context context,AttributeSet set)
	{
		super(context,set);
		this.context=context;
		initView();
		initListener();
		// TODO Auto-generated constructor stub
	}
	

	
	
	/**
	 * 初始化页面控件
	 */
	public abstract void initView();
	
	/**
	 * 初始化页面事件
	 */
	public abstract void initListener();

}
