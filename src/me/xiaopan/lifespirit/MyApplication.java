package me.xiaopan.lifespirit;

import java.util.List;

import me.xiaopan.lifespirit.task.BaseTask;
import android.app.Application;

public class MyApplication extends Application {
	private List<BaseTask> runningTaskList;//运行中的任务列表
	
	@Override
	public void onCreate() {
		super.onCreate();
		runningTaskList = BaseTask.readEnableTasks(getBaseContext());
	}

	public List<BaseTask> getRunningTaskList() {
		return runningTaskList;
	}

	public void setRunningTaskList(List<BaseTask> runningTaskList) {
		this.runningTaskList = runningTaskList;
	}
	
	public void updateTask(BaseTask task){
		int location = findTask(task);
		//如果当前运行任务列表中存在此任务
		if(location != -1){
			//如果当前此任务是打开状态就更新，否则就删除
			if(task.isEnable()){
				runningTaskList.set(location, task);
			}else{
				runningTaskList.remove(location);
			}
		}else{
			//如果当前此任务是打开状态
			if(task.isEnable()){
				runningTaskList.add(task);
			}
		}
	}
	
	public int findTask(BaseTask task){
		int position = -1;
		for(int w = 0, size = runningTaskList.size(); w < size; w++){
			if(runningTaskList.get(w).getCreateTime().getTimeInMillis() == task.getCreateTime().getTimeInMillis()){
				position = w;
				break;
			}
		}
		return position;
	}
}