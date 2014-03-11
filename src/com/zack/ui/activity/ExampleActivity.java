package com.zack.ui.activity;
//package com.zack.ui.activity;
//
//
//import com.zack.beans.RequestBean;
//import com.zack.beans.ResponseBean;
//import com.zack.net.ProcessListener;
//import com.zack.utils.StringUtils;
//
//import android.R.integer;
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.ProgressDialog;
//import android.app.AlertDialog.Builder;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Looper;
//import android.os.Message;
//import android.widget.Toast;
//
///***
// * 项目基础activity，每个项目要根据实际情况重写
// * @author zack
// *
// */
//public abstract class BaseActivity extends Activity implements ProcessListener {
//
//
//	
//	//页面提示框代码
//	public static int FIRST_VALUE=1000;
//	public static final int SHOW_LOADING_DIALOG=FIRST_VALUE++; //加载提示框
//	public static final int SHOW_PROMPT_DIALOG=FIRST_VALUE++;
//	public static final int SHOW_ERROR_DIALOG=FIRST_VALUE++;
//	public static final int NETWORK_DONE=FIRST_VALUE++;
//	public static final int CANCEL_LOADING_DIALOG=FIRST_VALUE++;
//	
//	protected ProgressDialog progressDialog;
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//
//	}
//	
//	protected Handler handler=new Handler()
//	{
//		public void handleMessage(android.os.Message msg) 
//		{
//			String message="";
//			if(msg.what==SHOW_LOADING_DIALOG)
//			{
//				message = (msg.obj != null) ? (String) msg.obj : "请稍后...";
//
//				progressDialog = new ProgressDialog(BaseActivity.this);
//				progressDialog.setCancelable(false);
//				progressDialog.setMessage(message);
//				progressDialog.show();
//			}
//			//网络连接异常处理
//			else if(msg.what==SHOW_ERROR_DIALOG)
//			{
//				if(progressDialog!=null)
//					progressDialog.dismiss();
//				ResponseBean responseBean=(ResponseBean)msg.obj;
//				if(responseBean.getResponseCode().equals(ResponseBean.RESP_NET_ERROR))
//					message="网络连接错误，请检查网络!";
//				else if(responseBean.getResponseCode().equals(ResponseBean.RESP_PARSER_ERROR))
//					message="服务器发生异常，正在处理中，请稍候!";
//				else if(responseBean.getResponseCode().equals(ResponseBean.RESP_SERVICE_ERROR))
//					message=responseBean.getResponseMsg();
//				showErrorMessage(message);
//			}
//			else if(msg.what==CANCEL_LOADING_DIALOG)
//			{
//				cancelLoadingDialog();
//				if(progressDialog!=null)
//					progressDialog.dismiss();
//			}
//			else if(msg.what==NETWORK_DONE)
//			{
//				cancelLoadingDialog();
//				ResponseBean responseBean=(ResponseBean) msg.obj;
//				DealResponse(responseBean);
//				
//			}
//			else if(msg.what==SHOW_PROMPT_DIALOG)
//			{
//				message = (String)msg.obj;
//				Toast.makeText(BaseActivity.this, message, Toast.LENGTH_SHORT).show();	
//			}
//			
//		};
//	};
//	
//	/***
//	 * 展示提示信息
//	 * @param prompt 
//	 */
//	public void showPromptDialog(String prompt)
//	{
//		Message message=handler.obtainMessage();
//		message.what=SHOW_PROMPT_DIALOG;
//		message.obj=prompt;
//		message.sendToTarget();
//	}
//	
//	/***
//	 * 展示验证错误提示信息
//	 * @param message
//	 */
//	public void showValidateMessage(String message)
//	{
//		if(!StringUtils.isNull(message))
//			showErrorMessage(message);
//	}
//	
//	
//	/***
//	 * 展示错误信息
//	 * @param message
//	 */
//	public void showErrorMessage(String message)
//	{
//		if(!StringUtils.isNull(message))
//			Toast.makeText(this, message, Toast.LENGTH_SHORT).show();	
//	}
//
//	
//	/***
//	 * 加载等待框
//	 */
//	public void showLoadingDialog()
//	{
//		progressDialog = new ProgressDialog(this); 
//		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); 
//		progressDialog.setMessage("正在发送请求，请稍候..."); 
//		progressDialog.setCancelable(false); 
//		progressDialog.show();
//	}
//	
//	
//
//	/***
//	 * 加载等待框
//	 */
//	public void showLoadingDialog(String message)
//	{
//		progressDialog = new ProgressDialog(this); 
//		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); 
//		progressDialog.setMessage(message); 
//		progressDialog.setCancelable(false); 
//		progressDialog.show();
//	}
//	
//	/***
//	 * 取消等待框
//	 */
//	public void cancelLoadingDialog()
//	{
//		progressDialog.dismiss();
//	}
//
//	/////////处理网络请求接口
//	@Override
//	public void isStarted(RequestBean requestBean) 
//	{
//		Message message=handler.obtainMessage();
//		message.what=SHOW_LOADING_DIALOG;
//		//message.obj=requestBean;
//		message.sendToTarget();
//	};
//
//
//	@Override
//	public void isDone(ResponseBean responseBean) {
//		Message message=handler.obtainMessage();
//		message.what=NETWORK_DONE;
//		message.obj=responseBean;
//		message.sendToTarget();
//	}
//
//
//	@Override
//	public void isError(ResponseBean responseBean)
//	{
//		
//		Message message=handler.obtainMessage();
//		message.what=SHOW_ERROR_DIALOG;
//		message.obj=responseBean;
//		message.sendToTarget();
//	
//	}
//		
//	@Override
//	public void doNoNet()
//	{
//	}
//	/////////处理网络请求接口
//	
//	
//	/***
//	 * 网络通信成功，处理返回结果，此时responseBean为正确的数据
//	 */
//	public  void DealResponse(ResponseBean responseBean)
//	{
//		
//	}
//	
//	
//
//	/***
//	 * 退出系统提示框
//	 */
//	public void showExitDialog()
//	{
//		AlertDialog.Builder builder =new Builder(this);
//		builder.setMessage("确定退出系统？");
//		builder.setTitle("退出");
//		builder.setPositiveButton("是", new DialogInterface.OnClickListener() 
//		{
//
//		   @Override
//		   public void onClick(DialogInterface dialog, int which) {
//			   System.exit(0);
//		   }
//		});
//
//		  
//		builder.setNegativeButton("否", new DialogInterface.OnClickListener() 
//		{
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				
//				 dialog.dismiss();
//			}
//		});  
//		builder.create().show();
//		
//	}
//	
//	
//	
//	public void toLastActivity(int code)
//	{
//		this.finishActivity(code);
//	}
//	
//	public void toLastActivity()
//	{
//		this.finish();
//	}
//}
