package me.xiaopan.lifespirit.task.scenariomode;

import java.io.Serializable;

import android.content.Context;

/**
 * 电话铃声
 */
public class PhoneRingtone extends BaseScenarioOption implements Serializable{
	private static final long serialVersionUID = 1L;

	public PhoneRingtone() {
	}

	@Override
	public void onExecute(Context context) {
		
	}

	@Override
	public String onGetIntro(Context context) {
		return null;
	}
}