package me.xiaopan.lifespirit.task;

/**
 * 抽象的任务
 */
public abstract class AbstractTask {
	public String name;
	public BaseTime createTime;
	public BaseTime triggerTime;
	public BaseTime nextTriggerTimr;
}