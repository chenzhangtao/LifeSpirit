package me.xiaopan.lifespirit.task;

import java.util.List;

import me.xiaopan.javalibrary.util.Time;
import android.content.Context;

/**
 * 打开应用程序
 */
public class OpenApplication extends BaseTask{
	private String applicationName;
	private String applicationPackageName;
	
	@Override
	public String getIntro(Context context) {
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
	public String getType() {
		return null;
	}

	@Override
	public void execute(Context context, Time currentTime) {
		if(isEnable()){
			context.startActivity(context.getPackageManager().getLaunchIntentForPackage(applicationPackageName));
			super.execute(context, currentTime);
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