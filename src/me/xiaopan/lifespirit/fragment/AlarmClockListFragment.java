package me.xiaopan.lifespirit.fragment;

import me.xiaopan.androidlibrary.widget.ReboundListView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AlarmClockListFragment extends Fragment implements BaseFragment{

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