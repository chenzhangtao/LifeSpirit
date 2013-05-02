package me.xiaopan.lifespirit.task;

import android.content.Context;

/**
 * 重复
 */
public class Repeat extends TaskOption{
	private RepeatWay repeatWay = RepeatWay.ONLY_ONE_TIME;
	
	public Repeat(Context context) {
		super(context);
	}

	@Override
	public String onGetIntro() {
		if(repeatWay == RepeatWay.ONLY_ONE_TIME){
			return "只执行一次";
		}else if(repeatWay == RepeatWay.EVERYDAY){
			return "每天";
		}else if(repeatWay == RepeatWay.STATUTORY_WORKING_DAYS){
			return "法定工作日（智能跳过节假日）";
		}else if(repeatWay == RepeatWay.LEGAL_AND_OFF_DAY){
			return "法定休息日（周末和法定节假日）";
		}else if(repeatWay == RepeatWay.CUSTOM){
			return "自定义";
		}else{
			return null;
		}
	}
	
	public RepeatWay getRepeatWay() {
		return repeatWay;
	}

	public void setRepeatWay(RepeatWay repeatWay) {
		this.repeatWay = repeatWay;
	}

	/**
	 * 重复方式
	 */
	public enum RepeatWay{
		/**
		 * 只执行一次
		 */
		ONLY_ONE_TIME(0),
		/**
		 * 每天执行一次
		 */
		EVERYDAY(1),
		/**
		 * 法定工作日
		 */
		STATUTORY_WORKING_DAYS(2),
		/**
		 * 法定休息日
		 */
		LEGAL_AND_OFF_DAY(3),
		/**
		 * 自定义
		 */
		CUSTOM(4);
		
		private int index;
		
		RepeatWay(int index){
			this.index = index;
		}
		
		public int getIndex(){
			return index;
		}
	}
}