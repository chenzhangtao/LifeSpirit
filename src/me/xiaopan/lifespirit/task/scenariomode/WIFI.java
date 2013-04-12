package me.xiaopan.lifespirit.task.scenariomode;

import me.xiaopan.androidlibrary.util.NetworkUtils;
import me.xiaopan.androidlibrary.util.SystemUtils.DeviceNotFoundException;
import me.xiaopan.lifespirit2.R;
import android.content.Context;

/**
 * WIFI
 */
public class WIFI extends BaseScenarioOption {
	private static final long serialVersionUID = 1L;
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
	public void onExecute(Context context) {
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