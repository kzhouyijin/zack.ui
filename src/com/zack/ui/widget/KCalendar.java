package com.zack.ui.widget;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zack.ui.R;



import android.content.Context;
import android.graphics.Color;

import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

/**
 * �Զ�����Է�ҳ�ͱ�ǵ������ؼ�
 * @author ���ս�
 *
 */

public class KCalendar extends ViewFlipper implements android.view.GestureDetector.OnGestureListener {
	public static final int COLOR_BG_WEEK_TITLE = Color.parseColor("#ffbcc9d1"); // ���ڱ��ⱳ����ɫ
	public static final int COLOR_TX_WEEK_TITLE = Color.parseColor("#ff1f303a"); // ���ڱ���������ɫ
	public static final int COLOR_TX_THIS_MONTH_DAY = Color.parseColor("#ff564b4b"); // ��ǰ������������ɫ
	public static final int COLOR_TX_OTHER_MONTH_DAY = Color.parseColor("#ffcccccc"); // ����������������ɫ
	public static final int COLOR_TX_THIS_DAY = Color.parseColor("#ff008000"); // ��������������ɫ
	public static final int COLOR_BG_THIS_DAY = Color.parseColor("#ffcccccc"); // ��������������ɫ
	public static final int COLOR_BG_SELECT_DAY=Color.parseColor("#f0ffff");
	public static final int COLOR_BG_CALENDAR = Color.parseColor("#ffeeeeee"); // ��������ɫ

	private GestureDetector gd; // ���Ƽ�����
	private Animation push_left_in; // ����-���
	private Animation push_left_out; // ����-���
	private Animation push_right_in; // ����-�ҽ�
	private Animation push_right_out; // ����-�ҳ�

	private int ROWS_TOTAL = 6; // ����������
	private int COLS_TOTAL = 7; // ����������
	private String[][] dates = new String[6][7]; // ��ǰ��������

	private OnCalendarClickListener onCalendarClickListener; // ������ҳ�ص�
	private OnCalendarDateChangedListener onCalendarDateChangedListener; // ��������ص�

	private String[] weekday = new String[] { "����", "��һ", "�ܶ�", "����", "����", "����", "����" }; // ���ڱ���

	private int calendarYear; // �������
	private int calendarMonth; // �����·�
	private Date thisday = new Date(); // ����
	private Date calendarday; // ��������µ�һ��(1��)

	private LinearLayout firstCalendar; // ��һ������
	private LinearLayout secondCalendar; // �ڶ�������
	private LinearLayout currentCalendar; // ��ǰ��ʾ������

	private Map<String, Integer> marksMap = new HashMap<String, Integer>(); // ����ĳ�����ӱ���ע(Integer
																			// Ϊbitmap
																			// res
																			// id)
	private Map<String, Integer> dayBgColorMap = new HashMap<String, Integer>(); // ����ĳ�����ӵı���ɫ

	private int selectCol=-1; //ѡ�е���
	private int selectRow=-1; //ѡ�е���
	
	public KCalendar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public KCalendar(Context context) {
		super(context);
		init();
	}

	private void init() {
		setBackgroundColor(COLOR_BG_CALENDAR);
		// ʵ������ʰ������
		gd = new GestureDetector(this);
		// ��ʼ��������������
		push_left_in = AnimationUtils.loadAnimation(getContext(), R.anim.push_left_in);
		push_left_out = AnimationUtils.loadAnimation(getContext(), R.anim.push_left_out);
		push_right_in = AnimationUtils.loadAnimation(getContext(), R.anim.push_right_in);
		push_right_out = AnimationUtils.loadAnimation(getContext(), R.anim.push_right_out);
		push_left_in.setDuration(400);
		push_left_out.setDuration(400);
		push_right_in.setDuration(400);
		push_right_out.setDuration(400);
		// ��ʼ����һ������
		firstCalendar = new LinearLayout(getContext());
		firstCalendar.setOrientation(LinearLayout.VERTICAL);
		firstCalendar.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
		// ��ʼ���ڶ�������
		secondCalendar = new LinearLayout(getContext());
		secondCalendar.setOrientation(LinearLayout.VERTICAL);
		secondCalendar.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
		// ����Ĭ������Ϊ��һ������
		currentCalendar = firstCalendar;
		// ����ViewFlipper
		addView(firstCalendar);
		addView(secondCalendar);
		// �����������
		drawFrame(firstCalendar);
		drawFrame(secondCalendar);
		// ���������ϵ�����(1��)
		calendarYear = thisday.getYear() + 1900;
		calendarMonth = thisday.getMonth();
		calendarday = new Date(calendarYear - 1900, calendarMonth, 1);
		// ���չʾ����
		setCalendarDate();
	}

