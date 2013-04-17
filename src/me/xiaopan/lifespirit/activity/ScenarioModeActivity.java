package me.xiaopan.lifespirit.activity;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import me.xiaopan.lifespirit.task.BaseTaskOption;
import me.xiaopan.lifespirit.task.ScenarioMode;
import me.xiaopan.lifespirit.widget.Preference;
import me.xiaopan.lifespirit2.R;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.gson.Gson;

/**
 * 情景模式界面
 */
public class ScenarioModeActivity extends BaseTaskActivity {
	public static final String PARAM_OPTIONAL_STRING_SCENARIO_MODE = "PARAM_OPTIONAL_SCENARIO_MODE";
	private ScenarioMode scenarioMode;
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
	private boolean add;
	
	@Override
	protected void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.activity_scenario_mode);
		super.onInitLayout(savedInstanceState);
		
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
		super.onInitListener(savedInstanceState);
	}

	@Override
	protected void onInitData(Bundle savedInstanceState) {
		String scenairoModeJson = getIntent().getStringExtra(PARAM_OPTIONAL_STRING_SCENARIO_MODE);
		if(add = scenairoModeJson == null){
			scenarioMode = new ScenarioMode(getBaseContext());
		}else{
			scenarioMode = new Gson().fromJson(scenairoModeJson, ScenarioMode.class);
		}
		baseTask = scenarioMode;
		super.onInitData(savedInstanceState);

		bluetoothPreference.setClickSwitchToggleState(false);
		wifiPreference.setClickSwitchToggleState(false);
		mobileNetworkPreference.setClickSwitchToggleState(false);
		airplanceModePreference.setClickSwitchToggleState(false);
		volumePreference.setClickSwitchToggleState(false);
		phoneRingtonePreference.setClickSwitchToggleState(false);
		smsRingtonePreference.setClickSwitchToggleState(false);
		notificationRingtonePreference.setClickSwitchToggleState(false);
		ringtoneModePreference.setClickSwitchToggleState(false);
		brightnessPreference.setClickSwitchToggleState(false);
		dormantPreference.setClickSwitchToggleState(false);
		
		contact = new Contact(11);
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
		private Map<Preference, BaseTaskOption> map;
		public Contact(int size){
			map = new HashMap<Preference, BaseTaskOption>(size);
		}
		
		public void put(Preference preference, BaseTaskOption scenarioModeOption){
			map.put(preference, scenarioModeOption);
		}
		
		public void invalidate(){
			Preference preference;
			BaseTaskOption taskOption;
			for(Entry<Preference, BaseTaskOption> entry : map.entrySet()){
				preference = entry.getKey();
				taskOption = entry.getValue();
				preference.setSubtitle(taskOption.onGetIntro(getBaseContext()));
			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean result = true;
		switch (item.getItemId()) {
			case R.id.menu_task_save:
				if(scenarioMode.saveToLocal(getBaseContext())){
					getIntent().putExtra(IndexActivity.RETURN_OPTIONAL_STRING, new Gson().toJson(scenarioMode));
					setResult(RESULT_OK, getIntent());
					if(add){
						toastL("新建情景模式成功！");
					}else{
						toastL("修改情景模式成功！");
					}
					finishActivity();
				}else{
					if(add){
						toastL("新建情景模式失败！");
					}else{
						toastL("修改情景模式失败！");
					}
				}
				break;
			default: result = super.onOptionsItemSelected(item); break;
		}
		return result;
	}
}