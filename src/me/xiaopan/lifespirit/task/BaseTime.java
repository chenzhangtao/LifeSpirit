package me.xiaopan.lifespirit.task;

import me.xiaopan.javalibrary.util.Time;

public abstract class BaseTime extends Time{
	
	public BaseTime(long milliseconds) {
		super(milliseconds);
	}
	
	public BaseTime() {
		super();
	}
	
	/**
	 * 比较小时和分钟
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static int compareHourAndMinute(BaseTime time1, BaseTime time2){
		int result = time1.getHour() - time2.getHour();
		if(result == 0){
			result = time1.getMinute() - time2.getMinute();
		}
		return result;
	}
	
	/**
	 * 比较时间2跟时间2的大小
	 * @param year1 时间1的年份
	 * @param month1 时间1的月份
	 * @param dayOfMonth1 时间1的日份
	 * @param hourOfDay1 时间1的小时
	 * @param minute1 时间1的分钟
	 * @param year2 时间2的年份
	 * @param month2 时间2的月份
	 * @param dayOfMonth2 日份
	 * @param hourOfDay2 小时
	 * @param minute2 分钟
	 * @return 大于0：时间1大于时间2；等于0：两者相等；小于0：时间1小于时间2
	 */
	public static int compareTime(int year1, int month1, int dayOfMonth1, int hourOfDay1, int minute1, int year2, int month2, int dayOfMonth2, int hourOfDay2, int minute2){
		int result = 0;
		if(year1 == year2){
			if(month1 == month2){
				if(dayOfMonth1 == dayOfMonth2){
					if(hourOfDay1 == hourOfDay2){
						if(minute1 == minute2){
							result = 0;
						}else{
							result = minute1 - minute2;
						}
					}else{
						result = hourOfDay1 - hourOfDay2;
					}
				}else{
					result = dayOfMonth1 - dayOfMonth2;
				}
			}else{
				result = month1 - month2;
			}
		}else{
			result = year1 - year2;
		}
		return result;
	}
	
	public static int compareTime(BaseTime time1, BaseTime time2){
		return compareTime(time1.getYear(), time1.getMonth(), time1.getDayOfMonth(), time1.getHourOfDay(), time1.getMinute(), time2.getYear(), time2.getMonth(), time2.getDayOfMonth(), time2.getHourOfDay(), time2.getMinute());
	}
}