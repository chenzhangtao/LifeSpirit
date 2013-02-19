package me.xiaopan.lifespirit.activity;

import java.util.ArrayList;
import java.util.List;

import me.xiaopan.androidlibrary.widget.PullListView;
import me.xiaopan.androidlibrary.widget.ViewAdapter;
import me.xiaopan.lifespirit.MyBaseActivity;
import me.xiaopan.lifespirit.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.ToggleButton;

public class SettingActivity extends MyBaseActivity {
	private List<View> viewList;
	private PullListView settingListView;
	private ViewAdapter viewAdapter;
	private ToggleButton autoStartToggleButton, onAnimationToggleButton;
	
	@Override
	public void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.setting);
		getActionBar().setBackgroundDrawable(getDrawable(R.drawable.shape_comm_titlebar));
		getActionBar().setDisplayHomeAsUpEnabled(true);
		settingListView = (PullListView) findViewById(android.R.id.list);
	}

	@Override
	public void onInitListener(Bundle savedInstanceState) {
		settingListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				switch(position - settingListView.getHeaderViewsCount()){
					case 0 : 
						autoStartToggleButton.toggle();
						getMyApplication().getPreferencesManager().setAutoStartBackgroundService(autoStartToggleButton.isChecked());
						break;
					case 1 : 
						onAnimationToggleButton.toggle();
						getMyApplication().getPreferencesManager().setOnInterfaceSwitchingAnimation(onAnimationToggleButton.isChecked());
						break;
				}
			}
		});
	}

	@Override
	public void onInitData(Bundle savedInstanceState) {
		viewList = new ArrayList<View>();
		builderViewList();
		settingListView.openListHeaderReboundMode();
		settingListView.openListFooterReboundMode();
		settingListView.setAdapter(viewAdapter = new ViewAdapter(viewList));
	}
	
	private void builderViewList(){
		viewList.clear();
		
		//自动启动
		View autoStart = LayoutInflater.from(this).inflate(R.layout.setting_item_toggle, null);
		((TextView) autoStart.findViewById(R.id.text_settingItem_title)).setText(R.string.setting_autoStart);
		((TextView) autoStart.findViewById(R.id.text_settingItem_hint)).setText(R.string.setting_autoStart_hint);
		autoStartToggleButton = (ToggleButton) autoStart.findViewById(R.id.toggle_settingItem);
		autoStartToggleButton.setChecked(getMyApplication().getPreferencesManager().isAutoStartBackgroundService());
		viewList.add(autoStart);
		
		//使用动画
		View useAnimation = LayoutInflater.from(this).inflate(R.layout.setting_item_toggle, null);
		((TextView) useAnimation.findViewById(R.id.text_settingItem_title)).setText(R.string.setting_userInterfaceSwitchingAnimation);
		((TextView) useAnimation.findViewById(R.id.text_settingItem_hint)).setText(R.string.setting_userInterfaceSwitchingAnimation_hint);
		onAnimationToggleButton = (ToggleButton) useAnimation.findViewById(R.id.toggle_settingItem);
		onAnimationToggleButton.setChecked(getMyApplication().getPreferencesManager().isOnInterfaceSwitchingAnimation());
		viewList.add(useAnimation);
	}
	
	@Override
	public void onBackPressed() {
		finishActivity(R.anim.base_slide_to_bottom_in, R.anim.base_slide_to_bottom_out);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.settings, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home: finishActivity(R.anim.base_slide_to_bottom_in, R.anim.base_slide_to_bottom_out); break;
			case R.id.menu_settings_recover:
				getMyApplication().getPreferencesManager().clearAllAttr();
				builderViewList();
				viewAdapter.notifyDataSetChanged();
				toastS(R.string.setting_recoverDefult_success);
				break;
			case R.id.comm_menu_stop: 
				if(getMyApplication().getPreferencesManager().isBackgServiceRunning()){
					getMyApplication().stopBackgService();
				}else{
					getMyApplication().startBackgService();
				}
				break;
			case R.id.base_menu_settings: startActivity(SettingActivity.class, R.anim.base_slide_to_top_in, R.anim.base_slide_to_top_out); break;
			case R.id.base_menu_exit: finishApplication(); break;
			default: break;
		}
		return true;
	}
}