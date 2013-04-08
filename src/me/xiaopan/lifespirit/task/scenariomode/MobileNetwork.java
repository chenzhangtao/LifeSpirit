package me.xiaopan.lifespirit.task.scenariomode;

import me.xiaopan.androidlibrary.util.NetworkUtils;
import me.xiaopan.lifespirit2.R;
import me.xiaopan.lifespirit.task.TaskOption;
import android.content.Context;

/**
 * 移动网络
 */
public class MobileNetwork extends  TaskOption {
	private boolean open;
	
	public MobileNetwork(Context context) {
		super(context);
		setOpen(!NetworkUtils.isMobileNetworkOpen(getContext()));
	}

	@Override
	public void onExecute() {
		if(isEnable()){
			NetworkUtils.setMobileNetwork(getContext(), isOpen());
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