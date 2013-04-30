package me.xiaopan.lifespirit.task;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.xiaopan.androidlibrary.util.AndroidUtils;
import me.xiaopan.androidlibrary.util.Logger;
import me.xiaopan.javalibrary.io.FileScanner;
import me.xiaopan.javalibrary.util.DateTimeUtils;
import me.xiaopan.javalibrary.util.FileUtils;
import me.xiaopan.javalibrary.util.Time;
import me.xiaopan.javalibrary.util.StringUtils.StringCheckUpWayEnum;
import me.xiaopan.lifespirit.task.scenariomode.AirplaneMode;
import me.xiaopan.lifespirit.task.scenariomode.Bluetooth;
import me.xiaopan.lifespirit.task.scenariomode.Brightness;
import me.xiaopan.lifespirit.task.scenariomode.Dormant;
import me.xiaopan.lifespirit.task.scenariomode.MobileNetwork;
import me.xiaopan.lifespirit.task.scenariomode.NotificationRingtone;
import me.xiaopan.lifespirit.task.scenariomode.PhoneRingtone;
import me.xiaopan.lifespirit.task.scenariomode.RingtoneMode;
import me.xiaopan.lifespirit.task.scenariomode.SmsRingtone;
import me.xiaopan.lifespirit.task.scenariomode.Volume;
import me.xiaopan.lifespirit.task.scenariomode.WIFI;
import android.content.Context;

import com.google.gson.Gson;

/**
 * 情景模式
 */
public class ScenarioMode extends BaseTask{
	public static final String TYPE = "SCENARIO_MODE";
	private Bluetooth bluetooth;
	private AirplaneMode airplaneMode;
	private MobileNetwork mobileNetwork;
	private Brightness brightness;
	private Dormant dormant;
	private WIFI wifi;
	private Volume volume;
	private RingtoneMode ringtoneMode;
	private PhoneRingtone phoneRingtone;
	private SmsRingtone smsRingtone;
	private NotificationRingtone notificationRingtone;
	
	/**
	 * 创建一个任务
	 * @param context 上下文
	 */
	public ScenarioMode(Context context){
		setBluetooth(new Bluetooth());
		setAirplaneMode(new AirplaneMode(context));
		setMobileNetwork(new MobileNetwork(context));
		setBrightness(new Brightness(context));
		setDormant(new Dormant(context));
		setWifi(new WIFI(context));
		setVolume(new Volume(context));
		setRingtoneMode(new RingtoneMode(context));
		setPhoneRingtone(new PhoneRingtone());
		setSmsRingtone(new SmsRingtone());
		setNotificationRingtone(new NotificationRingtone());
	}
	
	@Override
	public String getIntro(Context context){
		return "";
	}

	public static List<ScenarioMode> readScenarioModes(Context context) {
		FileScanner fileScanner = new FileScanner(new File(context.getFilesDir().getPath()+File.separator+TASK_DIR));
		fileScanner.setFileTypeFilterWay(StringCheckUpWayEnum.CONTAIN_KEYWORDS);
		fileScanner.addFileTypeKeyWords(ScenarioMode.TYPE);
		List<File> files = fileScanner.scan();
		if(files != null && files.size() > 0){
			List<ScenarioMode> scenarioModes = new ArrayList<ScenarioMode>(files.size());
			Gson gson = new Gson();
			for(File file : files){
				try {
					scenarioModes.add(gson.fromJson(FileUtils.readString(file), ScenarioMode.class));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return scenarioModes;
		}else{
			return null;
		}
	}

	@Override
	public String getType() {
		return TYPE;
	}
	
	@Override
	public void execute(Context context, Time currentTime){
		if(isEnable()){
//			getBluetooth().onExecute(context);
//			getMobileNetwork().onExecute(context);
//			getWifi().onExecute(context);
//			getBrightness().onExecute(context);
//			getDormant().onExecute(context);
//			getVolume().onExecute(context);
//			getRingtoneMode().onExecute(context);
//			getAirplaneMode().onExecute(context);
			AndroidUtils.toastL(context, "执行了，时间："+DateTimeUtils.getCurrentDateTimeByDefultCustomFormat());
			Logger.w("执行了，时间："+DateTimeUtils.getCurrentDateTimeByDefultCustomFormat());
			super.execute(context, currentTime);
		}
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

	public Brightness getBrightness() {
		return brightness;
	}

	public void setBrightness(Brightness brightness) {
		this.brightness = brightness;
	}

	public Dormant getDormant() {
		return dormant;
	}

	public void setDormant(Dormant dormant) {
		this.dormant = dormant;
	}

	public WIFI getWifi() {
		return wifi;
	}

	public void setWifi(WIFI wifi) {
		this.wifi = wifi;
	}

	public Volume getVolume() {
		return volume;
	}

	public void setVolume(Volume volume) {
		this.volume = volume;
	}

	public RingtoneMode getRingtoneMode() {
		return ringtoneMode;
	}

	public void setRingtoneMode(RingtoneMode ringtoneMode) {
		this.ringtoneMode = ringtoneMode;
	}

	public PhoneRingtone getPhoneRingtone() {
		return phoneRingtone;
	}

	public void setPhoneRingtone(PhoneRingtone phoneRingtone) {
		this.phoneRingtone = phoneRingtone;
	}

	public SmsRingtone getSmsRingtone() {
		return smsRingtone;
	}

	public void setSmsRingtone(SmsRingtone smsRingtone) {
		this.smsRingtone = smsRingtone;
	}

	public NotificationRingtone getNotificationRingtone() {
		return notificationRingtone;
	}

	public void setNotificationRingtone(NotificationRingtone notificationRingtone) {
		this.notificationRingtone = notificationRingtone;
	}
}