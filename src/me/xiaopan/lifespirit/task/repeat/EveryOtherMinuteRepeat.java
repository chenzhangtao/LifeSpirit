package me.xiaopan.lifespirit.task.repeat;

import me.xiaopan.lifespirit2.R;
import android.content.Context;

/**
 * 每隔多少分钟重复一次
 */
public class EveryOtherMinuteRepeat extends EveryOtherRepeat{
	private static final long serialVersionUID = 1L;
	
	public EveryOtherMinuteRepeat(){
		super(RepeatWay.EVERY_OTHER_MINUTE);
	}

	@Override
	public String onGetIntro(Context context) {
		return context.getString(R.string.repeat_everyOtherMinute, getSpace());
	}
}