package me.xiaopan.lifespirit.activity;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import me.xiaopan.androidlibrary.util.Colors;
import me.xiaopan.lifespirit.MyBaseActivity;
import me.xiaopan.lifespirit2.R;
import me.xiaopan.lifespirit.task.ScenarioMode;
import me.xiaopan.lifespirit.task.TaskOption;
import me.xiaopan.lifespirit.util.ViewUtils;
import me.xiaopan.lifespirit.widget.Preference;
import android.os.Bundle;
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
	}

	@Override
	protected void onInitData(Bundle savedInstanceState) {
		timePicker.setIs24HourView(true);
		ViewUtils.setTimePickerTextColor(timePicker, Colors.BLACK);
		
		scenarioMode = new ScenarioMode(getBaseContext());
		scenarioMode.getName().setName("起床");
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
			for(Entry<Preference, TaskOption> entry : map.entrySet()){
				entry.getKey().setSubtitle(entry.getValue().onGetIntro());
			}
		}
	}
}