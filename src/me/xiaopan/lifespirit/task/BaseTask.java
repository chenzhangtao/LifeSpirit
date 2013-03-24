package me.xiaopan.lifespirit.task;

import android.content.Context;

/**
 * 任务基类
 */
public abstract class BaseTask {
	private Context context;
	private boolean enable;
	private CreateTime createTime;
	private NextExecuteTime nextExecuteTime;
	private TriggerTime triggerTime;
	private Repeat repeat;
	private String name;
	
	/**
	 * 创建一个任务
	 * @param context 上下文
	 */
	public BaseTask(Context context){
		setContext(context);
		setEnable(true);
		setCreateTime(new CreateTime());
	}
	
	/**
	 * 获取简介
	 * @return 信息
	 */
	public abstract String getIntro();
	
	/**
	 * 执行
	 */
	public void execute(){
		
	}
	
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}