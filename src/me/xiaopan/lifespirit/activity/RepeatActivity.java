package me.xiaopan.lifespirit.activity;

import java.io.Serializable;

import me.xiaopan.androidlibrary.util.Colors;
import me.xiaopan.lifespirit.MyBaseActivity;
import me.xiaopan.lifespirit.task.repeat.BaseRepeatWay;
import me.xiaopan.lifespirit.task.repeat.BaseRepeatWay.Way;
import me.xiaopan.lifespirit.task.repeat.OnlyOneTimeRepeat;
import me.xiaopan.lifespirit.util.ViewUtils;
import me.xiaopan.lifespirit.widget.Preference;
import me.xiaopan.lifespirit2.R;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

public class RepeatActivity extends MyBaseActivity {
	public static final String PARAM_OPTIONAL_REPEAT = "PARAM_OPTIONAL_REPEAT";
	private DatePicker datePicker;
	private Preference onlyOneTimePreference;
	private OnlyOneTimeRepeat oneTimeRepeat;
	
	@Override
	protected void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.repeat);
		datePicker = (DatePicker) findViewById(R.id.datePicker_repeat);
		onlyOneTimePreference = (Preference) findViewById(R.id.preference_repeat_onlyOneTime);
	}

	@Override
	protected void onInitListener(Bundle savedInstanceState) {
		datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), new OnDateChangedListener() {
			@Override
			public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				oneTimeRepeat.setYear(year);
				oneTimeRepeat.setMonth(monthOfYear + 1);
				oneTimeRepeat.setDayOfMonth(dayOfMonth);
				onlyOneTimePreference.setTitle(oneTimeRepeat.onGetIntro(getBaseContext()));
			}
		});
		
		onlyOneTimePreference.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
	}

	@Override
	protected void onInitData(Bundle savedInstanceState) {
		ViewUtils.setDatePickerTextColor(datePicker, Colors.BLACK);
		
		BaseRepeatWay repeat = null;
		Serializable serializable = getIntent().getSerializableExtra(PARAM_OPTIONAL_REPEAT);
		if(serializable != null && serializable instanceof BaseRepeatWay){
			repeat = (BaseRepeatWay) serializable;
		}else{
			repeat = new OnlyOneTimeRepeat();
		}
		
		if(repeat.geWay() == Way.ONLY_ONE_TIME){
			oneTimeRepeat = (OnlyOneTimeRepeat) repeat;
			onlyOneTimePreference.setSelected(true);
		}else{
			oneTimeRepeat = new OnlyOneTimeRepeat();
		}
		onlyOneTimePreference.setTitle(oneTimeRepeat.onGetIntro(getBaseContext()));
	}
}