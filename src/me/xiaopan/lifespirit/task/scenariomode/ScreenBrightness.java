package me.xiaopan.lifespirit.task.scenariomode;

import me.xiaopan.androidlibrary.util.SystemUtils;
import me.xiaopan.lifespirit.R;
import me.xiaopan.lifespirit.other.TaskItemImpl;
import me.xiaopan.lifespirit.task.Task;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class ScreenBrightness extends  TaskItemImpl {
	/**
	 * KEY - 表示当前任务项
	 */
	public static final String KEY = "KEY_KEY_SCREEN_BRIGHTNESS";
	/**
	 * KEY - 自动调节
	 */
	private static final String KEY_AUTO_ADJUSTMENT = "KEY_AUTO_ADJUSTMENT";
	/**
	 * 自动调节
	 */
	private boolean autoAdjustmen;
	
	public ScreenBrightness(Context context, Task task) {
		super(context, task, context.getString(R.string.taskItem_screenBrightness));
		setAutoAdjustmen(SystemUtils.isScreenBrightnessModeAuto(getContext()));
		setValue(SystemUtils.getScreenBrightness(getContext()));
	}
	
	public ScreenBrightness(Context context, Task task, String screenBrightnessJSON)  {
		this(context, task);
		fromJSON(screenBrightnessJSON);
	}

	@Override
	public void execute() {
		if(isChecked()){
			//先设置模式
			SystemUtils.setScreenBrightnessMode(getContext(), isAutoAdjustmen());
			//如果不是自动的就设置亮度
			if(!isAutoAdjustmen()){
				SystemUtils.setScreenBrightness(getContext(), getValue());
			}
		}
	}

	@Override
	public String getHintText() {
		String result = "";
		if(isAutoAdjustmen()){
			result += getContext().getString(R.string.base_automatic);
		}else{
			result += getValue();
		}
		return result;
	}

	public boolean isAutoAdjustmen() {
		return autoAdjustmen;
	}

	public void setAutoAdjustmen(boolean autoAdjustmen) {
		this.autoAdjustmen = autoAdjustmen;
	}

	@Override
	public String toJSON(){
		JSONObject screenBrightness = new JSONObject();
		try {
			screenBrightness.put(KEY_CHECKED, isChecked());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			screenBrightness.put(KEY_AUTO_ADJUSTMENT, isAutoAdjustmen());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			screenBrightness.put(KEY_VALUE, getValue());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return screenBrightness.toString();
	}
	
	@Override
	public void fromJSON(String screenBrightnessJSON)  {
		if(screenBrightnessJSON != null){
			try {
				JSONObject screenBrightness = new JSONObject(screenBrightnessJSON);
				try {
					setChecked(screenBrightness.getBoolean(KEY_CHECKED));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				try {
					setAutoAdjustmen(screenBrightness.getBoolean(KEY_AUTO_ADJUSTMENT));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				try {
					setValue(screenBrightness.getInt(KEY_VALUE));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}