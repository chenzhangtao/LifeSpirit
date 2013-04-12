package me.xiaopan.lifespirit.task.scenariomode;

import me.xiaopan.lifespirit.task.BaseTaskOption;
import android.content.Context;

/**
 * 情景模式选项
 */
public abstract class BaseScenarioOption extends BaseTaskOption{
	private static final long serialVersionUID = 1L;
	private boolean enable;//是否启用此选项
	private boolean support = true;//是否支持此功能
	
	/**
	 * 执行
	 * @param context 上下文
	 */
	public abstract void onExecute(Context context);
	
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