package me.xiaopan.lifespirit.task.repeat;

import me.xiaopan.lifespirit2.R;
import android.content.Context;

/**
 * 每隔多少月重复一次
 */
public class EveryOtherMonthRepeat extends EveryOtherRepeat{
	private static final long serialVersionUID = 1L;
	
	public EveryOtherMonthRepeat(){
		super(RepeatWay.EVERY_OTHER_MONTH);
	}

	@Override
	public String onGetIntro(Context context) {
		return context.getString(R.string.repeat_everyOtherMonth, getSpace());
	}
}