package me.xiaopan.lifespirit.task.repeat;

import me.xiaopan.lifespirit.task.BaseTime;
import me.xiaopan.lifespirit.task.Repeat;
import me.xiaopan.lifespirit2.R;
import android.content.Context;

/**
 * 法定工作日执行
 */
public class StatutoryWorkingDaysRepeat extends BaseRepeatWay{
	@Override
	public String onGetIntro(Context context, Repeat repeat) {
		return context.getString(R.string.repeat_statutoryWorkingDays);
	}

	@Override
	public BaseTime onGetNextExecuteTime(Repeat repeat) {
		return null;
	}

	@Override
	public boolean isExecute(Repeat repeat) {
		return false;
	}
}