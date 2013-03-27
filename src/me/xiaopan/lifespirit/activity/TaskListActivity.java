package me.xiaopan.lifespirit.activity;

import me.xiaopan.androidlibrary.widget.MyBaseAdapter.ChoiceModeListener;
import me.xiaopan.androidlibrary.widget.PullListView;
import me.xiaopan.javalibrary.util.CollectionUtils;
import me.xiaopan.javalibrary.util.FileUtils;
import me.xiaopan.lifespirit.MyBaseActivity;
import me.xiaopan.lifespirit.R;
import me.xiaopan.lifespirit.adapter.TaskAdapter;
import me.xiaopan.lifespirit.enums.TaskSortWay;
import me.xiaopan.lifespirit.task.Task;
import me.xiaopan.lifespirit.widget.ImageTextButton;

import org.json.JSONException;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;

public class TaskListActivity extends MyBaseActivity {
	private static final int TASK_EDIT_REQUEST_CODE = 101;
	public static final String KEY_IS_ADD = "KEY_IS_ADD";
	public static final String BROADCAST_FILETER_ACTION_TASKLISTACTIVITY = "BROADCAST_FILETER_ACTION_TASKLISTACTIVITY";
	/**
	 * 任务列表
	 */
	private PullListView taskListView;
	/**
	 * 任务Adapter
	 */
	private TaskAdapter taskAdapter;
	/**
	 * 工具栏
	 */
	private LinearLayout toolbar;
	/**
	 * 删除任务按钮
	 */
	private ImageTextButton removeTaskButton;
	/**
	 * 设置任务排序的对话框
	 */
	private AlertDialog setTaskSortWayDialog;
	/**
	 * 当前编辑的任务的索引
	 */
	private int currentEditTaskIndex = -5;

