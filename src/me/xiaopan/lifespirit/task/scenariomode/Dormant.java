package me.xiaopan.lifespirit.task.scenariomode;

import me.xiaopan.androidlibrary.util.SystemUtils;
import me.xiaopan.javalibrary.util.DateTimeUtils;
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
	public void onExecute(Context context) {
		if(isEnable()){
			SystemUtils.setScreenDormantTime(context, getDormantTimeInMillis());
		}
	}

	@Override
	public String onGetIntro(Context context) {
		return DateTimeUtils.milliSecondToIncompleteHourMinuteSecond(getDormantTimeInMillis(), "", context.getString(R.string.base_hours), context.getString(R.string.base_minutes), context.getString(R.string.base_second), "", false, false,false);
	}

	public int getDormantTimeInMillis() {
		return dormantTimeInMillis;
	}

	public void setDormantTimeInMillis(int dormantTimeInMillis) {
		this.dormantTimeInMillis = dormantTimeInMillis;
	}
}