package me.xiaopan.lifespirit.task.scenariomode;

import me.xiaopan.androidlibrary.util.SystemUtils;
import me.xiaopan.javalibrary.util.DateTimeUtils;
import me.xiaopan.lifespirit2.R;
import me.xiaopan.lifespirit.task.TaskOption;
import android.content.Context;

/**
 * 休眠
 */
public class Dormant extends  TaskOption {
	private int dormantTimeInMillis;
	
	public Dormant(Context context) {
		super(context);
		setDormantTimeInMillis(SystemUtils.getScreenDormantTime(getContext()));
	}

	@Override
	public void onExecute() {
		if(isEnable()){
			SystemUtils.setScreenDormantTime(getContext(), getDormantTimeInMillis());
		}
	}

	@Override
	public String onGetIntro() {
		return DateTimeUtils.milliSecondToIncompleteHourMinuteSecond(getDormantTimeInMillis(), "", getContext().getResources().getString(R.string.base_hours), getContext().getResources().getString(R.string.base_minutes), getContext().getResources().getString(R.string.base_second), "", false, false,false);
	}

	public int getDormantTimeInMillis() {
		return dormantTimeInMillis;
	}

	public void setDormantTimeInMillis(int dormantTimeInMillis) {
		this.dormantTimeInMillis = dormantTimeInMillis;
	}
}