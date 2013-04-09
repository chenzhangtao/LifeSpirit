package me.xiaopan.lifespirit.task.repeat;

import me.xiaopan.lifespirit2.R;
import android.content.Context;

/**
 * 每隔多少小时重复一次
 */
public class EveryOtherHourRepeat extends EveryOtherRepeat{
	private static final long serialVersionUID = 1L;
	
	public EveryOtherHourRepeat(){
		super(RepeatWay.EVERY_OTHER_HOUR);
	}

	@Override
	public String onGetIntro(Context context) {
		return context.getString(R.string.repeat_everyOtherHour, getSpace());
	}
}