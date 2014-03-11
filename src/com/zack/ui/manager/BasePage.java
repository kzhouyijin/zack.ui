package com.zack.ui.manager;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/***
 * 自定义页面
 * @author 周艺津
 *
 */
public abstract class BasePage extends RelativeLayout{

	
	/**
	 * 当用户点击其他界面时，此变量标识当前的view是否要保存到viewStack中。
	 */
	public boolean isSave = true;
	
	protected Context context;
	
	public BasePage(Context context)
	{
		super(context);
		this.context=context;
	
		// TODO Auto-generated constructor stub
	}
	

	
	

}
