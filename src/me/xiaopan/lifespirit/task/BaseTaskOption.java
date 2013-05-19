package me.xiaopan.lifespirit.task;

import me.xiaopan.easyjava.util.Time;
import android.content.Context;

/**
 * 任务选项
 */
public abstract class BaseTaskOption{
	/**
	 * 获取简介信息
	 * @param context 
	 * @return 简介信息，用于显示在情景模式编辑页面
	 */
	public abstract String getIntro(Context context);
	
	/**
	 * 当任务执行的时候
	 * @param context 
	 * @param currentTime 当前时间
	 * @return 
	 */
	public abstract void onExecute(Context context, Time currentTime);
}