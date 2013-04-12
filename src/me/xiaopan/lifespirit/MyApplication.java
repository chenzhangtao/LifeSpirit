package me.xiaopan.lifespirit;

import java.util.ArrayList;
import java.util.List;

import me.xiaopan.lifespirit.task.BaseTask;
import android.app.Application;

public class MyApplication extends Application {
	private List<BaseTask> taskList;
	
	@Override
	public void onCreate() {
		super.onCreate();
		taskList = new ArrayList<BaseTask>();
	}
	
	public List<BaseTask> getTaskList() {
		return taskList;
	}
	
	public void setTaskList(List<BaseTask> taskList) {
		this.taskList = taskList;
	}
}