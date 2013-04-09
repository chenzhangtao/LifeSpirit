package me.xiaopan.lifespirit.task.repeat;

import me.xiaopan.lifespirit2.R;
import android.content.Context;

/**
 * 每隔多少周重复一次
 */
public class EveryOtherWeekRepeat extends EveryOtherRepeat{
	private static final long serialVersionUID = 1L;
	
	public EveryOtherWeekRepeat(){
		super(RepeatWay.EVERY_OTHER_WEEK);
	}

	@Override
	public String onGetIntro(Context context) {
		return context.getString(R.string.repeat_everyOtherWeek, getSpace());
	}
}