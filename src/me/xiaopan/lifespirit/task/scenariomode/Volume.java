package me.xiaopan.lifespirit.task.scenariomode;

import me.xiaopan.lifespirit.R;
import me.xiaopan.lifespirit.task.Task;
import me.xiaopan.lifespirit.task.TaskItemImpl;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.media.AudioManager;


public class Volume extends TaskItemImpl {
	public static final String KEY = "KEY_VOLUME";
	
	public static final String KEY_MEDIA_VOLUME = "KEY_MEDIA_VOLUME";
	public static final String KEY_ALARM_VOLUME = "KEY_ALARM_VOLUME";
	public static final String KEY_RINGTONE_VOLUME = "KEY_RINGTONE_VOLUME";
	public static final String KEY_NOTIFICATION_VOLME = "KEY_NOTIFICATION_VOLME";
	public static final String KEY_VOICE_CALL_VOLME = "KEY_VOICE_CALL_VOLME";
	public static final String KEY_SYSTEM_VOLUME = "KEY_SYSTEM_VOLUME";
	
	private int mediaVolume = -5;
	private int alarmVolume = -5;
	private int ringtoneVolume = -5;
	private int notificationVolme = -5;
	private int voiceCallVolme = -5;
	private int systemVolume = -5;
	
	public Volume(Context context, Task task) {
		super(context, task, context.getString(R.string.taskItem_volume));
		AudioManager audioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
		setMediaVolume(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
		setAlarmVolume(audioManager.getStreamVolume(AudioManager.STREAM_ALARM));
		setRingtoneVolume(audioManager.getStreamVolume(AudioManager.STREAM_RING));
		setNotificationVolme(audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION));
		setVoiceCallVolme(audioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL));
		setSystemVolume(audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM));
	}
	
	public Volume(Context context, Task task, String volumeJSON){
		this(context, task);
		fromJSON(volumeJSON);
	}

	@Override
	public String getHintText() {
		String result = getString(R.string.taskItem_volume_media)+getMediaVolume()+", ";
		result += getString(R.string.taskItem_volume_alarm)+getAlarmVolume()+", ";
		result += getString(R.string.taskItem_volume_ringtone)+getRingtoneVolume()+", ";
		result += getString(R.string.taskItem_volume_notification)+getNotificationVolme()+", ";
		result += getString(R.string.taskItem_volume_voiceCall)+getVoiceCallVolme()+", ";
		result += getString(R.string.taskItem_volume_system)+getSystemVolume();
		return result;
	}

	@Override
	public void execute() {
		if(isChecked()){
			AudioManager audioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
			audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, getMediaVolume(), 0);
			audioManager.setStreamVolume(AudioManager.STREAM_ALARM, getAlarmVolume(), 0);
			audioManager.setStreamVolume(AudioManager.STREAM_RING, getRingtoneVolume(), 0);
			audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, getNotificationVolme(), 0);
			audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, getVoiceCallVolme(), 0);
			audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, getSystemVolume(), 0);
		}
	}

	@Override
	public String toJSON() {
		JSONObject volume = new JSONObject();
		try {
			volume.put(KEY_CHECKED, isChecked());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			volume.put(KEY_MEDIA_VOLUME, getMediaVolume());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			volume.put(KEY_ALARM_VOLUME, getAlarmVolume());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			volume.put(KEY_RINGTONE_VOLUME, getRingtoneVolume());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			volume.put(KEY_NOTIFICATION_VOLME, getNotificationVolme());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			volume.put(KEY_VOICE_CALL_VOLME, getVoiceCallVolme());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			volume.put(KEY_SYSTEM_VOLUME, getSystemVolume());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return volume.toString();
	}

	@Override
	public void fromJSON(String volumeJSON) {
		if(volumeJSON != null){
			try {
				JSONObject volume = new JSONObject(volumeJSON);
				try {
					setChecked(volume.getBoolean(KEY_CHECKED));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				try {
					setMediaVolume(volume.getInt(KEY_MEDIA_VOLUME));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				try {
					setAlarmVolume(volume.getInt(KEY_ALARM_VOLUME));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				try {
					setRingtoneVolume(volume.getInt(KEY_RINGTONE_VOLUME));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				try {
					setNotificationVolme(volume.getInt(KEY_NOTIFICATION_VOLME));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				try {
					setVoiceCallVolme(volume.getInt(KEY_VOICE_CALL_VOLME));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				try {
					setSystemVolume(volume.getInt(KEY_SYSTEM_VOLUME));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
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
