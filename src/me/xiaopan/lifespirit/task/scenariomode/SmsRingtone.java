package me.xiaopan.lifespirit.task.scenariomode;

import java.io.Serializable;

import android.content.Context;

/**
 * 短信铃声
 */
public class SmsRingtone extends BaseScenarioOption implements Serializable{
	private static final long serialVersionUID = 1L;

	public SmsRingtone() {
	}

	@Override
	public void onExecute(Context context) {
		
	}

	@Override
	public String onGetIntro(Context context) {
		return null;
	}
}