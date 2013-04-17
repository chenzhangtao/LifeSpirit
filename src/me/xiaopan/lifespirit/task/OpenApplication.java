package me.xiaopan.lifespirit.task;

import java.util.List;

import android.content.Context;

/**
 * 打开应用程序
 */
public class OpenApplication extends BaseTask{
	private String applicationName;
	private String applicationPackageName;
	
	@Override
	public String onGetIntro(Context context) {
		return null;
	}

	@Override
	public boolean saveToLocal(Context context) {
		return false;
	}

	public List<OpenApplication> readOpenApplications(Context context) {
		return null;
	}

	@Override
	public String onGetType() {
		return null;
	}

	@Override
	public void onExecute(Context context) {
		if(isEnable()){
			context.startActivity(context.getPackageManager().getLaunchIntentForPackage(applicationPackageName));
		}
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getApplicationPackageName() {
		return applicationPackageName;
	}

	public void setApplicationPackageName(String applicationPackageName) {
		this.applicationPackageName = applicationPackageName;
	}
}