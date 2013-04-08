package me.xiaopan.lifespirit.task;

import android.content.Context;

public class Name extends TaskOption{
	private String name;

	public Name(Context context) {
		super(context);
	}

	@Override
	public String onGetIntro() {
		return name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}