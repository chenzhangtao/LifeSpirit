package me.xiaopan.lifespirit.task;

import me.xiaopan.lifespirit.task.repeat.Repeat;
import me.xiaopan.lifespirit.task.repeat.OnlyOneTimeRepeat;
import android.content.Context;


/**
 * 任务基类
 */
public abstract class BaseTask {
	private boolean enable;
	private CreateTime createTime;
	private NextExecuteTime nextExecuteTime;
	private TriggerTime triggerTime;
	private Name name;
	private Repeat repeat;
	
	/**
	 * 创建一个任务
	 */
	public BaseTask(){
		setEnable(true);
		setCreateTime(new CreateTime());
		setTriggerTime(new TriggerTime());
		setNextExecuteTime(new NextExecuteTime());
		setName(new Name());
		setRepeat(new OnlyOneTimeRepeat());
	}
	
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

	public NextExecuteTime getNextExecuteTime() {
		return nextExecuteTime;
	}

	public void setNextExecuteTime(NextExecuteTime nextExecuteTime) {
		this.nextExecuteTime = nextExecuteTime;
	}

	public TriggerTime getTriggerTime() {
		return triggerTime;
	}

	public void setTriggerTime(TriggerTime triggerTime) {
		this.triggerTime = triggerTime;
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