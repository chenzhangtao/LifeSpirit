package me.xiaopan.lifespirit.util;

import me.xiaopan.androidlibrary.util.Logger;
import me.xiaopan.javalibrary.util.IntegerUtils;
import me.xiaopan.javalibrary.util.Time;

public class TimeUtils {
	/**
	 * 获取数字时钟字符串，例如07:04
	 * @param time
	 * @return
	 */
	public static final String getDigitalClockString(Time time){
		return IntegerUtils.fillZero(time.getHourOfDay(), 2)+":"+IntegerUtils.fillZero(time.getMinute(), 2);
	}
	
	/**
	 * 比较time1和time2的大小
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static final int compare(Time time1, Time time2){
		Logger.w("time1:"+time1.toStringBy24Hour()+" time2:"+time2.toStringBy24Hour());
		if(time1.getYear() != time2.getYear()){
			return time1.getYear() - time2.getYear();
		}else if(time1.getMonth() != time2.getMonth()){
			return time1.getMonth() - time2.getMonth();
		}else if(time1.getDayOfMonth() != time2.getDayOfMonth()){
			return time1.getDayOfMonth() - time2.getDayOfMonth();
		}else if(time1.getHourOfDay() != time2.getHourOfDay()){
			return time1.getHourOfDay() - time2.getHourOfDay();
		}else if(time1.getMinute() != time2.getMinute()){
			return time1.getMinute() - time2.getMinute();
		}else{
			return 0;
		}
	}
}