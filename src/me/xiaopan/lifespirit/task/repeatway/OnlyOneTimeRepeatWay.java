package me.xiaopan.lifespirit.task.repeatway;

import me.xiaopan.easyjava.util.Time;
import me.xiaopan.lifespirit.task.Repeat;
import me.xiaopan.lifespirit.util.TimeUtils;
import me.xiaopan.lifespirit2.R;
import android.content.Context;

/**
 * 只执行一次
 */
public class OnlyOneTimeRepeatWay extends RepeatWayInterface{
	@Override
	public String getIntro(Context context, Repeat repeat) {
		return context.getString(
				R.string.repeat_onlyOneTime, 
				(
						repeat.getTriggerTime().getYear()+context.getString(R.string.base_year)+
						(repeat.getTriggerTime().getMonth()+1)+context.getString(R.string.base_month)+
						repeat.getTriggerTime().getDayOfMonth()+context.getString(R.string.base_day)
				)
		);
	}

	@Override
	public Time getNextExecuteTime(Repeat repeat) {
		//如果当前任务尚未过期
		if(TimeUtils.compare(repeat.getTriggerTime(), new Time()) > 0){
			return repeat.getTriggerTime();
		}else{
			return null;
		}
	}
}