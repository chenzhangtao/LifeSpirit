package me.xiaopan.lifespirit;

import java.util.ArrayList;
import java.util.List;

import me.xiaopan.lifespirit.task.BaseTask;
import android.app.Application;

public class MyApplication extends Application {
	private List<BaseTask> runningTaskList;//运行中的任务列表
	
	@Override
	public void onCreate() {
		super.onCreate();
		runningTaskList = new ArrayList<BaseTask>();
	}

	public List<BaseTask> getRunningTaskList() {
		return runningTaskList;
	}

	public void setRunningTaskList(List<BaseTask> runningTaskList) {
		this.runningTaskList = runningTaskList;
	}
}