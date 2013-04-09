package me.xiaopan.lifespirit.activity;

import java.io.Serializable;

import me.xiaopan.androidlibrary.util.Colors;
import me.xiaopan.lifespirit.MyBaseActivity;
import me.xiaopan.lifespirit.task.repeat.EveryOtherDayRepeat;
import me.xiaopan.lifespirit.task.repeat.EveryOtherHourRepeat;
import me.xiaopan.lifespirit.task.repeat.EveryOtherMinuteRepeat;
import me.xiaopan.lifespirit.task.repeat.EveryOtherMonthRepeat;
import me.xiaopan.lifespirit.task.repeat.EveryOtherWeekRepeat;
import me.xiaopan.lifespirit.task.repeat.EveryOtherYearRepeat;
import me.xiaopan.lifespirit.task.repeat.OnlyOneTimeRepeat;
import me.xiaopan.lifespirit.task.repeat.Repeat;
import me.xiaopan.lifespirit.task.repeat.Repeat.RepeatWay;
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
	private Preference everyOtherMinutePreference;
	private Preference everyOtherHourPreference;
	private Preference everyOtherDayPreference;
	private Preference everyOtherWeekPreference;
	private Preference everyOtherMonthPreference;
	private Preference everyOtherYearPreference;
	private OnlyOneTimeRepeat onlyOneTimeRepeat;
	private EveryOtherMinuteRepeat everyOtherMinuteRepeat;
	private EveryOtherHourRepeat everyOtherHourRepeat;
	private EveryOtherDayRepeat everyOtherDayRepeat;
	private EveryOtherWeekRepeat everyOtherWeekRepeat;
	private EveryOtherMonthRepeat everyOtherMonthRepeat;
	private EveryOtherYearRepeat everyOtherYearRepeat;
	
	@Override
	protected void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.repeat);
		datePicker = (DatePicker) findViewById(R.id.datePicker_repeat);
		onlyOneTimePreference = (Preference) findViewById(R.id.preference_repeat_onlyOneTime);
		everyOtherMinutePreference = (Preference) findViewById(R.id.preference_repeat_everyOtherMinute);
		everyOtherHourPreference = (Preference) findViewById(R.id.preference_repeat_everyOtherHour);
		everyOtherDayPreference = (Preference) findViewById(R.id.preference_repeat_everyOtherDay);
		everyOtherWeekPreference = (Preference) findViewById(R.id.preference_repeat_everyOtherWeek);
		everyOtherMonthPreference = (Preference) findViewById(R.id.preference_repeat_everyOtherMonth);
		everyOtherYearPreference = (Preference) findViewById(R.id.preference_repeat_everyOtherYear);
	}

	@Override
	protected void onInitListener(Bundle savedInstanceState) {
		onlyOneTimePreference.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				returnResult(onlyOneTimeRepeat);
			}
		});
		
		everyOtherMinutePreference.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				returnResult(everyOtherMinuteRepeat);
			}
		});
		
		everyOtherHourPreference.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				returnResult(everyOtherHourRepeat);
			}
		});
		
		everyOtherDayPreference.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				returnResult(everyOtherDayRepeat);
			}
		});
		
		everyOtherWeekPreference.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				returnResult(everyOtherWeekRepeat);
			}
		});
		
		everyOtherMonthPreference.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				returnResult(everyOtherMonthRepeat);
			}
		});
		
		everyOtherYearPreference.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				returnResult(everyOtherYearRepeat);
			}
		});
	}
	
	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		onlyOneTimeRepeat.getTriggerTime().setYear(year);
		onlyOneTimeRepeat.getTriggerTime().setMonth(monthOfYear);
		onlyOneTimeRepeat.getTriggerTime().setDayOfMonth(dayOfMonth);
		onlyOneTimeRepeat.getTriggerTime().update();
		onlyOneTimePreference.setTitle(onlyOneTimeRepeat.onGetIntro(getBaseContext()));
	}

	@Override
	protected void onInitData(Bundle savedInstanceState) {
		ViewUtils.setDatePickerTextColor(datePicker, Colors.BLACK);
		
		Serializable serializable = getIntent().getSerializableExtra(PARAM_OPTIONAL_REPEAT);
		Repeat repeat = (serializable != null && serializable instanceof Repeat)?(Repeat) serializable:new OnlyOneTimeRepeat();
		datePicker.init(repeat.getTriggerTime().getYear(), repeat.getTriggerTime().getMonth(), repeat.getTriggerTime().getDayOfMonth(), this);
		initRepeat(repeat);
	}
	
	private void initRepeat(Repeat repeat){
		onlyOneTimeRepeat = new OnlyOneTimeRepeat();
		everyOtherMinuteRepeat = new EveryOtherMinuteRepeat();
		everyOtherHourRepeat = new EveryOtherHourRepeat();
		everyOtherDayRepeat = new EveryOtherDayRepeat();
		everyOtherWeekRepeat = new EveryOtherWeekRepeat();
		everyOtherMonthRepeat = new EveryOtherMonthRepeat();
		everyOtherYearRepeat = new EveryOtherYearRepeat();
		
		if(repeat.geRepeatWay() == RepeatWay.ONLY_ONE_TIME){
			onlyOneTimeRepeat = (OnlyOneTimeRepeat) repeat;
			onlyOneTimePreference.setSelected(true);
		}else if(repeat.geRepeatWay() == RepeatWay.EVERY_OTHER_MINUTE){
			everyOtherMinuteRepeat = (EveryOtherMinuteRepeat) repeat;
			everyOtherMinutePreference.setSelected(true);
		}else if(repeat.geRepeatWay() == RepeatWay.EVERY_OTHER_HOUR){
			everyOtherHourRepeat = (EveryOtherHourRepeat) repeat;
			everyOtherHourPreference.setSelected(true);
		}else if(repeat.geRepeatWay() == RepeatWay.EVERY_OTHER_DAY){
			everyOtherDayRepeat = (EveryOtherDayRepeat) repeat;
			everyOtherDayPreference.setSelected(true);
		}else if(repeat.geRepeatWay() == RepeatWay.EVERY_OTHER_WEEK){
			everyOtherWeekRepeat = (EveryOtherWeekRepeat) repeat;
			everyOtherWeekPreference.setSelected(true);
		}else if(repeat.geRepeatWay() == RepeatWay.EVERY_OTHER_MONTH){
			everyOtherMonthRepeat = (EveryOtherMonthRepeat) repeat;
			everyOtherMonthPreference.setSelected(true);
		}else if(repeat.geRepeatWay() == RepeatWay.EVERY_OTHER_YEAR){
			everyOtherYearRepeat = (EveryOtherYearRepeat) repeat;
			everyOtherYearPreference.setSelected(true);
		}
		
		onlyOneTimePreference.setTitle(onlyOneTimeRepeat.onGetIntro(getBaseContext()));
		everyOtherMinutePreference.setTitle(everyOtherMinuteRepeat.onGetIntro(getBaseContext()));
		everyOtherHourPreference.setTitle(everyOtherHourRepeat.onGetIntro(getBaseContext()));
		everyOtherDayPreference.setTitle(everyOtherDayRepeat.onGetIntro(getBaseContext()));
		everyOtherWeekPreference.setTitle(everyOtherWeekRepeat.onGetIntro(getBaseContext()));
		everyOtherMonthPreference.setTitle(everyOtherMonthRepeat.onGetIntro(getBaseContext()));
		everyOtherYearPreference.setTitle(everyOtherYearRepeat.onGetIntro(getBaseContext()));
	}
	
	private void returnResult(Repeat repeat){
		Bundle bundle = new Bundle();
		bundle.putSerializable(RETURN_OPTIONAL_REPEAT, repeat);
		getIntent().putExtras(bundle);
		setResult(RESULT_OK, getIntent());
		finishActivity();
	}
}