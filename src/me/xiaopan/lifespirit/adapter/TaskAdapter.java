package me.xiaopan.lifespirit.adapter;

import java.io.IOException;
import java.util.List;

import me.xiaopan.androidlibrary.util.AndroidUtils;
import me.xiaopan.androidlibrary.widget.MyBaseAdapter;
import me.xiaopan.lifespirit.R;
import me.xiaopan.lifespirit.activity.TaskListActivity;
import me.xiaopan.lifespirit.task.Task;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

public class TaskAdapter extends MyBaseAdapter {

	private Context context;
	private List<Task> taskList;
	private TaskListActivity taskListActivity;
	
	public TaskAdapter(Context context, TaskListActivity taskListActivity, List<Task> taskList){
		this.context = context;
		this.taskListActivity = taskListActivity;
		this.taskList = taskList;
	}
	
	@Override
	public int getRealCount() {
		return taskList.size();
	}

	@Override
	public Object getItem(int position) {
		return taskList.get(position);
	}

	@Override
	public View getRealView(final int realPosition, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.task_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.toggleButton = (ToggleButton) convertView.findViewById(R.id.toggle_taskListItem);
			viewHolder.tiggerTimeText = (TextView) convertView.findViewById(R.id.text_taskListItem_time);
			viewHolder.taskNameText = (TextView) convertView.findViewById(R.id.text_taskListItem_name);
			viewHolder.taskRepeatText = (TextView) convertView.findViewById(R.id.text_taskListItem_repeat);
			viewHolder.nextExecuteTimeText = (TextView) convertView.findViewById(R.id.text_taskListItem_nextExecuteTime);
			viewHolder.taskInfoText = (TextView) convertView.findViewById(R.id.text_taskListItem_info);
			viewHolder.choiceButton = (CompoundButton) convertView.findViewById(R.id.base_button_listItemChoice);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		final Task task = taskList.get(realPosition);

		//处理开关按钮
		viewHolder.toggleButton.setOnCheckedChangeListener(null);
		viewHolder.toggleButton.setChecked(task.isOpen());
		viewHolder.toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				//如果是要开启任务，并且任务已经过期就关闭任务并且提示任务已经过期不能开启，否则就开启任务
				if(isChecked && task.isPastDue()){
					buttonView.setChecked(!buttonView.isChecked());
					AndroidUtils.toastL(context, R.string.hint_taskPastDue);
				}else{
					//设置任务开关
					task.setOpen(isChecked);
					
					//将任务写入本地
					try {
						task.writer();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					//更新下一个要执行的任务
					taskListActivity.getMyApplication().updateNextExecuteTask();
					
					//如果后台任务的为自动启动或者后台任务已经启动，就启动后台任务
					if(taskListActivity.getMyApplication().getPreferencesManager().isAutoStartBackgroundService() || taskListActivity.getMyApplication().getPreferencesManager().isBackgServiceRunning()){
						taskListActivity.getMyApplication().startBackgService();
					}
				}
				notifyDataSetChanged();
			}
		});
		//设置任务触发时间
		viewHolder.tiggerTimeText.setText(task.getTriggerTime().getShowInTaskInfoText());
		//设置任务名字
		viewHolder.taskNameText.setText(task.getTaskName().getShowInTaskInfoText());
		//设置任务重复
		viewHolder.taskRepeatText.setText(task.getRepeat().getShowInTaskInfoText());
		//设置任务下次执行时间
		viewHolder.nextExecuteTimeText.setText(task.getNextExecuteTime().getShowInTaskInfoText());
		//设置任务信息
		viewHolder.taskInfoText.setText(task.getTaskInfo());
		//处理选择按钮
		choiceButtonHandle(viewHolder.choiceButton, realPosition);
		
		return convertView;
	}
	
	class ViewHolder{
		ToggleButton toggleButton;
		TextView tiggerTimeText;
		TextView taskNameText;
		TextView taskRepeatText;
		TextView nextExecuteTimeText;
		TextView taskInfoText;
		CompoundButton choiceButton;
	}
}