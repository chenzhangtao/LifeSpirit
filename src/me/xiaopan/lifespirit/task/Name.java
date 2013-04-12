package me.xiaopan.lifespirit.task;

import android.content.Context;

/**
 * 名称
 */
public class Name extends BaseTaskOption{
	private static final long serialVersionUID = 1L;
	private String name;

	@Override
	public String onGetIntro(Context context) {
		return name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}