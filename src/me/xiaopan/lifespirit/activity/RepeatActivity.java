package me.xiaopan.lifespirit.activity;

import me.xiaopan.androidlibrary.util.Colors;
import me.xiaopan.lifespirit.MyBaseActivity;
import me.xiaopan.lifespirit.util.ViewUtils;
import me.xiaopan.lifespirit2.R;
import android.os.Bundle;
import android.widget.DatePicker;

public class RepeatActivity extends MyBaseActivity {
	private DatePicker datePicker;
	
	@Override
	protected void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.repeat);
		datePicker = (DatePicker) findViewById(R.id.datePicker_repeat);
	}

	@Override
	protected void onInitListener(Bundle savedInstanceState) {
		
	}

	@Override
	protected void onInitData(Bundle savedInstanceState) {
		ViewUtils.setDatePickerTextColor(datePicker, Colors.BLACK);
	}
}