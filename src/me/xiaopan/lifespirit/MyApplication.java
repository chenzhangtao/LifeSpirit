package me.xiaopan.lifespirit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import me.xiaopan.javalibrary.io.FileScanner;
import me.xiaopan.javalibrary.util.DateTimeUtils;
import me.xiaopan.javalibrary.util.FileUtils;
import me.xiaopan.javalibrary.util.StringUtils;
import me.xiaopan.lifespirit.enums.TaskSortWay;
import me.xiaopan.lifespirit.service.ExecuteTaskService;
import me.xiaopan.lifespirit.task.BaseTask;
import me.xiaopan.lifespirit.task.BaseTime;

import org.json.JSONException;

import android.app.Application;
import android.content.Intent;

/**
 * 
 * @version 1.0 
 * @author panpf
 * @date May 28, 2012
 */
public class MyApplication extends Application {
	private PreferencesManager preferencesManager;
	/**
	 * 任务列表
	 */
	private List<BaseTask> taskList;
	/**
	 * 下一个要执行的任务的索引
	 */
	private int nextExecuteTaskIndex;
	
	@Override
	public void onCreate() {
		super.onCreate();
		ApplicationExceptionHandler.getInstance().init(getBaseContext());
		//初始化配置文件管理器
		preferencesManager = new PreferencesManager(getBaseContext());
		//从本地读取任务列表
		setTaskList(readTaskList());
		//对任务列表进行排序
		taskSort();
		//更新下一个要执行的任务
		updateNextExecuteTask();
		//如果当前后台任务的启动方式为自动并且有任务可执行，就启动后台任务
		if(getPreferencesManager().isAutoStartBackgroundService() && getNextExecuteTask() != null){
			startBackgService();
		} 
	}
	
	/* ******************************************** 任务相关  ***************************************** */
	/**
	 * 更新下一个要执行的任务
	 */
	public void updateNextExecuteTask(){
		if(getTaskList().size() > 0){
			//创建下次执行的任务的索引，默认为-5
			int nextExecuteTaskIndex = -5;
			//循环比较找出下一个要执行的任务的索引
			for(int w = 0; w < getTaskList().size(); w++){
				//如果当前任务已经开启
				if(getTaskList().get(w).isOpen()){
					//如果还是默认值，就将当前的任务设为下一个执行的任务
					if(nextExecuteTaskIndex == -5){
						nextExecuteTaskIndex = w;
					}else{
						//如果下一个执行的任务的执行时间大于当前任务的执行时间，说明当前任务的执行时间更接近当前时间
						if(BaseTime.compareTime(getTaskList().get(nextExecuteTaskIndex).getNextExecuteTime(), getTaskList().get(w).getNextExecuteTime()) > 0){
							nextExecuteTaskIndex = w;
						}
					}
				}
			}
			setNextExecuteTaskIndex(nextExecuteTaskIndex);
		}else{
			setNextExecuteTaskIndex(-5);
		}
	}
	
	/**
	 * 获取下次执行的任务
	 * @return 下次执行的任务
	 */
	public BaseTask getNextExecuteTask() {
		BaseTask nextExecuteTask = null;
		if(getNextExecuteTaskIndex() >= 0 && getNextExecuteTaskIndex() < getTaskList().size()){
			nextExecuteTask = getTaskList().get(getNextExecuteTaskIndex());
		}
		return nextExecuteTask;
	}
	
	/**
	 * 对任务列表进行排序
	 */
	public void taskSort(){
		BaseTask[] tasks = getTaskList().toArray(new BaseTask[getTaskList().size()]);
		Arrays.sort(tasks, getTaskComparator());
		getTaskList().clear();
		for(BaseTask task : tasks){
			getTaskList().add(task);
		}
	}
	
