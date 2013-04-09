package me.xiaopan.lifespirit.task.repeat;

import me.xiaopan.lifespirit2.R;
import android.content.Context;

/**
 * 每隔多少年重复一次
 */
public class EveryOtherYearRepeat extends EveryOtherRepeat{
	private static final long serialVersionUID = 1L;
	
	public EveryOtherYearRepeat(){
		super(RepeatWay.EVERY_OTHER_YEAR);
	}

	@Override
	public String onGetIntro(Context context) {
		return context.getString(R.string.repeat_everyOtherYear, getSpace());
	}
}