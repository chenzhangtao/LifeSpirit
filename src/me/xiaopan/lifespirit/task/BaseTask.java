package me.xiaopan.lifespirit.task;

import java.io.Serializable;

import android.content.Context;


/**
 * 任务基类
 */
public abstract class BaseTask implements Serializable{
	private static final long serialVersionUID = 1L;
	private boolean enable;
	private CreateTime createTime;
	private Name name;
	private Repeat repeat;
	
	/**
	 * 创建一个任务
	 */
	public BaseTask(){
		setEnable(true);
		setCreateTime(new CreateTime());
		setName(new Name());
		setRepeat(new Repeat());
	}
	
	/**
	 * 将当前任务保存到本地
	 * @param context 上下文
	 * @return true：保存成功；false：保存失败
	 */
	public abstract boolean saveToLocal(Context context);
	
	/**
	 * 读取所有的任务
	 * @param context 上下文
	 * @return
	 */
	public abstract BaseTask[] readTasks(Context context);
	
	/**
	 * 获取简介
	 * @param context 上下文
	 * @return 信息
	 */
	public abstract String onGetIntro(Context context);
	
	/**
	 * 执行
	 * @param context 上下文
	 */
	public abstract void onExecute(Context context);
	
	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public CreateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(CreateTime createTime) {
		this.createTime = createTime;
	}

	public Repeat getRepeat() {
		return repeat;
	}

	public void setRepeat(Repeat repeat) {
		this.repeat = repeat;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}
}