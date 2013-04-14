package me.xiaopan.lifespirit.fragment;

import java.util.ArrayList;
import java.util.List;

import me.xiaopan.androidlibrary.widget.ReboundListView;
import me.xiaopan.lifespirit.adapter.ScenarioModeAdapter;
import me.xiaopan.lifespirit.task.ScenarioMode;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ScenairoModeListFragment extends Fragment implements BaseFragment{

	private String pagerTitle;
	private ListView listView;
	private List<ScenarioMode> scenarioModeList;
	private ScenarioModeAdapter scenarioModeAdapter;
	
	public ScenairoModeListFragment(String pagerTitle){
		this.pagerTitle = pagerTitle;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		listView = new ReboundListView(getActivity().getBaseContext());
		scenarioModeList = new ArrayList<ScenarioMode>(0);
		scenarioModeAdapter = new ScenarioModeAdapter(scenarioModeList);
		listView.setAdapter(scenarioModeAdapter);
		return listView;
	}

	@Override
	public String getPageTitle() {
		return pagerTitle;
	}
}