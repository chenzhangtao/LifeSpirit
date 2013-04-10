package me.xiaopan.lifespirit.task.repeat;

import me.xiaopan.lifespirit2.R;
import android.content.Context;

/**
 * 只执行一次
 */
public class OnlyOneTimeRepeat extends Repeat{
	private static final long serialVersionUID = 1L;
	
	public OnlyOneTimeRepeat(){
		super(RepeatWay.ONLY_ONE_TIME);
	}

	@Override
	public String onGetIntro(Context context) {
		return context.getString(
				R.string.repeat_onlyOneTime, 
				(
						getTriggerTime().getYear()+context.getString(R.string.base_year)+
						(getTriggerTime().getMonth()+1)+context.getString(R.string.base_month)+
						getTriggerTime().getDayOfMonth()+context.getString(R.string.base_day) +
						getTriggerTime().getHourOfDay()+context.getString(R.string.base_dian) +
						getTriggerTime().getMinute()+context.getString(R.string.base_minute)
				)
		);
	}
}