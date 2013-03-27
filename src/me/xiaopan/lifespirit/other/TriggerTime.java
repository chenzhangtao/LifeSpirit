package me.xiaopan.lifespirit.other;

import me.xiaopan.lifespirit.R;
import me.xiaopan.lifespirit.task.Task;
import android.content.Context;


public class TriggerTime extends Time {
	/**
	 * KEY - 触发时间
	 */
	public static final String KEY = "KEY_TRIGGER_TIME";
	
	public TriggerTime(Context context, Task task) {
		super(context, task, context.getString(R.string.taskItem_tiggerTime));
	}

	public TriggerTime(Context context, Task task, String timeJSON) {
		super(context, task, context.getString(R.string.taskItem_tiggerTime), timeJSON);
	}
}
