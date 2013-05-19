package me.xiaopan.lifespirit.task.scenariomode;

import me.xiaopan.easyandroid.util.NetworkUtils;
import me.xiaopan.easyandroid.util.SystemUtils.DeviceNotFoundException;
import me.xiaopan.easyjava.util.Time;
import me.xiaopan.lifespirit2.R;
import android.content.Context;

/**
 * WIFI
 */
public class WIFI extends BaseScenarioOption {
	private boolean open;
	
	public WIFI(Context context) {
		try {
			setOpen(!NetworkUtils.isWifiOpen(context));
		} catch (DeviceNotFoundException e) {
			e.printStackTrace();
			setSupport(false);
			setEnable(false);
		}
	}

	@Override
	public void onExecute(Context context, Time currentTime) {
		if(isEnable()){
			try {
				NetworkUtils.setWifi(context, isOpen());
			} catch (DeviceNotFoundException e) {
				e.printStackTrace();
				setSupport(false);
				setEnable(false);
			}
		}
	}

	@Override
	public String getIntro(Context context) {
		return isOpen()?context.getString(R.string.base_open):context.getString(R.string.base_close);
	}
	
	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
}