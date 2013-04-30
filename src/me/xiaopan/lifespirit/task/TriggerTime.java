package me.xiaopan.lifespirit.task;

import me.xiaopan.javalibrary.util.IntegerUtils;
import me.xiaopan.javalibrary.util.Time;

/**
 * 触发时间
 */
public class TriggerTime extends Time{
	public TriggerTime(long milliseconds) {
		super(milliseconds);
	}
	
	public TriggerTime() {
		super();
	}
	
	public String getTimeString(){
		return IntegerUtils.fillZero(getHourOfDay(), 2)+":"+IntegerUtils.fillZero(getMinute(), 2);
	}
}