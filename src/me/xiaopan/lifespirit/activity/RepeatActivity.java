package me.xiaopan.lifespirit.activity;

import java.io.Serializable;

import me.xiaopan.androidlibrary.util.AndroidUtils;
import me.xiaopan.androidlibrary.util.AnimationUtils;
import me.xiaopan.androidlibrary.util.DialogUtils;
import me.xiaopan.lifespirit.MyBaseActivity;
import me.xiaopan.lifespirit.task.repeat.EveryOtherDayRepeat;
import me.xiaopan.lifespirit.task.repeat.EveryOtherHourRepeat;
import me.xiaopan.lifespirit.task.repeat.EveryOtherMinuteRepeat;
import me.xiaopan.lifespirit.task.repeat.EveryOtherRepeat;
import me.xiaopan.lifespirit.task.repeat.LegalAndOffDayRepeat;
import me.xiaopan.lifespirit.task.repeat.OnlyOneTimeRepeat;
import me.xiaopan.lifespirit.task.repeat.BaseRepeat;
import me.xiaopan.lifespirit.task.repeat.BaseRepeat.RepeatWay;
import me.xiaopan.lifespirit.task.repeat.StatutoryWorkingDaysRepeat;
import me.xiaopan.lifespirit.util.TemporaryRegister;
import me.xiaopan.lifespirit.util.Utils;
import me.xiaopan.lifespirit.widget.Preference;
import me.xiaopan.lifespirit2.R;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

public class RepeatActivity extends MyBaseActivity implements TemporaryRegister{
	public static final String PARAM_OPTIONAL_REPEAT = "PARAM_OPTIONAL_REPEAT";
	public static final String RETURN_OPTIONAL_REPEAT = "RETURN_OPTIONAL_REPEAT";
	private Preference onlyOneTimePreference;
	private Preference statutoryWorkingDaysPreference;
	private Preference legalAndOffDayPreference;
	private Preference everyOtherMinutePreference;
	private Preference everyOtherHourPreference;
	private Preference everyOtherDayPreference;
	private OnlyOneTimeRepeat onlyOneTimeRepeat;
	private StatutoryWorkingDaysRepeat statutoryWorkingDaysRepeat;
	private LegalAndOffDayRepeat legalAndOffDayRepeat;
	private EveryOtherMinuteRepeat everyOtherMinuteRepeat;
	private EveryOtherHourRepeat everyOtherHourRepeat;
	private EveryOtherDayRepeat everyOtherDayRepeat;
	private AlertDialog tempAlertDialog;
	
	private boolean needSave;
	
