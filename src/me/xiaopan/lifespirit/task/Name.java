package me.xiaopan.lifespirit.task;

import java.io.Serializable;

import android.content.Context;

public class Name extends BaseTaskOption implements Serializable{
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