package me.xiaopan.lifespirit.adapter;

import java.util.List;

import me.xiaopan.javalibrary.util.StringUtils;
import me.xiaopan.lifespirit.task.BaseTask;
import me.xiaopan.lifespirit2.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TaskAdapter extends BaseAdapter {
	private Context context;
	private List<BaseTask> taskList;
	private LayoutInflater layoutInflater;
	private BaseTask task;
	
	public TaskAdapter(Context context, List<BaseTask> taskList){
		this.context = context;
		layoutInflater = LayoutInflater.from(context);
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
			convertView = layoutInflater.inflate(R.layout.list_item_task, null);
			viewHolder.triggerTimeText = (TextView) convertView.findViewById(R.id.text_taskItem_triggerTime);
			viewHolder.nameText = (TextView) convertView.findViewById(R.id.text_taskItem_name);
			viewHolder.repeatText = (TextView) convertView.findViewById(R.id.text_taskItem_repeat);
			viewHolder.timeRemainingText = (TextView) convertView.findViewById(R.id.text_taskItem_timeRemaining);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		task = taskList.get(position);
		viewHolder.triggerTimeText.setText(task.getRepeat().getTriggerTime().getTimeString());
		viewHolder.nameText.setText(StringUtils.isNotNullAndEmpty(task.getName().getName())?(context.getString(R.string.listItem_name, task.getName().getName())):null);
		viewHolder.repeatText.setText(task.getRepeat().getIntro(context));
		viewHolder.timeRemainingText.setText(null);
		
		return convertView;
	}
	
	private class ViewHolder{
		private TextView triggerTimeText;
		private TextView nameText;
		private TextView repeatText;
		private TextView timeRemainingText;
	}
}