package me.xiaopan.lifespirit.task.repeat;

import me.xiaopan.lifespirit2.R;
import android.content.Context;

/**
 * 每隔多少天重复一次
 */
public class EveryOtherDayRepeat extends EveryOtherRepeat{
	private static final long serialVersionUID = 1L;
	
	public EveryOtherDayRepeat(){
		super(RepeatWay.EVERY_OTHER_DAY);
	}

	@Override
	public String onGetIntro(Context context) {
		return context.getString(R.string.repeat_everyOtherDay, getSpace());
	}
}