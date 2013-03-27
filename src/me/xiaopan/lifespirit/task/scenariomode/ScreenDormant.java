package me.xiaopan.lifespirit.task.scenariomode;

import me.xiaopan.androidlibrary.util.SystemUtils;
import me.xiaopan.javalibrary.util.DateTimeUtils;
import me.xiaopan.lifespirit.R;
import me.xiaopan.lifespirit.other.TaskItemImpl;
import me.xiaopan.lifespirit.task.Task;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class ScreenDormant extends  TaskItemImpl {
	/**
	 * KEY - 表示当前任务项
	 */
	public static final String KEY = "KEY_SCREEN_DORMANT";
	
	public ScreenDormant(Context context, Task task) {
		super(context, task, context.getString(R.string.taskItem_screenDormant));
		setValue(SystemUtils.getScreenDormantTime(getContext()));
	}
	
	public ScreenDormant(Context context, Task task, String screenDormantJSON)  {
		this(context, task);
		fromJSON(screenDormantJSON);
	}

	@Override
	public void execute() {
		if(isChecked()){
			SystemUtils.setScreenDormantTime(getContext(), getValue());
		}
	}

	@Override
	public String getHintText() {
		return DateTimeUtils.milliSecondToIncompleteHourMinuteSecond(
				getValue(), 
				"", 
				getString(R.string.base_hours), 
				getString(R.string.base_minutes), 
				getString(R.string.base_seconds), 
				"", 
				false, 
				false,
				false
		);
	}

	@Override
	public String toJSON(){
		JSONObject screenDormant = new JSONObject();
		try {
			screenDormant.put(KEY_CHECKED, isChecked());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			screenDormant.put(KEY_VALUE, getValue());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return screenDormant.toString();
	}
	
	@Override
	public void fromJSON(String screenDormantJSON)  {
		if(screenDormantJSON != null){
			try {
				JSONObject screenDormant = new JSONObject(screenDormantJSON);
				try {
					setChecked(screenDormant.getBoolean(KEY_CHECKED));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				try {
					setValue(screenDormant.getInt(KEY_VALUE));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}