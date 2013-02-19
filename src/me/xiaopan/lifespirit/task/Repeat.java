package me.xiaopan.lifespirit.task;

import me.xiaopan.lifespirit.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;


public class Repeat extends TaskItemImpl{
	/**
	 * KEY - 表示当前任务项
	 */
	public static final String KEY = "KEY_REPEAT";
	/**
	 * KEY - 重复方式
	 */
	private static final String KEY_REPEAT_WAY = "KEY_REPEAT_WAY";
	/**
	 * 重复方式
	 */
	private RepeatWay repeatWay;
	/**
	 * 当重复方式为分钟、小时、天时长度为1，
	 * 当重复为星期时，取值范围为1(周一)-7(周日)
	 * 当重复为月时，取值范围为1(1号)-31(31号)
	 */
	private int[] values;
	
	public Repeat(Context context, Task task){
		super(context, task, context.getString(R.string.taskItem_repeat));
		setShowInTaskInfo(false);
		setNeedValiChecked(false);
		setRepeatWay(RepeatWay.EVERY_DAY);
		setValues(new int[]{1});
	}
	
	public Repeat(Context context, Task task, String repeatJSON) {
		this(context, task);
		fromJSON(repeatJSON);
	}

	@Override
	public String getHintText() {
		String hintText = "";
		//如果开启重复
		if(isChecked()){
			if(getRepeatWay() == RepeatWay.EVERY_DAY){
				hintText = getString(R.string.taskItem_repeat_everyDay) + getString(R.string.taskItem_repeat_executeOne);
			}else if(getRepeatWay() == RepeatWay.MINUTE){
				hintText = getString(R.string.taskItem_repeat_meiGei) + getValues()[0] + getString(R.string.base_minutes) + getString(R.string.taskItem_repeat_executeOne);
			}else if(getRepeatWay() == RepeatWay.HOUR){
				hintText = getString(R.string.taskItem_repeat_meiGei) + getValues()[0] + getString(R.string.base_ge) + getString(R.string.base_hours) + getString(R.string.taskItem_repeat_executeOne);
			}else if(getRepeatWay() == RepeatWay.DAY){
				hintText = getString(R.string.taskItem_repeat_meiGei) + getValues()[0] + getString(R.string.base_tian) + getString(R.string.taskItem_repeat_executeOne);
			}else if(getRepeatWay() == RepeatWay.WEEK){
				hintText = getAllSelectedWeekString();
			}else if(getRepeatWay() == RepeatWay.MONTH){
				hintText = getAllSelectedMonthString();
			}
		}else{
			hintText = getString(R.string.taskItem_repeat_text_only) + 
			getTask().getTriggerTime().getYear() + getString(R.string.base_year) + 
			getTask().getTriggerTime().getMonth() + getString(R.string.base_month) + 
			getTask().getTriggerTime().getDay() + getString(R.string.base_day) + getString(R.string.taskItem_repeat_executeOne);
		}
		return hintText;
	}
	
	@Override
	public String getShowInTaskInfoText() {
		String hintText = "";
		//如果开启重复
		if(isChecked()){
			if(getRepeatWay() == RepeatWay.EVERY_DAY){
				hintText = getString(R.string.taskItem_repeat_everyDay) + getString(R.string.taskItem_repeat_executeOne);
			}else if(getRepeatWay() == RepeatWay.MINUTE){
				hintText = getString(R.string.taskItem_repeat_meiGei) + getValues()[0] + getString(R.string.base_minutes) + getString(R.string.taskItem_repeat_executeOne);
			}else if(getRepeatWay() == RepeatWay.HOUR){
				hintText = getString(R.string.taskItem_repeat_meiGei) + getValues()[0] + getString(R.string.base_ge) + getString(R.string.base_hours) + getString(R.string.taskItem_repeat_executeOne);
			}else if(getRepeatWay() == RepeatWay.DAY){
				hintText = getString(R.string.taskItem_repeat_meiGei) + getValues()[0] + getString(R.string.base_tian) + getString(R.string.taskItem_repeat_executeOne);
			}else if(getRepeatWay() == RepeatWay.WEEK){
				hintText = getAllSelectedWeekString();
			}else if(getRepeatWay() == RepeatWay.MONTH){
				hintText = getAllSelectedMonthString();
			}
		}else{
			hintText = getString(R.string.taskItem_repeat_text_noRepeat);
		}
		return hintText;
	}

	/**
	 * 获取所有的重复选项的数组
	 * @return 所有的重复选项的数组
	 */
	public String[] getItems(){
		return new String[]{
			getString(R.string.taskItem_repeat_minute),
			getString(R.string.taskItem_repeat_hour),
			getString(R.string.taskItem_repeat_day),
			getString(R.string.taskItem_repeat_week),
			getString(R.string.taskItem_repeat_month),
			getString(R.string.taskItem_repeat_everyDay)
		};
	}

	/**
	 * 获取星期数组
	 * @return 星期数组
	 */
	public String[] getWeeks(){
		return new String[]{
			getString(R.string.base_monday),
			getString(R.string.base_tuesday),
			getString(R.string.base_wednesday),
			getString(R.string.base_thursday),
			getString(R.string.base_friday),
			getString(R.string.base_saturday),
			getString(R.string.base_sounday)
		};
	}
	
