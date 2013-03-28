package me.xiaopan.lifespirit.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import me.xiaopan.androidlibrary.util.DialogUtils;
import me.xiaopan.androidlibrary.util.SystemUtils;
import me.xiaopan.androidlibrary.widget.MyBaseAdapter;
import me.xiaopan.androidlibrary.widget.MyBaseAdapter.ChoiceModeListener;
import me.xiaopan.androidlibrary.widget.PullListView;
import me.xiaopan.javalibrary.util.DateTimeUtils;
import me.xiaopan.javalibrary.util.StringUtils;
import me.xiaopan.lifespirit.Attribute;
import me.xiaopan.lifespirit.MyBaseActivity;
import me.xiaopan.lifespirit.R;
import me.xiaopan.lifespirit.adapter.ApplicationAdapter;
import me.xiaopan.lifespirit.adapter.TaskItemAdapter;
import me.xiaopan.lifespirit.domain.Application;
import me.xiaopan.lifespirit.task.Repeat;
import me.xiaopan.lifespirit.task.Repeat.RepeatWay;
import me.xiaopan.lifespirit.task.SendMessage;
import me.xiaopan.lifespirit.task.Task;
import me.xiaopan.lifespirit.task.TaskName;
import me.xiaopan.lifespirit.task.Time;
import me.xiaopan.lifespirit.task.TriggerTime;
import me.xiaopan.lifespirit.task.scenariomode.AirplaneMode;
import me.xiaopan.lifespirit.task.scenariomode.Bluetooth;
import me.xiaopan.lifespirit.task.scenariomode.MobileNetwork;
import me.xiaopan.lifespirit.task.scenariomode.RingnoteMode;
import me.xiaopan.lifespirit.task.scenariomode.ScreenBrightness;
import me.xiaopan.lifespirit.task.scenariomode.ScreenDormant;
import me.xiaopan.lifespirit.task.scenariomode.Volume;
import me.xiaopan.lifespirit.task.scenariomode.Wifi;
import me.xiaopan.lifespirit.task.scenariomode.RingnoteMode.RingnoteModeEnum;

import org.json.JSONException;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.TimePicker;

public class TaskEditActivity extends MyBaseActivity {
	private static final int REQUEST_CODE_MESSAGE_EDIT = 101;
	private static final String SAVE_CONTENT = "SAVE_CONTENT";
	private Task task;
	private PullListView taskItemListView;
	private TaskItemAdapter taskItemAdapter;
	private AlertDialog alertDialog;
	
