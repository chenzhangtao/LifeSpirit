package me.xiaopan.lifespirit.activity;

import me.xiaopan.androidlibrary.util.AndroidUtils;
import me.xiaopan.androidlibrary.util.AnimationUtils;
import me.xiaopan.androidlibrary.util.DialogUtils;
import me.xiaopan.lifespirit.MyBaseActivity;
import me.xiaopan.lifespirit.task.Repeat;
import me.xiaopan.lifespirit.task.Repeat.RepeatWay;
import me.xiaopan.lifespirit.task.repeat.BaseEveryOtherRepeat;
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

import com.google.gson.Gson;

public class RepeatActivity extends MyBaseActivity implements TemporaryRegister{
	public static final String PARAM_OPTIONAL_STRING_REPEAT = "PARAM_OPTIONAL_REPEAT";
	private Repeat repeat;
	private Preference onlyOneTimePreference;
	private Preference statutoryWorkingDaysPreference;
	private Preference legalAndOffDayPreference;
	private Preference everyOtherMinutePreference;
	private Preference everyOtherHourPreference;
	private Preference everyOtherDayPreference;
	private AlertDialog tempAlertDialog;
	
	private boolean needSave;
	
	@Override
	protected void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.activity_repeat);
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
				returnResult(RepeatWay.ONLY_ONE_TIME);
			}
		});
		
		onlyOneTimePreference.setOnNextButtonClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DatePickerDialog datePickerDialog = new DatePickerDialog(RepeatActivity.this, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						if(needSave){
							repeat.getTriggerTime().setYear(year);
							repeat.getTriggerTime().setMonth(monthOfYear);
							repeat.getTriggerTime().setDayOfMonth(dayOfMonth);
							repeat.getTriggerTime().update();
							onlyOneTimePreference.setTitle(repeat.getOnlyOneTimeRepeat().onGetIntro(getBaseContext(), repeat));
						}
					}
				}, repeat.getTriggerTime().getYear(), repeat.getTriggerTime().getMonth(), repeat.getTriggerTime().getDayOfMonth());
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
				returnResult(RepeatWay.STATUTORY_WORKING_DAYS);
			}
		});
		
		legalAndOffDayPreference.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				returnResult(RepeatWay.LEGAL_AND_OFF_DAY);
			}
		});
		
		everyOtherMinutePreference.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				returnResult(RepeatWay.EVERY_OTHER_MINUTE);
			}
		});
		
		everyOtherMinutePreference.setOnNextButtonClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setSpace(everyOtherMinutePreference, repeat.getEveryOtherMinuteRepeat());
			}
		});
		
		everyOtherHourPreference.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				returnResult(RepeatWay.EVERY_OTHER_HOUR);
			}
		});
		
		everyOtherHourPreference.setOnNextButtonClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setSpace(everyOtherHourPreference, repeat.getEveryOtherHourRepeat());
			}
		});
		
		everyOtherDayPreference.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				returnResult(RepeatWay.EVERY_OTHER_DAY);
			}
		});
		
		everyOtherDayPreference.setOnNextButtonClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setSpace(everyOtherDayPreference, repeat.getEveryOtherDayRepeat());
			}
		});
	}

	@Override
	protected void onInitData(Bundle savedInstanceState) {
		String repeatJson = getIntent().getStringExtra(PARAM_OPTIONAL_STRING_REPEAT);
		initRepeat(repeat = (repeatJson != null)?new Gson().fromJson(repeatJson, Repeat.class):new Repeat());
	}
	
	private void initRepeat(Repeat repeat){
		//设置选中项
		if(repeat.getRepeatWay() == RepeatWay.ONLY_ONE_TIME){
			onlyOneTimePreference.setSelected(true);
		}else if(repeat.getRepeatWay() == RepeatWay.STATUTORY_WORKING_DAYS){
			statutoryWorkingDaysPreference.setSelected(true);
		}else if(repeat.getRepeatWay() == RepeatWay.LEGAL_AND_OFF_DAY){
			legalAndOffDayPreference.setSelected(true);
		}else if(repeat.getRepeatWay() == RepeatWay.EVERY_OTHER_MINUTE){
			everyOtherMinutePreference.setSelected(true);
		}else if(repeat.getRepeatWay() == RepeatWay.EVERY_OTHER_HOUR){
			everyOtherHourPreference.setSelected(true);
		}else if(repeat.getRepeatWay() == RepeatWay.EVERY_OTHER_DAY){
			everyOtherDayPreference.setSelected(true);
		}
		
		onlyOneTimePreference.setTitle(repeat.getOnlyOneTimeRepeat().onGetIntro(getBaseContext(), repeat));
		statutoryWorkingDaysPreference.setTitle(repeat.getStatutoryWorkingDaysRepeat().onGetIntro(getBaseContext(), repeat));
		legalAndOffDayPreference.setTitle(repeat.getLegalAndOffDayRepeat().onGetIntro(getBaseContext(), repeat));
		everyOtherMinutePreference.setTitle(repeat.getEveryOtherMinuteRepeat().onGetIntro(getBaseContext(), repeat));
		everyOtherHourPreference.setTitle(repeat.getEveryOtherHourRepeat().onGetIntro(getBaseContext(), repeat));
		everyOtherDayPreference.setTitle(repeat.getEveryOtherDayRepeat().onGetIntro(getBaseContext(), repeat));
	}
	
	private void returnResult(RepeatWay repeatWay){
		repeat.setRepeatWay(repeatWay);
		getIntent().putExtra(BaseTaskActivity.RETURN_OPTIONAL_REPEAT, new Gson().toJson(repeat));
		setResult(RESULT_OK, getIntent());
		finishActivity();
	}
	
	/**
	 * 设置间隔
	 */
	private void setSpace(final Preference preference, final BaseEveryOtherRepeat everyOtherRepeat){
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
				preference.setTitle(everyOtherRepeat.onGetIntro(getBaseContext(), repeat));
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