	@Override
	protected void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.repeat);
		onlyOneTimePreference = (Preference) findViewById(R.id.preference_repeat_onlyOneTime);
		statutoryWorkingDaysPreference = (Preference) findViewById(R.id.preference_repeat_statutoryWorkingDays);
		legalAndOffDayPreference = (Preference) findViewById(R.id.preference_repeat_legalAndOffDay);
		everyOtherMinutePreference = (Preference) findViewById(R.id.preference_repeat_everyOtherMinute);
		everyOtherHourPreference = (Preference) findViewById(R.id.preference_repeat_everyOtherHour);
		everyOtherDayPreference = (Preference) findViewById(R.id.preference_repeat_everyOtherDay);
	}

	@Override
	protected void onInitListener(Bundle savedInstanceState) {
		onlyOneTimePreference.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				returnResult(onlyOneTimeRepeat);
			}
		});
		
		onlyOneTimePreference.setOnNextButtonClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DatePickerDialog datePickerDialog = new DatePickerDialog(RepeatActivity.this, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						if(needSave){
							onlyOneTimeRepeat.getTriggerTime().setYear(year);
							onlyOneTimeRepeat.getTriggerTime().setMonth(monthOfYear);
							onlyOneTimeRepeat.getTriggerTime().setDayOfMonth(dayOfMonth);
							onlyOneTimeRepeat.getTriggerTime().update();
							onlyOneTimePreference.setTitle(onlyOneTimeRepeat.onGetIntro(getBaseContext()));
						}
					}
				}, onlyOneTimeRepeat.getTriggerTime().getYear(), onlyOneTimeRepeat.getTriggerTime().getMonth(), onlyOneTimeRepeat.getTriggerTime().getDayOfMonth());
				datePickerDialog.setButton(getString(R.string.base_confirm), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						needSave = true;
					}
				});
				needSave = false;
				datePickerDialog.show();
			}
		});
		
		statutoryWorkingDaysPreference.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				returnResult(statutoryWorkingDaysRepeat);
			}
		});
		
		legalAndOffDayPreference.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				returnResult(legalAndOffDayRepeat);
			}
		});
		
		everyOtherMinutePreference.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				returnResult(everyOtherMinuteRepeat);
			}
		});
		
		everyOtherMinutePreference.setOnNextButtonClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setSpace(everyOtherMinutePreference, everyOtherMinuteRepeat);
			}
		});
		
		everyOtherHourPreference.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				returnResult(everyOtherHourRepeat);
			}
		});
		
		everyOtherHourPreference.setOnNextButtonClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setSpace(everyOtherHourPreference, everyOtherHourRepeat);
			}
		});
		
		everyOtherDayPreference.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				returnResult(everyOtherDayRepeat);
			}
		});
		
		everyOtherDayPreference.setOnNextButtonClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setSpace(everyOtherDayPreference, everyOtherDayRepeat);
			}
		});
	}

	@Override
	protected void onInitData(Bundle savedInstanceState) {
		Serializable serializable = getIntent().getSerializableExtra(PARAM_OPTIONAL_REPEAT);
		BaseRepeat repeat = (serializable != null && serializable instanceof BaseRepeat)?(BaseRepeat) serializable:new OnlyOneTimeRepeat();
		initRepeat(repeat);
	}
	
	private void initRepeat(BaseRepeat repeat){
		onlyOneTimeRepeat = new OnlyOneTimeRepeat();
		statutoryWorkingDaysRepeat = new StatutoryWorkingDaysRepeat();
		legalAndOffDayRepeat = new LegalAndOffDayRepeat();
		everyOtherMinuteRepeat = new EveryOtherMinuteRepeat();
		everyOtherHourRepeat = new EveryOtherHourRepeat();
		everyOtherDayRepeat = new EveryOtherDayRepeat();
		
		if(repeat.geRepeatWay() == RepeatWay.ONLY_ONE_TIME){
			onlyOneTimeRepeat = (OnlyOneTimeRepeat) repeat;
			onlyOneTimePreference.setSelected(true);
		}else if(repeat.geRepeatWay() == RepeatWay.STATUTORY_WORKING_DAYS){
			statutoryWorkingDaysRepeat = (StatutoryWorkingDaysRepeat) repeat;
			statutoryWorkingDaysPreference.setSelected(true);
		}else if(repeat.geRepeatWay() == RepeatWay.LEGAL_AND_OFF_DAY){
			legalAndOffDayRepeat = (LegalAndOffDayRepeat) repeat;
			legalAndOffDayPreference.setSelected(true);
		}else if(repeat.geRepeatWay() == RepeatWay.EVERY_OTHER_MINUTE){
			everyOtherMinuteRepeat = (EveryOtherMinuteRepeat) repeat;
			everyOtherMinutePreference.setSelected(true);
		}else if(repeat.geRepeatWay() == RepeatWay.EVERY_OTHER_HOUR){
			everyOtherHourRepeat = (EveryOtherHourRepeat) repeat;
			everyOtherHourPreference.setSelected(true);
		}else if(repeat.geRepeatWay() == RepeatWay.EVERY_OTHER_DAY){
			everyOtherDayRepeat = (EveryOtherDayRepeat) repeat;
			everyOtherDayPreference.setSelected(true);
		}
		
		onlyOneTimePreference.setTitle(onlyOneTimeRepeat.onGetIntro(getBaseContext()));
		statutoryWorkingDaysPreference.setTitle(statutoryWorkingDaysRepeat.onGetIntro(getBaseContext()));
		legalAndOffDayPreference.setTitle(legalAndOffDayRepeat.onGetIntro(getBaseContext()));
		everyOtherMinutePreference.setTitle(everyOtherMinuteRepeat.onGetIntro(getBaseContext()));
		everyOtherHourPreference.setTitle(everyOtherHourRepeat.onGetIntro(getBaseContext()));
		everyOtherDayPreference.setTitle(everyOtherDayRepeat.onGetIntro(getBaseContext()));
	}
	
	private void returnResult(BaseRepeat repeat){
		Bundle bundle = new Bundle();
		bundle.putSerializable(RETURN_OPTIONAL_REPEAT, repeat);
		getIntent().putExtras(bundle);
		setResult(RESULT_OK, getIntent());
		finishActivity();
	}
	
	/**
	 * 设置间隔
	 */
	private void setSpace(final Preference preference, final EveryOtherRepeat everyOtherRepeat){
		//创建对话框并设置标题
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		//设置要显示的视图并初始化编辑框的默认值
		final EditText spaceEdit = new EditText(getBaseContext());
		spaceEdit.setHint(R.string.repeat_spaceInputHint);
		spaceEdit.setText(""+everyOtherRepeat.getSpace());
		spaceEdit.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
		spaceEdit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
		LinearLayout linearLayout = me.xiaopan.androidlibrary.util.ViewUtils.createLinearLayout(getBaseContext(), LinearLayout.HORIZONTAL, LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		linearLayout.setPadding(30, 50, 30, 50);
		linearLayout.addView(spaceEdit, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		builder.setView(linearLayout);

		//设置确定按钮
		builder.setPositiveButton(R.string.base_confirm, new DialogInterface.OnClickListener() { public void onClick(DialogInterface dialog, int which) {
			String name = spaceEdit.getEditableText().toString().trim();
			if(!"".equals(name) && !"0".equals(name)){
				everyOtherRepeat.setSpace(Integer.valueOf(name));
				preference.setTitle(everyOtherRepeat.onGetIntro(getBaseContext()));
				DialogUtils.setDialogClickClose(tempAlertDialog, true);
			}else{
				toastL(R.string.repeat_spaceInputErrorHint);
				AnimationUtils.shake(spaceEdit);
				AndroidUtils.openSoftKeyboard(getBaseContext(), spaceEdit);
				DialogUtils.setDialogClickClose(tempAlertDialog, false);
				getMessageHanlder().postDelayed(new Runnable() {
					@Override
					public void run() {
						DialogUtils.setDialogClickClose(tempAlertDialog, true);
					};
				}, 10);
			}
		}});

		Utils.builderAlertDialog(getBaseContext(), builder.create(), this, spaceEdit).show();
	}

	@Override
	public void onRegister(AlertDialog alertDialog) {
		tempAlertDialog = alertDialog;
	}
}