	/**
	 * 获取日份数组
	 * @return 日份数组
	 */
	public String[] getDays(){
		return new String[]{
				1+getString(R.string.base_hao),
				2+getString(R.string.base_hao),
				3+getString(R.string.base_hao),
				4+getString(R.string.base_hao),
				5+getString(R.string.base_hao),
				6+getString(R.string.base_hao),
				7+getString(R.string.base_hao),
				8+getString(R.string.base_hao),
				9+getString(R.string.base_hao),
				10+getString(R.string.base_hao),
				11+getString(R.string.base_hao),
				12+getString(R.string.base_hao),
				13+getString(R.string.base_hao),
				14+getString(R.string.base_hao),
				15+getString(R.string.base_hao),
				16+getString(R.string.base_hao),
				17+getString(R.string.base_hao),
				18+getString(R.string.base_hao),
				19+getString(R.string.base_hao),
				20+getString(R.string.base_hao),
				21+getString(R.string.base_hao),
				22+getString(R.string.base_hao),
				23+getString(R.string.base_hao),
				24+getString(R.string.base_hao),
				25+getString(R.string.base_hao),
				26+getString(R.string.base_hao),
				27+getString(R.string.base_hao),
				28+getString(R.string.base_hao),
				29+getString(R.string.base_hao),
				30+getString(R.string.base_hao),
				31+getString(R.string.base_hao),
			};
	}

	@Override
	public void execute() {}
	
	/**
	 * 根据星期的代码获取星期的名字
	 * @param weekCode 星期的代码
	 * @return 获取星期的名字
	 */
	private String getWeekName(int weekCode){
		String result = "";
		switch(weekCode){
			case 1 : result = getString(R.string.base_monday); break;
			case 2 : result = getString(R.string.base_tuesday); break;
			case 3 : result = getString(R.string.base_wednesday); break;
			case 4 : result = getString(R.string.base_thursday); break;
			case 5 : result = getString(R.string.base_friday); break;
			case 6 : result = getString(R.string.base_saturday); break;
			case 7 : result = getString(R.string.base_sounday); break;
		}
		return result;
	}
	
	/**
	 * 获取所有选中的星期的字符串
	 * @return 所有星期的字符串
	 */
	private String getAllSelectedWeekString(){
		StringBuffer sb = new StringBuffer();
		for(int w = 0; w < getValues().length-1; w++){
			sb.append(getWeekName(getValues()[w]));
			sb.append(", ");
		}
		sb.append(getWeekName(getValues()[getValues().length-1]));
		return sb.toString();
	}
	
	/**
	 * 获取所有选中的月份的字符串
	 * @return 所有月份的字符串
	 */
	private String getAllSelectedMonthString(){
		StringBuffer sb = new StringBuffer();
		for(int w = 0; w < getValues().length-1; w++){
			sb.append(getValues()[w]);
			sb.append(getString(R.string.base_hao));
			sb.append(", ");
		}
		sb.append(getValues()[getValues().length-1]);
		sb.append(getString(R.string.base_hao));
		return sb.toString();
	}
	
	public RepeatWay getRepeatWay() {
		return repeatWay;
	}

	public void setRepeatWay(RepeatWay repeatWay) {
		this.repeatWay = repeatWay;
	}

	public int[] getValues() {
		return values;
	}

	public void setValues(int[] values) {
		this.values = values;
	}
	
	public void setValues(Integer[] values) {
		this.values = new int[values.length];
		for(int w = 0; w < values.length; w++){
			this.values[w] = values[w];
		}
	}
	
	/**
	 * 重复方式
	 * @version 1.0 
	 * @author panpf
	 * @date May 18, 2012
	 */
	public enum RepeatWay{
		MINUTE(0), HOUR(1), DAY(2), WEEK(3), MONTH(4), EVERY_DAY(5);
		private int index;
		RepeatWay(int index){
			this.index = index;
		}
		public int getIndex(){
			return index;
		}
	}
	
	@Override
	public String toJSON(){
		JSONObject repeat = new JSONObject();
		try {
			repeat.put(KEY_CHECKED, isChecked());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			repeat.put(KEY_REPEAT_WAY, getRepeatWay().name());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			JSONArray jsonArray = new JSONArray();
			for(int value : getValues()){
				jsonArray.put(value);
			}
			repeat.put(KEY_VALUE, jsonArray);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return repeat.toString();
	}
	
	@Override
	public void fromJSON(String repeatJSON) {
		if(repeatJSON != null){
			try {
				JSONObject repeatIntent = new JSONObject(repeatJSON);
				try {
					setChecked(repeatIntent.getBoolean(KEY_CHECKED));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				try {
					setRepeatWay(RepeatWay.valueOf(repeatIntent.getString(KEY_REPEAT_WAY)));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				try {
					JSONArray jsonArray = repeatIntent.getJSONArray(KEY_VALUE);
					int[] values = new int[jsonArray.length()];
					for(int w = 0; w < values.length; w++){
						values[w] = jsonArray.getInt(w);
					}
					setValues(values);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}
