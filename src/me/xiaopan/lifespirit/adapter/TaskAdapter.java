package me.xiaopan.lifespirit.adapter;

import java.util.List;

import me.xiaopan.easyjava.util.StringUtils;
import me.xiaopan.easyjava.util.Time;
import me.xiaopan.lifespirit.task.Task;
import me.xiaopan.lifespirit.util.TimeUtils;
import me.xiaopan.lifespirit2.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 任务适配器
 */
public class TaskAdapter extends BaseAdapter {
	private Context context;
	private List<Task> taskList;
	private Task task;
	
	public TaskAdapter(Context context, List<Task> taskList){
		this.context = context;
		this.taskList = taskList;
	}
	
	@Override
	public int getCount() {
		return taskList.size();
	}

	@Override
	public Object getItem(int position) {
		return taskList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.list_item_task, null);
			viewHolder.triggerTimeText = (TextView) convertView.findViewById(R.id.text_taskItem_triggerTime);
			viewHolder.nameText = (TextView) convertView.findViewById(R.id.text_taskItem_name);
			viewHolder.repeatText = (TextView) convertView.findViewById(R.id.text_taskItem_repeat);
			viewHolder.timeRemainingText = (TextView) convertView.findViewById(R.id.text_taskItem_timeRemaining);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		task = taskList.get(position);
		viewHolder.triggerTimeText.setText(TimeUtils.getDigitalClockString(task.getRepeat().getTriggerTime()));
		viewHolder.nameText.setText(StringUtils.isNotNullAndEmpty(task.getName())?(context.getString(R.string.listItem_task_name, task.getName())):null);
		viewHolder.repeatText.setText(task.getRepeat().getIntro(context));
		viewHolder.timeRemainingText.setText(context.getString(R.string.listItem_task_timeRemaining, TimeUtils.getTimeRemaining(context, new Time(), task.getRepeat().getNextExecuteTime())));
		
		return convertView;
	}
	
	private class ViewHolder{
		private TextView triggerTimeText;
		private TextView nameText;
		private TextView repeatText;
		private TextView timeRemainingText;
	}
}