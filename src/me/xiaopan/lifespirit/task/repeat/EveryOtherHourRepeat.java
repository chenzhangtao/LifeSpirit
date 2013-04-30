package me.xiaopan.lifespirit.task.repeat;

import me.xiaopan.javalibrary.util.Time;
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

	@Override
	public Time onGetNextExecuteTime(Repeat repeat) {
		return null;
	}

	@Override
	public boolean isExecute(Repeat repeat) {
		return false;
	}
}