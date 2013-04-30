package me.xiaopan.lifespirit.task;

import me.xiaopan.javalibrary.util.Time;
import android.content.Context;

/**
 * 名称
 */
public class Name extends BaseTaskOption{
	private String name;

	@Override
	public String getIntro(Context context) {
		return name;
	}

	@Override
	public void onExecute(Context context, Time currentTime) {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}