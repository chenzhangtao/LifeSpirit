package me.xiaopan.lifespirit.service;

import java.util.Calendar;
import java.util.GregorianCalendar;

import me.xiaopan.androidlibrary.util.BroadcastUtils;
import me.xiaopan.androidlibrary.util.Logger;
import me.xiaopan.javalibrary.util.DateTimeUtils;
import me.xiaopan.javalibrary.util.Time;
import me.xiaopan.lifespirit.MyApplication;
import me.xiaopan.lifespirit.activity.IndexActivity;
import me.xiaopan.lifespirit.task.BaseTask;
import me.xiaopan.lifespirit2.R;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class TaskService extends Service {
	private static final String LOG_TAG = "TaskService";
	private static final int NOTIFICATION_ID = 1002;//通知ID
	private static final int STOP_TYPE_NOT_EXECUTE_TESK = -5;//任务停止类型 - 由于没有可执行任务而停止
	private MyApplication myApplication;//MyApplication对象
	private PendingIntent startServiceIntent; //启动Service的Intent
	private AlarmManager alarmManager;//报警管理器
	private int stopType = 0;//服务停止类型，根据不同的类型提示不同的内容。0：正常停止；1：由于没有可执行任务而停止

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		myApplication = (MyApplication) getApplication();
		
		//如果有可执行任务就继续否则就停止
		if(!myApplication.getRunningTaskManager().isEmpty()){
			Logger.i(LOG_TAG, "创建 - 继续 "+DateTimeUtils.getCurrentDateTimeByDefultCustomFormat());
			startServiceIntent = PendingIntent.getService(getBaseContext(), 101, new Intent(getBaseContext(), TaskService.class), 0);//实例化启动服务的Intent
			
			/* 获取闹钟管理器并设置从下一分钟开始每隔一分钟启动一次服务 */
			alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
			Calendar  calendar = new GregorianCalendar();
			calendar.add(Calendar.MINUTE, 1);//将时间向后推一分钟
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 60*1000, startServiceIntent);//将启动服务的Intent添加到报警管理器中并设置为每隔一分钟启动一次
		}else{
			Logger.i(LOG_TAG, "创建 - 停止 "+DateTimeUtils.getCurrentDateTimeByDefultCustomFormat());
			//停止服务
			stopSevice();
		}
	}

	@Override
	public void onStart(Intent intent, int startId) {
		
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//如果有可执行任务就继续否则就停止服务
		if(!myApplication.getRunningTaskManager().isEmpty()){
			Logger.i(LOG_TAG, "启动 - 继续 "+DateTimeUtils.getCurrentDateTimeByDefultCustomFormat());
			new Thread(new Runnable() {
				@Override
				public void run() {
					/* 遍历所有正在运行的任务，该执行的执行该提醒的提醒 */
					Time currentTime = new Time();
					for(BaseTask task : myApplication.getRunningTaskManager().getRunningTaskList()){
						if(task.isExecute(currentTime)){//如果需要执行
							task.execute(getBaseContext(), currentTime);
							if(!task.isEnable()){
								myApplication.getRunningTaskManager().getRunningTaskList().remove(task);
							}
						}else if(task.isRemind()){//如果需要提醒
							
						}
					}
					
					BroadcastUtils.sendBroadcast(getBaseContext(), IndexActivity.ACTION_BROADCAST_REFRESH);//向运行中任务列表界面发送刷新广播
					
					/* 如果依然有可执行任务，就发送运行中通知否则就停止服务 */
					if(!myApplication.getRunningTaskManager().isEmpty()){
						sendRunningNotification();
					}else{
						stopSevice();
					}
				}
			}).start();
		}else{
			Logger.i(LOG_TAG, "启动 - 停止 "+DateTimeUtils.getCurrentDateTimeByDefultCustomFormat());
			//停止服务
			stopSevice();
		}
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		Logger.i(LOG_TAG, "销毁 "+DateTimeUtils.getCurrentDateTimeByDefultCustomFormat());
		super.onDestroy();

		//从报警管理器中移除启动服务的Intent
		if(alarmManager != null){
			alarmManager.cancel(startServiceIntent);
		}
//
//		//发送停止通知
//		sendStopNotificatiion();
	}

	/**
	 * 停止服务
	 * @param stopType
	 */
	private void stopSevice(){
		stopType = STOP_TYPE_NOT_EXECUTE_TESK;
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
		Notification notifications = new Notification(R.drawable.icon_launcher, getString(R.string.app_name), System.currentTimeMillis());
		//将通知标记为不可清除
		notifications.flags = Notification.FLAG_NO_CLEAR;
		//设置要显示的信息
		notifications.setLatestEventInfo(
			this, 
//			myApplication.getNextExecuteTask().getNextExecuteTime().getRemainingTime() + myApplication.getNextExecuteTask().getTaskName().getShowInTaskInfoText(), 
			"生活精灵正在运行", 
//			myApplication.getNextExecuteTask().getTaskInfo() , 
			"",
			PendingIntent.getActivity(this, 0, intent, 0)
		);
		//将通知设为前台通知，同时与该服务绑定，使该服务不会被系统回收
		startForeground(NOTIFICATION_ID, notifications);
	}

//	/**
//	 * 发送停止通知
//	 */
//	private void sendStopNotificatiion(){
//		String notificationShowHintText = getString(R.string.hint_hasStopped);
//		//如果是由于没有可执行任务而停止
//		if(stopType == STOP_TYPE_NOT_EXECUTE_TESK){
//			notificationShowHintText = getString(R.string.hint_noEexecuteTask);
//		}
//		NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//		Notification notification = new Notification(R.drawable.icon_launcher, getString(R.string.app_name), System.currentTimeMillis());
//		notification.setLatestEventInfo(this, getString(R.string.app_name), notificationShowHintText, PendingIntent.getActivity(this, 0, new Intent(this, TaskListActivity.class), 0));
//		notificationManager.notify(NOTIFICATION_ID, notification);
//	}
}