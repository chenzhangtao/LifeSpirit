package me.xiaopan.lifespirit.task.repeatway;

import me.xiaopan.javalibrary.util.Time;
import me.xiaopan.lifespirit.task.Repeat;
import me.xiaopan.lifespirit2.R;
import android.content.Context;

/**
 * 法定工作日执行
 */
public class StatutoryWorkingDaysRepeatWay extends RepeatWayInterface{
	@Override
	public String getIntro(Context context, Repeat repeat) {
		return context.getString(R.string.repeat_statutoryWorkingDays);
	}

	@Override
	public Time getNextExecuteTime(Repeat repeat) {
		return null;
	}
}