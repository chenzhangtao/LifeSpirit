package me.xiaopan.lifespirit.task.repeatway;

import java.util.Calendar;

import me.xiaopan.lifespirit.task.Repeat;
import me.xiaopan.lifespirit2.R;
import android.content.Context;

/**
 * 每隔多少天重复一次
 */
public class EveryOtherDayRepeatWay extends BaseEveryOtherRepeatWay{
	@Override
	public String getIntro(Context context, Repeat repeat) {
		return context.getString(R.string.repeat_everyOtherDay, getSpace());
	}

	@Override
	public int getUpdateField() {
		return Calendar.DAY_OF_MONTH;
	}
}