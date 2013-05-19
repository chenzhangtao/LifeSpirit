package me.xiaopan.lifespirit.task.scenariomode;

import me.xiaopan.easyandroid.util.SystemUtils;
import me.xiaopan.easyjava.util.DateTimeUtils;
import me.xiaopan.easyjava.util.Time;
import me.xiaopan.lifespirit2.R;
import android.content.Context;

/**
 * 休眠
 */
public class Dormant extends BaseScenarioOption {
	private int dormantTimeInMillis;
	
	public Dormant(Context context) {
		setDormantTimeInMillis(SystemUtils.getScreenDormantTime(context));
	}

	@Override
	public void onExecute(Context context, Time currentTime) {
		if(isEnable()){
			SystemUtils.setScreenDormantTime(context, getDormantTimeInMillis());
		}
	}

	@Override
	public String getIntro(Context context) {
		return DateTimeUtils.milliSecondToDayHourMinuteSecond(getDormantTimeInMillis(), null, context.getString(R.string.base_hours), context.getString(R.string.base_minutes), context.getString(R.string.base_second), null, false, false);
	}

	public int getDormantTimeInMillis() {
		return dormantTimeInMillis;
	}

	public void setDormantTimeInMillis(int dormantTimeInMillis) {
		this.dormantTimeInMillis = dormantTimeInMillis;
	}
}