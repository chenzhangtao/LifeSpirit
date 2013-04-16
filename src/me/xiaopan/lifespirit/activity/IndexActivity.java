package me.xiaopan.lifespirit.activity;

import me.xiaopan.lifespirit.MyBaseActivity;
import me.xiaopan.lifespirit.adapter.TaskAdapter;
import me.xiaopan.lifespirit.task.BaseTask;
import me.xiaopan.lifespirit.task.ScenarioMode;
import me.xiaopan.lifespirit2.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

/**
 * 首页
 */
public class IndexActivity extends MyBaseActivity {
	private static final int REQUEST_CODE_UPDATE = 101;
	private static final int REQUEST_CODE_ADD = 102;
	public static final String RETURN_OPTIONAL = "RETURN_OPTIONAL";
	private Button addTaskButton;
	private Button taskListButton;
	private ListView list;
	private TaskAdapter taskAdapter;
	private int updateTaskPosition;
	
	@Override
	protected void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.activity_index);
		addTaskButton = (Button) findViewById(R.id.button_index_addTask);
		taskListButton = (Button) findViewById(R.id.button_index_taskList);
		list = (ListView) findViewById(android.R.id.list);
	}

	@Override
	protected void onInitListener(Bundle savedInstanceState) {
		addTaskButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivityForResult(ScenarioModeActivity.class, REQUEST_CODE_ADD);
			} 
		});

		taskListButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(TaskListActivity.class);
			}
		});
		
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				updateTaskPosition = position - list.getHeaderViewsCount();
				BaseTask task = getMyApplication().getRunningTaskList().get(updateTaskPosition);
				if(task instanceof ScenarioMode){
					Bundle bundle = new Bundle();
					bundle.putSerializable(ScenarioModeActivity.PARAM_OPTIONAL_SCENARIO_MODE, task);
					startActivityForResult(ScenarioModeActivity.class, REQUEST_CODE_UPDATE, bundle);
				}
			}
		});
	}

	@Override
	protected void onInitData(Bundle savedInstanceState) {
		list.setAdapter(taskAdapter = new TaskAdapter(getBaseContext(), getMyApplication().getRunningTaskList()));
	}

	@Override
	protected boolean isRemoveTitleBar() {
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK){
			switch(requestCode){
				case REQUEST_CODE_ADD : 
					getMyApplication().getRunningTaskList().add((BaseTask) data.getSerializableExtra(RETURN_OPTIONAL));
					taskAdapter.notifyDataSetChanged();
					break;
				case REQUEST_CODE_UPDATE : 
					getMyApplication().getRunningTaskList().set(updateTaskPosition, (BaseTask) data.getSerializableExtra(RETURN_OPTIONAL));
					taskAdapter.notifyDataSetChanged();
					break;
			}
		}
	}
}