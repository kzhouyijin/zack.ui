package com.zack.ui.manager;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/***
 * �Զ���ҳ��
 * @author ���ս�
 *
 */
public abstract class BasePage extends RelativeLayout{

	
	/**
	 * ���û������������ʱ���˱�����ʶ��ǰ��view�Ƿ�Ҫ���浽viewStack�С�
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
