package me.xiaopan.lifespirit.other;

import me.xiaopan.javalibrary.util.DateTimeUtils;
import me.xiaopan.javalibrary.util.IntegerUtils;
import me.xiaopan.lifespirit.R;
import me.xiaopan.lifespirit.task.Task;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public abstract class Time extends TaskItemImpl {
	/**
	 * KEY - 年 
	 */
	private static final String KEY_YEAR = "KEY_YEAR";
	/**
	 * KEY - 月
	 */
	private static final String KEY_MONTH = "KEY_MONTH";
	/**
	 * KEY - 日
	 */
	private static final String KEY_DAY = "KEY_DAY";
	/**
	 * KEY - 小时 
	 */
	private static final String KEY_HOUR = "KEY_HOUR";
	/**
	 * KEY - 分钟
	 */
	private static final String KEY_MINUTE = "KEY_MINUTE";
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	
	public Time(Context context, Task task, String taskItemName) {
		super(context, task, taskItemName);
		setShowCheckBox(false);
		setShowInTaskInfo(false);
		setNeedValiChecked(false);
		int[] currentTimesBy24Hour = DateTimeUtils.getCurrentTimesBy24Hour();
		setYear(currentTimesBy24Hour[0]);
		setMonth(currentTimesBy24Hour[1]);
		setDay(currentTimesBy24Hour[2]);
		setHour(currentTimesBy24Hour[3]);
		setMinute(currentTimesBy24Hour[4]);
	}
	
	public Time(Context context, Task task, String taskItemName, String timeJSON)  {
		this(context, task, taskItemName);
		fromJSON(timeJSON);
	}

	@Override
	public void execute() {}

	@Override
	public String getHintText() {
		String result = getHour() + getString(R.string.base_dian);
		if(getMinute() > 0){
			result += IntegerUtils.fillZero(getMinute(), 2) + getString(R.string.base_minute);
		}
		return result;
	}
	
	@Override
	public String getShowInTaskInfoText(){
		return IntegerUtils.fillZero(getHour(), 2) + ":" + IntegerUtils.fillZero(getMinute(), 2);
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

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}
	
	public String getNotificationHintTime(){
		String result = getYear() + getString(R.string.base_year) + getMonth() + getString(R.string.base_month) + getDay() + getString(R.string.base_day) + getHour() + getString(R.string.base_dian);
		if(getMinute() > 0){
			result += IntegerUtils.fillZero(getMinute(), 2) + getString(R.string.base_minute);
		}
		return result;
	}
	
	public static int compareTime(Time time1, Time time2){
		return contrastTime(time1.getYear(), time1.getMonth(), time1.getDay(), time1.getHour(), time1.getMinute(), time2.getYear(), time2.getMonth(), time2.getDay(), time2.getHour(), time2.getMinute());
	}
	
	/**
	 * 比较小时和分钟
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static int compareHourAndMinute(Time time1, Time time2){
		int result = time1.getHour() - time2.getHour();
		if(result == 0){
			result = time1.getMinute() - time2.getMinute();
		}
		return result;
	}
	
	/**
	 * 比较时间2跟时间2的大小
	 * @param year1 时间1的年份
	 * @param month1 时间1的月份
	 * @param day1 时间1的日份
	 * @param hour1 时间1的小时
	 * @param minute1 时间1的分钟
	 * @param year2 时间2的年份
	 * @param month2 时间2的月份
	 * @param day2 日份
	 * @param hour2 小时
	 * @param minute2 分钟
	 * @return 大于0：时间1大于时间2；等于0：两者相等；小于0：时间1小于时间2
	 */
	public static int contrastTime(int year1, int month1, int day1, int hour1, int minute1, int year2, int month2, int day2, int hour2, int minute2){
		int result = 0;
		if(year1 == year2){
			if(month1 == month2){
				if(day1 == day2){
					if(hour1 == hour2){
						if(minute1 == minute2){
							result = 0;
						}else{
							result = minute1 - minute2;
						}
					}else{
						result = hour1 - hour2;
					}
				}else{
					result = day1 - day2;
				}
			}else{
				result = month1 - month2;
			}
		}else{
			result = year1 - year2;
		}
		return result;
	}
	
	@Override
	public String toJSON(){
		JSONObject bluetooth = new JSONObject();
		try {
			bluetooth.put(KEY_YEAR, getYear());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			bluetooth.put(KEY_MONTH, getMonth());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			bluetooth.put(KEY_DAY, getDay());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			bluetooth.put(KEY_HOUR, getHour());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			bluetooth.put(KEY_MINUTE, getMinute());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return bluetooth.toString();
	}
	
	@Override
	public void fromJSON(String timeJSON)  {
		if(timeJSON != null){
			try {
				JSONObject time = new JSONObject(timeJSON);
				try {
					setYear(time.getInt(KEY_YEAR));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				try {
					setMonth(time.getInt(KEY_MONTH));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				try {
					setDay(time.getInt(KEY_DAY));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				try {
					setHour(time.getInt(KEY_HOUR));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				try {
					setMinute(time.getInt(KEY_MINUTE));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}