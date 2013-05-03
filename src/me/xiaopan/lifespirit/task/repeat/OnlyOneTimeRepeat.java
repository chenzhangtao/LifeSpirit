package me.xiaopan.lifespirit.task.repeat;

import java.util.Calendar;
import java.util.GregorianCalendar;

import me.xiaopan.lifespirit2.R;

import android.content.Context;

/**
 * 只执行一次
 */
public class OnlyOneTimeRepeat extends BaseRepeatWay{
	private static final long serialVersionUID = 1L;
	private int year;
	private int month;
	private int dayOfMonth;
	
	public OnlyOneTimeRepeat(){
		super(Way.ONLY_ONE_TIME);
		Calendar calendar = new GregorianCalendar();
		setYear(calendar.get(Calendar.YEAR));
		setMonth(calendar.get(Calendar.MONTH) + 1);
		setDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH));
	}

	@Override
	public String onGetIntro(Context context) {
		return context.getString(R.string.repeat_onlyOneTime, (getYear()+context.getString(R.string.base_year)+getMonth()+context.getString(R.string.base_month)+getDayOfMonth()+context.getString(R.string.base_day)));
	}
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDayOfMonth() {
		return dayOfMonth;
	}
	public void setDayOfMonth(int dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}
}