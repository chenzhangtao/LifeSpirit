package me.xiaopan.lifespirit.adapter;

import java.util.List;

import me.xiaopan.androidlibrary.widget.BaseSlidingToggleButton;
import me.xiaopan.javalibrary.util.StringUtils;
import me.xiaopan.lifespirit.task.ScenarioMode;
import me.xiaopan.lifespirit.util.TimeUtils;
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
	private OnCheckedChanageListener onCheckedChanageListener;
	
	public ScenarioModeAdapter(Context context, List<ScenarioMode> scenarioModeList, OnCheckedChanageListener onCheckedChanageListener){
		this.context = context;
		layoutInflater = LayoutInflater.from(context);
		this.scenarioModeList = scenarioModeList;
		this.onCheckedChanageListener = onCheckedChanageListener;
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
			viewHolder.nameText = (TextView) convertView.findViewById(R.id.text_scenairoModeItem_name);
			viewHolder.repeatText = (TextView) convertView.findViewById(R.id.text_scenairoModeItem_repeat);
			viewHolder.timeRemainingText = (TextView) convertView.findViewById(R.id.text_scenairoModeItem_timeRemaining);
			viewHolder.slidingToggleButton = (SlidingToggleButton) convertView.findViewById(R.id.slidingToggle_scenairoModeItem);
			viewHolder.slidingToggleButton.setOnCheckedChanageListener(new SlidingToggleButton.OnCheckedChanageListener() {
				@Override
				public void onCheckedChanage(BaseSlidingToggleButton slidingToggleButton, boolean isChecked) {
					if(onCheckedChanageListener != null && slidingToggleButton.getTag() != null){
						onCheckedChanageListener.onCheckedChanage((Integer) slidingToggleButton.getTag(), isChecked);
					}
				}
			});
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		scenarioMode = scenarioModeList.get(position);
		viewHolder.triggerTimeText.setText(TimeUtils.getDigitalClockString(scenarioMode.getRepeat().getTriggerTime()));
		viewHolder.nameText.setText(StringUtils.isNotNullAndEmpty(scenarioMode.getName())?(" - "+scenarioMode.getName()):"");
		viewHolder.repeatText.setText(scenarioMode.getRepeat().getIntro(context));
		viewHolder.timeRemainingText.setText(null);
		
		viewHolder.slidingToggleButton.setTag(null);
		viewHolder.slidingToggleButton.setChecked(scenarioMode.isEnable());
		viewHolder.slidingToggleButton.setTag(position);
		
		return convertView;
	}
	
	private class ViewHolder{
		private TextView triggerTimeText;
		private TextView nameText;
		private TextView repeatText;
		private TextView timeRemainingText;
		private SlidingToggleButton slidingToggleButton;
	}
	
	public interface OnCheckedChanageListener{
		public void onCheckedChanage(int position, boolean isChecked);
	}
}