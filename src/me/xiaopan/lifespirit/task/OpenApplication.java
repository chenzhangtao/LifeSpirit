package me.xiaopan.lifespirit.task;

import android.content.Context;

/**
 * 打开应用程序
 */
public class OpenApplication extends BaseTask{
	private String applicationName;
	private String applicationPackageName;
	
	public OpenApplication(Context context) {
		super(context);
	}
	
	@Override
	public String getIntro() {
		return null;
	}

	@Override
	public void execute() {
		if(isEnable()){
			getContext().startActivity(getContext().getPackageManager().getLaunchIntentForPackage(applicationPackageName));
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