package me.xiaopan.lifespirit.task;

import android.content.Context;

public abstract class TaskItemImpl implements TaskItem {
	private Context context;
	private BaseTask task;
	private boolean exist;
	private boolean checked;
	private String mainText;
	private boolean showCheckBox;
	private boolean open;
	private int value;
	private String content;
	private boolean showInTaskInfo;
	private boolean needValiChecked;
	
	public TaskItemImpl(Context context, BaseTask task, String taskItemName){
		setContext(context);
		setTask(task);
		setMainText(taskItemName);
		setShowCheckBox(true);
		setExist(true);
		setShowInTaskInfo(true);
		setNeedValiChecked(true);
	}

	@Override
	public Context getContext() {
		return context;
	}
	
	@Override
	public void setContext(Context context) {
		this.context = context;
	}
	
	@Override
	public BaseTask getTask() {
		return task;
	}

	@Override
	public void setTask(BaseTask task) {
		this.task = task;
	}

	@Override
	public boolean isExist() {
		return exist;
	}

	@Override
	public void setExist(boolean exist) {
		this.exist = exist;
	}

	@Override
	public boolean isChecked() {
		return checked;
	}

	@Override
	public void setChecked(boolean startUsing) {
		this.checked = startUsing;
	}

	@Override
	public String getMainText() {
		return mainText;
	}

	@Override
	public void setMainText(String mainText) {
		this.mainText = mainText;
	}

	@Override
	public void setHintText(String hintText) {}

	@Override
	public String getShowInTaskInfoText() {
		return getHintText() == null?getMainText():getMainText() +"("+getHintText()+")";
	}

	@Override
	public void setShowInTaskInfoText(String listHintText) {}

	@Override
	public boolean isShowCheckBox() {
		return showCheckBox;
	}

	@Override
	public void setShowCheckBox(boolean showCheckedBox) {
		this.showCheckBox = showCheckedBox;
	}
	
	public String getString(int resId){
		return getContext().getString(resId);
	}
	
	@Override
	public boolean isOpen() {
		return open;
	}
	
	@Override
	public void setOpen(boolean open) {
		this.open = open;
	}

	@Override
	public int getValue() {
		return value;
	}

	@Override
	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String getContent() {
		return content;
	}

	@Override
	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public boolean isShowInTaskInfo() {
		return showInTaskInfo;
	}

	@Override
	public void setShowInTaskInfo(boolean showInTaskInfo) {
		this.showInTaskInfo = showInTaskInfo;
	}

	@Override
	public boolean isNeedValiChecked() {
		return needValiChecked;
	}

	@Override
	public void setNeedValiChecked(boolean needValiChecked) {
		this.needValiChecked = needValiChecked;
	}
}
