package me.xiaopan.lifespirit.task.repeat;

import me.xiaopan.lifespirit.task.Repeat;
import me.xiaopan.lifespirit2.R;
import android.content.Context;

/**
 * 只执行一次
 */
public class OnlyOneTimeRepeat extends BaseRepeat{
	private static final long serialVersionUID = 1L;
	
	@Override
	public String onGetIntro(Context context, Repeat repeat) {
		return context.getString(
				R.string.repeat_onlyOneTime, 
				(
						repeat.getTriggerTime().getYear()+context.getString(R.string.base_year)+
						(repeat.getTriggerTime().getMonth()+1)+context.getString(R.string.base_month)+
						repeat.getTriggerTime().getDayOfMonth()+context.getString(R.string.base_day)
				)
		);
	}
}