package me.xiaopan.lifespirit.task.scenariomode;

import me.xiaopan.androidlibrary.util.NetworkUtils;
import me.xiaopan.androidlibrary.util.SystemUtils.DeviceNotFoundException;
import me.xiaopan.lifespirit.R;
import me.xiaopan.lifespirit.task.BaseTask;
import me.xiaopan.lifespirit.task.TaskItemImpl;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class Wifi extends  TaskItemImpl {
	/**
	 * KEY - 表示当前任务项
	 */
	public static final String KEY = "KEY_WIFI";
	
	public Wifi(Context context, BaseTask task) {
		super(context, task, context.getString(R.string.taskItem_wifi));
		try {
			setOpen(!NetworkUtils.isWifiOpen(getContext()));
		} catch (DeviceNotFoundException e) {
			e.printStackTrace();
			setExist(false);
			setChecked(false);
		}
	}
	
	public Wifi(Context context, BaseTask task, String wifiJSON)  {
		this(context, task);
		fromJSON(wifiJSON);
	}

	@Override
	public void execute() {
		if(isChecked()){
			try {
				NetworkUtils.setWifi(getContext(), isOpen());
			} catch (DeviceNotFoundException e) {
				e.printStackTrace();
				setExist(false);
				setChecked(false);
			}
		}
	}

	@Override
	public String getHintText() {
		return isOpen()? getString(R.string.base_open) : getString(R.string.base_close);
	}
	
	@Override
	public String toJSON(){
		JSONObject wifi = new JSONObject();
		try {
			wifi.put(KEY_CHECKED, isChecked());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			wifi.put(KEY_OPEN, isOpen());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return wifi.toString();
	}
	
	@Override
	public void fromJSON(String wifiJSON)  {
		if(wifiJSON != null){
			try {
				JSONObject wifi = new JSONObject(wifiJSON);
				try {
					setChecked(wifi.getBoolean(KEY_CHECKED));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				try {
					setOpen(wifi.getBoolean(KEY_OPEN));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}