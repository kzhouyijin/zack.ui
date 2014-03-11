package com.zack.ui.widget;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * 可以倒计时的TextView
 * @author 周艺津
 *
 */
public class CountTimeTextView extends TextView {

	
	private MyCountTime countTime;
	private OnTimeListener onTimeListener;
	
	public OnTimeListener getOnTimeListener() {
		return onTimeListener;
	}

	public void setOnTimeListener(OnTimeListener onTimeListener) {
		this.onTimeListener = onTimeListener;
	}

	public CountTimeTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public CountTimeTextView(Context context,AttributeSet set)
	{
		super(context,set);
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * 开始倒计时,需先设置onTimeListener，否则该方法无效
	 * @param millisInFuture
	 * @param countDownInterval
	 */
	public void startCountTime(long millisInFuture,long countDownInterval)
	{
		//先取消之前的倒计时
		if(countTime!=null)
			countTime.cancel();
		if(onTimeListener==null)
			return;
		countTime=new MyCountTime(this,onTimeListener, millisInFuture, countDownInterval);
		countTime.start();
	}
	
	/***
	 * 时间变化时调用的接口
	 * @author 周艺津
	 *
	 */
	public interface OnTimeListener
	{
		
		//时间变化时触发的事件
		public void onTick(View view,long time);
		
		//倒计时结束时触发的事件
		public void onFinish(View view);	
	}

	//倒计时类
	private class MyCountTime extends CountDownTimer
	{
		private OnTimeListener listener;
		private View view;
		
		
		public MyCountTime(View view,OnTimeListener listener,long millisInFuture, long countDownInterval)
		{
			super(millisInFuture, countDownInterval);
			this.listener=listener;
			this.view=view;
			
		}
		

		private MyCountTime(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onTick(long millisUntilFinished) {
			
			listener.onTick(view,millisUntilFinished);
		}

		@Override
		public void onFinish() {
			listener.onFinish(view);
			
		}
		
	}
}
