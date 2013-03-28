package me.xiaopan.lifespirit.task.scenariomode;

import me.xiaopan.lifespirit.R;
import me.xiaopan.lifespirit.task.Task;
import me.xiaopan.lifespirit.task.TaskItemImpl;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.media.AudioManager;


public class RingnoteMode extends TaskItemImpl {
	/**
	 * KEY - 表示当前任务项
	 */
	public static final String KEY = "KEY_RINGNOTE_MODE";
	private RingnoteModeEnum ringnoteMode;

	public RingnoteMode(Context context, Task task) {
		super(context, task, context.getString(R.string.taskItem_ringnoteMode));
		AudioManager audioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
		int ringnoteMode = audioManager.getRingerMode();
		int vibrateMode = audioManager.getVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER);
		switch(ringnoteMode){
			case AudioManager.RINGER_MODE_NORMAL : 
				switch(vibrateMode){
					case AudioManager.VIBRATE_SETTING_ON : setRingnoteMode(RingnoteModeEnum.RINGDOWN_AND_VRIBATE); break;
					case AudioManager.VIBRATE_SETTING_OFF : setRingnoteMode(RingnoteModeEnum.RINGDOWN_NOT_VRIBATE); break;
					case AudioManager.VIBRATE_SETTING_ONLY_SILENT : setRingnoteMode(RingnoteModeEnum.RINGDOWN_NOT_VRIBATE); break;
				}
				break;
			case AudioManager.RINGER_MODE_VIBRATE : 
				switch(vibrateMode){
					case AudioManager.VIBRATE_SETTING_ON : setRingnoteMode(RingnoteModeEnum.NOT_RINGDOWN_VRIBATE); break;
					case AudioManager.VIBRATE_SETTING_OFF : setRingnoteMode(RingnoteModeEnum.NOT_RINGDOWN_NOT_VRIBATE); break;
					case AudioManager.VIBRATE_SETTING_ONLY_SILENT : setRingnoteMode(RingnoteModeEnum.NOT_RINGDOWN_VRIBATE); break;
				}
				break;
			case AudioManager.RINGER_MODE_SILENT : 
				switch(vibrateMode){
					case AudioManager.VIBRATE_SETTING_ON : setRingnoteMode(RingnoteModeEnum.NOT_RINGDOWN_VRIBATE); break;
					case AudioManager.VIBRATE_SETTING_OFF : setRingnoteMode(RingnoteModeEnum.NOT_RINGDOWN_NOT_VRIBATE); break;
					case AudioManager.VIBRATE_SETTING_ONLY_SILENT : setRingnoteMode(RingnoteModeEnum.NOT_RINGDOWN_VRIBATE); break;
				}
				break;
		}
	}
	
	public RingnoteMode(Context context, Task task, String ringnoteModeJSON){
		this(context, task);
		fromJSON(ringnoteModeJSON);
	}

	@Override
	public String getHintText() {
		return getContext().getResources().getStringArray(R.array.taskItem_ringnoteMode_itemNames)[getRingnoteMode().getIndex()];
	}

	@Override
	public void execute() {
		if(isChecked()){
			AudioManager audioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
			if(getRingnoteMode() == RingnoteModeEnum.RINGDOWN_AND_VRIBATE){
				audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
				audioManager.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER, AudioManager.VIBRATE_SETTING_ON);
				audioManager.setVibrateSetting(AudioManager.VIBRATE_TYPE_NOTIFICATION, AudioManager.VIBRATE_SETTING_ON);
			}else if(getRingnoteMode() == RingnoteModeEnum.RINGDOWN_NOT_VRIBATE){
				audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
				audioManager.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER, AudioManager.VIBRATE_SETTING_OFF);
				audioManager.setVibrateSetting(AudioManager.VIBRATE_TYPE_NOTIFICATION, AudioManager.VIBRATE_SETTING_OFF);
			}else if(getRingnoteMode() == RingnoteModeEnum.NOT_RINGDOWN_VRIBATE){
				audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
				audioManager.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER, AudioManager.VIBRATE_SETTING_ON);
				audioManager.setVibrateSetting(AudioManager.VIBRATE_TYPE_NOTIFICATION, AudioManager.VIBRATE_SETTING_ON);
			}else if(getRingnoteMode() == RingnoteModeEnum.NOT_RINGDOWN_NOT_VRIBATE){
				audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
				audioManager.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER, AudioManager.VIBRATE_SETTING_OFF);
				audioManager.setVibrateSetting(AudioManager.VIBRATE_TYPE_NOTIFICATION, AudioManager.VIBRATE_SETTING_OFF);
			}
		}
	}

	@Override
	public String toJSON() {
		JSONObject silent = new JSONObject();
		try {
			silent.put(KEY_CHECKED, isChecked());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			silent.put(KEY_CONTENT, getRingnoteMode().name());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return silent.toString();
	}

	@Override
	public void fromJSON(String json) {
		if(json != null){
			try {
				JSONObject ringnoteMode = new JSONObject(json);
				try {
					setChecked(ringnoteMode.getBoolean(KEY_CHECKED));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				try {
					setRingnoteMode(RingnoteModeEnum.valueOf(ringnoteMode.getString(KEY_CONTENT)));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	public RingnoteModeEnum getRingnoteMode() {
		return ringnoteMode;
	}

	public void setRingnoteMode(RingnoteModeEnum ringnoteMode) {
		this.ringnoteMode = ringnoteMode;
	}
	
	public enum RingnoteModeEnum{
		RINGDOWN_AND_VRIBATE(0), 
		RINGDOWN_NOT_VRIBATE(1), 
		NOT_RINGDOWN_VRIBATE(2), 
		NOT_RINGDOWN_NOT_VRIBATE(3);
		private int index;
		private RingnoteModeEnum(int index){
			this.index = index;
		}
		
		public int getIndex(){
			return this.index;
		}
	}
}
