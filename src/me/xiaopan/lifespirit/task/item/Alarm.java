package me.xiaopan.lifespirit.task.item;

import me.xiaopan.lifespirit.R;
import me.xiaopan.lifespirit.task.Task;
import me.xiaopan.lifespirit.task.TaskItemImpl;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;


public class Alarm extends TaskItemImpl {
	/**
	 * KEY - 表示当前任务项
	 */
	public static final String KEY = "KEY_ALARM";
	
	public Alarm(Context context, Task task) {
		super(context, task, context.getString(R.string.taskItem_alarm));
	}
	
	public Alarm(Context context, Task task, String airplaneModeJSON){
		this(context, task);
		fromJSON(airplaneModeJSON);
	}

	@Override
	public String getHintText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void execute() {
//		getContext().startActivity(getContext().getPackageManager().getLaunchIntentForPackage("com.xiaopan.android.timingtask"));
//		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//		builder.setTitle("时间到！");
//		builder.setNegativeButton(R.string.base_cancle, null);
//		builder.setPositiveButton(R.string.base_confirm, null);
//		builder.create().show();
	}
	
	@Override
	public String toJSON(){
		JSONObject alarm = new JSONObject();
		try {
			alarm.put(KEY_CHECKED, isChecked());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			alarm.put(KEY_CONTENT, getContent());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return alarm.toString();
	}
	
	@Override
	public void fromJSON(String nameJSON)  {
		if(nameJSON != null){
			try {
				JSONObject alarm = new JSONObject(nameJSON);
				try {
					setChecked(alarm.getBoolean(KEY_CHECKED));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				try {
					setContent(alarm.getString(KEY_CONTENT));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}
