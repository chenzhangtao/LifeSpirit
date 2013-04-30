package me.xiaopan.lifespirit.util;

import me.xiaopan.javalibrary.util.IntegerUtils;
import me.xiaopan.javalibrary.util.Time;

public class TimeUtils {
	public static String getTimeString(Time time){
		return IntegerUtils.fillZero(time.getHourOfDay(), 2)+":"+IntegerUtils.fillZero(time.getMinute(), 2);
	}
}