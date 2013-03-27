package me.xiaopan.lifespirit.task.scenariomode;

import me.xiaopan.lifespirit.R;
import me.xiaopan.lifespirit.other.TaskItemImpl;
import me.xiaopan.lifespirit.task.Task;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;


public class OpenApplication extends TaskItemImpl {
	/**
	 * KEY - 打开应用程序
	 */
	public static final String KEY = "KEY_OPEN_APPLICATION";
	/**
	 * KEY - 应用程序名字
	 */
	public static final String KEY_NAME = "KEY_NAME";
	/**
	 * 应用程序名字
	 */
	private String name;
	
	public OpenApplication(Context context, Task task) {
		super(context, task, context.getString(R.string.taskItem_openApplication));
	}
	
	public OpenApplication(Context context, Task task, String applicatinJSON){
		this(context, task);
		fromJSON(applicatinJSON);
	}

	@Override
	public String getHintText() {
		return getName() == null ? getString(R.string.base_none):getName();
	}

	@Override
	public void execute() {
		if(isChecked()){
			getContext().startActivity(getContext().getPackageManager().getLaunchIntentForPackage(getContent()));
		}
	}

	@Override
	public String toJSON() {
		JSONObject openApplication = new JSONObject();
		try {
			openApplication.put(KEY_CHECKED, isChecked());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			openApplication.put(KEY_CONTENT, getContent());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			openApplication.put(KEY_NAME, getName());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return openApplication.toString();
	}

	@Override
	public void fromJSON(String json) {
		if(json != null){
			try {
				JSONObject openApplication = new JSONObject(json);
				try {
					setChecked(openApplication.getBoolean(KEY_CHECKED));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				try {
					setContent(openApplication.getString(KEY_CONTENT));
				} catch (JSONException e) {
					e.printStackTrace();
				}try {
					setName(openApplication.getString(KEY_NAME));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
