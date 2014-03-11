package com.zack.ui.view;

import android.R.integer;
import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.zack.ui.R;
import com.zack.ui.manager.BasePage;

//底部按钮导航栏
public class BottomButtonsView extends BaseView implements OnClickListener
{

	
	private ImageView button1;
	private Button button2;
	private Button button3;
	private Button button4;
	private Button button5;
	
	public static final int BUTTON1INDEX=0;
	public static final int BUTTON2INDEX=1;
	public static final int BUTTON3INDEX=2;
	public static final int BUTTON4INDEX=3;
	public static final int BUTTON5INDEX=4;
	
	//切换按钮时触发的事件
	private OnSwitchButtonListener onSwitchButtonListener;
	
	//上次操作的索引
	private int lastIndex;

	public BottomButtonsView(Context context,AttributeSet set) {
		super(context,set);
		
		
		// TODO Auto-generated constructor stub
	}

	
	
	public void setOnSwitchButtonListener(OnSwitchButtonListener listener)
	{
		this.onSwitchButtonListener=listener;
	}
	
	private void onSwitch(int index)
	{
		if(onSwitchButtonListener!=null)
			onSwitchButtonListener.OnSwitch(index);
	}
	
	@Override
	public void onClick(View v) {
		
		int viewid=v.getId();
		//点击同一个按钮，不触发事件
		if(lastIndex==viewid)
			return;
		lastIndex=viewid;
		if(viewid==R.id.buttom_buttons_button1)
		{
			updateButtonState(BUTTON1INDEX);
			onSwitch(BUTTON1INDEX);
		}
		else if(viewid==R.id.button_buttons_button2)
		{
			updateButtonState(BUTTON2INDEX);
			onSwitch(BUTTON2INDEX);
		}
		else if(viewid==R.id.button_buttons_button3)
		{
			updateButtonState(BUTTON3INDEX);
			onSwitch(BUTTON3INDEX);
		}
		else if(viewid==R.id.button_buttons_button4)
		{
			updateButtonState(BUTTON4INDEX);
			onSwitch(BUTTON4INDEX);
		}
		else if(viewid==R.id.button_buttons_button5)
		{
			updateButtonState(BUTTON5INDEX);
			onSwitch(BUTTON5INDEX);
		}
		
		
		
	}

	@Override
	public void initView() {
		View.inflate(context, R.layout.bottom_buttons, this);
		button1=(ImageView)findViewById(R.id.buttom_buttons_button1);
		button2=(Button)findViewById(R.id.button_buttons_button2);
		button3=(Button)findViewById(R.id.button_buttons_button3);
		button4=(Button)findViewById(R.id.button_buttons_button4);
		button5=(Button)findViewById(R.id.button_buttons_button5);
		
		
	}
	
	@Override
	public void initListener() {
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);
		button4.setOnClickListener(this);
		button5.setOnClickListener(this);
		
	}
	
	private void updateButtonState(int index)
	{
		ColorStateList whiteColor = getResources().getColorStateList(R.color.white);
		ColorStateList redColor = getResources().getColorStateList(R.color.red);
		button1.setImageResource(R.drawable.grid_m);
		button2.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_m, 0, 0);
		button2.setTextColor(whiteColor);
		button3.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.mysale_m,
				0, 0);
		button3.setTextColor(whiteColor);
		button4.setCompoundDrawablesWithIntrinsicBounds(0,
				R.drawable.shopcar_m, 0, 0);
		button4.setTextColor(whiteColor);
		button5.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.more_m, 0, 0);
		button5.setTextColor(whiteColor);
		
		switch (index) {
			case BUTTON1INDEX:
				button1.setImageResource(R.drawable.grid_f);
				break;
			case BUTTON2INDEX:
				button2.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_f,
						0, 0);
				button2.setTextColor(redColor);
				break;
			case BUTTON3INDEX:
				button3.setCompoundDrawablesWithIntrinsicBounds(0,
						R.drawable.my_sale_f, 0, 0);
				button3.setTextColor(redColor);
				break;
			case BUTTON4INDEX:
				button4.setCompoundDrawablesWithIntrinsicBounds(0,
						R.drawable.shop_car_f, 0, 0);
				button4.setTextColor(redColor);
				break;
			case BUTTON5INDEX:
				button5.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.more_f,
						0, 0);
				button5.setTextColor(redColor);
				break;
			}
	}
	
	
	
	

	//按钮切换事件
	public interface OnSwitchButtonListener
	{
		public void OnSwitch(int index);
	}
	
	

}
