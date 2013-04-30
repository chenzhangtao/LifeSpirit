package me.xiaopan.lifespirit.task;

import me.xiaopan.javalibrary.util.Time;
import me.xiaopan.lifespirit.task.repeat.EveryOtherDayRepeat;
import me.xiaopan.lifespirit.task.repeat.EveryOtherHourRepeat;
import me.xiaopan.lifespirit.task.repeat.EveryOtherMinuteRepeat;
import me.xiaopan.lifespirit.task.repeat.LegalAndOffDayRepeat;
import me.xiaopan.lifespirit.task.repeat.OnlyOneTimeRepeat;
import me.xiaopan.lifespirit.task.repeat.StatutoryWorkingDaysRepeat;
import android.content.Context;

/**
 * 重复
 */
public class Repeat extends BaseTaskOption{
	private Time triggerTime;
	private Time nextExecuteTime;
	private Time lastExecuteTime;
	private RepeatWay repeatWay;
	private OnlyOneTimeRepeat onlyOneTimeRepeat;
	private StatutoryWorkingDaysRepeat statutoryWorkingDaysRepeat;
	private LegalAndOffDayRepeat legalAndOffDayRepeat;
	private EveryOtherMinuteRepeat everyOtherMinuteRepeat;
	private EveryOtherHourRepeat everyOtherHourRepeat;
	private EveryOtherDayRepeat everyOtherDayRepeat;
	
	public Repeat(){
		setTriggerTime(new Time());
		setOnlyOneTimeRepeat(new OnlyOneTimeRepeat());
		setStatutoryWorkingDaysRepeat(new StatutoryWorkingDaysRepeat());
		setLegalAndOffDayRepeat(new LegalAndOffDayRepeat());
		setEveryOtherMinuteRepeat(new EveryOtherMinuteRepeat());
		setEveryOtherHourRepeat(new EveryOtherHourRepeat());
		setEveryOtherDayRepeat(new EveryOtherDayRepeat());
		setRepeatWay(RepeatWay.ONLY_ONE_TIME);
	}
	
	/**
	 * 获取简介
	 * @param context 上下文
	 * @return 信息
	 */
	@Override
	public String getIntro(Context context){
		if(repeatWay == RepeatWay.ONLY_ONE_TIME){
			return onlyOneTimeRepeat.getIntro(context, this);
		}else if(repeatWay == RepeatWay.STATUTORY_WORKING_DAYS){
			return statutoryWorkingDaysRepeat.getIntro(context, this);
		}else if(repeatWay == RepeatWay.LEGAL_AND_OFF_DAY){
			return legalAndOffDayRepeat.getIntro(context, this);
		}else if(repeatWay == RepeatWay.EVERY_OTHER_DAY){
			return everyOtherDayRepeat.getIntro(context, this);
		}else if(repeatWay == RepeatWay.EVERY_OTHER_HOUR){
			return everyOtherHourRepeat.getIntro(context, this);
		}else if(repeatWay == RepeatWay.EVERY_OTHER_MINUTE){
			return everyOtherMinuteRepeat.getIntro(context, this);
		}else{
			return null;
		}
	}
	
	/**
	 * 更新下次执行时间
	 * @return true：更新成功；false：更新失败，任务已经终止
	 */
	public boolean updateNextExecuteTime(){
		if(repeatWay == RepeatWay.ONLY_ONE_TIME){
			setNextExecuteTime(onlyOneTimeRepeat.getNextExecuteTime(this));
		}else if(repeatWay == RepeatWay.STATUTORY_WORKING_DAYS){
			setNextExecuteTime(statutoryWorkingDaysRepeat.getNextExecuteTime(this));
		}else if(repeatWay == RepeatWay.LEGAL_AND_OFF_DAY){
			setNextExecuteTime(legalAndOffDayRepeat.getNextExecuteTime(this));
		}else if(repeatWay == RepeatWay.EVERY_OTHER_DAY){
			setNextExecuteTime(everyOtherDayRepeat.getNextExecuteTime(this));
		}else if(repeatWay == RepeatWay.EVERY_OTHER_HOUR){
			setNextExecuteTime(everyOtherHourRepeat.getNextExecuteTime(this));
		}else if(repeatWay == RepeatWay.EVERY_OTHER_MINUTE){
			setNextExecuteTime(everyOtherMinuteRepeat.getNextExecuteTime(this));
		}else{
			setNextExecuteTime(null);
		}
		return getNextExecuteTime() != null;
	}
	
