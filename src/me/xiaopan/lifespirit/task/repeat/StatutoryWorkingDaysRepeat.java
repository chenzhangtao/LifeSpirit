package me.xiaopan.lifespirit.task.repeat;

import me.xiaopan.lifespirit2.R;
import android.content.Context;

/**
 * 法定工作日执行
 */
public class StatutoryWorkingDaysRepeat extends BaseRepeat{
	private static final long serialVersionUID = 1L;
	
	public StatutoryWorkingDaysRepeat(){
		super(RepeatWay.STATUTORY_WORKING_DAYS);
	}

	@Override
	public String onGetIntro(Context context) {
		return context.getString(R.string.repeat_statutoryWorkingDays);
	}
}