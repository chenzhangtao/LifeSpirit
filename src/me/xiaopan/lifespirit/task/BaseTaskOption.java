package me.xiaopan.lifespirit.task;

import java.io.Serializable;

import android.content.Context;

/**
 * 任务选项
 */
public abstract class BaseTaskOption implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 获取简介信息
	 * @param context 
	 * @return 简介信息，用于显示在情景模式编辑页面
	 */
	public abstract String onGetIntro(Context context);
}