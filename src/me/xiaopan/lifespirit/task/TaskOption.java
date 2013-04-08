package me.xiaopan.lifespirit.task;

import android.content.Context;

/**
 * 任务选项
 */
public abstract class TaskOption{
	private Context context;
	
	public TaskOption(Context context){
		setContext(context);
	}
	
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
}