package me.xiaopan.lifespirit.activity;

import me.xiaopan.easyandroid.util.AndroidLogger;
import me.xiaopan.lifespirit.MyBaseActivity;
import me.xiaopan.lifespirit.adapter.TaskAdapter;
import me.xiaopan.lifespirit.task.ScenarioMode;
import me.xiaopan.lifespirit.task.Task;
import me.xiaopan.lifespirit.util.TimeUtils;
import me.xiaopan.lifespirit2.R;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;

/**
 * 首页
 */
public class IndexActivity extends MyBaseActivity {
	public static final String ACTION_BROADCAST_REFRESH = "ACTION_BROADCAST_REFRESH";
	private static final int REQUEST_CODE_UPDATE_SCENARIO_MODE = 101;
	private static final int REQUEST_CODE_ADD_SCENARIO_MODE = 102;
	private Button addTaskButton;
	private Button taskListButton;
	private ListView list;
	private TaskAdapter taskAdapter;
	private int updateTaskPosition;
	private RefreshRunnable refreshRunnable;
	
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
				startActivityForResult(ScenarioModeActivity.class, REQUEST_CODE_ADD_SCENARIO_MODE);
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
				Task task = getMyApplication().getRunningTaskManager().getRunningTaskList().get(updateTaskPosition);
				if(task instanceof ScenarioMode){
					Bundle bundle = new Bundle();
					bundle.putString(ScenarioModeActivity.PARAM_OPTIONAL_STRING_SCENARIO_MODE, new Gson().toJson(task));
					startActivityForResult(ScenarioModeActivity.class, REQUEST_CODE_UPDATE_SCENARIO_MODE, bundle);
				}
			}
		});
	}

	@Override
	protected void onInitData(Bundle savedInstanceState) {
		list.setAdapter(taskAdapter = new TaskAdapter(getBaseContext(), getMyApplication().getRunningTaskManager().getRunningTaskList()));
		refreshRunnable = new RefreshRunnable();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		taskAdapter.notifyDataSetChanged();
		getMessageHanlder().postDelayed(refreshRunnable,TimeUtils.getCurrentMinuteRemainingSecond() * 1000);
	}

	@Override
	protected void onPause() {
		super.onPause();
		getMessageHanlder().removeCallbacks(refreshRunnable);
	}

	@Override
	protected boolean isRemoveTitleBar() {
		return true;
	}
	
	private class RefreshRunnable implements Runnable{
		@Override
		public void run() {
			AndroidLogger.i("刷新");
			taskAdapter.notifyDataSetChanged();
			getMessageHanlder().postDelayed(refreshRunnable, 60*1000);
		}
	}
}