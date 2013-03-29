package me.xiaopan.lifespirit.activity;

import me.xiaopan.lifespirit.MyBaseActivity;
import me.xiaopan.lifespirit.R;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 首页
 * @author xiaopan
 *
 */
public class IndexActivity extends MyBaseActivity {
	private Button addTaskButton;
	private Button taskListButton;
	
	@Override
	protected void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.index);
		addTaskButton = (Button) findViewById(R.id.button_index_addTask);
		taskListButton = (Button) findViewById(R.id.button_index_taskList);
	}

	@Override
	protected void onInitListener(Bundle savedInstanceState) {
		addTaskButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(ScenarioModeActivity.class);
			} 
		});

		taskListButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
	}

	@Override
	protected void onInitData(Bundle savedInstanceState) {
	}

	@Override
	protected boolean isRemoveTitleBar() {
		return true;
	}
}