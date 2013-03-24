package me.xiaopan.lifespirit.task.scenariomode;

import me.xiaopan.androidlibrary.util.NetworkUtils;
import me.xiaopan.lifespirit.R;
import me.xiaopan.lifespirit.task.BaseTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class MobileNetwork extends  TaskItemImpl {
	/**
	 * KEY - 表示当前任务项
	 */
	public static final String KEY = "KEY_MOBILE_NETWORK";
	
	public MobileNetwork(Context context, BaseTask task) {
		super(context, task, context.getString(R.string.taskItem_mobileNetwork));
		setOpen(!NetworkUtils.isMobileNetworkOpen(getContext()));
	}
	
	public MobileNetwork(Context context, BaseTask task, String mobileNetworkJSON){
		this(context, task);
		fromJSON(mobileNetworkJSON);
	}

	@Override
	public void execute() {
		if(isChecked()){
			NetworkUtils.setMobileNetwork(getContext(), isOpen());
		}
	}

	@Override
	public String getHintText() {
		return isOpen()? getString(R.string.base_open) : getString(R.string.base_close);
	}
	
	@Override
	public String toJSON(){
		JSONObject mobileNetwork = new JSONObject();
		try {
			mobileNetwork.put(KEY_CHECKED, isChecked());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			mobileNetwork.put(KEY_OPEN, isOpen());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return mobileNetwork.toString();
	}
	
	@Override
	public void fromJSON(String mobileNetworkJSON){
		if(mobileNetworkJSON != null){
			try {
				JSONObject mobileNetwork = new JSONObject(mobileNetworkJSON);
				try {
					setChecked(mobileNetwork.getBoolean(KEY_CHECKED));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				try {
					setOpen(mobileNetwork.getBoolean(KEY_OPEN));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}