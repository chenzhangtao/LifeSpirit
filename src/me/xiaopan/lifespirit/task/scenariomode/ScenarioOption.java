package me.xiaopan.lifespirit.task.scenariomode;

import me.xiaopan.lifespirit.task.TaskOption;
import android.content.Context;

/**
 * 情景模式选项
 */
public abstract class ScenarioOption extends TaskOption{
	private boolean enable = true;//是否启用此选项
	private boolean support = true;//是否支持此功能
	
	public ScenarioOption(Context context){
		super(context);
	}
	
	/**
	 * 执行
	 */
	public abstract void onExecute();
	
	/**
	 * 是否启用此选项
	 * @return 是否启用此选项
	 */
	public boolean isEnable() {
		return enable;
	}

	/**
	 * 设置是否启用此选项
	 * @param enable 是否启用此选项
	 */
	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	/**
	 * 是否支持此功能
	 * @return 是否支持此功能
	 */
	public boolean isSupport() {
		return support;
	}

	/**
	 * 设置是否支持此功能
	 * @param support 是否支持此功能
	 */
	public void setSupport(boolean support) {
		this.support = support;
	}
}