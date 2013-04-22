package me.xiaopan.lifespirit;

import android.app.Application;

public class MyApplication extends Application {
	private RunningTaskManager runningTaskManager;
	
	@Override
	public void onCreate() {
		super.onCreate();
		runningTaskManager = new RunningTaskManager(getBaseContext());
	}

	public RunningTaskManager getRunningTaskManager() {
		return runningTaskManager;
	}
}