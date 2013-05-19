package me.xiaopan.lifespirit.task.repeatway;

import me.xiaopan.easyjava.util.Time;
import me.xiaopan.lifespirit.task.Repeat;
import me.xiaopan.lifespirit2.R;
import android.content.Context;

/**
 * 法定休息日执行
 */
public class LegalAndOffDayRepeatWay extends RepeatWayInterface{
	@Override
	public String getIntro(Context context, Repeat repeat) {
		return context.getString(R.string.repeat_legalAndOffDay);
	}

	@Override
	public Time getNextExecuteTime(Repeat repeat) {
		return null;
	}
}