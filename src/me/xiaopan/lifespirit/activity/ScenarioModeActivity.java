package me.xiaopan.lifespirit.activity;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import me.xiaopan.androidlibrary.util.AndroidUtils;
import me.xiaopan.androidlibrary.util.AnimationUtils;
import me.xiaopan.androidlibrary.util.Colors;
import me.xiaopan.androidlibrary.util.DialogUtils;
import me.xiaopan.lifespirit.MyBaseActivity;
import me.xiaopan.lifespirit.task.ScenarioMode;
import me.xiaopan.lifespirit.task.TaskOption;
import me.xiaopan.lifespirit.task.scenariomode.ScenarioOption;
import me.xiaopan.lifespirit.util.ViewUtils;
import me.xiaopan.lifespirit.widget.Preference;
import me.xiaopan.lifespirit2.R;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnShowListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

/**
 * 情景模式界面
 */
public class ScenarioModeActivity extends MyBaseActivity {
	private TimePicker timePicker;
	private ScenarioMode scenarioMode;
	private Preference namePreference;
	private Preference repeatPreference;
	private Preference bluetoothPreference;
	private Preference wifiPreference;
	private Preference mobileNetworkPreference;
	private Preference airplanceModePreference;
	private Preference volumePreference;
	private Preference phoneRingtonePreference;
	private Preference smsRingtonePreference;
	private Preference notificationRingtonePreference;
	private Preference ringtoneModePreference;
	private Preference brightnessPreference;
	private Preference dormantPreference;
	private Contact contact;
	private AlertDialog tempAlertDialog;
	
