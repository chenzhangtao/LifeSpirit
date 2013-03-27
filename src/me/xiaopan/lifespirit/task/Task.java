package me.xiaopan.lifespirit.task;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import me.xiaopan.javalibrary.util.DateTimeUtils;
import me.xiaopan.javalibrary.util.FileUtils;
import me.xiaopan.lifespirit.other.Repeat;
import me.xiaopan.lifespirit.other.TaskItem;
import me.xiaopan.lifespirit.other.TaskName;
import me.xiaopan.lifespirit.other.Time;
import me.xiaopan.lifespirit.other.TriggerTime;
import me.xiaopan.lifespirit.other.Repeat.RepeatWay;
import me.xiaopan.lifespirit.task.scenariomode.AirplaneMode;
import me.xiaopan.lifespirit.task.scenariomode.Alarm;
import me.xiaopan.lifespirit.task.scenariomode.Bluetooth;
import me.xiaopan.lifespirit.task.scenariomode.MobileNetwork;
import me.xiaopan.lifespirit.task.scenariomode.NextExecuteTime;
import me.xiaopan.lifespirit.task.scenariomode.OpenApplication;
import me.xiaopan.lifespirit.task.scenariomode.RingnoteMode;
import me.xiaopan.lifespirit.task.scenariomode.ScreenBrightness;
import me.xiaopan.lifespirit.task.scenariomode.ScreenDormant;
import me.xiaopan.lifespirit.task.scenariomode.SendMessage;
import me.xiaopan.lifespirit.task.scenariomode.Volume;
import me.xiaopan.lifespirit.task.scenariomode.Wifi;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

/**
 * 任务类
 * @version 1.0 
 * @author panpf
 * @date Apr 30, 2012
 */
public class Task {
	public static final String KEY = "TASK";
	private static final String KEY_CREATE_TIME = "KEY_CREATE_TIME";
	private Context context;
	private boolean open;
	private long createTime;
	private NextExecuteTime nextExecuteTime;
	private List<TaskItem> taskItemList;
	private String taskInfo;
	
	private TaskName taskName;
	private TriggerTime triggerTime;
	private Repeat repeat;
	
	private Bluetooth bluetooth;
	private AirplaneMode airplaneMode;
	private MobileNetwork mobileNetwork;
	private ScreenBrightness screenBrightness;
	private ScreenDormant screenDormant;
	private SendMessage sendMessage;
	private Wifi wifi;
	private Volume volume;
	private RingnoteMode ringnoteMode;
	private OpenApplication openApplication;
	private Alarm alarm;
	
	/**
	 * 创建一个任务
	 * @param context 上下文
	 */
	public Task(Context context){
		setContext(context);
		setOpen(true);
		setCreateTime(System.currentTimeMillis());
		setTriggerTime(new TriggerTime(getContext(), this));
		setNextExecuteTime(new NextExecuteTime(getContext(), this));
		setTaskName(new TaskName(getContext(), this));
		setRepeat(new Repeat(getContext(), this));
		setBluetooth(new Bluetooth(getContext(), this));
		setAirplaneMode(new AirplaneMode(getContext(), this));
		setMobileNetwork(new MobileNetwork(getContext(), this));
		setScreenBrightness(new ScreenBrightness(getContext(), this));
		setScreenDormant(new ScreenDormant(getContext(), this));
		setSendMessage(new SendMessage(getContext(), this));
		setWifi(new Wifi(getContext(), this));
		setVolume(new Volume(getContext(), this));
		setRingnoteMode(new RingnoteMode(getContext(), this));
		setOpenApplication(new OpenApplication(getContext(), this));
		setAlarm(new Alarm(getContext(), this));
	}
	
	/**
	 * 创建一个任务
	 * @param context 上下文
	 * @param taskJSON 描述任务的JSON字符串
	 * @throws JSONException
	 */
	public Task(Context context, String taskJSON) throws JSONException{
		setContext(context);
		fromJSON(taskJSON);
	}
	
	/**
	 * 获取任务信息
	 * @return 任务信息
	 */
	public String getTaskInfo(){
		if(taskInfo == null){
			StringBuffer stringBuffer = new StringBuffer();
			//循环遍历所有任务项，将符合条件的任务项添加到任务信息中
			for(TaskItem taskItem : getTaskItemList()){
				if(taskItem.isShowInTaskInfo() && taskItem.isChecked()){
					stringBuffer.append(taskItem.getShowInTaskInfoText());
					stringBuffer.append(", ");
				}
			}
			//如果结果是空的
			if(stringBuffer.length() <= 0){
				stringBuffer.append("");
			}else{
				stringBuffer.delete(stringBuffer.length()-2, stringBuffer.length());
			}
			taskInfo = stringBuffer.toString();
		}
		return taskInfo;
	}
	
