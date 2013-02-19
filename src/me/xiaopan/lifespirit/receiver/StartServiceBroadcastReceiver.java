package me.xiaopan.lifespirit.receiver;

import me.xiaopan.lifespirit.PreferencesManager;
import me.xiaopan.lifespirit.service.ExecuteTaskService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;

/**
 * 用来启动执行任务的服务的广播接收器
 */
public class StartServiceBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if(PreferenceManager.getDefaultSharedPreferences(context).getBoolean(PreferencesManager.AUTO_START_BACKGROUND_SERVICE, true)){
			context.startService(new Intent(context, ExecuteTaskService.class));
		}
	}
}