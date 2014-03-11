package com.zack.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/***
 * �Զ���ؼ�������
 * @author ���ս�
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
	 * ��ʼ��ҳ��ؼ�
	 */
	public abstract void initView();
	
	/**
	 * ��ʼ��ҳ���¼�
	 */
	public abstract void initListener();

}