	/**
	 * 执行
	 */
	public void execute(){
		if(isOpen()){
			getBluetooth().execute();
			getMobileNetwork().execute();
			getWifi().execute();
			getSendMessage().execute();
			getScreenBrightness().execute();
			getScreenDormant().execute();
			getVolume().execute();
			getRingnoteMode().execute();
			getOpenApplication().execute();
			getAirplaneMode().execute();
			getAlarm().execute();
			
			//计算下次执行时间
			updateNextExecuteTime();
			
			//如果已经过期，就关闭任务
			if(isPastDue()){
				setOpen(false);
			}
			
			//写入本地
			try {
				writer();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 将当前任务写出去
	 * @throws IOException
	 */
	public void writer() throws IOException{
		File file = FileUtils.createFile(getContext().getFilesDir(), getCreateTime()+".task", true);
		try {
			FileUtils.writeString(file, toJSON(), false);
		} catch (IOException e) {
			e.printStackTrace();
			file.delete();
			throw e;
		}
	}
	
	/**
	 * 判断除了重复之外是否有选中其他项
	 * @return 除了重复之外是否有选中其他项
	 */
	public boolean isHaveOperation(){
		boolean result = false;
		for(TaskItem taskItem : getTaskItemList()){
			if(taskItem.isNeedValiChecked() && taskItem.isChecked()){
				result = true;
				break;
			}
		}
		return result;
	}
	
	/**
	 * 更新下一次执行时间
	 * @param currYear 当前年
	 * @param currMonth 当前月
	 * @param currDay 当前日
	 * @param currHour 当前小时
	 * @param currMinute 当前分钟
	 */
	public void updateNextExecuteTime(int currYear, int currMonth, int currDay, int currHour, int currMinute){
		//如果下次执行时间小于等于当前时间，说明下次执行时间已经过期需要更新
		int nextExecuteYear = getNextExecuteTime().getYear();
		int nextExecuteMonth = getNextExecuteTime().getMonth();
		int nextExecuteDay = getNextExecuteTime().getDay();
		int nextExecuteHour = getNextExecuteTime().getHour();
		int nextExecuteMinute = getNextExecuteTime().getMinute();
		
		//如果开启了重复
		if(getRepeat().isChecked()){
			//创建日历对象
			GregorianCalendar gc = new GregorianCalendar(nextExecuteYear, nextExecuteMonth-1, nextExecuteDay, nextExecuteHour, nextExecuteMinute);
			
			//如果重复方式为每天、按分钟、按小时、按天其中之一
			if(getRepeat().getRepeatWay() == RepeatWay.EVERY_DAY || getRepeat().getRepeatWay() == RepeatWay.MINUTE || getRepeat().getRepeatWay() == RepeatWay.HOUR || getRepeat().getRepeatWay() == RepeatWay.DAY){
				//接下来要改变的字段，默认为日份
				int field = Calendar.DAY_OF_MONTH;
				if(getRepeat().getRepeatWay() == RepeatWay.MINUTE){
					field = Calendar.MINUTE;
				}else if(getRepeat().getRepeatWay() == RepeatWay.HOUR){
					field = Calendar.HOUR_OF_DAY;
				}
				
				//如果下次执行时间小于或者等于当前时间
				boolean running = true;
				while(running){
					//设置年月日时分字段
					nextExecuteYear = gc.get(Calendar.YEAR);
					nextExecuteMonth = gc.get(Calendar.MONTH) + 1;
					nextExecuteDay = gc.get(Calendar.DAY_OF_MONTH);
					nextExecuteHour = gc.get(Calendar.HOUR_OF_DAY);
					nextExecuteMinute = gc.get(Calendar.MINUTE);
					//如果刚刚更新过的下次执行时间大于当前时间，结束
					if(Time.contrastTime(nextExecuteYear, nextExecuteMonth, nextExecuteDay, nextExecuteHour, nextExecuteMinute, currYear, currMonth, currDay, currHour, currMinute) > 0){
						running = false;
					}else{
						//改变相关字段
						gc.add(field, getRepeat().getValues()[0]);
					}
				}
			}else if(getRepeat().getRepeatWay() == RepeatWay.WEEK || getRepeat().getRepeatWay() == RepeatWay.MONTH){
				boolean running = true;
				while(running){
					int value = 0;
					if(getRepeat().getRepeatWay() == RepeatWay.WEEK){
						//获取星期
						value = gc.get(Calendar.DAY_OF_WEEK) - 1;
						//如果是0，将换成7，因为西方的习惯0标识周日，而东方的习惯7表示周日
						if(value == 0){
							value = 7;
						}
					}else{
						value = gc.get(Calendar.DAY_OF_MONTH);
					}

					nextExecuteYear = gc.get(Calendar.YEAR);
					nextExecuteMonth = gc.get(Calendar.MONTH) + 1;
					nextExecuteDay = gc.get(Calendar.DAY_OF_MONTH);
					nextExecuteHour = gc.get(Calendar.HOUR_OF_DAY);
					nextExecuteMinute = gc.get(Calendar.MINUTE);
					
					//循环判断当前日份是否是循环的日份之一
					for(int currentValue : getRepeat().getValues()){
						Log.i(getTaskName().getContent(), "WEEK: value="+value+",currentValue="+currentValue);
						if(value == currentValue && Time.contrastTime(nextExecuteYear, nextExecuteMonth, nextExecuteDay, nextExecuteHour, nextExecuteMinute, currYear, currMonth, currDay, currHour, currMinute) > 0){
							running = false;
							break;
						}
					}
					
					if(running){
						gc.add(Calendar.DAY_OF_MONTH, 1);
					}
				}
			}
		}
		getNextExecuteTime().setYear(nextExecuteYear);
		getNextExecuteTime().setMonth(nextExecuteMonth);
		getNextExecuteTime().setDay(nextExecuteDay);
		getNextExecuteTime().setHour(nextExecuteHour);
		getNextExecuteTime().setMinute(nextExecuteMinute);
	}
	
	/**
	 * 更新当前任务的下次执行时间
	 */
	public void updateNextExecuteTime(){
		int[] currentTimesBy24Hour = DateTimeUtils.getCurrentTimesBy24Hour();
		updateNextExecuteTime(currentTimesBy24Hour[0], currentTimesBy24Hour[1], currentTimesBy24Hour[2], currentTimesBy24Hour[3], currentTimesBy24Hour[4]);
	}
	
	/**
	 * 判断当前任务是否过期
	 * @return 当前任务是否过期
	 */
	public boolean isPastDue(int currYear, int currMonth, int currDay, int currHour, int currMinute){
		boolean result = false;
		//比较下一次执行的时间与当前的时间
		int contrastResult = Time.contrastTime(
			getNextExecuteTime().getYear(), getNextExecuteTime().getMonth(), getNextExecuteTime().getDay(), getNextExecuteTime().getHour(), getNextExecuteTime().getMinute(), 
			currYear, currMonth, currDay, currHour, currMinute
		);
		
		//如果下一次执行的时间小于当前的时间
		if(contrastResult <= 0){
			//更新下一次执行的时间
			updateNextExecuteTime(currYear, currMonth, currDay, currHour, currMinute);
			//再次比较下一次执行的时间与当前的时间
			contrastResult = Time.contrastTime(
				getNextExecuteTime().getYear(), getNextExecuteTime().getMonth(), getNextExecuteTime().getDay(), getNextExecuteTime().getHour(), getNextExecuteTime().getMinute(), 
				currYear, currMonth, currDay, currHour, currMinute
			);
			//如果下一次执行的时间依然小于当前的时间
			if(contrastResult <= 0){
				result = true;
			}
			try {
				writer();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 判断当前任务是否过期
	 * @return 当前任务是否过期
	 */
	public boolean isPastDue(){
		int[] currentTimesBy24Hour = DateTimeUtils.getCurrentTimesBy24Hour();
		return isPastDue(currentTimesBy24Hour[0], currentTimesBy24Hour[1], currentTimesBy24Hour[2], currentTimesBy24Hour[3], currentTimesBy24Hour[4]);
	}
	
	/**
	 * 将任务对象转换为JSON字符串
	 * @return JSON字符串
	 */
	public String toJSON(){
		JSONObject task = new JSONObject();
		try {
			task.put(TaskItem.KEY_CHECKED, isOpen());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			task.put(KEY_CREATE_TIME, getCreateTime());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			task.put(TriggerTime.KEY, getTriggerTime().toJSON());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			task.put(NextExecuteTime.KEY, getNextExecuteTime().toJSON());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			task.put(Repeat.KEY, getRepeat().toJSON());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			task.put(TaskName.KEY, getTaskName().toJSON());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			task.put(Bluetooth.KEY, getBluetooth().toJSON());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			task.put(AirplaneMode.KEY, getAirplaneMode().toJSON());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			task.put(MobileNetwork.KEY, getMobileNetwork().toJSON());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			task.put(ScreenBrightness.KEY, getScreenBrightness().toJSON());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			task.put(ScreenDormant.KEY, getScreenDormant().toJSON());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			task.put(SendMessage.KEY, getSendMessage().toJSON());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			task.put(Wifi.KEY, getWifi().toJSON());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			task.put(Volume.KEY, getVolume().toJSON());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			task.put(RingnoteMode.KEY, getRingnoteMode().toJSON());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			task.put(OpenApplication.KEY, getOpenApplication().toJSON());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			task.put(Alarm.KEY, getAlarm().toJSON());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return task.toString();
	}
	
	/**
	 * 从一个JSON字符串中解析需要的任务数据
	 * @param taskJSON
	 * @throws JSONException
	 */
	public void fromJSON(String taskJSON) throws JSONException{
		JSONObject task = new JSONObject(taskJSON);
		try {
			setOpen(task.getBoolean(TaskItem.KEY_CHECKED));
		} catch (JSONException e) {
			setOpen(false);
			e.printStackTrace();
		}
		try {
			setCreateTime(task.getLong(KEY_CREATE_TIME));
		} catch (JSONException e) {
			setCreateTime(System.currentTimeMillis());
			e.printStackTrace();
		}
		try {
			setTriggerTime(new TriggerTime(getContext(), this, task.getString(TriggerTime.KEY)));
		} catch (JSONException e) {
			setTriggerTime(new TriggerTime(getContext(), this));
			e.printStackTrace();
		}
		try {
			setNextExecuteTime(new NextExecuteTime(getContext(), this, task.getString(NextExecuteTime.KEY)));
		} catch (JSONException e) {
			setNextExecuteTime(new NextExecuteTime(getContext(), this));
			e.printStackTrace();
		}
		try {
			setTaskName(new TaskName(getContext(), this, task.getString(TaskName.KEY)));
		} catch (JSONException e) {
			setRepeat(new Repeat(getContext(), this));
			e.printStackTrace();
		}
		try {
			setRepeat(new Repeat(getContext(), this, task.getString(Repeat.KEY)));
		} catch (JSONException e) {
			setRepeat(new Repeat(getContext(), this));
			e.printStackTrace();
		}
		try {
			setBluetooth(new Bluetooth(getContext(), this, task.getString(Bluetooth.KEY)));
		} catch (JSONException e) {
			setBluetooth(new Bluetooth(getContext(), this));
			e.printStackTrace();
		}
		try {
			setAirplaneMode(new AirplaneMode(getContext(), this, task.getString(AirplaneMode.KEY)));
		} catch (JSONException e) {
			setAirplaneMode(new AirplaneMode(getContext(), this));
			e.printStackTrace();
		}
		try {
			setMobileNetwork(new MobileNetwork(getContext(), this, task.getString(MobileNetwork.KEY)));
		} catch (JSONException e) {
			setMobileNetwork(new MobileNetwork(getContext(), this));
			e.printStackTrace();
		}
		try {
			setScreenBrightness(new ScreenBrightness(getContext(), this, task.getString(ScreenBrightness.KEY)));
		} catch (JSONException e) {
			setScreenBrightness(new ScreenBrightness(getContext(), this));
			e.printStackTrace();
		}
		try {
			setScreenDormant(new ScreenDormant(getContext(), this, task.getString(ScreenDormant.KEY)));
		} catch (JSONException e) {
			setScreenDormant(new ScreenDormant(getContext(), this));
			e.printStackTrace();
		}
		try {
			setSendMessage(new SendMessage(getContext(), this, task.getString(SendMessage.KEY)));
		} catch (JSONException e) {
			setSendMessage(new SendMessage(getContext(), this));
			e.printStackTrace();
		}
		try {
			setWifi(new Wifi(getContext(), this, task.getString(Wifi.KEY)));
		} catch (JSONException e) {
			setWifi(new Wifi(getContext(), this));
			e.printStackTrace();
		}
		try {
			setVolume((new Volume(getContext(), this, task.getString(Volume.KEY))));
		} catch (JSONException e) {
			setVolume(new Volume(getContext(), this));
			e.printStackTrace();
		}
		try {
			setRingnoteMode(new RingnoteMode(getContext(), this, task.getString(RingnoteMode.KEY)));
		} catch (JSONException e) {
			setRingnoteMode(new RingnoteMode(getContext(), this));
			e.printStackTrace();
		}
		try {
			setOpenApplication((new OpenApplication(getContext(), this, task.getString(OpenApplication.KEY))));
		} catch (JSONException e) {
			setOpenApplication(new OpenApplication(getContext(), this));
			e.printStackTrace();
		}
		try {
			setAlarm((new Alarm(getContext(), this, task.getString(Alarm.KEY))));
		} catch (JSONException e) {
			setAlarm(new Alarm(getContext(), this));
			e.printStackTrace();
		}
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public NextExecuteTime getNextExecuteTime() {
		return nextExecuteTime;
	}

	public void setNextExecuteTime(NextExecuteTime nextExecuteTime) {
		this.nextExecuteTime = nextExecuteTime;
	}

	public List<TaskItem> getTaskItemList() {
		if(taskItemList == null){
			taskItemList = new ArrayList<TaskItem>();
			taskItemList.add(getTaskName());
			taskItemList.add(getTriggerTime());
			taskItemList.add(getRepeat());
			taskItemList.add(getBluetooth());
			taskItemList.add(getWifi());
			taskItemList.add(getMobileNetwork());
			taskItemList.add(getVolume());
			taskItemList.add(getRingnoteMode());
			taskItemList.add(getAirplaneMode());
			taskItemList.add(getScreenBrightness());
			taskItemList.add(getScreenDormant());
			taskItemList.add(getSendMessage());
			taskItemList.add(getOpenApplication());
		}
		return taskItemList;
	}

	public void setTaskItemList(List<TaskItem> taskItemList) {
		this.taskItemList = taskItemList;
	}

	public TaskName getTaskName() {
		return taskName;
	}

	public void setTaskName(TaskName taskName) {
		this.taskName = taskName;
	}

	public TriggerTime getTriggerTime() {
		return triggerTime;
	}

	public void setTriggerTime(TriggerTime triggerTime) {
		this.triggerTime = triggerTime;
	}

	public Bluetooth getBluetooth() {
		return bluetooth;
	}

	public void setBluetooth(Bluetooth bluetooth) {
		this.bluetooth = bluetooth;
	}

	public AirplaneMode getAirplaneMode() {
		return airplaneMode;
	}

	public void setAirplaneMode(AirplaneMode airplaneMode) {
		this.airplaneMode = airplaneMode;
	}

	public MobileNetwork getMobileNetwork() {
		return mobileNetwork;
	}

	public void setMobileNetwork(MobileNetwork mobileNetwork) {
		this.mobileNetwork = mobileNetwork;
	}

	public Repeat getRepeat() {
		return repeat;
	}

	public void setRepeat(Repeat repeat) {
		this.repeat = repeat;
	}

	public ScreenBrightness getScreenBrightness() {
		return screenBrightness;
	}

	public void setScreenBrightness(ScreenBrightness screenBrightness) {
		this.screenBrightness = screenBrightness;
	}

	public ScreenDormant getScreenDormant() {
		return screenDormant;
	}

	public void setScreenDormant(ScreenDormant screenDormant) {
		this.screenDormant = screenDormant;
	}

	public SendMessage getSendMessage() {
		return sendMessage;
	}

	public void setSendMessage(SendMessage sendMessage) {
		this.sendMessage = sendMessage;
	}

	public Wifi getWifi() {
		return wifi;
	}

	public void setWifi(Wifi wifi) {
		this.wifi = wifi;
	}

	public Volume getVolume() {
		return volume;
	}

	public void setVolume(Volume volume) {
		this.volume = volume;
	}

	public RingnoteMode getRingnoteMode() {
		return ringnoteMode;
	}

	public void setRingnoteMode(RingnoteMode ringnoteMode) {
		this.ringnoteMode = ringnoteMode;
	}

	public OpenApplication getOpenApplication() {
		return openApplication;
	}

	public void setOpenApplication(OpenApplication openApplication) {
		this.openApplication = openApplication;
	}

	public Alarm getAlarm() {
		return alarm;
	}

	public void setAlarm(Alarm alarm) {
		this.alarm = alarm;
	}
}
