package me.xiaopan.lifespirit.task.scenariomode;

import me.xiaopan.androidlibrary.util.SystemUtils;
import me.xiaopan.androidlibrary.util.SystemUtils.DeviceNotFoundException;
import me.xiaopan.lifespirit.R;
import me.xiaopan.lifespirit.task.BaseTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class Bluetooth extends TaskItemImpl {
	/**
	 * KEY - 表示当前任务项
	 */
	public static final String KEY = "KEY_BLUETOOTH";
	
	public Bluetooth(Context context, BaseTask task) {
		super(context, task, context.getString(R.string.taskItem_bluetooth));
		try {
			setOpen(!SystemUtils.isBluetoothOpen());
		} catch (DeviceNotFoundException e) {
			e.printStackTrace();
			setExist(false);
			setChecked(false);
		}
	}
	
	public Bluetooth(Context context, BaseTask task, String bluetoothJSON){
		this(context, task);
		fromJSON(bluetoothJSON);
	}

	@Override
	public void execute() {
		if(isChecked()){
			try {
				SystemUtils.setBluetooth(isOpen());
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
		JSONObject bluetooth = new JSONObject();
		try {
			bluetooth.put(KEY_CHECKED, isChecked());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			bluetooth.put(KEY_OPEN, isOpen());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return bluetooth.toString();
	}
	
	@Override
	public void fromJSON(String bluetoothJSON){
		if(bluetoothJSON != null){
			try {
				JSONObject bluetooth = new JSONObject(bluetoothJSON);
				try {
					setChecked(bluetooth.getBoolean(KEY_CHECKED));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				try {
					setOpen(bluetooth.getBoolean(KEY_OPEN));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}
