package me.xiaopan.lifespirit.util;

import me.xiaopan.androidlibrary.util.AndroidUtils;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnShowListener;
import android.widget.EditText;

public class Utils {
	
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
	}
}