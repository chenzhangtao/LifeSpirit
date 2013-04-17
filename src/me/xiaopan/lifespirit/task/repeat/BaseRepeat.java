package me.xiaopan.lifespirit.task.repeat;

import me.xiaopan.lifespirit.task.Repeat;
import android.content.Context;

/**
 * 重复
 */
public abstract class BaseRepeat{
	/**
	 * 获取简介
	 * @param context 上下文
	 * @param repeat 重复对象
	 * @return 信息
	 */
	public abstract String onGetIntro(Context context, Repeat repeat);
}