	private void drawFrame(LinearLayout oneCalendar) {
		// ���Ʊ���(�������յ�������)
		LinearLayout title = new LinearLayout(getContext());
		title.setBackgroundColor(COLOR_BG_WEEK_TITLE);
		title.setOrientation(LinearLayout.HORIZONTAL);
		title.setLayoutParams(new LinearLayout.LayoutParams(-1, 0, 0.5f));
		oneCalendar.addView(title);

		LinearLayout content = new LinearLayout(getContext());
		content.setOrientation(LinearLayout.VERTICAL);
		content.setLayoutParams(new LinearLayout.LayoutParams(-1, 0, 7f));
		content.setBackgroundResource(R.drawable.calendar_bg);
		oneCalendar.addView(content);

		for (int i = 0; i < COLS_TOTAL; i++) {
			TextView view = new TextView(getContext());
			view.setGravity(Gravity.CENTER);
			view.setText(weekday[i]);
			view.setTextColor(COLOR_TX_WEEK_TITLE);
			view.setLayoutParams(new LinearLayout.LayoutParams(0, -1, 1));
			title.addView(view);
		}
		// ���������ϵ���
		for (int i = 0; i < ROWS_TOTAL; i++) {
			LinearLayout row = new LinearLayout(getContext());
			row.setOrientation(LinearLayout.HORIZONTAL);
			row.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 1));
			content.addView(row);
			// ���������ϵ���
			for (int j = 0; j < COLS_TOTAL; j++) {
				RelativeLayout col = new RelativeLayout(getContext());
				col.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1));
				col.setBackgroundResource(R.drawable.calendar_day_bg);
				row.addView(col);
				// ��ÿһ�����Ӽ��ϼ���
				col.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						ViewGroup parent = (ViewGroup) v.getParent();
					//	int row = 0, col = 0;
						for (int i = 0; i < parent.getChildCount(); i++) {
							if (v.equals(parent.getChildAt(i))) {
								selectCol = i;
								break;
							}
						}
						ViewGroup pparent = (ViewGroup) parent.getParent();
						for (int i = 0; i < pparent.getChildCount(); i++) {
							if (parent.equals(pparent.getChildAt(i))) {
								selectRow = i;
								break;
							}
						}
						if (onCalendarClickListener != null) {
					
							onCalendarClickListener.onCalendarClick(selectRow, selectCol, dates[selectRow][selectCol]);
						}
						KCalendar.this.setCalendarDate();
						
					}
				});
			}
		}
	}

	/**
	 * �������(�������ڡ���ǡ�������)
	 */
	protected void setCalendarDate() {
		// �������������ӻ�ȡ��һ�������ڼ�
		int weekday = calendarday.getDay();
		// ÿ���µ�һ��
		int firstDay = 1;
		// ÿ�����м��,����ѭ�����Զ�++
		int day = firstDay;
		// ÿ���µ����һ��
		int lastDay = getDateNum(calendarday.getYear(), calendarday.getMonth());
		// �¸��µ�һ��
		int nextMonthDay = 1;
		int lastMonthDay = 1;
		// ���ÿһ���ո�
		for (int i = 0; i < ROWS_TOTAL; i++) {
			for (int j = 0; j < COLS_TOTAL; j++) {
				// ����µ�һ�첻�������,����Ҫ�����ϸ��µ�ʣ�༸��
				if (i == 0 && j == 0 && weekday != 0) {
					int year = 0;
					int month = 0;
					int lastMonthDays = 0;
					// ����������1�£���һ���¾���ȥ���12��
					if (calendarday.getMonth() == 0) {
						year = calendarday.getYear() - 1;
						month = Calendar.DECEMBER;
					} else {
						year = calendarday.getYear();
						month = calendarday.getMonth() - 1;
					}
					// �ϸ��µ����һ���Ǽ���
					lastMonthDays = getDateNum(year, month);
					// ��һ������չʾ���Ǽ���
					int firstShowDay = lastMonthDays - weekday + 1;
					// ����
					for (int k = 0; k < weekday; k++) {
						lastMonthDay = firstShowDay + k;
						RelativeLayout group = getDateView(0, k);
						group.setGravity(Gravity.CENTER);
						TextView view = null;
						if (group.getChildCount() > 0) {
							view = (TextView) group.getChildAt(0);
						} else {
							LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -1);
							view = new TextView(getContext());
							view.setLayoutParams(params);
							view.setGravity(Gravity.CENTER);
							group.addView(view);
						}
						view.setText(Integer.toString(lastMonthDay));
						view.setTextColor(COLOR_TX_OTHER_MONTH_DAY);
						dates[0][k] = format(new Date(year, month, lastMonthDay));
						
						
						// �������ڱ���ɫ
						if (dayBgColorMap.get(dates[0][k]) != null) {
							view.setBackgroundColor(dayBgColorMap.get(dates[0][k]));
						} else {
							view.setBackgroundColor(Color.TRANSPARENT);
						}
						// ���ñ��
						if(selectRow==0&&selectCol==k)
						{
							view.setBackgroundColor(COLOR_BG_SELECT_DAY);
						}

						setMarker(group, 0, k);

					}
					j = weekday - 1;
					// ����µ�һ��������죬���û����ϸ��µ����ڣ�ֱ�ӻ�������µ�����
				} else {
					RelativeLayout group = getDateView(i, j);
					group.setGravity(Gravity.CENTER);
					TextView view = null;
					if (group.getChildCount() > 0) {
						view = (TextView) group.getChildAt(0);
					} else {
						LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -1);
						view = new TextView(getContext());
						view.setLayoutParams(params);
						view.setGravity(Gravity.CENTER);
						group.addView(view);
					}
					// ����
					if (day <= lastDay) {
						dates[i][j] = format(new Date(calendarday.getYear(), calendarday.getMonth(), day));
						view.setText(Integer.toString(day));
				
						// ����
						if (thisday.getDate() == day && thisday.getMonth() == calendarday.getMonth() && thisday.getYear() == calendarday.getYear()) {
							view.setTextColor(COLOR_TX_THIS_DAY);
							view.setBackgroundColor(COLOR_BG_THIS_DAY);
						} else {
							view.setTextColor(COLOR_TX_THIS_MONTH_DAY);
							view.setBackgroundColor(Color.TRANSPARENT);
						}
						
						if(selectRow==i&&selectCol==j)
						{
							view.setBackgroundColor(COLOR_BG_SELECT_DAY);
						}
						// ��������������һ��Ĭ�ϵ�"����"����ɫ��������������ʱ���Ÿ�������䱳��ɫ
						// �������ڱ���ɫ
						if (dayBgColorMap.get(dates[i][j]) != null) {
							view.setBackgroundColor(dayBgColorMap.get(dates[i][j]));
						}
						// ���ñ��
						setMarker(group, i, j);

						day++;
						// �¸���
					} else {
						if (calendarday.getMonth() == Calendar.DECEMBER) {
							dates[i][j] = format(new Date(calendarday.getYear() + 1, Calendar.JANUARY, nextMonthDay));
						} else {
							dates[i][j] = format(new Date(calendarday.getYear(), calendarday.getMonth() + 1, nextMonthDay));
						}
						view.setText(Integer.toString(nextMonthDay));
						view.setTextColor(COLOR_TX_OTHER_MONTH_DAY);
						// �������ڱ���ɫ
						if (dayBgColorMap.get(dates[i][j]) != null) {
							view.setBackgroundColor(dayBgColorMap.get(dates[i][j]));
						} else {
							view.setBackgroundColor(Color.TRANSPARENT);
						}
						if(selectRow==i&&selectCol==j)
						{
							view.setBackgroundColor(COLOR_BG_SELECT_DAY);
						}
						
						// ���ñ��
						setMarker(group, i, j);
						nextMonthDay++;
					}
				}
			}
		}
	}

	/**
	 * onClick�ӿڻص�
	 */
	public interface OnCalendarClickListener {
		void onCalendarClick(int row, int col, String dateFormat);
	}

	/**
	 * ondateChange�ӿڻص�
	 */
	public interface OnCalendarDateChangedListener {
		void onCalendarDateChanged(int year, int month);
	}

	/**
	 * ���ݾ����ĳ��ĳ�£�չʾһ������
	 * 
	 * @param year
	 * @param month
	 */
	public void showCalendar(int year, int month) {
		calendarYear = year;
		calendarMonth = month - 1;
		calendarday = new Date(calendarYear - 1900, calendarMonth, 1);
		setCalendarDate();
	}

	/**
	 * ���ݵ�ǰ�£�չʾһ������
	 * 
	 * @param year
	 * @param month
	 */
	public void showCalendar() {
		Date now = new Date();
		calendarYear = now.getYear() + 1900;
		calendarMonth = now.getMonth();
		calendarday = new Date(calendarYear - 1900, calendarMonth, 1);
		setCalendarDate();
	}

	/**
	 * ��һ������
	 */
	public synchronized void nextMonth() {
		
		selectCol=-1;
		selectRow=-1;
		// �ı���������˳��
		if (currentCalendar == firstCalendar) {
			currentCalendar = secondCalendar;
		} else {
			currentCalendar = firstCalendar;
		}
		// ���ö���
		setInAnimation(push_left_in);
		setOutAnimation(push_left_out);
		// �ı���������
		if (calendarMonth == Calendar.DECEMBER) {
			calendarYear++;
			calendarMonth = Calendar.JANUARY;
		} else {
			calendarMonth++;
		}
		calendarday = new Date(calendarYear - 1900, calendarMonth, 1);
		// �������
		setCalendarDate();
		// �·�����һ��
		showNext();
		// �ص�
		if (onCalendarDateChangedListener != null) {
			onCalendarDateChangedListener.onCalendarDateChanged(calendarYear, calendarMonth + 1);
		}
	}

	/**
	 * ��һ������
	 */
	public synchronized void lastMonth() {
		selectCol=-1;
		selectRow=-1;
		if (currentCalendar == firstCalendar) {
			currentCalendar = secondCalendar;
		} else {
			currentCalendar = firstCalendar;
		}
		setInAnimation(push_right_in);
		setOutAnimation(push_right_out);
		if (calendarMonth == Calendar.JANUARY) {
			calendarYear--;
			calendarMonth = Calendar.DECEMBER;
		} else {
			calendarMonth--;
		}
		calendarday = new Date(calendarYear - 1900, calendarMonth, 1);
		setCalendarDate();
		showPrevious();
		if (onCalendarDateChangedListener != null) {
			onCalendarDateChangedListener.onCalendarDateChanged(calendarYear, calendarMonth + 1);
		}
	}

	/**
	 * ��ȡ������ǰ���
	 */
	public int getCalendarYear() {
		return calendarday.getYear() + 1900;
	}

	/**
	 * ��ȡ������ǰ�·�
	 */
	public int getCalendarMonth() {
		return calendarday.getMonth() + 1;
	}

	/**
	 * ����������һ�����
	 * 
	 * @param date
	 *            ����
	 * @param id
	 *            bitmap res id
	 */
	public void addMark(Date date, int id) {
		addMark(format(date), id);
	}
	
	/**
	 * ����������һ�����
	 * 
	 * @param date
	 *            ����
	 * @param id
	 *            bitmap res id
	 */
	void addMark(String date, int id) {
		marksMap.put(date, id);
		setCalendarDate();
	}

	/**
	 * ����������һ����
	 * 
	 * @param date
	 *            ����
	 * @param id
	 *            bitmap res id
	 */
	public void addMarks(Date[] date, int id) {
		for (int i = 0; i < date.length; i++) {
			marksMap.put(format(date[i]), id);
		}
		setCalendarDate();
	}

	/**
	 * ����������һ����
	 * 
	 * @param date
	 *            ����
	 * @param id
	 *            bitmap res id
	 */
	public void addMarks(List<Date> date, int id) {
		for (int i = 0; i < date.size(); i++) {
			marksMap.put(format(date.get(i)), id);
		}
		setCalendarDate();
	}

	/**
	 * �Ƴ������ϵı��
	 */
	public void removeMark(Date date) {
		removeMark(format(date));
	}

	/**
	 * �Ƴ������ϵı��
	 */
	public void removeMark(String date) {
		marksMap.remove(date);
		setCalendarDate();
	}
	
	/**
	 * �Ƴ������ϵ����б��
	 */
	public void removeAllMarks() {
		marksMap.clear();
		setCalendarDate();
	}

	/**
	 * ������������ĳ�����ڵı���ɫ
	 * 
	 * @param date
	 * @param color
	 */
	public void setCalendarDayBgColor(Date date, int color) {
		setCalendarDayBgColor(format(date), color);
	}

	/**
	 * ������������ĳ�����ڵı���ɫ
	 * 
	 * @param date
	 * @param color
	 */
	void setCalendarDayBgColor(String date, int color) {
		dayBgColorMap.put(date, color);
		setCalendarDate();
	}

	/**
	 * ��������һ�����ڵı���ɫ
	 * 
	 * @param date
	 * @param color
	 */
	public void setCalendarDaysBgColor(List<String> date, int color) {
		for (int i = 0; i < date.size(); i++) {
			dayBgColorMap.put(date.get(i), color);
		}
		setCalendarDate();
	}
	
	/**
	 * ��������һ�����ڵı���ɫ
	 * 
	 * @param date
	 * @param color
	 */
	public void setCalendarDayBgColor(String[] date, int color) {
		for (int i = 0; i < date.length; i++) {
			dayBgColorMap.put(date[i], color);
		}
		setCalendarDate();
	}

	/**
	 * �Ƴ���������ĳ�����ڵı���ɫ
	 * 
	 * @param date
	 * @param color
	 */
	public void removeCalendarDayBgColor(Date date) {
		removeCalendarDayBgColor(format(date));
	}

	/**
	 * �Ƴ���������ĳ�����ڵı���ɫ
	 * 
	 * @param date
	 * @param color
	 */
	public void removeCalendarDayBgColor(String date) {
		dayBgColorMap.remove(date);
		setCalendarDate();
	}
	
	/**
	 * �Ƴ���������ĳ�����ڵı���ɫ
	 * 
	 * @param date
	 * @param color
	 */
	public void removeAllBgColor(String date) {
		dayBgColorMap.clear();
		setCalendarDate();
	}

	/**
	 * �������кŻ�ð�װÿһ�����ӵ�LinearLayout
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public String getDate(int row, int col) {
		return dates[row][col];
	}
	
	/**
	 * ĳ���Ƿ񱻱����
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public boolean hasMarked(String date) {
		return marksMap.get(date) == null ? false : true;
	}
	
	/**
	 * ������б���Լ�����
	 */
	public void clearAll() {
		marksMap.clear();
		dayBgColorMap.clear();
	}

	/***********************************************
	 * 
	 * private methods
	 * 
	 **********************************************/

	// ���ñ��
	private void setMarker(RelativeLayout group, int i, int j) {
		int childCount = group.getChildCount();
		if (marksMap.get(dates[i][j]) != null) {
			if (childCount < 2) {
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-2, -2);
				params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
				params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
				params.setMargins(0, 5, 5, 0);
				ImageView markView = new ImageView(getContext());
				markView.setImageResource(marksMap.get(dates[i][j]));
				markView.setLayoutParams(params);
				group.addView(markView);
			}
		} else {
			if (childCount > 1) {
				group.removeView(group.getChildAt(1));
			}
		}
		

	}
	
	
	/**
	 * ��ȡѡ�������
	 * @return
	 */
	public Date getSelectedDate()
	{
		if(selectRow==-1||selectCol==-1)
			return null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			return sdf.parse(getDate(selectRow, selectCol));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
			

			
	}

	/**
	 * ����ĳ��ĳ���ж�����
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	private int getDateNum(int year, int month) {
		Calendar time = Calendar.getInstance();
		time.clear();
		time.set(Calendar.YEAR, year + 1900);
		time.set(Calendar.MONTH, month);
		return time.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * �������кŻ�ð�װÿһ�����ӵ�LinearLayout
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	private RelativeLayout getDateView(int row, int col) {
		return (RelativeLayout) ((LinearLayout) ((LinearLayout) currentCalendar.getChildAt(1)).getChildAt(row)).getChildAt(col);
	}

	/**
	 * ��Dateת�����ַ���->2013-3-3
	 */
	private String format(Date d) {
		return addZero(d.getYear() + 1900, 4) + "-" + addZero(d.getMonth() + 1, 2) + "-" + addZero(d.getDate(), 2);
	}

	// 2��4
	private static String addZero(int i, int count) {
		if (count == 2) {
			if (i < 10) {
				return "0" + i;
			}
		} else if (count == 4) {
			if (i < 10) {
				return "000" + i;
			} else if (i < 100 && i > 10) {
				return "00" + i;
			} else if (i < 1000 && i > 100) {
				return "0" + i;
			}
		}
		return "" + i;
	}

	/***********************************************
	 * 
	 * Override methods
	 * 
	 **********************************************/

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (gd != null) {
			if (gd.onTouchEvent(ev))
				return true;
		}
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return this.gd.onTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		// ����/�ϻ���
		if (e1.getX() - e2.getX() > 20) {
			nextMonth();
		}
		// ����/�»���
		else if (e1.getX() - e2.getX() < -20) {
			lastMonth();
		}
		return false;
	}

	/***********************************************
	 * 
	 * get/set methods
	 * 
	 **********************************************/

	public OnCalendarClickListener getOnCalendarClickListener() {
		return onCalendarClickListener;
	}

	public void setOnCalendarClickListener(OnCalendarClickListener onCalendarClickListener) {
		this.onCalendarClickListener = onCalendarClickListener;
	}

	public OnCalendarDateChangedListener getOnCalendarDateChangedListener() {
		return onCalendarDateChangedListener;
	}

	public void setOnCalendarDateChangedListener(OnCalendarDateChangedListener onCalendarDateChangedListener) {
		this.onCalendarDateChangedListener = onCalendarDateChangedListener;
	}

	public Date getThisday() {
		return thisday;
	}

	public void setThisday(Date thisday) {
		this.thisday = thisday;
	}

	public Map<String, Integer> getDayBgColorMap() {
		return dayBgColorMap;
	}

	public void setDayBgColorMap(Map<String, Integer> dayBgColorMap) {
		this.dayBgColorMap = dayBgColorMap;
	}
	

}