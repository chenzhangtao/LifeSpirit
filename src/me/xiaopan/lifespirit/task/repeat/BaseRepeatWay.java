package me.xiaopan.lifespirit.task.repeat;

import java.io.Serializable;

import android.content.Context;

public abstract class BaseRepeatWay implements Serializable{
	private static final long serialVersionUID = 1L;
	private Way way;
	
	public BaseRepeatWay( Way way){
		this.way = way;
	}
	
	/**
	 * 获取简介
	 * @param context 上下文
	 * @return 信息
	 */
	public abstract String onGetIntro(Context context);

	public Way geWay() {
		return way;
	}

	public void seWay(Way way) {
		this.way = way;
	}
	
	/**
	 * 重复方式
	 */
	public enum Way{
		/**
		 * 只执行一次
		 */
		ONLY_ONE_TIME,
		/**
		 * 每天执行一次
		 */
		EVERYDAY,
		/**
		 * 法定工作日
		 */
		STATUTORY_WORKING_DAYS,
		/**
		 * 法定休息日
		 */
		LEGAL_AND_OFF_DAY,
		/**
		 * 自定义
		 */
		CUSTOM;
	}
}