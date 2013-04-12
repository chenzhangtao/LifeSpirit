package me.xiaopan.lifespirit.task.repeat;

import java.io.Serializable;

import me.xiaopan.lifespirit.task.Repeat;
import me.xiaopan.lifespirit2.R;
import android.content.Context;

/**
 * 只执行一次
 */
public class OnlyOneTimeRepeat extends BaseRepeat implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Override
	public String onGetIntro(Context context, Repeat repeat) {
		return context.getString(
				R.string.repeat_onlyOneTime, 
				(
						repeat.getTriggerTime().getYear()+context.getString(R.string.base_year)+
						(repeat.getTriggerTime().getMonth()+1)+context.getString(R.string.base_month)+
						repeat.getTriggerTime().getDayOfMonth()+context.getString(R.string.base_day) +
						repeat.getTriggerTime().getHourOfDay()+context.getString(R.string.base_dian) +
						repeat.getTriggerTime().getMinute()+context.getString(R.string.base_minute)
				)
		);
	}
}