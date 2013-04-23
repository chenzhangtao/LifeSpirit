package me.xiaopan.lifespirit.task.repeat;

import me.xiaopan.lifespirit.task.BaseTime;
import me.xiaopan.lifespirit.task.Repeat;
import android.content.Context;

/**
 * 重复
 */
public abstract class BaseRepeatWay{
	/**
	 * 获取简介
	 * @param context 上下文
	 * @param repeat 重复对象
	 * @return 信息
	 */
	public abstract String onGetIntro(Context context, Repeat repeat);
	
	/**
	 * 获取下次执行时间
	 * @param repeat
	 * @return
	 */
	public abstract BaseTime onGetNextExecuteTime(Repeat repeat);
	
	/**
	 * 判断是否需要执行
	 * @param repeat
	 * @return
	 */
	public abstract boolean isExecute(Repeat repeat);
}