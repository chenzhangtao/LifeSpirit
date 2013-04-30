package me.xiaopan.lifespirit.task.scenariomode;

import me.xiaopan.androidlibrary.util.SystemUtils;
import me.xiaopan.lifespirit2.R;
import android.content.Context;

/**
 * 亮度
 */
public class Brightness extends BaseScenarioOption {
	private int brightness;//亮度
	private boolean autoAdjustmen;//自动调节
	
	public Brightness(Context context) {
		setAutoAdjustmen(SystemUtils.isScreenBrightnessModeAuto(context));
		setBrightness(SystemUtils.getScreenBrightness(context));
	}

	@Override
	public void onExecute(Context context) {
		if(isEnable()){
			//先设置模式
			SystemUtils.setScreenBrightnessMode(context, isAutoAdjustmen());
			//如果不是自动的就设置亮度
			if(!isAutoAdjustmen()){
				SystemUtils.setScreenBrightness(context, getBrightness());
			}
		}
	}

	@Override
	public String getIntro(Context context) {
		return isAutoAdjustmen()?context.getString(R.string.base_automatic):(""+getBrightness());
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