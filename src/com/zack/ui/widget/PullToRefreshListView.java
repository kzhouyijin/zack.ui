package com.zack.ui.widget;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.zack.ui.R;

/***
 * 下拉刷新ListView
 * @author zack
 *
 */
public class PullToRefreshListView extends ListView implements OnScrollListener {  
  

	//下拉到足够的高度，提示释放刷新
	private final static int RELEASE_To_REFRESH = 0;
	//下拉listview
	private final static int PULL_To_REFRESH = 1;
	//正在刷新
	private final static int REFRESHING = 2;
	//刷新完成
	private final static int DONE = 3;
	private final static int LOADING = 4;
	
	private final static int RATIO = 3;
	private TextView tips1;
	private TextView tips2;
	private ImageView arrowImageView;
	private ProgressBar progressBar;

	
	private int state;
	private boolean isBack;
	//是否设置了头部刷新事件
	private boolean isRefreshable;
	private boolean isRecored;
	private int startY;
	private int firstItemIndex;
	
	private LayoutInflater inflater;
	private LinearLayout headView;
	
	//高度
	private int headContentHeight;
	
	//动画
	private RotateAnimation animation;
	private RotateAnimation reverseAnimation;
	//刷新事件
	private OnRefreshListener refreshListener;
	
	

	private RelativeLayout footView;
	private ProgressBar footProgressBar;
	private TextView footTextView;
	private boolean isFootRefresh=false;
	
