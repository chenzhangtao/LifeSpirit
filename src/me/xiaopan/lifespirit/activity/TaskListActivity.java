package me.xiaopan.lifespirit.activity;

import java.util.ArrayList;
import java.util.List;

import me.xiaopan.lifespirit.MyBaseFragmentActivity;
import me.xiaopan.lifespirit.adapter.FragmentAdapter;
import me.xiaopan.lifespirit.fragment.AlarmClockListFragment;
import me.xiaopan.lifespirit.fragment.ScenairoModeListFragment;
import me.xiaopan.lifespirit2.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;

public class TaskListActivity extends MyBaseFragmentActivity {
	private ViewPager viewPager;
	private PagerTabStrip pagerTabStrip;
	private List<Fragment> fragmentList;
	
	@Override
	protected void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.activity_task_list);
		viewPager = (ViewPager) findViewById(R.id.viewPager_taskList);
		pagerTabStrip = (PagerTabStrip) findViewById(R.id.pagerTabStrip_taskList);
	}

	@Override
	protected void onInitListener(Bundle savedInstanceState) {
	}

	@Override
	protected void onInitData(Bundle savedInstanceState) {
		pagerTabStrip.setTextColor(getColor(R.color.comm_theme));
		pagerTabStrip.setTabIndicatorColorResource(R.color.comm_theme);
		
		fragmentList = new ArrayList<Fragment>(2);
		fragmentList.add(new ScenairoModeListFragment(getString(R.string.activityTitle_scenarioMode)));
		fragmentList.add(new AlarmClockListFragment(getString(R.string.activityTitle_alarmClock)));
		
		viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragmentList));
	}
}