	@Override
	public void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.task_list);
		taskListView = (PullListView) findViewById(android.R.id.list);
		toolbar = (LinearLayout) findViewById(R.id.taskList_toolbar);
		removeTaskButton = (ImageTextButton) findViewById(R.id.taskList_button_removeTask);
	}

	@Override
	public void onInitListener(Bundle savedInstanceState) {
		//设置按钮点击事件监听器
		removeTaskButton.setOnClickListener(new OnClickListener() { public void onClick(View v) {
			removeTask(taskAdapter.getSelectedIndexs());
		}});
	}

	@Override
	public void onInitData(Bundle savedInstanceState) {
		taskAdapter = new TaskAdapter(getBaseContext(), this, getMyApplication().getTaskList());
		taskAdapter.openChiceMode();
		taskAdapter.setAbsListView(taskListView);
		
		//设置列表点击事件监听器
		taskAdapter.setOnItemClickListener(new OnItemClickListener() { public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Bundle bundle = new Bundle();
			bundle.putString(Task.KEY, getMyApplication().getTaskList().get(position).toJSON());
			bundle.putBoolean(KEY_IS_ADD, false);
			currentEditTaskIndex = position;
			startActivityForResult(TaskEditActivity.class, TASK_EDIT_REQUEST_CODE, bundle);
		}});
		
		taskAdapter.setChoiceModeListener(new ChoiceModeListener() {
			@Override
			public void onUpdateSelectedNumber(int selectedNumber) {
				getActionBar().setTitle(getString(R.string.taskList_text_selected) + selectedNumber + getString(R.string.base_ge) + getString(R.string.taskList_text_task));
			}
			
			@Override
			public void onSelectAll() {
				
			}
			
			@Override
			public void onOpen() {
				
			}
			
			@Override
			public void onItemClick(int realPosition, boolean isChecked) {
				
			}
			
			@Override
			public void onInto() {
				toolbar.setVisibility(View.VISIBLE);
			}
			
			@Override
			public void onExit() {
				toolbar.setVisibility(View.GONE);
			}
			
			@Override
			public void onDeselectAll() {
				
			}
			
			@Override
			public void onClose() {
				
			}
		});
		
		taskListView.openListHeaderReboundMode();
		taskListView.openListFooterReboundMode();
		taskListView.setAdapter(taskAdapter);
		openBroadcastReceiver(BROADCAST_FILETER_ACTION_TASKLISTACTIVITY);
	}

	@Override
	public void onReceivedBroadcast(Intent intent) {
		taskAdapter.notifyDataSetChanged();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == TASK_EDIT_REQUEST_CODE && resultCode == RESULT_OK && data != null){
			//如果是添加任务
			if(data.getBooleanExtra(KEY_IS_ADD, true)){
				try {
					getMyApplication().getTaskList().add(new Task(this, data.getStringExtra(Task.KEY)));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}else{
				try {
					getMyApplication().getTaskList().set(currentEditTaskIndex, new Task(this, data.getStringExtra(Task.KEY)));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			//对任务列表进行排序
			getMyApplication().taskSort();
			
			//刷新列表
			taskAdapter.notifyDataSetChanged();
			
			//更新下一个要执行的任务
			getMyApplication().updateNextExecuteTask();

			//如果当前后台任务的启动方式为自动启动或者后台任务已经启动了，就启动后台任务
			if(getMyApplication().getPreferencesManager().isAutoStartBackgroundService() || getMyApplication().getPreferencesManager().isBackgServiceRunning()){
				getMyApplication().startBackgService();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 删除任务
	 */
	private void removeTask(int[] indexs){
		//如果选中个数大于0就继续执行删除，否则提示没有选中任务
		if(indexs.length > 0){
			for(Object object : CollectionUtils.removes(getMyApplication().getTaskList(), indexs)){
				FileUtils.delete(getFileFromFilesDir(((Task) object).getCreateTime()+".task"));
			}
			
			//退出多选模式
			taskAdapter.exitChoiceMode();
			
			//更新下一个要执行的任务
			getMyApplication().updateNextExecuteTask();
			
			//如果后台服务是自动启动就启动后台服务
			if(getMyApplication().getPreferencesManager().isAutoStartBackgroundService()){
				getMyApplication().startBackgService();
			}
		}else{
			toastL(R.string.hint_noSlectedTask);
		}
	}
	
	@Override
	public void onBackPressed() {
		//如果是多选模式就推出多选模式，否则就退出当前Activity
		if(taskAdapter.isOpenedChoiceMode() && taskAdapter.isIntoChoiceMode()){
			taskAdapter.exitChoiceMode();
		}else{
			super.onBackPressed();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.task_list, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_taskList_add:
				Bundle bundle = new Bundle();
				bundle.putBoolean(KEY_IS_ADD, true);
				startActivityForResult(TaskEditActivity.class, TASK_EDIT_REQUEST_CODE, bundle);
				break;
			case R.id.menu_taskList_refresh:
				taskAdapter.notifyDataSetChanged();
				break;
			case R.id.menu_taskList_sort:
				AlertDialog.Builder builder = new AlertDialog.Builder(TaskListActivity.this);
				builder.setTitle(R.string.menu_sort_title);
				builder.setSingleChoiceItems(R.array.menu_sort_items, getMyApplication().getPreferencesManager().getTaskSortWay().getIndex(), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if(setTaskSortWayDialog != null){
							setTaskSortWayDialog.dismiss();
						}
						switch(which){
							case 0 : getMyApplication().getPreferencesManager().setTaskSortWay(TaskSortWay.TIGGER_TIME_ASC); break;
							case 1 : getMyApplication().getPreferencesManager().setTaskSortWay(TaskSortWay.TIGGER_TIME_DESC); break;
							case 2 : getMyApplication().getPreferencesManager().setTaskSortWay(TaskSortWay.CREATE_TIME_ASC); break;
							case 3 : getMyApplication().getPreferencesManager().setTaskSortWay(TaskSortWay.CREATE_TIME_DESC); break;
						}
						getMyApplication().taskSort();
						taskAdapter.notifyDataSetChanged();
					}
				});
				setTaskSortWayDialog = builder.create();
				setTaskSortWayDialog.setOnDismissListener(new OnDismissListener() { public void onDismiss(DialogInterface dialog) {
					setTaskSortWayDialog = null; 
				}});
				setTaskSortWayDialog.show();
				break;
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}
}