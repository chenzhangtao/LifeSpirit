package me.xiaopan.lifespirit.task.scenariomode;

import java.io.Serializable;

import me.xiaopan.lifespirit2.R;
import android.content.Context;
import android.media.AudioManager;

/**
 * 铃声模式
 */
public class RingtoneMode extends BaseScenarioOption implements Serializable {
	private static final long serialVersionUID = 1L;
	private RingnoteModeEnum ringnoteMode;

	public RingtoneMode(Context context) {
		AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
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

	@Override
	public void onExecute(Context context) {
		if(isEnable()){
			AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
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
	public String onGetIntro(Context context) {
		return context.getResources().getStringArray(R.array.taskItem_ringnoteMode_itemNames)[getRingnoteMode().getIndex()];
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