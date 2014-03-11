package com.zack.ui.widget;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * ���Ե���ʱ��TextView
 * @author ���ս�
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
	 * ��ʼ����ʱ,��������onTimeListener������÷�����Ч
	 * @param millisInFuture
	 * @param countDownInterval
	 */
	public void startCountTime(long millisInFuture,long countDownInterval)
	{
		//��ȡ��֮ǰ�ĵ���ʱ
		if(countTime!=null)
			countTime.cancel();
		if(onTimeListener==null)
			return;
		countTime=new MyCountTime(this,onTimeListener, millisInFuture, countDownInterval);
		countTime.start();
	}
	
	/***
	 * ʱ��仯ʱ���õĽӿ�
	 * @author ���ս�
	 *
	 */
	public interface OnTimeListener
	{
		
		//ʱ��仯ʱ�������¼�
		public void onTick(View view,long time);
		
		//����ʱ����ʱ�������¼�
		public void onFinish(View view);	
	}

	//����ʱ��
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
