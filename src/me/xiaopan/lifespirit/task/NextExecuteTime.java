package me.xiaopan.lifespirit.task;

/**
 * 下次执行时间
 */
public class NextExecuteTime extends BaseTime {
	private static final long serialVersionUID = 1L;

	public NextExecuteTime(long milliseconds) {
		super(milliseconds);
	}
	
	public NextExecuteTime() {
		super();
	}
}