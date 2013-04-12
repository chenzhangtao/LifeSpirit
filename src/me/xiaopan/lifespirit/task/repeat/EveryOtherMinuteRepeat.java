package me.xiaopan.lifespirit.task.repeat;

import java.io.Serializable;

import me.xiaopan.lifespirit.task.Repeat;
import me.xiaopan.lifespirit2.R;
import android.content.Context;

/**
 * 每隔多少分钟重复一次
 */
public class EveryOtherMinuteRepeat extends BaseEveryOtherRepeat implements Serializable{
	private static final long serialVersionUID = 1L;

	@Override
	public String onGetIntro(Context context, Repeat repeat) {
		return context.getString(R.string.repeat_everyOtherMinute, getSpace());
	}
}