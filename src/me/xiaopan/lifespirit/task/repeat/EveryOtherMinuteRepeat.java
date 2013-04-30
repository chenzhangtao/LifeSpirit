package me.xiaopan.lifespirit.task.repeat;

import me.xiaopan.javalibrary.util.Time;
import me.xiaopan.lifespirit.task.Repeat;
import me.xiaopan.lifespirit2.R;
import android.content.Context;

/**
 * 每隔多少分钟重复一次
 */
public class EveryOtherMinuteRepeat extends BaseEveryOtherRepeat{
	@Override
	public String getIntro(Context context, Repeat repeat) {
		return context.getString(R.string.repeat_everyOtherMinute, getSpace());
	}

	@Override
	public Time getNextExecuteTime(Repeat repeat) {
		return null;
	}

	@Override
	public boolean isExecute(Repeat repeat, Time currentTime) {
		return false;
	}
}