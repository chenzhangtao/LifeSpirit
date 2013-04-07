package me.xiaopan.lifespirit.activity;

import me.xiaopan.androidlibrary.util.Colors;
import me.xiaopan.lifespirit.MyBaseActivity;
import me.xiaopan.lifespirit.R;
import me.xiaopan.lifespirit.util.ViewUtils;
import android.os.Bundle;
import android.widget.TimePicker;

/**
 * 情景模式界面
 */
public class ScenarioModeActivity extends MyBaseActivity {

	private TimePicker timePicker;
	
	@Override
	protected void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.scenario_mode);
		timePicker = (TimePicker) findViewById(R.id.timePicker_scenaireMode);
	}

	@Override
	protected void onInitListener(Bundle savedInstanceState) {

	}

	@Override
	protected void onInitData(Bundle savedInstanceState) {
		timePicker.setIs24HourView(true);
		ViewUtils.setTimePickerTextColor(timePicker, Colors.BLACK);
	}
}