	/**
	 * 获取任务对比器
	 * @return
	 */
	private Comparator<BaseTask> getTaskComparator(){
		Comparator<BaseTask> taskComparator = null;
		if(preferencesManager.getTaskSortWay() == TaskSortWay.TIGGER_TIME_ASC){
			taskComparator = new Comparator<BaseTask>() {
				@Override
				public int compare(BaseTask lhs, BaseTask rhs) {
					return BaseTime.compareHourAndMinute(lhs.getTriggerTime(), rhs.getTriggerTime());
				}
			};
		}else if(preferencesManager.getTaskSortWay() == TaskSortWay.TIGGER_TIME_DESC){
			taskComparator = new Comparator<BaseTask>() {
				@Override
				public int compare(BaseTask lhs, BaseTask rhs) {
					return BaseTime.compareHourAndMinute(lhs.getTriggerTime(), rhs.getTriggerTime()) * -1;
				}
			};
		}else if(preferencesManager.getTaskSortWay() == TaskSortWay.CREATE_TIME_ASC){
			taskComparator = new Comparator<BaseTask>() {
				@Override
				public int compare(BaseTask lhs, BaseTask rhs) {
					return (int)lhs.getCreateTime() - (int)rhs.getCreateTime();
				}
			};
		}else if(preferencesManager.getTaskSortWay() == TaskSortWay.CREATE_TIME_DESC){
			taskComparator = new Comparator<BaseTask>() {
				@Override
				public int compare(BaseTask lhs, BaseTask rhs) {
					return ((int)lhs.getCreateTime() - (int)rhs.getCreateTime()) * -1;
				}
			};
		}
		return taskComparator;
	}
	
	/**
	 * 从本地读取任务列表，并对任务列表进行过期过滤
	 * @return 任务列表
	 */
	private List<BaseTask> readTaskList(){
		List<BaseTask> taskList = new ArrayList<BaseTask>();
		FileScanner fs = new FileScanner(getFilesDir());
		fs.setFileTypeFilterWay(StringUtils.StringCheckUpWayEnum.EQUAL_KEYWORDS);
		fs.addFileTypeKeyWords("task");
		//读取文件中的内容，并根据内容创建任务对象
		for(File file : fs.scan()){
			try {
				taskList.add(new BaseTask(this, FileUtils.readString(file)));
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//如果最终的任务列表的长度大于0，就对列表中的任务进行过期验证
		if(taskList.size() > 0){
			int[] currentTimesBy24Hour = DateTimeUtils.getCurrentTimesBy24Hour();
			BaseTask task = null;
			for(int w = 0; w < taskList.size(); w++){
				task = taskList.get(w);
				//如果已经开启并且已经过期
				if(task.isOpen() && task.isPastDue(currentTimesBy24Hour[0], currentTimesBy24Hour[1], currentTimesBy24Hour[2], currentTimesBy24Hour[3], currentTimesBy24Hour[4])){
					//关闭任务
					task.setOpen(false);
					//写入本地
					try {
						task.writer();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return taskList;
	}
	
	/* ******************************************** 服务相关  ***************************************** */
	/**
	 * 启动后台服务
	 */
	public void startBackgService(){
		preferencesManager.setBackgServiceRunning(true);
		startService(new Intent(this, ExecuteTaskService.class));
	}
	
	/**
	 * 停止后台服务
	 */
	public void stopBackgService(){
		preferencesManager.setBackgServiceRunning(false);
		stopService(new Intent(this, ExecuteTaskService.class));
	}
	
	/* ******************************************** GET/SET ***************************************** */
	public List<BaseTask> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<BaseTask> taskList) {
		this.taskList = taskList;
	}
	
	public int getNextExecuteTaskIndex() {
		return nextExecuteTaskIndex;
	}

	public void setNextExecuteTaskIndex(int nextExecuteTaskIndex) {
		this.nextExecuteTaskIndex = nextExecuteTaskIndex;
	}

	public PreferencesManager getPreferencesManager() {
		return preferencesManager;
	}

	public void setPreferencesManager(PreferencesManager preferencesManager) {
		this.preferencesManager = preferencesManager;
	}
}