package me.xiaopan.lifespirit.task.scenariomode;

import me.xiaopan.androidlibrary.util.SystemUtils;
import me.xiaopan.androidlibrary.util.SystemUtils.DeviceNotFoundException;
import me.xiaopan.lifespirit2.R;
import me.xiaopan.lifespirit.task.TaskOption;
import android.content.Context;

/**
 * 蓝牙
 */
public class Bluetooth extends TaskOption {
	private boolean open;
	
	public Bluetooth(Context context) {
		super(context);
		try {
			setOpen(!SystemUtils.isBluetoothOpen());
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
				SystemUtils.setBluetooth(isOpen());
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