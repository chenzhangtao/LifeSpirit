package me.xiaopan.lifespirit;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import me.xiaopan.javalibrary.io.FileScanner;
import me.xiaopan.javalibrary.util.FileUtils;
import me.xiaopan.javalibrary.util.StringUtils.StringCheckUpWayEnum;
import me.xiaopan.lifespirit.service.ExecuteService;
import me.xiaopan.lifespirit.service.CountdownService;
import me.xiaopan.lifespirit.task.ScenarioMode;
import me.xiaopan.lifespirit.task.Task;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;

/**
 * 运行中任务管理器
 */
public class RunningTaskManager {
	/**
	 * 请求码 - 执行任务
	 */
	private static final int REQUEST_CODE_EXECUTE_TASK = 26713821;
	private Context context;
	private List<Task> runningTaskList;//运行中的任务列表
	private List<Task> sortTaskList;
	private ExecuteTimeComparator executeTimeComparator;
	private AlarmManager alarmManager;
	private PendingIntent startExecuteTaskServicePendingIntent;
	
	public RunningTaskManager(Context context){
		this.context = context;
		alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		executeTimeComparator = new ExecuteTimeComparator();
		
		/* 初始化运行中任务列表 */
		FileScanner fileScanner = new FileScanner(new File(context.getFilesDir().getPath()+File.separator+Task.TASK_DIR));
		fileScanner.setFileTypeFilterWay(StringCheckUpWayEnum.ENDS_WITH_KEYWORDS);
		fileScanner.addFileTypeKeyWords(Task.STATE_ENABLE);
		List<File> files = fileScanner.scan();
		if(files != null && files.size() > 0){
			runningTaskList = new ArrayList<Task>(files.size());
			Gson gson = new Gson();
			Class<? extends Task> clas = null;
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
			runningTaskList = new ArrayList<Task>(0);
		}
		
		updateSortTaskList();//更新排序任务列表
		updateAlarm();//更新定时器
		updateCountdownService();//更新倒计时服务
	}
	
	/**
	 * 更新排序列表的内容，并按下一次执行时间排序，最靠近当前时间的在最前面
	 */
	public void updateSortTaskList(){
		if(sortTaskList == null){
			sortTaskList = new ArrayList<Task>(runningTaskList.size());
		}else{
			sortTaskList.clear();
		}
		for(Task task : runningTaskList){
			sortTaskList.add(task);
		}
		
		//按下一次执行时间排序
		Collections.sort(sortTaskList, executeTimeComparator);
	}
	
	/**
	 * 更新定时器
	 */
	private void updateAlarm(){
		if(startExecuteTaskServicePendingIntent != null){
			alarmManager.cancel(startExecuteTaskServicePendingIntent);
		}
		Intent executeTaskIntent = new Intent(context, ExecuteService.class);
		startExecuteTaskServicePendingIntent = PendingIntent.getService(context, REQUEST_CODE_EXECUTE_TASK, executeTaskIntent, 0);
		alarmManager.set(AlarmManager.RTC_WAKEUP, getNextExcuteTime(), startExecuteTaskServicePendingIntent);
	}
	
	/**
	 * 更新倒计时服务
	 */
	public void updateCountdownService(){
		if(isEmpty()){
			context.stopService(new Intent(context, CountdownService.class));
		}else{
			context.startService(new Intent(context, CountdownService.class));
		}
	}
	
	/**
	 * 更新任务
	 * @param task
	 */
	public void updateTask(Task task){
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
		
		//更新排序任务列表
		updateSortTaskList();
		
		updateCountdownService();
	}
	
	/**
	 * 查找任务
	 * @param task
	 * @return
	 */
	public int findTask(Task task){
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
	 * 获取下一个要执行的任务
	 * @return 任务可能有多个
	 */
	public List<Task> getNextTask(){
		if(sortTaskList.size() > 0){
			if(sortTaskList.size() == 1){
				List<Task> nextTasks = new ArrayList<Task>(1);
				nextTasks.add(sortTaskList.get(0));
				return nextTasks;
			}else{
				int yes = 0;
				long duibiTime = sortTaskList.get(0).getRepeat().getNextExecuteTime().getTimeInMillis();
				for(int w = 1, size = sortTaskList.size(); w < size; w++){
					if(sortTaskList.get(w).getRepeat().getNextExecuteTime().getTimeInMillis() == duibiTime){
						yes++;
					}else{
						break;
					}
				}
				
				List<Task> nextTasks = new ArrayList<Task>(yes+1);
				for(int w = 0; w <=yes; w++){
					nextTasks.add(sortTaskList.get(w));
				}
				return nextTasks;
			}
		}else{
			return null;
		}
	}
	
	public long getNextExcuteTime(){
		if(sortTaskList.size() > 0){
			return sortTaskList.get(0).getRepeat().getNextExecuteTime().getTimeInMillis();
		}else{
			return -1;
		}
	}

	/**
	 * 获取运行中的任务列表
	 * @return
	 */
	public List<Task> getRunningTaskList() {
		return runningTaskList;
	}
	
	private class ExecuteTimeComparator implements Comparator<Task>{
		@Override
		public int compare(Task lhs, Task rhs) {
			return (int) (lhs.getRepeat().getNextExecuteTime().getTimeInMillis() - rhs.getRepeat().getNextExecuteTime().getTimeInMillis());
		}
	}
}