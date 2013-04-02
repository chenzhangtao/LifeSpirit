package me.xiaopan.lifespirit.task.scenariomode;

import me.xiaopan.androidlibrary.util.SystemUtils;
import me.xiaopan.lifespirit.R;
import me.xiaopan.lifespirit.task.BaseTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class AirplaneMode extends  BaseScenarioModeItemImpl {
	/**
	 * KEY - 表示当前任务项
	 */
	public static final String KEY = "KEY_AIRPLANE_MODE";
	
	public AirplaneMode(Context context, BaseTask task) {
		super(context, task, context.getString(R.string.taskItem_airplaneMode));
		setOpen(!SystemUtils.isAirplaneModeOpen(getContext()));
	}
	
	public AirplaneMode(Context context, BaseTask task, String airplaneModeJSON){
		this(context, task);
		fromJSON(airplaneModeJSON);
	}

	@Override
	public void execute() {
		if (isChecked()) {
			SystemUtils.setAirplaneMode(getContext(), isOpen());
		}
	}

	@Override
	public String getHintText() {
		return isOpen()? getString(R.string.base_open) : getString(R.string.base_close);
	}
	
	@Override
	public String toJSON(){
		JSONObject airplaneMode = new JSONObject();
		try {
			airplaneMode.put(KEY_CHECKED, isChecked());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			airplaneMode.put(KEY_OPEN, isOpen());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return airplaneMode.toString();
	}
	
	@Override
	public void fromJSON(String airplaneModeJSON){
		if(airplaneModeJSON != null){
			try {
				JSONObject airplaneMode = new JSONObject(airplaneModeJSON);
				try {
					setChecked(airplaneMode.getBoolean(KEY_CHECKED));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				try {
					setOpen(airplaneMode.getBoolean(KEY_OPEN));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}