	@Override
	public void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.task_edit);
		taskItemListView = (PullListView) findViewById(android.R.id.list);
	}

	@Override
	public void onInitListener(Bundle savedInstanceState) {//设置任务项列表的点击事件监听器
		taskItemListView.setOnItemClickListener(new OnItemClickListener() { 
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//如果当前项存在，就执行点击事件；否则提示不存在
				if(task.getTaskItemList().get((int)id).isExist()){
					switch(position - taskItemListView.getHeaderViewsCount()){
						case 0 : setTaskName(); break;
						case 1 : setTaskTriggerTime(); break;
						case 2 : setTaskRepeat(); break;
						case 3 : setBluetooth(); break;
						case 4 : setWifi(); break;
						case 5 : setMobileNetwork(); break;
						case 6 : setVolume(); break;
						case 7 : setRingnoteMode(); break;
						case 8 : setAirplaneMode(); break;
						case 9 : setScreentBrightness(); break;
						case 10 : setScreenDormant(); break;
						case 11 : setSendMessage(); break;
						case 12 : setOpenApplication(); break;
					}
				}else{
					toastL(getString(R.string.hint_deviceNotExist) + task.getTaskItemList().get(position - taskItemListView.getHeaderViewsCount()).getMainText());
				}
			}
		});
	}

	@Override
	public void onInitData(Bundle savedInstanceState) {
		//设置标题
		if(isAddTask()){
			getActionBar().setTitle(R.string.taskEdit_text_title_addTask);
		}else{
			getActionBar().setTitle(R.string.taskEdit_text_title_updateTask);
		}
		
		//创建默认的任务对象
		task = new Task(getBaseContext());
		
		//如果保存数据的对象不为null，说明是在后台被销毁又重新创建的，就要从保存实体的对象中取数据
		if(savedInstanceState != null){
			try {
				task.fromJSON(savedInstanceState.getString(SAVE_CONTENT));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}else{
			//如果是修改任务就从Intent中获取要编辑的任务的JSON字符串并解析
			if(!isAddTask()){
				try {
					task.fromJSON(getIntent().getStringExtra(Task.KEY));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		
		taskItemListView.openListHeaderReboundMode();
		taskItemListView.openListFooterReboundMode();
		//初始化任务项Adapter并与任务项列表绑定
		taskItemListView.setAdapter(taskItemAdapter = new TaskItemAdapter(getBaseContext(), task.getTaskItemList()));
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putString(SAVE_CONTENT, task.toJSON());
		super.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == REQUEST_CODE_MESSAGE_EDIT && resultCode == RESULT_OK && data != null){
			task.getSendMessage().fromJSON(data.getStringExtra(SendMessage.KEY));
			taskItemAdapter.notifyDataSetChanged();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 设置任务名称 
	 */
	private void setTaskName(){
		final TaskName taskName = task.getTaskName();
		
		//创建对话框并设置标题
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.taskItem_setName);
		
		//设置要显示的元素列表
		final String[] taskNames = getResources().getStringArray(R.array.taskItem_name_items);
		builder.setItems(taskNames, new OnClickListener() { public void onClick(DialogInterface dialog, int which) {
			taskName.setContent(taskNames[which]);
			taskItemAdapter.notifyDataSetChanged();
		}});
		
		//设置取消按钮
		builder.setNegativeButton(R.string.base_cancel, null);
		
		//设置无按钮
		builder.setNeutralButton(R.string.base_none, new OnClickListener() { public void onClick(DialogInterface dialog, int which) {
			taskName.setContent(null);
			taskItemAdapter.notifyDataSetChanged();
		}});
		
		//设置自定义按钮
		builder.setPositiveButton(R.string.base_custom, new OnClickListener() { public void onClick(DialogInterface dialog, int which) {
			setCustomTaskName();
		}});
		
		builder.create().show();
	}
	
	/**
	 * 设置自定义任务名称
	 */
	private void setCustomTaskName(){
		final TaskName taskName = task.getTaskName();
		
		//创建对话框并设置标题
		AlertDialog.Builder builder = new AlertDialog.Builder(TaskEditActivity.this);
		builder.setTitle(getString(R.string.taskItem_name_customTitle));
		
		//设置要显示的视图并初始化编辑框的默认值
		View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.task_edit_custom_name, null);
		final EditText taskNameEdit = (EditText) view.findViewById(R.id.edit_taskEditCustomName_name);
		taskNameEdit.setText(task.getTaskName().getContent());
		builder.setView(view);
		
		//设置取消按钮
		builder.setNegativeButton(R.string.base_cancel, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				DialogUtils.setDialogClickClose(alertDialog, true);
			}
		});
		
		//设置确定按钮
		builder.setPositiveButton(R.string.base_confirm, new OnClickListener() { public void onClick(DialogInterface dialog, int which) {
			String name = taskNameEdit.getEditableText().toString();
			if("".equals(name)){
				toastL(R.string.hint_taskNameNull);
				DialogUtils.setDialogClickClose(alertDialog, false);
			}else{
				taskName.setContent(name);
				taskItemAdapter.notifyDataSetChanged();
				DialogUtils.setDialogClickClose(alertDialog, true);
			}
		}});
		
		builderAlertDialog(builder.create()).show();
	}
	
	/**
	 * 设置任务触发时间
	 */
	private void setTaskTriggerTime(){
		final TriggerTime triggerTimer = task.getTriggerTime();
		TimePickerDialog timePickerDialog = new TimePickerDialog(this, new OnTimeSetListener() {
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				triggerTimer.setHour(hourOfDay);
				triggerTimer.setMinute(minute);
				taskItemAdapter.notifyDataSetChanged();
			}
		}, triggerTimer.getHour(), triggerTimer.getMinute(), true);
		timePickerDialog.setTitle(getString(R.string.taskItem_setTiggerTime));
		timePickerDialog.show();
	}
	
	/**
	 * 设置任务触发日期
	 */
	private void setTaskTriggerDate(){
		final TriggerTime triggerTimer = task.getTriggerTime();
		DatePickerDialog datePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				triggerTimer.setYear(year);
				triggerTimer.setMonth(monthOfYear+1);
				triggerTimer.setDay(dayOfMonth);
				taskItemAdapter.notifyDataSetChanged();
			}
		}, triggerTimer.getYear(), triggerTimer.getMonth()-1, triggerTimer.getDay());
		datePickerDialog.setTitle(R.string.taskItem_repeat_triggerDate);
		datePickerDialog.show();
	}
	
	/**
	 * 获取设置任务重复的对话框
	 * @return 设置任务重复的对话框
	 */
	private void setTaskRepeat(){
		final Repeat repeat = task.getRepeat();
		if(repeat.isChecked()){
			//创建对话框并设置标题
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.taskItem_repeat_selectRepeatWay);
			
			//设置取消按钮
			builder.setNegativeButton(R.string.base_cancel, null);
			
			//设置单选元素
			builder.setSingleChoiceItems(repeat.getItems(), repeat.getRepeatWay().getIndex(), new OnClickListener() { public void onClick(DialogInterface dialog, int which) {
				alertDialog.dismiss();
				switch (which) {
				case 0: setRepeatMinuteOrHourOrDay(RepeatWay.MINUTE); break;
				case 1: setRepeatMinuteOrHourOrDay(RepeatWay.HOUR); break;
				case 2: setRepeatMinuteOrHourOrDay(RepeatWay.DAY); break;
				case 3: setRepeatWeekOrMonth(RepeatWay.WEEK); break;
				case 4: setRepeatWeekOrMonth(RepeatWay.MONTH); break;
				case 5: 
					task.getRepeat().setValues(new int[]{1});
					task.getRepeat().setRepeatWay(RepeatWay.EVERY_DAY);
					taskItemAdapter.notifyDataSetChanged();
					break;
				}
			}});
			
			//设置对话框显示、取消、销毁、事件监听器并显示
			builderAlertDialog(builder.create()).show();
		}else{
			setTaskTriggerDate();
		}
	}
	
	/**
	 * 设置按分钟、按小时、按天重复项
	 * @param repeatWay 重复方式
	 */
	private void setRepeatMinuteOrHourOrDay(final RepeatWay repeatWay){
		final Repeat repeat = task.getRepeat();
		//初始化标题
		String title = "";
		if(repeatWay == RepeatWay.MINUTE){
			title = getString(R.string.taskItem_repeat_minute);
		}else if(repeatWay == RepeatWay.HOUR){
			title = getString(R.string.taskItem_repeat_hour);
		}else if(repeatWay == RepeatWay.DAY){
			title = getString(R.string.taskItem_repeat_day);
		}
		
		View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.task_edit_value, null);
		
		//设置文本标识
		TextView textView = (TextView) view.findViewById(R.id.text_taskEditValue_type);
		if(repeatWay == RepeatWay.MINUTE){
			textView.setText(R.string.base_minutes);
		}else if(repeatWay == RepeatWay.HOUR){
			textView.setText(R.string.base_hours);
		}else if(repeatWay == RepeatWay.DAY){
			textView.setText(R.string.base_tian);
		}
		
		//获取文本编辑框并且如果任务的重复方式与当前的重复方式相同的话就，就将编辑框的默认值设置任务重复之前的值
		final EditText editText = (EditText) view.findViewById(R.id.edit_taskEditValue);
		if(repeat.getRepeatWay() == repeatWay && repeat.getValues().length > 0){
			editText.setText(""+repeat.getValues()[0]);
		}
		
		//创建对话框
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		//设置标题
		builder.setTitle(title);
		//设置显示的视图
		builder.setView(view);
		//设置取消按钮点击事件监听器
		builder.setNegativeButton(R.string.base_cancel, new OnClickListener() { public void onClick(DialogInterface dialog, int which) {
			DialogUtils.setDialogClickClose(alertDialog, true);
		}});
		//设置确定按钮点击事件监听器
		builder.setPositiveButton(R.string.base_confirm, new OnClickListener() { public void onClick(DialogInterface dialog, int which) {
			//获取用户输入的值
			String value = editText.getText().toString();
			//如果为空，提示不能为空并不关闭对话框
			if("".equals(value)){
				toastL(R.string.hint_contentNotNull);
				DialogUtils.setDialogClickClose(alertDialog, false);
			}else{
				//设置重复的值
				repeat.setValues(new int[]{Integer.valueOf(value)});
				//设置重复方式
				repeat.setRepeatWay(repeatWay);
				//关闭对话框
				DialogUtils.setDialogClickClose(alertDialog, true);
				//刷新列表
				taskItemAdapter.notifyDataSetChanged();
			}
		}});
		
		//设置对话框显示、取消、销毁、事件监听器并显示
		builderAlertDialog(builder.create()).show();
	}
	
	/**
	 * 设置按星期、按月重复
	 * @param repeatWay 重复方式
	 */
	private void setRepeatWeekOrMonth(final RepeatWay repeatWay){
		final Repeat repeat = task.getRepeat();
		
		//初始化标题以及列表项
		String title = "";
		String[] item = null;
		if(repeatWay == RepeatWay.WEEK){
			title = getString(R.string.taskItem_repeat_week);
			item = task.getRepeat().getWeeks();
		}else if(repeatWay == RepeatWay.MONTH){
			title = getString(R.string.taskItem_repeat_month);
			item = task.getRepeat().getDays();
		}
		
		//初始化选中项
		final boolean[] checkeds = new boolean[item.length];
		if(task.getRepeat().getRepeatWay() == repeatWay){
			for(int i : task.getRepeat().getValues()){
				checkeds[i-1] = true;
			}
		}
		
		//创建对话框并设置标题
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(title);
		
		//设置多选列表
		builder.setMultiChoiceItems(item, checkeds, new OnMultiChoiceClickListener() { public void onClick(DialogInterface dialog, int which, boolean isChecked) {
			checkeds[which] = isChecked;
		}});
		
		//设置取消按钮
		builder.setNegativeButton(R.string.base_cancel, new OnClickListener() { public void onClick(DialogInterface dialog, int which) {
			DialogUtils.setDialogClickClose(alertDialog, true);
		}});
		
		//设置确定按钮
		builder.setPositiveButton(R.string.base_confirm, new OnClickListener() { public void onClick(DialogInterface dialog, int which) {
			//检验是否有项被选中
			boolean isCheckeds = false;
			for(boolean boo : checkeds){
				if(boo){
					isCheckeds = true;
					break;
				}
			}
			//如果有被选中的
			if(isCheckeds){
				ArrayList<Integer> integerList = new ArrayList<Integer>();
				for(int w = 0; w < checkeds.length; w++){
					if(checkeds[w]){
						integerList.add(w+1);
					}
				}
				repeat.setValues(integerList.toArray(new Integer[integerList.size()]));
				repeat.setRepeatWay(repeatWay);
				DialogUtils.setDialogClickClose(alertDialog, true);
				taskItemAdapter.notifyDataSetChanged();
			}else{
				toastL(R.string.hint_mustSelected);
				DialogUtils.setDialogClickClose(alertDialog, false);
			}
		}});
		
		//设置对话框显示、取消、销毁、事件监听器并显示
		builderAlertDialog(builder.create()).show();
	}
	
	/**
	 * 设置蓝牙
	 */
	private void setBluetooth(){
		final Bluetooth bluetooth = task.getBluetooth();
		
		//创建对话框并设置标题
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.taskItem_bluetooth);
		
		//设置单选列表
		builder.setSingleChoiceItems(getResources().getStringArray(R.array.taskItem_bluetooth_itemNames), bluetooth.isOpen()?0:1, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				bluetooth.setOpen(which == 0);
				alertDialog.dismiss();
				taskItemAdapter.notifyDataSetChanged();
			}
		});
		
		//设置取消按钮
		builder.setNegativeButton(R.string.base_cancel, null);
		
		//设置对话框显示、取消、销毁、事件监听器并显示
		builderAlertDialog(builder.create()).show();
	}
	
	/**
	 * 设置wifi
	 */
	private void setWifi(){
		final Wifi wifi = task.getWifi();
		
		//创建对话框并设置标题
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.taskItem_wifi);
		
		//设置单选列表
		builder.setSingleChoiceItems(getResources().getStringArray(R.array.taskItem_wifi_itemNames), wifi.isOpen()?0:1, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				wifi.setOpen(which == 0);
				alertDialog.dismiss();
				taskItemAdapter.notifyDataSetChanged();
			}
		});
		
		//设置取消按钮
		builder.setNegativeButton(R.string.base_cancel, null);
		
		//设置对话框显示、取消、销毁、事件监听器并显示
		builderAlertDialog(builder.create()).show();
	}
	
	/**
	 * 设置移动网络
	 */
	private void setMobileNetwork(){
		final MobileNetwork mobileNetwork = task.getMobileNetwork();
		
		//创建对话框并设置标题
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.taskItem_mobileNetwork);
		
		//设置单选列表
		builder.setSingleChoiceItems(getResources().getStringArray(R.array.taskItem_mobileNetwork_itemNames), mobileNetwork.isOpen()?0:1, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mobileNetwork.setOpen(which == 0);
				alertDialog.dismiss();
				taskItemAdapter.notifyDataSetChanged();
			}
		});
		
		//设置取消按钮
		builder.setNegativeButton(R.string.base_cancel, null);
		
		//设置对话框显示、取消、销毁、事件监听器并显示
		builderAlertDialog(builder.create()).show();
	}
	
	/**
	 * 设置音量
	 */
	private void setVolume(){
		final Volume volume = task.getVolume();
		
		final Attribute attribute = new Attribute();
		attribute.int1 = volume.getMediaVolume();
		attribute.int2 = volume.getAlarmVolume();
		attribute.int3 = volume.getRingtoneVolume();
		attribute.int4 = volume.getNotificationVolme();
		attribute.int5 = volume.getVoiceCallVolme();
		attribute.int6 = volume.getSystemVolume();
		
		//创建要显示的视图并设置事件
		View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.task_edit_volume, null);
		SeekBar mediaSeekBar = (SeekBar) view.findViewById(R.id.seekBar_taskEditVolume_media);
		SeekBar alarmSeekBar = (SeekBar) view.findViewById(R.id.seekBar_taskEditVolume_alarm);
		SeekBar ringtoneSeekBar = (SeekBar) view.findViewById(R.id.seekBar_taskEditVolume_ringtone);
		SeekBar notifacationSeekBar = (SeekBar) view.findViewById(R.id.seekBar_taskEditVolume_notification);
		SeekBar voiceCallSeekBar = (SeekBar) view.findViewById(R.id.seekBar_taskEditVolume_voiceCall);
		SeekBar systemSeekBar = (SeekBar) view.findViewById(R.id.seekBar_taskEditVolume_system);
		
		AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		mediaSeekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
		alarmSeekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM));
		ringtoneSeekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_RING));
		notifacationSeekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION));
		voiceCallSeekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL));
		systemSeekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM));
		
		mediaSeekBar.setProgress(attribute.int1);
		alarmSeekBar.setProgress(attribute.int2);
		ringtoneSeekBar.setProgress(attribute.int3);
		notifacationSeekBar.setProgress(attribute.int4);
		voiceCallSeekBar.setProgress(attribute.int5);
		systemSeekBar.setProgress(attribute.int6);
		
		mediaSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			public void onStopTrackingTouch(SeekBar seekBar) {}
			public void onStartTrackingTouch(SeekBar seekBar) {}
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				attribute.int1 = progress;
			}
		});
		
		alarmSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			public void onStopTrackingTouch(SeekBar seekBar) {}
			public void onStartTrackingTouch(SeekBar seekBar) {}
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				attribute.int2 = progress;
			}
		});
		
		ringtoneSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			public void onStopTrackingTouch(SeekBar seekBar) {}
			public void onStartTrackingTouch(SeekBar seekBar) {}
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				attribute.int3 = progress;
			}
		});
		
		notifacationSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			public void onStopTrackingTouch(SeekBar seekBar) {}
			public void onStartTrackingTouch(SeekBar seekBar) {}
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				attribute.int4 = progress;
			}
		});
		
		voiceCallSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			public void onStopTrackingTouch(SeekBar seekBar) {}
			public void onStartTrackingTouch(SeekBar seekBar) {}
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				attribute.int5 = progress;
			}
		});
		
		systemSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			public void onStopTrackingTouch(SeekBar seekBar) {}
			public void onStartTrackingTouch(SeekBar seekBar) {}
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				attribute.int6 = progress;
			}
		});
		
		//创建对话框，设置标题、取消按钮、确定按钮以及要显示的视图并显示
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.taskItem_volume));
		builder.setNegativeButton(R.string.base_cancel, null);
		builder.setPositiveButton(R.string.base_confirm, new OnClickListener() { public void onClick(DialogInterface dialog, int which) {
			volume.setMediaVolume(attribute.int1);
			volume.setAlarmVolume(attribute.int2);
			volume.setRingtoneVolume(attribute.int3);
			volume.setNotificationVolme(attribute.int4);
			volume.setVoiceCallVolme(attribute.int5);
			volume.setSystemVolume(attribute.int6);
			taskItemAdapter.notifyDataSetChanged();
		}});
		builder.setView(view);
		builder.create().show();
	}
	
	/**
	 * 设置铃声模式
	 */
	private void setRingnoteMode(){
		final RingnoteMode ringnoteMode = task.getRingnoteMode();
		
		//创建对话框并设置标题
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.taskItem_ringnoteMode);
		
		//设置单选列表
		builder.setSingleChoiceItems(getResources().getStringArray(R.array.taskItem_ringnoteMode_itemNames), ringnoteMode.getRingnoteMode().getIndex(), new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch(which){
					case 0 : 
						ringnoteMode.setRingnoteMode(RingnoteModeEnum.RINGDOWN_AND_VRIBATE);
						break;
					case 1 : 
						ringnoteMode.setRingnoteMode(RingnoteModeEnum.RINGDOWN_NOT_VRIBATE);
						break;
					case 2 : 
						ringnoteMode.setRingnoteMode(RingnoteModeEnum.NOT_RINGDOWN_VRIBATE);
						break;
					case 3 : 
						ringnoteMode.setRingnoteMode(RingnoteModeEnum.NOT_RINGDOWN_NOT_VRIBATE);
						break;
				}
				alertDialog.dismiss();
				taskItemAdapter.notifyDataSetChanged();
			}
		});
		
		//设置取消按钮
		builder.setNegativeButton(R.string.base_cancel, null);
		
		//设置对话框显示、取消、销毁、事件监听器并显示
		builderAlertDialog(builder.create()).show();
	}
	
	/**
	 * 设置飞行模式
	 */
	private void setAirplaneMode(){
		final AirplaneMode airplaneMode = task.getAirplaneMode();
		
		//创建对话框并设置标题
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.taskItem_airplaneMode);
		
		//设置单选列表
		builder.setSingleChoiceItems(getResources().getStringArray(R.array.taskItem_airplaneMode_itemNames), airplaneMode.isOpen()?0:1, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				airplaneMode.setOpen(which == 0);
				alertDialog.dismiss();
				taskItemAdapter.notifyDataSetChanged();
			}
		});
		
		//设置取消按钮
		builder.setNegativeButton(R.string.base_cancel, null);
		
		//设置对话框显示、取消、销毁、事件监听器并显示
		builderAlertDialog(builder.create()).show();
	}
	
	/**
	 * 设置屏幕亮度
	 */
	private void setScreentBrightness(){
		final ScreenBrightness screenBrightness = task.getScreenBrightness();
		final Attribute attribute = new Attribute();
		attribute.int1 = screenBrightness.getValue();//标识屏幕亮度值
		attribute.int2 = SystemUtils.getScreenBrightness(getBaseContext());//旧的屏幕亮度值
		attribute.boolean1 = screenBrightness.isAutoAdjustmen();//表示是否自动调节屏幕亮度
		attribute.boolean2 = false;//表示在对话框关闭后是否需要恢复屏幕亮度
		
		//创建要显示的视图并设置相关事件
		View view = LayoutInflater.from(this).inflate(R.layout.task_edit_screen_brightness, null);
		CheckBox autoCheckBox = (CheckBox) view.findViewById(R.id.checkBox_taskEditSB_auto);
		final CheckBox preview = (CheckBox) view.findViewById(R.id.checkBox_taskEditSB_preview);
		final SeekBar seekBar = (SeekBar)  view.findViewById(R.id.seekBar_taskEditSB);
		
		//设置自动调节复选框的默认值
		autoCheckBox.setChecked(attribute.boolean1);
		
		//如果是自动调节就将预览复选框已经拖动进度条设为不可用的
		if(attribute.boolean1){
			preview.setEnabled(false);
			seekBar.setEnabled(false);
		}
		
		//设置自动调节复选框的值的改变事件
		autoCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() { public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			preview.setEnabled(!isChecked);
			seekBar.setEnabled(!isChecked);
			attribute.boolean1 = isChecked;
		}});
		
		//设置预览复选框为选中的标识开启预览
		preview.setChecked(true);

		//设置亮度拖动条的最大值为255
		seekBar.setMax(255);
		//设置进度为当前屏幕的亮度
		seekBar.setProgress(attribute.int1);
		//设置改变事件
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) { }
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) { }
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				attribute.int1 = progress;
				if(preview.isChecked()){
					SystemUtils.setWindowBrightness(TaskEditActivity.this, progress);
				}
				attribute.boolean2 = true;
			}
		});
		
		//创建对话框，设置标题、取消按钮、确定按钮、以及销毁事件
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.taskItem_screenBrightness);
		builder.setNegativeButton(R.string.base_cancel, null);
		builder.setPositiveButton(R.string.base_confirm, new OnClickListener() { public void onClick(DialogInterface dialog, int which) {
			screenBrightness.setValue(attribute.int1);
			screenBrightness.setAutoAdjustmen(attribute.boolean1);
			taskItemAdapter.notifyDataSetChanged();
		}});
		builder.setView(view);
		final AlertDialog scrrenBrightnessAlertDialog = builder.create();
		
		scrrenBrightnessAlertDialog.setOnDismissListener(new OnDismissListener() { public void onDismiss(DialogInterface dialog) {
			if(attribute.boolean2){
				SystemUtils.setWindowBrightness(TaskEditActivity.this, attribute.int2);	
			}
		}});
		
		scrrenBrightnessAlertDialog.show();
	}
	
	/**
	 * 设置屏幕休眠
	 */
	private void setScreenDormant(){
		final ScreenDormant sd = task.getScreenDormant();
		
		//计算默认选中项
		int selected = 0;
		final int[] itemValues = getResources().getIntArray(R.array.screenDormant_itemValues);
		int currentScreenDormantTime = sd.getValue();
		for(int w = 0; w < itemValues.length; w++){
			if(currentScreenDormantTime == itemValues[w]){
				selected = w;
				break;
			}
		}
		
		//创建对话框并设置标题
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.taskItem_screenDormant));
		
		//设置单选列表
		builder.setSingleChoiceItems(getResources().getStringArray(R.array.taskItem_screenDormant_itemNames), selected, new OnClickListener() {public void onClick(DialogInterface dialog, int which) {
			sd.setValue(itemValues[which]);
			taskItemAdapter.notifyDataSetChanged();
			alertDialog.dismiss();
		}});
		
		//设置取消按钮
		builder.setNegativeButton(R.string.base_cancel, null);
		
		//设置对话框显示、取消、销毁、事件监听器并显示
		builderAlertDialog(builder.create()).show();
	}
	
	/**
	 * 设置发送短信
	 */
	private void setSendMessage(){
		Bundle bundle = new Bundle();
		bundle.putString(SendMessage.KEY, task.getSendMessage().toJSON());
		startActivityForResult(MessageEditActivity.class, REQUEST_CODE_MESSAGE_EDIT, bundle);
	}
	
	/**
	 * 设置打开应用程序
	 */
	private void setOpenApplication(){
		new AsyncTask<Integer, Integer, List<Application>>(){
			private	ProgressDialog progressDialog;
			@Override
			protected void onPreExecute() {
				progressDialog = new ProgressDialog(TaskEditActivity.this);
				progressDialog.setTitle(R.string.taskItem_openApplication_readProfressDiaog_title);
				progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progressDialog.show();
				progressDialog.setCancelable(false);
			}
			
			@Override
			protected List<Application> doInBackground(Integer... params) {
				PackageManager packageManager = getPackageManager(); 
				List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
				
				List<Application> applicationList = new ArrayList<Application>();
				for(PackageInfo packageInfo : packageInfoList){
					try {
						ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageInfo.packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
						if((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0){
							Application application = new Application();
							application.setPackageName(packageInfo.packageName);
							application.setName(applicationInfo.loadLabel(packageManager).toString());
							application.setIcon(packageManager.getApplicationIcon(application.getPackageName()));
							applicationList.add(application);
						}
					} catch (NameNotFoundException e) {
						e.printStackTrace();
					}
				}
				return applicationList;
			}

			@Override
			protected void onPostExecute(final List<Application> result) {
				sort(result);
				AlertDialog.Builder builder = new AlertDialog.Builder(TaskEditActivity.this);
				builder.setTitle(R.string.taskItem_openApplication_editTitle);
				
				ListView listView = new ListView(getBaseContext());
				final ApplicationAdapter applicationAdapter = new ApplicationAdapter(getBaseContext(), result);
				applicationAdapter.setAbsListView(listView);
				applicationAdapter.setChoiceWay(MyBaseAdapter.ChoiceWay.SINGLE_CHOICE);
				applicationAdapter.openChiceMode();
				applicationAdapter.intoChoiceMode();
				if(task.getOpenApplication().getContent() != null){
					for(int w = 0; w < result.size(); w++){
						if(result.get(w).getPackageName().equals(task.getOpenApplication().getContent())){
							applicationAdapter.setChecked(w);
						}
					}
				}
				applicationAdapter.setChoiceModeListener(new ChoiceModeListener() {
					@Override
					public void onUpdateSelectedNumber(int selectedNumber) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onSelectAll() {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onOpen() {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onItemClick(int realPosition, boolean isChecked) {
						task.getOpenApplication().setContent(result.get(realPosition).getPackageName());
						task.getOpenApplication().setName(result.get(realPosition).getName());
						alertDialog.dismiss();
						taskItemAdapter.notifyDataSetChanged();
					}
					
					@Override
					public void onInto() {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onExit() {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onDeselectAll() {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onClose() {
						// TODO Auto-generated method stub
						
					}
				});
				
				listView.setAdapter(applicationAdapter);
				builder.setView(listView);
				builder.setNegativeButton(R.string.base_cancel, null);
				builder.setPositiveButton(R.string.base_none, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						task.getOpenApplication().setContent(null);
						task.getOpenApplication().setName(null);
						taskItemAdapter.notifyDataSetChanged();
					}
				});
				progressDialog.dismiss();
				builderAlertDialog(builder.create()).show();
			}
		}.execute(0);
	}
	
	/**
	 * 对应用程序列表进行排序
	 * @param applicationList
	 */
	private void sort(List<Application> applicationList){
		Collections.sort(applicationList, new Comparator<Application>() {
			public int compare(Application lhs, Application rhs) {
				return StringUtils.compare(lhs.getName(), rhs.getName());
			}
		});
	}
	
	/**
	 * 判断是否是添加任务
	 * @return 是否是添加任务
	 */
	private boolean isAddTask(){
		return getIntent().getBooleanExtra(TaskListActivity.KEY_IS_ADD, true);
	}

	/**
	 * 验证触发时间是否合法
	 * @return 触发时间是否合法
	 */
	private boolean valiTriggerTime(){
		boolean result = true;
		//如果没有开启重复，就判断任务时间是否大于当前时间
		if(!task.getRepeat().isChecked()){
			int[] currentTimesBy24Hour = DateTimeUtils.getCurrentTimesBy24Hour();
			int contrastResult = Time.contrastTime(
				task.getTriggerTime().getYear(), task.getTriggerTime().getMonth(), task.getTriggerTime().getDay(), 
				task.getTriggerTime().getHour(), task.getTriggerTime().getMinute(), 
				currentTimesBy24Hour[0], currentTimesBy24Hour[1], 
				currentTimesBy24Hour[2], currentTimesBy24Hour[3], 
				currentTimesBy24Hour[4]
			);
			if(contrastResult <= 0){
				result = false;
				toastL(R.string.hint_lessThanCurrnTime);
			}
		}
		
		//如果上一步验证通过，就验证是否与其他任务的时间有冲突
		if(result){
			for(Task currTask : getMyApplication().getTaskList()){
				if(currTask.getCreateTime() != task.getCreateTime() && currTask.getTriggerTime().getHour() == task.getTriggerTime().getHour() && currTask.getTriggerTime().getMinute() == task.getTriggerTime().getMinute()){
					result = false;
					break;
				}
			}
			//如果验证失败就提示相关信息
			if(!result){
				toastL(R.string.hint_timeConflict);
			}
		}
		
		return result;
	}
	
	/**
	 * 验证打开应用程序是否合法
	 * @return 打开应用程序是否合法
	 */
	private boolean valiOpenApplication(){
		boolean result = true;
		if(task.getOpenApplication().isChecked() && task.getOpenApplication().getContent() == null){
			result = false;
			toastS(R.string.hint_applicationNoHave);
		}
		return result;
	}
	
	/**
	 * 验证发送短信是否合法
	 * @return 发送短信是否合法
	 */
	private boolean valiMessage(){
		boolean result = true;
		if(task.getSendMessage().isChecked()){
			if(task.getSendMessage().getContent() == null){
				result = false;
				toastS(R.string.hint_messageNull);
			}else if(task.getSendMessage().getContactsList().size() < 0){
				result = false;
				toastS(R.string.hint_contactsNull);
			}
		}
		return result;
	}
	
	/**
	 * 绑定对话框，给对话框添加显示、取消、销毁事件
	 * @param newAlertDialog
	 * @return
	 */
	private AlertDialog builderAlertDialog(final AlertDialog newAlertDialog){
		newAlertDialog.setOnShowListener(new OnShowListener() {
			@Override
			public void onShow(DialogInterface dialog) {
				alertDialog = newAlertDialog;
			}
		});
		newAlertDialog.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				alertDialog = null;
			}
		});
		newAlertDialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				alertDialog = null;
			}
		});
		return newAlertDialog;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.task_edit, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_taskEdit_save:
				//验证时间、短信、打开应用程序是否合法
				if(valiTriggerTime() && valiMessage() && valiOpenApplication()){
					try {
						//验证除了重复之外是否开启的其他的任务项
						if(task.isHaveOperation()){
							//不管是添加还是修改都将任务状态改为开启
							task.setOpen(true);
							//设置下一次执行时间
							task.getNextExecuteTime().fromJSON(task.getTriggerTime().toJSON());
							//更新当前任务的下次执行时间
							task.updateNextExecuteTime();
							//写入本地
							task.writer();
							//将要返回的数据放入Intent
							getIntent().putExtra(Task.KEY, task.toJSON());
							//设置返回的结果
							setResult(RESULT_OK, getIntent());
							//退出当前activity
							finishActivity();
							
							//弹出提示
							if(isAddTask()){
								toastL(R.string.hint_addSuccess);
							}else{
								toastL(R.string.hint_updateSuccess);
							}
						}else{
							toastL(R.string.hint_noOperation);
						}
					} catch (IOException e) {
						e.printStackTrace();
						if(isAddTask()){
							toastL(R.string.hint_addError);
						}else{
							toastL(R.string.hint_updateError);
						}
					}
				}
				break;
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}
}