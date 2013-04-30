package me.xiaopan.lifespirit.task.repeat;

import me.xiaopan.javalibrary.util.Time;
import me.xiaopan.lifespirit.task.Repeat;
import me.xiaopan.lifespirit2.R;
import android.content.Context;

/**
 * 只执行一次
 */
public class OnlyOneTimeRepeat extends BaseRepeatWay{
	@Override
	public String getIntro(Context context, Repeat repeat) {
		return context.getString(
				R.string.repeat_onlyOneTime, 
				(
						repeat.getTriggerTime().getYear()+context.getString(R.string.base_year)+
						(repeat.getTriggerTime().getMonth()+1)+context.getString(R.string.base_month)+
						repeat.getTriggerTime().getDayOfMonth()+context.getString(R.string.base_day)
				)
		);
	}

	@Override
	public Time getNextExecuteTime(Repeat repeat) {
		return null;
	}

	@Override
	public boolean isExecute(Repeat repeat, Time currentTime) {
		return 
				repeat.getTriggerTime().getYear() == currentTime.getYear() && 
				repeat.getTriggerTime().getMonth() == currentTime.getMonth() && 
				repeat.getTriggerTime().getDayOfMonth() == currentTime.getDayOfMonth() && 
				repeat.getTriggerTime().getHourOfDay() == currentTime.getHourOfDay() && 
				repeat.getTriggerTime().getMinute() == currentTime.getMinute()
				;
	}
}