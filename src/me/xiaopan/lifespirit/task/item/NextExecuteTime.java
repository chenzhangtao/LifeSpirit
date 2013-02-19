package me.xiaopan.lifespirit.task.item;

import me.xiaopan.javalibrary.util.DateTimeUtils;
import me.xiaopan.lifespirit.R;
import me.xiaopan.lifespirit.task.Task;
import me.xiaopan.lifespirit.task.Time;
import android.content.Context;

public class NextExecuteTime extends Time {
	/**
	 * KEY - 下次执行时间
	 */
	public static final String KEY = "KEY_LAST_EXECUTE_TIME";
	
	public NextExecuteTime(Context context, Task task) {
		super(context, task, null);
	}

	public NextExecuteTime(Context context, Task task, String timeJSON) {
		super(context, task, null, timeJSON);
	}

	@Override
	public String getShowInTaskInfoText() {
		String result = "";
		//如果任务是开启状态
		if(getTask().isOpen()){
			result += getRemainingTime() + getString(R.string.base_execute);
		}else{
			result = getString(R.string.base_closed);
		}
		return result;
	}
	
	/**
	 * 获取剩余时间
	 * @return 剩余时间
	 */
	public String getRemainingTime(){
		String result = "";
		int[] currentTimesBy24Hour = DateTimeUtils.getCurrentTimesBy24Hour();
		//如果下次执行时间就是当前天
		if(currentTimesBy24Hour[0] == getYear() && currentTimesBy24Hour[1] == getMonth()){
			int day = getDay() - currentTimesBy24Hour[2];
			
			int hour = getHour() - currentTimesBy24Hour[3];
			if(hour < 0){
				day -= 1;
				hour = 24+hour;
			}
			
			int minute = getMinute() - currentTimesBy24Hour[4];
			if(minute < 0){
				hour -= 1;
				if(hour < 0){
					hour = 23;
					day -= 1;
				}
				minute = 60+minute;
			}
			
			if(day>0){
				result += day + getString(R.string.base_tian);
			}
			
			if(hour > 0){
				result += hour + getString(R.string.base_hours);
			}
			
			if(minute > 0){
				result += minute + getString(R.string.base_minutes);
			}
			
			result += getString(R.string.base_hou);
		}else{
			result = getNotificationHintTime();
		}
		return result;
	}
}
