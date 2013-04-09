package me.xiaopan.lifespirit.task.repeat;

import java.io.Serializable;

import me.xiaopan.lifespirit.task.NextExecuteTime;
import me.xiaopan.lifespirit.task.TriggerTime;
import android.content.Context;

/**
 * 重复
 */
public abstract class Repeat implements Serializable{
	private static final long serialVersionUID = 1L;
	private RepeatWay repeatWay;
	private NextExecuteTime nextExecuteTime;
	private TriggerTime triggerTime;
	
	public Repeat(RepeatWay repeatWay){
		this.repeatWay = repeatWay;
		setTriggerTime(new TriggerTime());
		setNextExecuteTime(new NextExecuteTime());
	}
	
	/**
	 * 获取简介
	 * @param context 上下文
	 * @return 信息
	 */
	public abstract String onGetIntro(Context context);

	public RepeatWay geRepeatWay() {
		return repeatWay;
	}

	public void setRepeatWay(RepeatWay repeatWay) {
		this.repeatWay = repeatWay;
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
	
	/**
	 * 重复方式
	 */
	public enum RepeatWay{
		/**
		 * 只执行一次
		 */
		ONLY_ONE_TIME,
		/**
		 * 每隔多少分钟执行一次
		 */
		EVERY_OTHER_MINUTE,
		/**
		 * 每隔多少小时执行一次
		 */
		EVERY_OTHER_HOUR,
		/**
		 * 每隔多少天执行一次
		 */
		EVERY_OTHER_DAY,
		/**
		 * 每隔多少周执行一次
		 */
		EVERY_OTHER_WEEK,
		/**
		 * 每隔多少月执行一次
		 */
		EVERY_OTHER_MONTH,
		/**
		 * 每隔多少年执行一次
		 */
		EVERY_OTHER_YEAR,
		/**
		 * 法定工作日
		 */
		STATUTORY_WORKING_DAYS,
		/**
		 * 法定休息日
		 */
		LEGAL_AND_OFF_DAY,
	}
}