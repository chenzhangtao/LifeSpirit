package me.xiaopan.lifespirit.activity;

import me.xiaopan.androidlibrary.util.AnimationUtils;
import me.xiaopan.androidlibrary.util.Colors;
import me.xiaopan.androidlibrary.util.DialogUtils;
import me.xiaopan.lifespirit.MyBaseActivity;
import me.xiaopan.lifespirit.task.BaseTask;
import me.xiaopan.lifespirit.task.Repeat;
import me.xiaopan.lifespirit.util.TemporaryRegister;
import me.xiaopan.lifespirit.util.Utils;
import me.xiaopan.lifespirit.util.ViewUtils;
import me.xiaopan.lifespirit.widget.Preference;
import me.xiaopan.lifespirit2.R;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

import com.google.gson.Gson;

/**
 * 情景模式界面
 */
public class BaseTaskActivity extends MyBaseActivity implements TemporaryRegister{
	private static final int REQUEST_CODE_REPEAT = 101;
	private TimePicker timePicker;
	protected BaseTask baseTask;
	private Preference namePreference;
	private Preference repeatPreference;
	private AlertDialog tempAlertDialog;
	
	@Override
	protected void onInitLayout(Bundle savedInstanceState) {
		timePicker = (TimePicker) findViewById(R.id.comm_preference_timePicker);
		namePreference = (Preference) findViewById(R.id.comm_preference_name);
		repeatPreference = (Preference) findViewById(R.id.comm_preference_repeat);
	}

	@Override
	protected void onInitListener(Bundle savedInstanceState) {
		namePreference.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//创建对话框并设置标题
				AlertDialog.Builder builder = new AlertDialog.Builder(BaseTaskActivity.this);
				builder.setTitle(R.string.taskItem_setName);

				//设置要显示的元素列表
				final String[] taskNames = getResources().getStringArray(R.array.taskItem_name_items);
				builder.setItems(taskNames, new DialogInterface.OnClickListener() { 
					@Override
					public void onClick(DialogInterface dialog, int which) {
						baseTask.getName().setName(taskNames[which]);
						namePreference.setSubtitle(baseTask.getName().getName());
					}
				});

				//设置取消按钮
				builder.setNegativeButton(R.string.base_cancel, null);

				//设置无按钮
				builder.setNeutralButton(R.string.base_none, new DialogInterface.OnClickListener() { 
					@Override
					public void onClick(DialogInterface dialog, int which) {
						baseTask.getName().setName(null);
						namePreference.setSubtitle(baseTask.getName().getName());
					}
				});

				//设置自定义按钮
				builder.setPositiveButton(R.string.base_custom, new DialogInterface.OnClickListener() { 
					@Override
					public void onClick(DialogInterface dialog, int which) {
						setCustomName();
					}
				});

				builder.create().show();
			}
		});
		
		repeatPreference.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putString(RepeatActivity.PARAM_OPTIONAL_STRING_REPEAT, new Gson().toJson(baseTask.getRepeat()));
				startActivityForResult(RepeatActivity.class, REQUEST_CODE_REPEAT, bundle);
			}
		});
	}

	@Override
	protected void onInitData(Bundle savedInstanceState) {
		ViewUtils.setTimePickerTextColor(timePicker, Colors.BLACK);
		timePicker.setIs24HourView(true);
		timePicker.setCurrentHour(baseTask.getRepeat().getTriggerTime().getHourOfDay());
		timePicker.setCurrentMinute(baseTask.getRepeat().getTriggerTime().getMinute());
		
		namePreference.setSubtitle(baseTask.getName().getIntro(getBaseContext()));
		repeatPreference.setSubtitle(baseTask.getRepeat().getIntro(getBaseContext()));
		
		timePicker.setOnTimeChangedListener(new OnTimeChangedListener() {
			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				if(baseTask != null){
					baseTask.getRepeat().getTriggerTime().setHourOfDay(hourOfDay);
					baseTask.getRepeat().getTriggerTime().setMinute(minute);
					baseTask.getRepeat().getTriggerTime().update();
					repeatPreference.setSubtitle(baseTask.getRepeat().getIntro(getBaseContext()));
				}
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK){
			switch (requestCode) {
				case REQUEST_CODE_REPEAT:
					baseTask.setRepeat(new Gson().fromJson(data.getStringExtra(RepeatActivity.RETURN_REQUIRED_STRING_REPEAT), Repeat.class));
					repeatPreference.setSubtitle(baseTask.getRepeat().getIntro(getBaseContext()));
					baseTask.getRepeat().setLastExecuteTime(null);
					break;
				default: break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 设置自定义任务名称
	 */
	private void setCustomName(){
		//创建对话框并设置标题
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		//设置要显示的视图并初始化编辑框的默认值
		final EditText nameEdit = new EditText(getBaseContext());
		nameEdit.setHint(R.string.taskEditCustomName_edit_name_hint);
		nameEdit.setText(baseTask.getName().getName());
		nameEdit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
		LinearLayout linearLayout = me.xiaopan.androidlibrary.util.ViewUtils.createLinearLayout(getBaseContext(), LinearLayout.HORIZONTAL, LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		linearLayout.setPadding(30, 50, 30, 50);
		linearLayout.addView(nameEdit, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		builder.setView(linearLayout);

		//设置取消按钮
		builder.setNegativeButton(R.string.base_cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				DialogUtils.setDialogClickClose(tempAlertDialog, true);
			}
		});

		//设置确定按钮
		builder.setPositiveButton(R.string.base_confirm, new DialogInterface.OnClickListener() { public void onClick(DialogInterface dialog, int which) {
			String name = nameEdit.getEditableText().toString();
			if(!"".equals(name)){
				baseTask.getName().setName(name);
				namePreference.setSubtitle(baseTask.getName().getName());
				DialogUtils.setDialogClickClose(tempAlertDialog, true);
			}else{
				toastL(R.string.hint_taskNameNull);
				AnimationUtils.shake(nameEdit);
				DialogUtils.setDialogClickClose(tempAlertDialog, false);
			}
		}});

		Utils.builderAlertDialog(getBaseContext(), builder.create(), this, nameEdit).show();
	}
	
	@Override
	public void onRegister(AlertDialog alertDialog) {
		tempAlertDialog = alertDialog;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.task_edit, menu);
		return super.onCreateOptionsMenu(menu);
	}
}