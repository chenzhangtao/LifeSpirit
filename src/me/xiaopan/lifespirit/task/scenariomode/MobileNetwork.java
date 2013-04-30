package me.xiaopan.lifespirit.task.scenariomode;

import me.xiaopan.androidlibrary.util.NetworkUtils;
import me.xiaopan.lifespirit2.R;
import android.content.Context;

/**
 * 移动网络
 */
public class MobileNetwork extends BaseScenarioOption {
	private boolean open;
	
	public MobileNetwork(Context context) {
		setOpen(!NetworkUtils.isMobileNetworkOpen(context));
	}

	@Override
	public void onExecute(Context context) {
		if(isEnable()){
			NetworkUtils.setMobileNetwork(context, isOpen());
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