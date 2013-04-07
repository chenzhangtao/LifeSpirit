package me.xiaopan.lifespirit.util;

import me.xiaopan.javalibrary.util.ClassUtils;
import android.graphics.Paint;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class ViewUtils {
	public static boolean setNumberPickerTextColor(NumberPicker numberPicker, int color){
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
}