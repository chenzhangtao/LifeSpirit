package me.xiaopan.lifespirit.activity;

import java.io.Serializable;

import me.xiaopan.androidlibrary.util.Colors;
import me.xiaopan.lifespirit.MyBaseActivity;
import me.xiaopan.lifespirit.task.repeat.Repeat;
import me.xiaopan.lifespirit.task.repeat.Repeat.RepeatWay;
import me.xiaopan.lifespirit.task.repeat.OnlyOneTimeRepeat;
import me.xiaopan.lifespirit.util.ViewUtils;
import me.xiaopan.lifespirit.widget.Preference;
import me.xiaopan.lifespirit2.R;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

public class RepeatActivity extends MyBaseActivity implements OnDateChangedListener{
	public static final String PARAM_OPTIONAL_REPEAT = "PARAM_OPTIONAL_REPEAT";
	public static final String RETURN_OPTIONAL_REPEAT = "RETURN_OPTIONAL_REPEAT";
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
		onlyOneTimePreference.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putSerializable(RETURN_OPTIONAL_REPEAT, oneTimeRepeat);
				getIntent().putExtras(bundle);
				setResult(RESULT_OK, getIntent());
				finishActivity();
			}
		});
	}
	
	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		oneTimeRepeat.setYear(year);
		oneTimeRepeat.setMonth(monthOfYear);
		oneTimeRepeat.setDayOfMonth(dayOfMonth);
		onlyOneTimePreference.setTitle(oneTimeRepeat.onGetIntro(getBaseContext()));
	}

	@Override
	protected void onInitData(Bundle savedInstanceState) {
		ViewUtils.setDatePickerTextColor(datePicker, Colors.BLACK);
		
		Repeat repeat = null;
		Serializable serializable = getIntent().getSerializableExtra(PARAM_OPTIONAL_REPEAT);
		if(serializable != null && serializable instanceof Repeat){
			repeat = (Repeat) serializable;
		}else{
			repeat = new OnlyOneTimeRepeat();
		}
		
		datePicker.init(repeat.getYear(), repeat.getMonth(), repeat.getDayOfMonth(), this);
		
		if(repeat.geRepeatWay() == RepeatWay.ONLY_ONE_TIME){
			oneTimeRepeat = (OnlyOneTimeRepeat) repeat;
			onlyOneTimePreference.setSelected(true);
		}else{
			oneTimeRepeat = new OnlyOneTimeRepeat();
		}
		onlyOneTimePreference.setTitle(oneTimeRepeat.onGetIntro(getBaseContext()));
	}
}