package me.xiaopan.lifespirit.activity;

import java.util.Calendar;
import java.util.GregorianCalendar;

import me.xiaopan.androidlibrary.util.AndroidUtils;
import me.xiaopan.androidlibrary.util.AnimationUtils;
import me.xiaopan.androidlibrary.util.Colors;
import me.xiaopan.androidlibrary.util.DialogUtils;
import me.xiaopan.lifespirit.MyBaseActivity;
import me.xiaopan.lifespirit.task.BaseTask;
import me.xiaopan.lifespirit.task.repeat.Repeat;
import me.xiaopan.lifespirit.util.ViewUtils;
import me.xiaopan.lifespirit.widget.Preference;
import me.xiaopan.lifespirit2.R;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

/**
 * 情景模式界面
 */
public class BaseTaskActivity extends MyBaseActivity {
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
		timePicker.setOnTimeChangedListener(new OnTimeChangedListener() {
			Calendar calendar;
			
			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				if(baseTask != null){
					if(calendar == null){
						calendar = new GregorianCalendar();
					}
					calendar.setTimeInMillis(System.currentTimeMillis());
					calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
					calendar.set(Calendar.MINUTE, minute);
					baseTask.getTriggerTime().setTimeInMillis(calendar.getTimeInMillis());
				}
			}
		});
		
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
				bundle.putSerializable(RepeatActivity.PARAM_OPTIONAL_REPEAT, baseTask.getRepeat());
				startActivityForResult(RepeatActivity.class, REQUEST_CODE_REPEAT, bundle);
			}
		});
	}

	@Override
	protected void onInitData(Bundle savedInstanceState) {
		timePicker.setIs24HourView(true);
		ViewUtils.setTimePickerTextColor(timePicker, Colors.BLACK);
		
		namePreference.setSubtitle(baseTask.getName().onGetIntro(getBaseContext()));
		repeatPreference.setSubtitle(baseTask.getRepeat().onGetIntro(getBaseContext()));
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK){
			switch (requestCode) {
				case REQUEST_CODE_REPEAT:
					baseTask.setRepeat((Repeat) data.getSerializableExtra(RepeatActivity.RETURN_OPTIONAL_REPEAT));
					repeatPreference.setSubtitle(baseTask.getRepeat().onGetIntro(getBaseContext()));
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
			if("".equals(name)){
				toastL(R.string.hint_taskNameNull);
				AnimationUtils.shake(nameEdit);
				DialogUtils.setDialogClickClose(tempAlertDialog, false);
			}else{
				baseTask.getName().setName(name);
				namePreference.setSubtitle(baseTask.getName().getName());
				DialogUtils.setDialogClickClose(tempAlertDialog, true);
			}
		}});

		builderAlertDialog(builder.create(), nameEdit).show();
	}
	
	/**
	 * 绑定对话框，给对话框添加显示、取消、销毁事件
	 * @param newAlertDialog
	 * @return
	 */
	private AlertDialog builderAlertDialog(final AlertDialog newAlertDialog, final EditText editText){
		newAlertDialog.setOnShowListener(new OnShowListener() {
			@Override
			public void onShow(DialogInterface dialog) {
				tempAlertDialog = newAlertDialog;
				if(editText != null){
					AndroidUtils.openSoftKeyboard(getBaseContext(), editText);
				}
			}
		});
		newAlertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				tempAlertDialog = null;
			}
		});
		newAlertDialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				tempAlertDialog = null;
			}
		});
		return newAlertDialog;
	}
}