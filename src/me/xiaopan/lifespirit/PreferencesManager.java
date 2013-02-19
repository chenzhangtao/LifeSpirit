package me.xiaopan.lifespirit;

import me.xiaopan.lifespirit.enums.TaskSortWay;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;


public class PreferencesManager {
	public static String BACKG_SERVICE_RUNNING = "BACKG_SERVICE_RU_STATE";
	public static String AUTO_START_BACKGROUND_SERVICE = "AUTO_START_BACKGROUND_SERVICE";
	public static String IS_ON_INTERFACE_SWITCHING_ANIMATION = "IS_ON_INTERFACE_SWITCHING_ANIMATION";
 	public static String TASK_SORT_WAY = "TASK_SORT_WAY";
	private SharedPreferences sharedPreferences;
	private Editor editor;
	
	public PreferencesManager(Context context){
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		editor = sharedPreferences.edit();
	}
	
	/**
	 * 判断是否自动启动后台服务
	 * @return 是否自动启动后台服务
	 */
	public boolean isAutoStartBackgroundService(){
		return sharedPreferences.getBoolean(AUTO_START_BACKGROUND_SERVICE, true);
	}
	
	/**
	 * 设置是否自动启动后台服务
	 * @param autoStartBackgroundService 是否自动启动后台服务
	 */
	public void setAutoStartBackgroundService(boolean autoStartBackgroundService){
		editor.putBoolean(AUTO_START_BACKGROUND_SERVICE, autoStartBackgroundService);
		editor.commit();
	}

	/**
	 * 判断后台服务是否正在运行
	 * @return 后台服务是否正在运行
	 */
	public boolean isBackgServiceRunning(){
		return sharedPreferences.getBoolean(BACKG_SERVICE_RUNNING, false);
	}
	
	/**
	 * 设置后台服务是否正在运行
	 * @param backgServiceRunning 后台服务是否正在运行
	 */
	public void setBackgServiceRunning(boolean backgServiceRunning){
		editor.putBoolean(BACKG_SERVICE_RUNNING, backgServiceRunning);
		editor.commit();
	}
	
	/**
	 * 判断是否开启界面切换动画
	 * @return 是否开启界面切换动画
	 */
	public boolean isOnInterfaceSwitchingAnimation(){
		return sharedPreferences.getBoolean(IS_ON_INTERFACE_SWITCHING_ANIMATION, true);
	}
	
	/**
	 * 设置是否开启界面切换动画
	 * @param onInterfaceSwitchingAnimation 是否开启界面切换动画
	 */
	public void setOnInterfaceSwitchingAnimation(boolean onInterfaceSwitchingAnimation){
		editor.putBoolean(IS_ON_INTERFACE_SWITCHING_ANIMATION, onInterfaceSwitchingAnimation);
		editor.commit();
	}
	
	/**
	 * 获取任务排序方式
	 * @return 任务排序方式
	 */
	public TaskSortWay getTaskSortWay(){
		return TaskSortWay.valueOf(sharedPreferences.getString(TASK_SORT_WAY, TaskSortWay.TIGGER_TIME_ASC.name()));
	}
	
	/**
	 * 设置任务排序方式
	 * @param taskSortWay 任务排序方式
	 */
	public void setTaskSortWay(TaskSortWay taskSortWay){
		editor.putString(TASK_SORT_WAY, taskSortWay.name());
		editor.commit();
	}
	
	public void clearAllAttr(){
		editor.clear();
		editor.commit();
	}
}