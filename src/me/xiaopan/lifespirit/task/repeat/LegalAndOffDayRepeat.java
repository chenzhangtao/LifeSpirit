package me.xiaopan.lifespirit.task.repeat;

import me.xiaopan.lifespirit2.R;
import android.content.Context;

/**
 * 法定休息日执行
 */
public class LegalAndOffDayRepeat extends BaseRepeat{
	private static final long serialVersionUID = 1L;
	
	public LegalAndOffDayRepeat(){
		super(RepeatWay.LEGAL_AND_OFF_DAY);
	}

	@Override
	public String onGetIntro(Context context) {
		return context.getString(R.string.repeat_legalAndOffDay);
	}
}