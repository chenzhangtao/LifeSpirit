package me.xiaopan.lifespirit.task;

import me.xiaopan.javalibrary.util.Time;

public abstract class BaseTime extends Time{
	private static final long serialVersionUID = 1L;

	public BaseTime(long milliseconds) {
		super(milliseconds);
	}
	
	public BaseTime() {
		super();
	}
}