	protected Handler handler=new Handler()
	{
		public void handleMessage(Message msg) 
		{
			if(msg.what==1)
			{
				changeHeaderViewByState();
			}
			else if(msg.what==2)
			{
				footProgressBar.setVisibility(View.GONE);
				footTextView.setText("更多");
			}
		}
	};
	public PullToRefreshListView (Context context) {
		super(context);
		init(context);
		// TODO Auto-generated constructor stub
	}

	
	public PullToRefreshListView(Context context,AttributeSet attribute)
	{
		super(context, attribute);
		init(context);
	}
	
	
	private void init(Context context)
	{
		inflater=LayoutInflater.from(context);
		headView=(LinearLayout)inflater.inflate(R.layout.pull_to_refresh_header, null);
		arrowImageView = (ImageView) headView.findViewById(R.id.pull_to_refresh_image);
		arrowImageView.setMinimumWidth(70);
		arrowImageView.setMinimumHeight(50);
		progressBar = (ProgressBar) headView.findViewById(R.id.pull_to_refresh_progress);
		tips1 = (TextView) headView.findViewById(R.id.pull_to_refresh_text);
		tips2 = (TextView) headView.findViewById(R.id.pull_to_refresh_updated_at);
		
		
		measureView(headView);
		headContentHeight = headView.getMeasuredHeight();
		headView.getMeasuredWidth();
		headView.setPadding(0, -1 * headContentHeight, 0, 0);
		headView.invalidate();
		addHeaderView(headView, null, false);
		setOnScrollListener(this);

		
//		footView=(RelativeLayout)inflater.inflate( R.layout.list_foot, null);
//		footProgressBar=(ProgressBar)footView.findViewById(R.id.list_footer_progress);
//		footTextView=(TextView)footView.findViewById(R.id.list_footer_text);
//		addFooterView(footView);
//		footView.setOnClickListener(this);
		
		animation = new RotateAnimation(0, -180,RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		animation.setInterpolator(new LinearInterpolator());
		animation.setDuration(250);
		animation.setFillAfter(true);

		reverseAnimation = new RotateAnimation(-180, 0,RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		reverseAnimation.setInterpolator(new LinearInterpolator());
		reverseAnimation.setDuration(200);
		reverseAnimation.setFillAfter(true);

		state = DONE;
		isRefreshable = false;
	}


	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		firstItemIndex = firstVisibleItem;
		
	}


	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		//如果有刷新事件
		if(isRefreshable)
		{
			switch (event.getAction()) 
			{
			case MotionEvent.ACTION_DOWN:
				if(firstItemIndex==0&&!isRecored)
				{
					isRecored=true;
					startY = (int) event.getY();
				}
				break;
			case MotionEvent.ACTION_UP:
				if(state!=REFRESHING&&state!=LOADING)
				{
					if(state==PULL_To_REFRESH)
					{
						state=DONE;
						changeHeaderViewByState();
					}
					
					if(state==RELEASE_To_REFRESH)
					{
						state=REFRESHING;
						changeHeaderViewByState();
						onRefresh();
					}
				}
				isRecored=false;
				isBack=false;
				break;
			case MotionEvent.ACTION_MOVE:
				int tempY=(int)event.getY();
				if(!isRecored&&firstItemIndex==0)
				{
					isRecored=true;
					startY=tempY;
				}
				if (state != REFRESHING && isRecored && state != LOADING) {
					if (state == RELEASE_To_REFRESH) {
						setSelection(0);
						if (((tempY - startY) / RATIO < headContentHeight)
								&& (tempY - startY) > 0) {
							state = PULL_To_REFRESH;
							changeHeaderViewByState();

						}
						else if (tempY - startY <= 0) {
							state = DONE;
							changeHeaderViewByState();

						}
					}
					if (state == PULL_To_REFRESH) {
						setSelection(0);
						if ((tempY - startY) / RATIO >= headContentHeight) {
							state = RELEASE_To_REFRESH;
							isBack = true;
							changeHeaderViewByState();

						}
						else if (tempY - startY <= 0) {
							state = DONE;
							changeHeaderViewByState();

						}
					}
					if (state == DONE) {
						if (tempY - startY > 0) {
							state = PULL_To_REFRESH;

							changeHeaderViewByState();

						}
					}
					if (state == PULL_To_REFRESH) {
						headView.setPadding(0, -1 * headContentHeight+(tempY - startY) / RATIO, 0, 0);

					}
					if (state == RELEASE_To_REFRESH) 
					{
						headView.setPadding(0, (tempY - startY) / RATIO- headContentHeight, 0, 0);
					}
				}
					
				break;

			default:
				break;
			}
		}
		return super.onTouchEvent(event);
	}
	
	
	private void measureView(View child)
	{
		ViewGroup.LayoutParams params=child.getLayoutParams();
		if(params==null)
			params=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, params.width);
		int lpHeight = params.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}
	
	
	//各个状态时headview显示的东西
	private void changeHeaderViewByState() {
		switch (state) {
		case RELEASE_To_REFRESH:
			arrowImageView.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.GONE);
			tips1.setVisibility(View.VISIBLE);
			tips2.setVisibility(View.VISIBLE);
			arrowImageView.clearAnimation();
			arrowImageView.startAnimation(animation);

			break;
		case PULL_To_REFRESH:
			progressBar.setVisibility(View.GONE);
			tips1.setVisibility(View.VISIBLE);
			tips2.setVisibility(View.VISIBLE);
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.VISIBLE);
			if (isBack) {
				isBack = false;
				arrowImageView.clearAnimation();
				arrowImageView.startAnimation(reverseAnimation);
			} else {
			}
			break;
		case REFRESHING:
			headView.setPadding(0, 0, 0, 0);
			progressBar.setVisibility(View.VISIBLE);
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.GONE);

			break;
		case DONE:
			headView.setPadding(0, -1 * headContentHeight, 0, 0);
			progressBar.setVisibility(View.GONE);
			arrowImageView.clearAnimation();
			arrowImageView.setImageResource(R.drawable.pulltorefresh_arrow);
			break;
		}
	}

	
	public void setOnRefreshListener(OnRefreshListener refreshListener ) {
		this.refreshListener = refreshListener;
		isRefreshable = true;
	}
	

	//刷新listview数据
	private void onRefresh() {
		if (refreshListener != null) {
			refreshListener.onHeaderRefresh(this);			
		}
	}
	
	//刷新完成事件
	public void onHeaderRefreshComplete() {
		state = DONE;
	//	lastUpdatedTextView.setText("已加载完成: " + new Date().toLocaleString());
		handler.sendEmptyMessage(1);
		//Log.v("@@@@@@", "onRefreshComplete() 被调用。。。");
	}
	
	
	
	//刷新完成事件
//	public void onFooterRefreshComplete() {
//		isFootRefresh = false;
//		handler.sendEmptyMessage(2);
//		
//	}
	
	
	public interface OnRefreshListener {
		public void onHeaderRefresh(PullToRefreshListView view);
	}
	
	
//	public void setOnFooterRefreshListener(OnFooterRefreshListener refreshListener ) {
//		this.footerRefreshListener = refreshListener;
//	}
	
	
//	public interface OnFooterRefreshListener
//	{
//		public void onFooterRefresh();
//	}
//
//
//	@Override
//	public void onClick(View view) {
//		if(!isFootRefresh)
//		{
//			isFootRefresh=true;
//
//			footProgressBar.setVisibility(View.VISIBLE);
//			footTextView.setText("正在加载数据...");
//			if(footerRefreshListener!=null)
//				footerRefreshListener.onFooterRefresh();
//		}
//		else 
//		{
//			return;
//		}
//		
//	}
}  
