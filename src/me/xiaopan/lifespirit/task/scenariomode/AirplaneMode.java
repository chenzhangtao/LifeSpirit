package me.xiaopan.lifespirit.task.scenariomode;

import me.xiaopan.androidlibrary.util.SystemUtils;
import me.xiaopan.lifespirit2.R;
import android.content.Context;

/**
 * 飞行模式
 */
public class AirplaneMode extends BaseScenarioOption{
	private static final long serialVersionUID = 1L;
	private boolean open;
	
	public AirplaneMode(Context context) {
		setOpen(!SystemUtils.isAirplaneModeOpen(context));
	}
	
	@Override
	public void onExecute(Context context) {
		if (isEnable()) {
			SystemUtils.setAirplaneMode(context, isOpen());
		}
	}

	@Override
	public String onGetIntro(Context context) {
		return isOpen()?context.getString(R.string.base_open):context.getString(R.string.base_close);
	}
	
	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
}