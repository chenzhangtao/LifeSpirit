package me.xiaopan.lifespirit.adapter;

import java.util.List;

import me.xiaopan.lifespirit.task.ScenarioMode;
import me.xiaopan.lifespirit.widget.SlidingToggleButton;
import me.xiaopan.lifespirit2.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ScenarioModeAdapter extends BaseAdapter {
	private Context context;
	private List<ScenarioMode> scenarioModeList;
	private LayoutInflater layoutInflater;
	private ScenarioMode scenarioMode;
	
	public ScenarioModeAdapter(Context context, List<ScenarioMode> scenarioModeList){
		this.context = context;
		layoutInflater = LayoutInflater.from(context);
		this.scenarioModeList = scenarioModeList;
	}
	
	@Override
	public int getCount() {
		return scenarioModeList.size();
	}

	@Override
	public Object getItem(int position) {
		return scenarioModeList.get(position);
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
			convertView = layoutInflater.inflate(R.layout.list_item_scenairo_mode, null);
			viewHolder.triggerTimeText = (TextView) convertView.findViewById(R.id.text_scenairoModeItem_triggerTime);
			viewHolder.repeatText = (TextView) convertView.findViewById(R.id.text_scenairoModeItem_repeat);
			viewHolder.timeRemainingText = (TextView) convertView.findViewById(R.id.text_scenairoModeItem_timeRemaining);
			viewHolder.slidingToggleButton = (SlidingToggleButton) convertView.findViewById(R.id.slidingToggle_scenairoModeItem);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		scenarioMode = scenarioModeList.get(position);
		viewHolder.triggerTimeText.setText(scenarioMode.getRepeat().getTriggerTime().getHourOfDay()+":"+scenarioMode.getRepeat().getTriggerTime().getMinute());
		viewHolder.repeatText.setText(scenarioMode.getRepeat().onGetIntro(context));
		viewHolder.timeRemainingText.setText(null);
		viewHolder.slidingToggleButton.setChecked(scenarioMode.isEnable());
		
		return convertView;
	}
	
	private class ViewHolder{
		private TextView triggerTimeText;
		private TextView repeatText;
		private TextView timeRemainingText;
		private SlidingToggleButton slidingToggleButton;
	}
}