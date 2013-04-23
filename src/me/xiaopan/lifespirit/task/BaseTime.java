package me.xiaopan.lifespirit.task;

import me.xiaopan.javalibrary.util.IntegerUtils;
import me.xiaopan.javalibrary.util.Time;

public class BaseTime extends Time{
	public BaseTime(long milliseconds) {
		super(milliseconds);
	}
	
	public BaseTime() {
		super();
	}
	
	public String getTimeString(){
		return IntegerUtils.fillZero(getHourOfDay(), 2)+":"+IntegerUtils.fillZero(getMinute(), 2);
	}
}