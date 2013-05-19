package me.xiaopan.lifespirit.task;

import me.xiaopan.easyjava.util.Time;
import me.xiaopan.lifespirit.task.repeatway.EveryOtherDayRepeatWay;
import me.xiaopan.lifespirit.task.repeatway.EveryOtherHourRepeatWay;
import me.xiaopan.lifespirit.task.repeatway.EveryOtherMinuteRepeatWay;
import me.xiaopan.lifespirit.task.repeatway.LegalAndOffDayRepeatWay;
import me.xiaopan.lifespirit.task.repeatway.OnlyOneTimeRepeatWay;
import me.xiaopan.lifespirit.task.repeatway.StatutoryWorkingDaysRepeatWay;
import me.xiaopan.lifespirit.util.TimeUtils;
import android.content.Context;

/**
 * 重复
 */
public class Repeat extends BaseTaskOption{
	private Time triggerTime;
	private Time nextExecuteTime;
	private Time lastExecuteTime;
	private RepeatWay repeatWay;
	private OnlyOneTimeRepeatWay onlyOneTimeRepeat;
	private StatutoryWorkingDaysRepeatWay statutoryWorkingDaysRepeat;
	private LegalAndOffDayRepeatWay legalAndOffDayRepeat;
	private EveryOtherMinuteRepeatWay everyOtherMinuteRepeat;
	private EveryOtherHourRepeatWay everyOtherHourRepeat;
	private EveryOtherDayRepeatWay everyOtherDayRepeat;
	
	public Repeat(){
		setTriggerTime(new Time());
		setOnlyOneTimeRepeat(new OnlyOneTimeRepeatWay());
		setStatutoryWorkingDaysRepeat(new StatutoryWorkingDaysRepeatWay());
		setLegalAndOffDayRepeat(new LegalAndOffDayRepeatWay());
		setEveryOtherMinuteRepeat(new EveryOtherMinuteRepeatWay());
		setEveryOtherHourRepeat(new EveryOtherHourRepeatWay());
		setEveryOtherDayRepeat(new EveryOtherDayRepeatWay());
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
		if(lastExecuteTime == null){
			lastExecuteTime = new Time();
		}
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
	 * 判断是否过过期
	 * @param currentTime 当前时间
	 * @return
	 */
	public boolean isExpiry(Time currentTime){
		//如果下次执行时间为null或者已经过期，就尝试再次更新下次执行时间
		if(nextExecuteTime == null || TimeUtils.compare(nextExecuteTime, currentTime) < 0){
			updateNextExecuteTime();
		}
		
		//如果下次执行时间不为null，并且下次执行时间大于当前时间说明尚未过期，反之就是已经过期了
		if(nextExecuteTime != null && TimeUtils.compare(nextExecuteTime, currentTime) > 0){
			return false;
		}else{
			nextExecuteTime = null;
			return true;
		}
	}
	
	/**
	 * 判断是否执行
	 * @param currentTime 当前时间
	 * @return
	 */
	public boolean isExecute(Time currentTime){
		return nextExecuteTime != null && TimeUtils.compare(nextExecuteTime, currentTime) == 0;		//如果正好到了触发点
	}

	@Override
	public void onExecute(Context context, Time currentTime) {
		lastExecuteTime = currentTime;//记录上次执行时间
		updateNextExecuteTime();//更新下次执行时间
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
	
	public OnlyOneTimeRepeatWay getOnlyOneTimeRepeat() {
		return onlyOneTimeRepeat;
	}

	public void setOnlyOneTimeRepeat(OnlyOneTimeRepeatWay onlyOneTimeRepeat) {
		this.onlyOneTimeRepeat = onlyOneTimeRepeat;
	}

	public StatutoryWorkingDaysRepeatWay getStatutoryWorkingDaysRepeat() {
		return statutoryWorkingDaysRepeat;
	}

	public void setStatutoryWorkingDaysRepeat(
			StatutoryWorkingDaysRepeatWay statutoryWorkingDaysRepeat) {
		this.statutoryWorkingDaysRepeat = statutoryWorkingDaysRepeat;
	}

	public LegalAndOffDayRepeatWay getLegalAndOffDayRepeat() {
		return legalAndOffDayRepeat;
	}

	public void setLegalAndOffDayRepeat(LegalAndOffDayRepeatWay legalAndOffDayRepeat) {
		this.legalAndOffDayRepeat = legalAndOffDayRepeat;
	}

	public EveryOtherMinuteRepeatWay getEveryOtherMinuteRepeat() {
		return everyOtherMinuteRepeat;
	}

	public void setEveryOtherMinuteRepeat(
			EveryOtherMinuteRepeatWay everyOtherMinuteRepeat) {
		this.everyOtherMinuteRepeat = everyOtherMinuteRepeat;
	}

	public EveryOtherHourRepeatWay getEveryOtherHourRepeat() {
		return everyOtherHourRepeat;
	}

	public void setEveryOtherHourRepeat(EveryOtherHourRepeatWay everyOtherHourRepeat) {
		this.everyOtherHourRepeat = everyOtherHourRepeat;
	}

	public EveryOtherDayRepeatWay getEveryOtherDayRepeat() {
		return everyOtherDayRepeat;
	}

	public void setEveryOtherDayRepeat(EveryOtherDayRepeatWay everyOtherDayRepeat) {
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