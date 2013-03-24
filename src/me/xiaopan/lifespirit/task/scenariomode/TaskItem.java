package me.xiaopan.lifespirit.task.scenariomode;

import me.xiaopan.lifespirit.task.BaseTask;
import android.content.Context;

public interface TaskItem{
	/**
	 * KEY - 表示该任务项是否被选中或者当前任务是否开启
	 */
	String KEY_CHECKED = "KEY_CHECKED";
	/**
	 * KEY - 标示该任务项的操作是关闭还是开启
	 */
	String KEY_OPEN = "KEY_OPEN";
	/**
	 * KEY - 表示该任务项的int值
	 */
	String KEY_VALUE = "KEY_VALUE";
	/**
	 * KEY - 表示该任务项的String值
	 */
	String KEY_CONTENT = "KEY_CONTENT";
	
	/**
	 * 获取上下文
	 * @return 上下文
	 */
	public Context getContext();
	
	/**
	 * 设置上下文
	 * @param context 上下文
	 */
	public void setContext(Context context);
	
	/**
	 * 获取任务
	 * @return 任务
	 */
	public BaseTask getTask();

	/**
	 * 设置任务
	 * @param task 任务
	 */
	public void setTask(BaseTask task);
	
	/**
	 * 判断是否存在当前项
	 * @return 是否存在当前项
	 */
	public boolean isExist();
	
	/**
	 * 设置是否存在当前项
	 * @param exist 是否存在当前项
	 */
	public void setExist(boolean exist);

	/**
	 * 判断该项是否被选中
	 * @return 该项是否被选中
	 */
	public boolean isChecked();
	
	/**
	 * 设置该项是否被选中
	 * @param isChecked 该项是否被选中
	 */
	public void setChecked(boolean isChecked);
	
	/**
	 * 获取要在任务项列表上显示的主文字
	 * @return 要在任务项列表上显示的主文字
	 */
	public String getMainText();

	/**
	 * 设置要在任务项列表上显示的主文字
	 * @param mainText 要在任务项列表上显示的主文字
	 */
	public void setMainText(String mainText);

	/**
	 * 获取要在任务项列表上显示的提示文字
	 * @return 要在任务项列表上显示的提示文字
	 */
	public String getHintText();
	
	/**
	 * 设置要在任务项列表上显示的提示文字
	 * @param hintText 要在任务项列表上显示的提示文字
	 */
	public void setHintText(String hintText);

	/**
	 * 获取要显示在任务信息中的内容
	 * @return 要显示在任务信息中的内容
	 */
	public String getShowInTaskInfoText();
	
	/**
	 * 设置要显示在任务信息中的内容
	 * @param showInTaskInfoText 要显示在任务信息中的内容
	 */
	public void setShowInTaskInfoText(String showInTaskInfoText);

	/**
	 * 判断是否显示用于选中当前任务项的复选框
	 * @return 是否显示用于选中当前任务项的复选框
	 */
	public boolean isShowCheckBox();
	
	/**
	 * 设置是否显示用于选中当前任务项的复选框
	 * @param showCheckedBox 是否显示用于选中当前任务项的复选框
	 */
	public void setShowCheckBox(boolean showCheckedBox);

	/**
	 * 判断是否开启
	 * @return 是否开启
	 */
	public boolean isOpen();

	/**
	 * 设置开启 
	 * @param open 开启
	 */
	public void setOpen(boolean open);

	/**
	 * 获取值
	 * @return 值
	 */
	public int getValue();

	/**
	 * 设置值
	 * @param value
	 */
	public void setValue(int value);
	
	/**
	 * 获取内容
	 * @return 内容
	 */
	public String getContent();
	
	/**
	 * 设置内容
	 * @param content 内容
	 */
	public void setContent(String content);
	
	/**
	 * 执行
	 */
	public void execute();
	
	/**
	 * 将当前对象转换成一JSON字符串
	 * @return JSON字符串
	 */
	public String toJSON();
	
	/**
	 * 从给定的json字符串中解析出当前对象需要的属性
	 * @param json 给定的json字符串
	 */
	public void fromJSON(String json);
	
	/**
	 * 判断当前项是否需要显示在任务信息上
	 * @return 当前项是否需要显示在任务信息上
	 */
	public boolean isShowInTaskInfo();
	
	/**
	 * 设置当前项是否需要显示在任务信息上
	 * @param isShowInTaskInfo 当前项是否需要显示在任务信息上
	 */
	public void setShowInTaskInfo(boolean isShowInTaskInfo);
	
	/**
	 * 判断当前任务项是否需要验证是否被选中
	 * @return 当前任务项是否需要验证是否被选中
	 */
	public boolean isNeedValiChecked();
	
	/**
	 * 设置当前任务项是否需要验证是否被选中
	 * @param needValiChecked 当前任务项是否需要验证是否被选中
	 */
	public void setNeedValiChecked(boolean needValiChecked);
}
