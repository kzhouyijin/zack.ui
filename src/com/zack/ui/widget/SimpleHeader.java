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
 * 简单导航头部控件
 * 左边为返回图片按键，点击返回上一页
 * 中间为页面标题
 * 右边为自定义操作按钮
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
		//获取自定义属性
		TypedArray array=context.obtainStyledAttributes(attrs,R.styleable.simpleheader);
		String titleStr=array.getString(R.styleable.simpleheader_title);
		String operateStr=array.getString(R.styleable.simpleheader_operateTitle);
		boolean haveOperate=array.getBoolean(R.styleable.simpleheader_haveOperation,true);
		Drawable backImg=array.getDrawable(R.styleable.simpleheader_backImg);
		
		//获取页面控件
		title=(TextView)layout.findViewById(R.id.simpleheader_title);
		
		// TODO Auto-generated constructor stub
	}

}
