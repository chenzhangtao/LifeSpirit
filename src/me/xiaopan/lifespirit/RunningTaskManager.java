package me.xiaopan.lifespirit;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.xiaopan.javalibrary.io.FileScanner;
import me.xiaopan.javalibrary.util.FileUtils;
import me.xiaopan.javalibrary.util.StringUtils.StringCheckUpWayEnum;
import me.xiaopan.lifespirit.service.TaskService;
import me.xiaopan.lifespirit.task.BaseTask;
import me.xiaopan.lifespirit.task.ScenarioMode;
import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;

/**
 * 运行中任务管理器
 */
public class RunningTaskManager {
	private Context context;
	private List<BaseTask> runningTaskList;//运行中的任务列表
	
	public RunningTaskManager(Context context){
		this.context = context;
		
		//读取所有状态为开启的任务
		FileScanner fileScanner = new FileScanner(new File(context.getFilesDir().getPath()+File.separator+BaseTask.TASK_DIR));
		fileScanner.setFileTypeFilterWay(StringCheckUpWayEnum.ENDS_WITH_KEYWORDS);
		fileScanner.addFileTypeKeyWords(BaseTask.STATE_ENABLE);
		List<File> files = fileScanner.scan();
		if(files != null && files.size() > 0){
			runningTaskList = new ArrayList<BaseTask>(files.size());
			Gson gson = new Gson();
			Class<? extends BaseTask> clas = null;
			for(File file : files){
				try {
					clas = null;
					if(file.getName().contains(ScenarioMode.TYPE)){
						clas = ScenarioMode.class;
					}
					
					if(clas != null){
						runningTaskList.add(gson.fromJson(FileUtils.readString(file), clas));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}else{
			runningTaskList = new ArrayList<BaseTask>(0);
		}
		if(!isEmpty()){
			context.startService(new Intent(context, TaskService.class));
		}
	}

	/**
	 * 获取运行中的任务列表
	 * @return
	 */
	public List<BaseTask> getRunningTaskList() {
		return runningTaskList;
	}
	
	/**
	 * 更新任务
	 * @param task
	 */
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
		updateTaskService();
	}
	
	/**
	 * 查找任务
	 * @param task
	 * @return
	 */
	public int findTask(BaseTask task){
		int position = -1;
		for(int w = 0, size = runningTaskList.size(); w < size; w++){
			if(runningTaskList.get(w).getId() == task.getId()){
				position = w;
				break;
			}
		}
		return position;
	}
	
	/**
	 * 是否是空的
	 * @return
	 */
	public boolean isEmpty(){
		return runningTaskList == null || runningTaskList.size() == 0;
	}
	
	/**
	 * 更新服务
	 */
	public void updateTaskService(){
		if(isEmpty()){
			context.stopService(new Intent(context, TaskService.class));
		}else{
			context.startService(new Intent(context, TaskService.class));
		}
	}
	
//	public long getNextTime(){
//		
//	}
}