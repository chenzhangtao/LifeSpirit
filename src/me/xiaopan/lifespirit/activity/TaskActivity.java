package me.xiaopan.lifespirit.activity;

import android.os.Bundle;
import me.xiaopan.lifespirit.MyBaseActivity;
import me.xiaopan.lifespirit.R;

/**
 * 任务界面，包含创建新任务以及修改任务功能
 * @author xiaopan
 *
 */
public class TaskActivity extends MyBaseActivity {

	@Override
	protected void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.task);
	}

	@Override
	protected void onInitListener(Bundle savedInstanceState) {

	}

	@Override
	protected void onInitData(Bundle savedInstanceState) {

	}
}