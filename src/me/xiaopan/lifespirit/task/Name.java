package me.xiaopan.lifespirit.task;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}