package me.xiaopan.lifespirit.task.scenariomode;

import java.io.Serializable;

import me.xiaopan.lifespirit2.R;
import android.content.Context;
import android.media.AudioManager;

/**
 * 音量
 */
public class Volume extends BaseScenarioOption implements Serializable {
	private static final long serialVersionUID = 1L;
	private int mediaVolume = -5;
	private int alarmVolume = -5;
	private int ringtoneVolume = -5;
	private int notificationVolme = -5;
	private int voiceCallVolme = -5;
	private int systemVolume = -5;
	
	public Volume(Context context) {
		AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		setMediaVolume(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
		setAlarmVolume(audioManager.getStreamVolume(AudioManager.STREAM_ALARM));
		setRingtoneVolume(audioManager.getStreamVolume(AudioManager.STREAM_RING));
		setNotificationVolme(audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION));
		setVoiceCallVolme(audioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL));
		setSystemVolume(audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM));
	}

	@Override
	public void onExecute(Context context) {
		if(isEnable()){
			AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
			audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, getMediaVolume(), 0);
			audioManager.setStreamVolume(AudioManager.STREAM_ALARM, getAlarmVolume(), 0);
			audioManager.setStreamVolume(AudioManager.STREAM_RING, getRingtoneVolume(), 0);
			audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, getNotificationVolme(), 0);
			audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, getVoiceCallVolme(), 0);
			audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, getSystemVolume(), 0);
		}
	}

	@Override
	public String onGetIntro(Context context) {
		String result = context.getString(R.string.taskItem_volume_media)+getMediaVolume()+", ";
		result += context.getString(R.string.taskItem_volume_alarm)+getAlarmVolume()+", ";
		result += context.getString(R.string.taskItem_volume_ringtone)+getRingtoneVolume()+", ";
		result += context.getString(R.string.taskItem_volume_notification)+getNotificationVolme()+", ";
		result += context.getString(R.string.taskItem_volume_voiceCall)+getVoiceCallVolme()+", ";
		result += context.getString(R.string.taskItem_volume_system)+getSystemVolume();
		return result;
	}

	public int getMediaVolume() {
		return mediaVolume;
	}

	public void setMediaVolume(int mediaVolume) {
		this.mediaVolume = mediaVolume;
	}

	public int getAlarmVolume() {
		return alarmVolume;
	}

	public void setAlarmVolume(int alarmVolume) {
		this.alarmVolume = alarmVolume;
	}

	public int getRingtoneVolume() {
		return ringtoneVolume;
	}

	public void setRingtoneVolume(int ringtoneVolume) {
		this.ringtoneVolume = ringtoneVolume;
	}

	public int getNotificationVolme() {
		return notificationVolme;
	}

	public void setNotificationVolme(int notificationVolme) {
		this.notificationVolme = notificationVolme;
	}

	public int getVoiceCallVolme() {
		return voiceCallVolme;
	}

	public void setVoiceCallVolme(int voiceCallVolme) {
		this.voiceCallVolme = voiceCallVolme;
	}

	public int getSystemVolume() {
		return systemVolume;
	}

	public void setSystemVolume(int systemVolume) {
		this.systemVolume = systemVolume;
	}
}