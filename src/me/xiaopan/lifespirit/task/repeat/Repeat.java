package me.xiaopan.lifespirit.task.repeat;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.content.Context;

/**
 * 重复
 */
public abstract class Repeat implements Serializable{
	private static final long serialVersionUID = 1L;
	private RepeatWay repeatWay;
	private int year;
	private int month;
	private int dayOfMonth;
	
	public Repeat( RepeatWay repeatWay){
		this.repeatWay = repeatWay;
		Calendar calendar = new GregorianCalendar();
		setYear(calendar.get(Calendar.YEAR));
		setMonth(calendar.get(Calendar.MONTH));
		setDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH));
	}
	
	/**
	 * 获取简介
	 * @param context 上下文
	 * @return 信息
	 */
	public abstract String onGetIntro(Context context);

	public RepeatWay geRepeatWay() {
		return repeatWay;
	}

	public void setRepeatWay(RepeatWay repeatWay) {
		this.repeatWay = repeatWay;
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
	
	/**
	 * 重复方式
	 */
	public enum RepeatWay{
		/**
		 * 只执行一次
		 */
		ONLY_ONE_TIME,
		/**
		 * 每天执行一次
		 */
		EVERYDAY,
		/**
		 * 法定工作日
		 */
		STATUTORY_WORKING_DAYS,
		/**
		 * 法定休息日
		 */
		LEGAL_AND_OFF_DAY,
		/**
		 * 自定义
		 */
		CUSTOM;
	}
}