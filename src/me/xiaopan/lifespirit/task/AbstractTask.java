package me.xiaopan.lifespirit.task;

import me.xiaopan.lifespirit.other.Time;

/**
 * 抽象的任务
 */
public abstract class AbstractTask {
	public String name;
	public Time createTime;
	public Time triggerTime;
	public Time nextTriggerTimr;
}