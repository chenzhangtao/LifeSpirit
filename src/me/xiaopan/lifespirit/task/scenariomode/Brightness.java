package me.xiaopan.lifespirit.task.scenariomode;

import me.xiaopan.androidlibrary.util.SystemUtils;
import me.xiaopan.lifespirit2.R;
import android.content.Context;

/**
 * 亮度
 */
public class Brightness extends ScenarioOption {
	private int brightness;//亮度
	private boolean autoAdjustmen;//自动调节
	
	public Brightness(Context context) {
		super(context);
		setAutoAdjustmen(SystemUtils.isScreenBrightnessModeAuto(getContext()));
		setBrightness(SystemUtils.getScreenBrightness(getContext()));
	}

	@Override
	public void onExecute() {
		if(isEnable()){
			//先设置模式
			SystemUtils.setScreenBrightnessMode(getContext(), isAutoAdjustmen());
			//如果不是自动的就设置亮度
			if(!isAutoAdjustmen()){
				SystemUtils.setScreenBrightness(getContext(), getBrightness());
			}
		}
	}

	@Override
	public String onGetIntro() {
		return isAutoAdjustmen()?getContext().getString(R.string.base_automatic):(""+getBrightness());
	}

	public boolean isAutoAdjustmen() {
		return autoAdjustmen;
	}

	public void setAutoAdjustmen(boolean autoAdjustmen) {
		this.autoAdjustmen = autoAdjustmen;
	}
	
	public int getBrightness() {
		return brightness;
	}

	public void setBrightness(int brightness) {
		this.brightness = brightness;
	}
}