package me.xiaopan.lifespirit.task;

import me.xiaopan.lifespirit.R;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;


public class TaskName extends TaskItemImpl {
	/**
	 * KEY - 表示当前任务项
	 */
	public static final String KEY = "KEY_TASK_NAME";
	
	public TaskName(Context context, Task task) {
		super(context, task, context.getString(R.string.taskItem_name));
		setShowCheckBox(false);
		setShowInTaskInfo(false);
		setNeedValiChecked(false);
	}
	
	public TaskName(Context context, Task task, String nameJSON) {
		this(context, task);
		fromJSON(nameJSON);
	}

	@Override
	public String getHintText() {
		return getContent() == null ? getContext().getResources().getString(R.string.base_none) : getContent();
	}
	
	@Override
	public String getShowInTaskInfoText() {
		return getContent() == null ? "" : " - " + getContent();
	}

	@Override
	public void execute() {}
	
	@Override
	public String toJSON(){
		JSONObject name = new JSONObject();
		try {
			name.put(KEY_CONTENT, getContent());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return name.toString();
	}
	
	@Override
	public void fromJSON(String nameJSON)  {
		if(nameJSON != null){
			try {
				JSONObject name = new JSONObject(nameJSON);
				try {
					setContent(name.getString(KEY_CONTENT));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}