	@Override
	protected void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.scenario_mode);
		timePicker = (TimePicker) findViewById(R.id.timePicker_scenaireMode);
		namePreference = (Preference) findViewById(R.id.preference_scenarioMode_name);
		repeatPreference = (Preference) findViewById(R.id.preference_scenarioMode_repeat);
		bluetoothPreference = (Preference) findViewById(R.id.preference_scenarioMode_bluetooth);
		wifiPreference = (Preference) findViewById(R.id.preference_scenarioMode_wifi);
		mobileNetworkPreference = (Preference) findViewById(R.id.preference_scenarioMode_mobileNetwork);
		airplanceModePreference = (Preference) findViewById(R.id.preference_scenarioMode_airplanceMode);
		volumePreference = (Preference) findViewById(R.id.preference_scenarioMode_volume);
		phoneRingtonePreference = (Preference) findViewById(R.id.preference_scenarioMode_phoneRingtone);
		smsRingtonePreference = (Preference) findViewById(R.id.preference_scenarioMode_smsRingtone);
		notificationRingtonePreference = (Preference) findViewById(R.id.preference_scenarioMode_notificationRingtone);
		ringtoneModePreference = (Preference) findViewById(R.id.preference_scenarioMode_ringtoneMode);
		brightnessPreference = (Preference) findViewById(R.id.preference_scenarioMode_brightness);
		dormantPreference = (Preference) findViewById(R.id.preference_scenarioMode_dormant);
	}

	@Override
	protected void onInitListener(Bundle savedInstanceState) {
		timePicker.setOnTimeChangedListener(new OnTimeChangedListener() {
			Calendar calendar;
			
			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				if(scenarioMode != null){
					if(calendar == null){
						calendar = new GregorianCalendar();
					}
					calendar.setTimeInMillis(System.currentTimeMillis());
					calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
					calendar.set(Calendar.MINUTE, minute);
					scenarioMode.getTriggerTime().setTimeInMillis(calendar.getTimeInMillis());
				}
			}
		});
		
		namePreference.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setName();
			}
		});
	}

	@Override
	protected void onInitData(Bundle savedInstanceState) {
		timePicker.setIs24HourView(true);
		ViewUtils.setTimePickerTextColor(timePicker, Colors.BLACK);
		
		scenarioMode = new ScenarioMode(getBaseContext());
		contact = new Contact(13);
		contact.put(namePreference, scenarioMode.getName());
		contact.put(repeatPreference, scenarioMode.getRepeat());
		contact.put(bluetoothPreference, scenarioMode.getBluetooth());
		contact.put(wifiPreference, scenarioMode.getWifi());
		contact.put(mobileNetworkPreference, scenarioMode.getMobileNetwork());
		contact.put(airplanceModePreference, scenarioMode.getAirplaneMode());
		contact.put(volumePreference, scenarioMode.getVolume());
		contact.put(phoneRingtonePreference, scenarioMode.getPhoneRingtone());
		contact.put(smsRingtonePreference, scenarioMode.getSmsRingtone());
		contact.put(notificationRingtonePreference, scenarioMode.getNotificationRingtone());
		contact.put(ringtoneModePreference, scenarioMode.getRingtoneMode());
		contact.put(brightnessPreference, scenarioMode.getBrightness());
		contact.put(dormantPreference, scenarioMode.getDormant());
		
		contact.invalidate();
	}
	
	private class Contact{
		private Map<Preference, TaskOption> map;
		public Contact(int size){
			map = new HashMap<Preference, TaskOption>(size);
		}
		
		public void put(Preference preference, TaskOption scenarioModeOption){
			map.put(preference, scenarioModeOption);
		}
		
		public void invalidate(){
			Preference preference;
			TaskOption taskOption;
			for(Entry<Preference, TaskOption> entry : map.entrySet()){
				preference = entry.getKey();
				taskOption = entry.getValue();
				preference.setSubtitle(taskOption.onGetIntro());
				if(taskOption instanceof ScenarioOption){
					preference.setEnablePreference(((ScenarioOption) taskOption).isEnable());
				}
			}
		}
	}
	
	/**
	 * 设置任务名称 
	 */
	private void setName(){
		//创建对话框并设置标题
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.taskItem_setName);

		//设置要显示的元素列表
		final String[] taskNames = getResources().getStringArray(R.array.taskItem_name_items);
		builder.setItems(taskNames, new DialogInterface.OnClickListener() { 
			@Override
			public void onClick(DialogInterface dialog, int which) {
				scenarioMode.getName().setName(taskNames[which]);
				contact.invalidate();
			}
		});

		//设置取消按钮
		builder.setNegativeButton(R.string.base_cancel, null);

		//设置无按钮
		builder.setNeutralButton(R.string.base_none, new DialogInterface.OnClickListener() { 
			@Override
			public void onClick(DialogInterface dialog, int which) {
				scenarioMode.getName().setName(null);
				contact.invalidate();
			}
		});

		//设置自定义按钮
		builder.setPositiveButton(R.string.base_custom, new DialogInterface.OnClickListener() { 
			@Override
			public void onClick(DialogInterface dialog, int which) {
				setCustomName();
			}
		});

		builder.create().show();
	}

	/**
	 * 设置自定义任务名称
	 */
	private void setCustomName(){
		//创建对话框并设置标题
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		//设置要显示的视图并初始化编辑框的默认值
		final EditText nameEdit = new EditText(getBaseContext());
		nameEdit.setHint(R.string.taskEditCustomName_edit_name_hint);
		nameEdit.setText(scenarioMode.getName().getName());
		LinearLayout linearLayout = me.xiaopan.androidlibrary.util.ViewUtils.createLinearLayout(getBaseContext(), LinearLayout.HORIZONTAL, LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		linearLayout.setPadding(30, 50, 30, 50);
		linearLayout.addView(nameEdit, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		builder.setView(linearLayout);

		//设置取消按钮
		builder.setNegativeButton(R.string.base_cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				DialogUtils.setDialogClickClose(tempAlertDialog, true);
			}
		});

		//设置确定按钮
		builder.setPositiveButton(R.string.base_confirm, new DialogInterface.OnClickListener() { public void onClick(DialogInterface dialog, int which) {
			String name = nameEdit.getEditableText().toString();
			if("".equals(name)){
				toastL(R.string.hint_taskNameNull);
				AnimationUtils.shake(nameEdit);
				DialogUtils.setDialogClickClose(tempAlertDialog, false);
			}else{
				scenarioMode.getName().setName(name);
				DialogUtils.setDialogClickClose(tempAlertDialog, true);
				contact.invalidate();
			}
		}});

		builderAlertDialog(builder.create(), nameEdit).show();
	}

	/**
	 * 绑定对话框，给对话框添加显示、取消、销毁事件
	 * @param newAlertDialog
	 * @return
	 */
	private AlertDialog builderAlertDialog(final AlertDialog newAlertDialog, final EditText editText){
		newAlertDialog.setOnShowListener(new OnShowListener() {
			@Override
			public void onShow(DialogInterface dialog) {
				tempAlertDialog = newAlertDialog;
				if(editText != null){
					AndroidUtils.openSoftKeyboard(getBaseContext(), editText);
				}
			}
		});
		newAlertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				tempAlertDialog = null;
			}
		});
		newAlertDialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				tempAlertDialog = null;
			}
		});
		return newAlertDialog;
	}
}