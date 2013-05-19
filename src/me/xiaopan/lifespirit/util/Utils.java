package me.xiaopan.lifespirit.util;

import me.xiaopan.easyandroid.util.AndroidUtils;
import me.xiaopan.easyjava.util.ClassUtils;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnShowListener;
import android.graphics.Paint;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class Utils {
	public interface TemporaryRegister {
		public void onRegister(AlertDialog alertDialog);
	}
	
	public static AlertDialog builderAlertDialog(final Context context, final AlertDialog newAlertDialog, final TemporaryRegister temporaryRegister, final EditText editText){
		newAlertDialog.setOnShowListener(new OnShowListener() {
			@Override
			public void onShow(DialogInterface dialog) {
				temporaryRegister.onRegister(newAlertDialog);
				if(editText != null){
					AndroidUtils.openSoftKeyboard(context, editText);
				}
			}
		});
		newAlertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				temporaryRegister.onRegister(null);
			}
		});
		newAlertDialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				temporaryRegister.onRegister(null);
			}
		});
		return newAlertDialog;
	}
	
	public static AlertDialog builderAlertDialog(final Context context, final AlertDialog newAlertDialog, final TemporaryRegister temporaryRegister){
		return builderAlertDialog(context, newAlertDialog, temporaryRegister, null);
	}public static boolean setNumberPickerTextColor(NumberPicker numberPicker, int color){
		if(numberPicker != null){
			Paint paint = ClassUtils.getObjectByFieldName(numberPicker, "mSelectorWheelPaint", Paint.class);
			EditText editText = ClassUtils.getObjectByFieldName(numberPicker, "mInputText", EditText.class);
			if(paint != null && editText != null){
				paint.setColor(color);
				editText.setTextColor(color);
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	public static boolean setTimePickerTextColor(TimePicker timePicker, int color){
		TextView textView = ClassUtils.getObjectByFieldName(timePicker, "mDivider", TextView.class);
		if(textView != null){
			textView.setTextColor(color);
		}
		return setNumberPickerTextColor(ClassUtils.getObjectByFieldName(timePicker, "mHourSpinner", NumberPicker.class), color) && setNumberPickerTextColor(ClassUtils.getObjectByFieldName(timePicker, "mMinuteSpinner", NumberPicker.class), color);
	}
	
	public static boolean setDatePickerTextColor(DatePicker datePicker, int color){
		return setNumberPickerTextColor(ClassUtils.getObjectByFieldName(datePicker, "mYearSpinner", NumberPicker.class), color) && 
				setNumberPickerTextColor(ClassUtils.getObjectByFieldName(datePicker, "mMonthSpinner", NumberPicker.class), color) && 
				setNumberPickerTextColor(ClassUtils.getObjectByFieldName(datePicker, "mDaySpinner", NumberPicker.class), color);
	}
}