	/**
	 * 判断是否执行
	 * @param currentTime 当前时间
	 * @return
	 */
	public boolean isExecute(Time currentTime){
		if(repeatWay == RepeatWay.ONLY_ONE_TIME){
			return onlyOneTimeRepeat.isExecute(this, currentTime);
		}else if(repeatWay == RepeatWay.STATUTORY_WORKING_DAYS){
			return statutoryWorkingDaysRepeat.isExecute(this, currentTime);
		}else if(repeatWay == RepeatWay.LEGAL_AND_OFF_DAY){
			return legalAndOffDayRepeat.isExecute(this, currentTime);
		}else if(repeatWay == RepeatWay.EVERY_OTHER_DAY){
			return everyOtherDayRepeat.isExecute(this, currentTime);
		}else if(repeatWay == RepeatWay.EVERY_OTHER_HOUR){
			return everyOtherHourRepeat.isExecute(this, currentTime);
		}else if(repeatWay == RepeatWay.EVERY_OTHER_MINUTE){
			return everyOtherMinuteRepeat.isExecute(this, currentTime);
		}else{
			return false;
		}
	}

	@Override
	public void onExecute(Context context, Time currentTime) {
		lastExecuteTime = currentTime;
	}

	public RepeatWay getRepeatWay() {
		return repeatWay;
	}

	public void setRepeatWay(RepeatWay repeatWay) {
		this.repeatWay = repeatWay;
	}

	public Time getNextExecuteTime() {
		return nextExecuteTime;
	}

	public void setNextExecuteTime(Time nextExecuteTime) {
		this.nextExecuteTime = nextExecuteTime;
	}

	public Time getLastExecuteTime() {
		return lastExecuteTime;
	}

	public void setLastExecuteTime(Time lastExecuteTime) {
		this.lastExecuteTime = lastExecuteTime;
	}

	public Time getTriggerTime() {
		return triggerTime;
	}

	public void setTriggerTime(Time triggerTime) {
		this.triggerTime = triggerTime;
	}
	
	public OnlyOneTimeRepeat getOnlyOneTimeRepeat() {
		return onlyOneTimeRepeat;
	}

	public void setOnlyOneTimeRepeat(OnlyOneTimeRepeat onlyOneTimeRepeat) {
		this.onlyOneTimeRepeat = onlyOneTimeRepeat;
	}

	public StatutoryWorkingDaysRepeat getStatutoryWorkingDaysRepeat() {
		return statutoryWorkingDaysRepeat;
	}

	public void setStatutoryWorkingDaysRepeat(
			StatutoryWorkingDaysRepeat statutoryWorkingDaysRepeat) {
		this.statutoryWorkingDaysRepeat = statutoryWorkingDaysRepeat;
	}

	public LegalAndOffDayRepeat getLegalAndOffDayRepeat() {
		return legalAndOffDayRepeat;
	}

	public void setLegalAndOffDayRepeat(LegalAndOffDayRepeat legalAndOffDayRepeat) {
		this.legalAndOffDayRepeat = legalAndOffDayRepeat;
	}

	public EveryOtherMinuteRepeat getEveryOtherMinuteRepeat() {
		return everyOtherMinuteRepeat;
	}

	public void setEveryOtherMinuteRepeat(
			EveryOtherMinuteRepeat everyOtherMinuteRepeat) {
		this.everyOtherMinuteRepeat = everyOtherMinuteRepeat;
	}

	public EveryOtherHourRepeat getEveryOtherHourRepeat() {
		return everyOtherHourRepeat;
	}

	public void setEveryOtherHourRepeat(EveryOtherHourRepeat everyOtherHourRepeat) {
		this.everyOtherHourRepeat = everyOtherHourRepeat;
	}

	public EveryOtherDayRepeat getEveryOtherDayRepeat() {
		return everyOtherDayRepeat;
	}

	public void setEveryOtherDayRepeat(EveryOtherDayRepeat everyOtherDayRepeat) {
		this.everyOtherDayRepeat = everyOtherDayRepeat;
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
		 * 法定工作日
		 */
		STATUTORY_WORKING_DAYS,
		/**
		 * 法定休息日
		 */
		LEGAL_AND_OFF_DAY,
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
		EVERY_OTHER_DAY;
	}
}