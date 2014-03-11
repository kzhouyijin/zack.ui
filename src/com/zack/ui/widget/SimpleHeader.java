package com.zack.ui.widget;

import com.zack.ui.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

/***
 * �򵥵���ͷ���ؼ�
 * ���Ϊ����ͼƬ���������������һҳ
 * �м�Ϊҳ�����
 * �ұ�Ϊ�Զ��������ť
 * @author Zack
 *
 */
public class SimpleHeader extends RelativeLayout {

	
	private TextView title;
	private ImageButton backButton;
	private Button operateButton;
	
	public SimpleHeader(Context context, AttributeSet attrs) 
	{
		
		super(context, attrs);
		View layout=LayoutInflater.from(context).inflate(R.layout.simpleheader, this);
		//��ȡ�Զ�������
		TypedArray array=context.obtainStyledAttributes(attrs,R.styleable.simpleheader);
		String titleStr=array.getString(R.styleable.simpleheader_title);
		String operateStr=array.getString(R.styleable.simpleheader_operateTitle);
		boolean haveOperate=array.getBoolean(R.styleable.simpleheader_haveOperation,true);
		Drawable backImg=array.getDrawable(R.styleable.simpleheader_backImg);
		
		//��ȡҳ��ؼ�
		title=(TextView)layout.findViewById(R.id.simpleheader_title);
		
		// TODO Auto-generated constructor stub
	}

}
