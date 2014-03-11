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
// * ��Ŀ����activity��ÿ����ĿҪ����ʵ�������д
// * @author zack
// *
// */
//public abstract class BaseActivity extends Activity implements ProcessListener {
//
//
//	
//	//ҳ����ʾ�����
//	public static int FIRST_VALUE=1000;
//	public static final int SHOW_LOADING_DIALOG=FIRST_VALUE++; //������ʾ��
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
//				message = (msg.obj != null) ? (String) msg.obj : "���Ժ�...";
//
//				progressDialog = new ProgressDialog(BaseActivity.this);
//				progressDialog.setCancelable(false);
//				progressDialog.setMessage(message);
//				progressDialog.show();
//			}
//			//���������쳣����
//			else if(msg.what==SHOW_ERROR_DIALOG)
//			{
//				if(progressDialog!=null)
//					progressDialog.dismiss();
//				ResponseBean responseBean=(ResponseBean)msg.obj;
//				if(responseBean.getResponseCode().equals(ResponseBean.RESP_NET_ERROR))
//					message="�������Ӵ�����������!";
//				else if(responseBean.getResponseCode().equals(ResponseBean.RESP_PARSER_ERROR))
//					message="�����������쳣�����ڴ����У����Ժ�!";
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
//	 * չʾ��ʾ��Ϣ
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
//	 * չʾ��֤������ʾ��Ϣ
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
//	 * չʾ������Ϣ
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
//	 * ���صȴ���
//	 */
//	public void showLoadingDialog()
//	{
//		progressDialog = new ProgressDialog(this); 
//		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); 
//		progressDialog.setMessage("���ڷ����������Ժ�..."); 
//		progressDialog.setCancelable(false); 
//		progressDialog.show();
//	}
//	
//	
//
//	/***
//	 * ���صȴ���
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
//	 * ȡ���ȴ���
//	 */
//	public void cancelLoadingDialog()
//	{
//		progressDialog.dismiss();
//	}
//
//	/////////������������ӿ�
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
//	/////////������������ӿ�
//	
//	
//	/***
//	 * ����ͨ�ųɹ��������ؽ������ʱresponseBeanΪ��ȷ������
//	 */
//	public  void DealResponse(ResponseBean responseBean)
//	{
//		
//	}
//	
//	
//
//	/***
//	 * �˳�ϵͳ��ʾ��
//	 */
//	public void showExitDialog()
//	{
//		AlertDialog.Builder builder =new Builder(this);
//		builder.setMessage("ȷ���˳�ϵͳ��");
//		builder.setTitle("�˳�");
//		builder.setPositiveButton("��", new DialogInterface.OnClickListener() 
//		{
//
//		   @Override
//		   public void onClick(DialogInterface dialog, int which) {
//			   System.exit(0);
//		   }
//		});
//
//		  
//		builder.setNegativeButton("��", new DialogInterface.OnClickListener() 
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
