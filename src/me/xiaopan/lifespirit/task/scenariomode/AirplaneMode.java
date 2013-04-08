package me.xiaopan.lifespirit.task.scenariomode;

import me.xiaopan.androidlibrary.util.SystemUtils;
import me.xiaopan.lifespirit2.R;
import android.content.Context;

/**
 * 飞行模式
 */
public class AirplaneMode extends ScenarioOption {
	private boolean open;
	
	public AirplaneMode(Context context) {
		super(context);
		setOpen(!SystemUtils.isAirplaneModeOpen(getContext()));
	}
	
	@Override
	public void onExecute() {
		if (isEnable()) {
			SystemUtils.setAirplaneMode(getContext(), isOpen());
		}
	}

	@Override
	public String onGetIntro() {
		return isOpen()?getContext().getResources().getString(R.string.base_open):getContext().getResources().getString(R.string.base_close);
	}
	
	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
}