package me.xiaopan.lifespirit.fragment;

import me.xiaopan.easyandroid.widget.ReboundListView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AlarmClockListFragment extends BaseFragment{

	private String pagerTitle;
	
	public AlarmClockListFragment(String pagerTitle){
		this.pagerTitle = pagerTitle;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ReboundListView reboundListView = new ReboundListView(getActivity().getBaseContext());
		
		return reboundListView;
	}

	@Override
	public String getPageTitle() {
		return pagerTitle;
	}
}