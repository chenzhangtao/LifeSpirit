package me.xiaopan.lifespirit.adapter;

import java.util.List;

import me.xiaopan.lifespirit.task.ScenarioMode;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ScenarioModeAdapter extends BaseAdapter {
	private List<ScenarioMode> scenarioModeList;
	
	public ScenarioModeAdapter(List<ScenarioMode> scenarioModeList){
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
		return null;
	}
}