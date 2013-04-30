package me.xiaopan.lifespirit.task.repeat;

import me.xiaopan.javalibrary.util.Time;
import me.xiaopan.lifespirit.task.Repeat;
import me.xiaopan.lifespirit2.R;
import android.content.Context;

/**
 * 法定休息日执行
 */
public class LegalAndOffDayRepeat extends BaseRepeatWay{
	@Override
	public String getIntro(Context context, Repeat repeat) {
		return context.getString(R.string.repeat_legalAndOffDay);
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