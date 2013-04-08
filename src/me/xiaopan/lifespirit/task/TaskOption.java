package me.xiaopan.lifespirit.task;

import android.content.Context;

/**
 * 任务选项
 */
public abstract class TaskOption{
	private Context context;
	private boolean enable;//是否启用此选项
	private boolean support = true;//是否支持此功能
	
	public TaskOption(Context context){
		setContext(context);
	}
	
	/**
	 * 执行
	 */
	public abstract void onExecute();
	
	/**
	 * 获取简介信息
	 * @return 简介信息，用于显示在情景模式编辑页面
	 */
	public abstract String onGetIntro();

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

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