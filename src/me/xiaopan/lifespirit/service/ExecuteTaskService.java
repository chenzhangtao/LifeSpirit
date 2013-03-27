package me.xiaopan.lifespirit.service;

import java.util.Calendar;
import java.util.GregorianCalendar;

import me.xiaopan.androidlibrary.util.BroadcastUtils;
import me.xiaopan.javalibrary.util.DateTimeUtils;
import me.xiaopan.lifespirit.MyApplication;
import me.xiaopan.lifespirit.R;
import me.xiaopan.lifespirit.activity.TaskListActivity;
import me.xiaopan.lifespirit.other.Time;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

/**
 * 执行任务的服务
 */
public class ExecuteTaskService extends Service {
	/**
	 * 通知ID
	 */
	private static final int notificationId = 1001;
	/**
	 * 任务停止类型 - 由于没有可执行任务而停止
	 */
	private static final int STOP_TYPE_NOT_EXECUTE_TESK = -5;
	/**
	 * MyApplication对象
	 */
	private MyApplication myApplication;
	/**
	 * 服务停止类型，根据不同的类型提示不同的内容。0：正常停止；1：由于没有可执行任务而停止
	 */
	private int stopType = 0;
	/**
	 * 启动Service的Intent
	 */
	private PendingIntent startServiceIntent; 
	/**
	 * 报警管理器
	 */
	AlarmManager alarmManager;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		//设置Applicaiton对象
		setMyApplication((MyApplication) getApplication());
		//如果有可执行任务
		if(getMyApplication().getNextExecuteTask() != null){
			//获取报警管理器
			setAlarmManager((AlarmManager) getSystemService(Context.ALARM_SERVICE));
			//根据当前时间创建日历对象
			GregorianCalendar  calendar = new GregorianCalendar();
			//将时间向后推一分钟
			calendar.add(Calendar.MINUTE, 1);
			calendar.set(Calendar.SECOND, 0);
			//实例化启动服务的Intent
			setStartServiceIntent(PendingIntent.getService(ExecuteTaskService.this, 0, new Intent(ExecuteTaskService.this, ExecuteTaskService.class), 0));
			//将启动服务的Intent添加到报警管理器中
			getAlarmManager().setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 60*1000, getStartServiceIntent());
			//设置后台服务的状态为正在运行
			getMyApplication().getPreferencesManager().setBackgServiceRunning(true);
		}else{
			//停止服务
			stopSevice();
			//设置后台服务的状态为停止
			getMyApplication().getPreferencesManager().setBackgServiceRunning(false);
		}
	}

	@Override
	public void onStart(Intent intent, int startId) {
		//如果有可执行任务
		if(getMyApplication().getNextExecuteTask() != null){
			new Thread(new Runnable() {
				@Override
				public void run() {
					//发送正在运行通知
					sendRunningNotification();
					
					//比较当前时间和下次要执行的任务的时间
					int[] currentTimesBy24Hour = DateTimeUtils.getCurrentTimesBy24Hour();
					int result = Time.contrastTime(
						currentTimesBy24Hour[0], currentTimesBy24Hour[1], currentTimesBy24Hour[2], currentTimesBy24Hour[3], currentTimesBy24Hour[4], 
						getMyApplication().getNextExecuteTask().getNextExecuteTime().getYear(), getMyApplication().getNextExecuteTask().getNextExecuteTime().getMonth(), 
						getMyApplication().getNextExecuteTask().getNextExecuteTime().getDay(), getMyApplication().getNextExecuteTask().getNextExecuteTime().getHour(), 
						getMyApplication().getNextExecuteTask().getNextExecuteTime().getMinute()
					);
					
					//如果当前时间大于或等于执行时间
					if(result >= 0){
						//执行任务
						getMyApplication().getNextExecuteTask().execute();
						//更新下次执行的任务
						getMyApplication().updateNextExecuteTask();
						//如果依然有可执行任务
						if(getMyApplication().getNextExecuteTask() != null){
							//发送正在运行通知
							sendRunningNotification();
						}else{
							//停止服务
							stopSevice();
						}
					}
					
					//向任务列表界面发送刷新广播
					BroadcastUtils.sendBroadcast(getBaseContext(), TaskListActivity.BROADCAST_FILETER_ACTION_TASKLISTACTIVITY);
				}
			}).start();
		}else{
			//停止服务
			stopSevice();
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		//从报警管理器中移除启动服务的Intent
		if(getAlarmManager() != null){
			getAlarmManager().cancel(getStartServiceIntent());
		}
		
		//发送停止通知
		sendStopNotificatiion();
		
		//将服务的状态标记为已经停止
		getMyApplication().getPreferencesManager().setBackgServiceRunning(false);
	}
	
	/**
	 * 停止服务
	 * @param stopType
	 */
	private void stopSevice(){
		setStopType(STOP_TYPE_NOT_EXECUTE_TESK);
		stopSelf();
	}
	
	/**
	 * 发送运行中通知
	 */
	private void sendRunningNotification(){
		//初始化要启动程序的Intent
		Intent intent = new Intent(Intent.ACTION_MAIN);
		//添加种类为要运行程序
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		//设置要运行的应用程序的包名以及主Activity
		intent.setComponent(new ComponentName(this.getPackageName(), this.getPackageName() + ".activity.TaskListActivity" ));
		//关键的一步，设置启动模式为如果有需要的话就重新创建
		intent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		//初始化要发送的通知
		Notification notifications = new Notification(R.drawable.ic_launcher, getString(R.string.app_name), System.currentTimeMillis());
		//将通知标记为不可清除
		notifications.flags = Notification.FLAG_NO_CLEAR;
		//设置要显示的信息
		notifications.setLatestEventInfo(
			this, 
			getMyApplication().getNextExecuteTask().getNextExecuteTime().getRemainingTime() + getMyApplication().getNextExecuteTask().getTaskName().getShowInTaskInfoText(), 
			getMyApplication().getNextExecuteTask().getTaskInfo() , 
			PendingIntent.getActivity(this, 0, intent, 0)
		);
		//将通知设为前台通知，同时与该服务绑定，使该服务不会被系统回收
		startForeground(notificationId, notifications);
	}
	
	/**
	 * 发送停止通知
	 */
	private void sendStopNotificatiion(){
		String notificationShowHintText = getString(R.string.hint_hasStopped);
		//如果是由于没有可执行任务而停止
		if(getStopType() == STOP_TYPE_NOT_EXECUTE_TESK){
			notificationShowHintText = getString(R.string.hint_noEexecuteTask);
		}
		NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(R.drawable.ic_launcher, getString(R.string.app_name), System.currentTimeMillis());
		notification.setLatestEventInfo(this, getString(R.string.app_name), notificationShowHintText, PendingIntent.getActivity(this, 0, new Intent(this, TaskListActivity.class), 0));
		notificationManager.notify(notificationId, notification);
	}

	public MyApplication getMyApplication() {
		return myApplication;
	}

	public void setMyApplication(MyApplication myApplication) {
		this.myApplication = myApplication;
	}

	public int getStopType() {
		return stopType;
	}

	public void setStopType(int stopType) {
		this.stopType = stopType;
	}

	public PendingIntent getStartServiceIntent() {
		return startServiceIntent;
	}

	public void setStartServiceIntent(PendingIntent startServiceIntent) {
		this.startServiceIntent = startServiceIntent;
	}

	public AlarmManager getAlarmManager() {
		return alarmManager;
	}

	public void setAlarmManager(AlarmManager alarmManager) {
		this.alarmManager = alarmManager;
	}
}