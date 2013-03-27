package me.xiaopan.lifespirit.widget;

import java.util.List;

import me.xiaopan.androidlibrary.util.AndroidUtils;
import me.xiaopan.lifespirit.R;
import me.xiaopan.lifespirit.task.TaskItem;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class TaskItemAdapter extends BaseAdapter {
	private Context context;
	private List<TaskItem> taskItemList;
	
	public TaskItemAdapter(Context context, List<TaskItem> taskItemList){
		this.context = context;
		this.taskItemList = taskItemList;
	}
	
	@Override
	public int getCount() {
		return taskItemList.size();
	}

	@Override
	public Object getItem(int position) {
		return taskItemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = LayoutInflater.from(context).inflate(R.layout.task_edit_item, null);
		final TaskItem taskItem = taskItemList.get(position);
		
		//主文字
		final TextView mainText = (TextView) view.findViewById(R.id.text_taskEditItem_name);
		mainText.setText(taskItem.getMainText());
		
		//提示文字
		final TextView hintText = (TextView) view.findViewById(R.id.text_taskEditItem_hint);
		hintText.setText(taskItem.getHintText());
		
		//复选框
		final CheckBox checkBox = (CheckBox) view.findViewById(R.id.base_button_listItemChoice);
		//默认复选框不显示
		checkBox.setVisibility(View.GONE);
		
		//如果需要显示当前任务项的复选框
		if(taskItem.isShowCheckBox()){
			//显示复选框
			checkBox.setVisibility(View.VISIBLE);
			//设置复选框的值
			checkBox.setChecked(taskItem.isChecked());
			//设置复选框的值的改变事件监听器
			checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() { 
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					//如果当前项存在，就执行点击事件；否则提示不存在
					if(taskItem.isExist()){
						//设置任务项是否选中
						taskItem.setChecked(isChecked);
						//刷新任务项列表
						notifyDataSetChanged();
					}else{
						//将复选框的值还原
						checkBox.setChecked(!isChecked);
						//提示不存在
						AndroidUtils.toastS(context, context.getString(R.string.hint_deviceNotExist) + taskItem.getMainText());
					}
				}
			});
		}
		
		if(taskItem.isExist()){
			view.setEnabled(true);
		}else{
			view.setEnabled(false);
		}
		
		return view;
	}
}