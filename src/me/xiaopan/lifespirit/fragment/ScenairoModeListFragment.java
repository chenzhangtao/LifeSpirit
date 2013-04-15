package me.xiaopan.lifespirit.fragment;

import java.util.List;

import me.xiaopan.lifespirit.adapter.ScenarioModeAdapter;
import me.xiaopan.lifespirit.task.ScenarioMode;
import me.xiaopan.lifespirit2.R;
import android.os.AsyncTask;
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
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_task_list, null);
		listView = (ListView) view.findViewById(android.R.id.list);
		new AsyncTask<String, String, List<ScenarioMode>>(){
			@Override
			protected List<ScenarioMode> doInBackground(String... params) {
				return ScenarioMode.readScenarioModes(getActivity());
			}

			@Override
			protected void onPostExecute(List<ScenarioMode> result) {
				if(result != null){
					scenarioModeList = result;
					scenarioModeAdapter = new ScenarioModeAdapter(getActivity().getBaseContext(), scenarioModeList);
					listView.setAdapter(scenarioModeAdapter);
				}
			}
		}.execute("");
		return view;
	}

	@Override
	public String getPageTitle() {
		return pagerTitle;
	}
}