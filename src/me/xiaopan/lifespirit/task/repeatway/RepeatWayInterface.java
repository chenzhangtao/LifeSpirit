package me.xiaopan.lifespirit.task.repeatway;

import me.xiaopan.javalibrary.util.Time;
import me.xiaopan.lifespirit.task.Repeat;
import android.content.Context;

/**
 * 重复
 */
public abstract class RepeatWayInterface{
	/**
	 * 获取简介
	 * @param context 上下文
	 * @param repeat 重复对象
	 * @return 信息
	 */
	public abstract String getIntro(Context context, Repeat repeat);
	
	/**
	 * 获取下次执行时间
	 * @param repeat
	 * @return
	 */
	public abstract Time getNextExecuteTime(Repeat repeat);
}