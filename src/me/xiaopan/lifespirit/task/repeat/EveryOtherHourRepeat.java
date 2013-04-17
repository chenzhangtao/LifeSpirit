package me.xiaopan.lifespirit.task.repeat;

import me.xiaopan.lifespirit.task.Repeat;
import me.xiaopan.lifespirit2.R;
import android.content.Context;

/**
 * 每隔多少小时重复一次
 */
public class EveryOtherHourRepeat extends BaseEveryOtherRepeat{
	@Override
	public String onGetIntro(Context context, Repeat repeat) {
		return context.getString(R.string.repeat_everyOtherHour, getSpace());
	}
}