package me.xiaopan.lifespirit.task.scenariomode;

import me.xiaopan.androidlibrary.util.NetworkUtils;
import me.xiaopan.androidlibrary.util.SystemUtils.DeviceNotFoundException;
import me.xiaopan.lifespirit2.R;
import me.xiaopan.lifespirit.task.TaskOption;
import android.content.Context;

/**
 * WIFI
 */
public class WIFI extends  TaskOption {
	private boolean open;
	
	public WIFI(Context context) {
		super(context);
		try {
			setOpen(!NetworkUtils.isWifiOpen(getContext()));
		} catch (DeviceNotFoundException e) {
			e.printStackTrace();
			setSupport(false);
			setEnable(false);
		}
	}

	@Override
	public void onExecute() {
		if(isEnable()){
			try {
				NetworkUtils.setWifi(getContext(), isOpen());
			} catch (DeviceNotFoundException e) {
				e.printStackTrace();
				setSupport(false);
				setEnable(false);
			}
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