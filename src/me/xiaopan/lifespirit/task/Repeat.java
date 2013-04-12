package me.xiaopan.lifespirit.task;

import java.io.Serializable;

import me.xiaopan.lifespirit.task.repeat.EveryOtherDayRepeat;
import me.xiaopan.lifespirit.task.repeat.EveryOtherHourRepeat;
import me.xiaopan.lifespirit.task.repeat.EveryOtherMinuteRepeat;
import me.xiaopan.lifespirit.task.repeat.LegalAndOffDayRepeat;
import me.xiaopan.lifespirit.task.repeat.OnlyOneTimeRepeat;
import me.xiaopan.lifespirit.task.repeat.StatutoryWorkingDaysRepeat;
import android.content.Context;

public class Repeat implements Serializable{
	private static final long serialVersionUID = 1L;
	private RepeatWay repeatWay;
	private TriggerTime triggerTime;
	private NextExecuteTime nextExecuteTime;
	private OnlyOneTimeRepeat onlyOneTimeRepeat;
	private StatutoryWorkingDaysRepeat statutoryWorkingDaysRepeat;
	private LegalAndOffDayRepeat legalAndOffDayRepeat;
	private EveryOtherMinuteRepeat everyOtherMinuteRepeat;
	private EveryOtherHourRepeat everyOtherHourRepeat;
	private EveryOtherDayRepeat everyOtherDayRepeat;
	
	public Repeat(){
		setRepeatWay(RepeatWay.ONLY_ONE_TIME);
		setTriggerTime(new TriggerTime());
		setNextExecuteTime(new NextExecuteTime());
		setOnlyOneTimeRepeat(new OnlyOneTimeRepeat());
		setStatutoryWorkingDaysRepeat(new StatutoryWorkingDaysRepeat());
		setLegalAndOffDayRepeat(new LegalAndOffDayRepeat());
		setEveryOtherMinuteRepeat(new EveryOtherMinuteRepeat());
		setEveryOtherHourRepeat(new EveryOtherHourRepeat());
		setEveryOtherDayRepeat(new EveryOtherDayRepeat());
	}
	
	/**
	 * 获取简介
	 * @param context 上下文
	 * @return 信息
	 */
	public String onGetIntro(Context context){
		if(repeatWay == RepeatWay.ONLY_ONE_TIME){
			return onlyOneTimeRepeat.onGetIntro(context, this);
		}else if(repeatWay == RepeatWay.STATUTORY_WORKING_DAYS){
			return statutoryWorkingDaysRepeat.onGetIntro(context, this);
		}else if(repeatWay == RepeatWay.LEGAL_AND_OFF_DAY){
			return legalAndOffDayRepeat.onGetIntro(context, this);
		}else if(repeatWay == RepeatWay.EVERY_OTHER_DAY){
			return everyOtherDayRepeat.onGetIntro(context, this);
		}else if(repeatWay == RepeatWay.EVERY_OTHER_HOUR){
			return everyOtherHourRepeat.onGetIntro(context, this);
		}else if(repeatWay == RepeatWay.EVERY_OTHER_MINUTE){
			return everyOtherMinuteRepeat.onGetIntro(context, this);
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