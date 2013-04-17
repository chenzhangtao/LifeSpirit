package me.xiaopan.lifespirit.fragment;

import java.util.List;

import me.xiaopan.lifespirit.activity.ScenarioModeActivity;
import me.xiaopan.lifespirit.adapter.ScenarioModeAdapter;
import me.xiaopan.lifespirit.task.ScenarioMode;
import me.xiaopan.lifespirit2.R;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.gson.Gson;

public class ScenairoModeListFragment extends BaseFragment{
	private static final int REQUEST_CODE_UPDATE_SCENARIO_MODE = 101;
	private String pagerTitle;
	private ListView listView;
	private List<ScenarioMode> scenarioModeList;
	private ScenarioModeAdapter scenarioModeAdapter;
	private int updateTaskPosition;
	
	public ScenairoModeListFragment(String pagerTitle){
		this.pagerTitle = pagerTitle;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_task_list, null);
		listView = (ListView) view.findViewById(android.R.id.list);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				updateTaskPosition = position - listView.getHeaderViewsCount();
				Intent intent = new Intent(getActivity(), ScenarioModeActivity.class);
				intent.putExtra(ScenarioModeActivity.PARAM_OPTIONAL_STRING_SCENARIO_MODE, new Gson().toJson(scenarioModeList.get(updateTaskPosition)));
				startActivityForResult(intent, REQUEST_CODE_UPDATE_SCENARIO_MODE);
			}
		});
		
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

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == Activity.RESULT_OK){
			switch(requestCode){
				case REQUEST_CODE_UPDATE_SCENARIO_MODE : 
					scenarioModeList.set(updateTaskPosition, new Gson().fromJson(data.getStringExtra(ScenarioModeActivity.RETURN_REQUIRED_STRING_SCENARIO_MODE), ScenarioMode.class));
					scenarioModeAdapter.notifyDataSetChanged();
					break;
			}
		}
	}
}