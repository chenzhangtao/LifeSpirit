package me.xiaopan.lifespirit.task.repeat;

import me.xiaopan.lifespirit.task.Repeat;
import me.xiaopan.lifespirit2.R;
import android.content.Context;

/**
 * 法定休息日执行
 */
public class LegalAndOffDayRepeat extends BaseRepeat{
	@Override
	public String onGetIntro(Context context, Repeat repeat) {
		return context.getString(R.string.repeat_legalAndOffDay);
	}
}