package me.xiaopan.lifespirit.task.repeatway;

import java.util.Calendar;
import java.util.GregorianCalendar;

import me.xiaopan.javalibrary.util.Time;
import me.xiaopan.lifespirit.task.Repeat;
import me.xiaopan.lifespirit.util.TimeUtils;

/**
 * 每隔多长时间重复一次
 */
public abstract class BaseEveryOtherRepeatWay extends RepeatWayInterface{
	private int space;//间隔
	
	public BaseEveryOtherRepeatWay(){
		setSpace(1);
	}

	public int getSpace() {
		return space;
	}

	public void setSpace(int space) {
		this.space = space;
	}

	@Override
	public Time getNextExecuteTime(Repeat repeat) {
		Time currentTime = new Time();
		Calendar calendar = new GregorianCalendar(repeat.getLastExecuteTime().getYear(), repeat.getLastExecuteTime().getMonth(), repeat.getLastExecuteTime().getDayOfMonth(), repeat.getLastExecuteTime().getHourOfDay(), repeat.getLastExecuteTime().getMinute(), repeat.getLastExecuteTime().getSecond());
		calendar.add(getUpdateField(), getSpace());
		Time nextExecuteTime = new Time(calendar.getTimeInMillis());
		
		while(TimeUtils.compare(currentTime, nextExecuteTime) >= 0){
			calendar.add(getUpdateField(), getSpace());
			nextExecuteTime = new Time(calendar.getTimeInMillis());
		}
		
		return nextExecuteTime;
	}
	
	public abstract int getUpdateField();
}