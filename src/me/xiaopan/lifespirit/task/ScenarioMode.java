package me.xiaopan.lifespirit.task;

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

/**
 * 情景模式
 * @author xiaopan
 *
 */
public class ScenarioMode extends BaseTask{
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
		super(context);
		setBluetooth(new Bluetooth(context));
		setAirplaneMode(new AirplaneMode(context));
		setMobileNetwork(new MobileNetwork(context));
		setBrightness(new Brightness(context));
		setDormant(new Dormant(context));
		setWifi(new WIFI(context));
		setVolume(new Volume(context));
		setRingtoneMode(new RingtoneMode(context));
		setPhoneRingtone(new PhoneRingtone(getContext()));
		setSmsRingtone(new SmsRingtone(getContext()));
		setNotificationRingtone(new NotificationRingtone(getContext()));
	}
	
	/**
	 * 获取任务信息
	 * @return 任务信息
	 */
	@Override
	public String getIntro(){
		return "";
	}
	
	/**
	 * 执行
	 */
	@Override
	public void execute(){
		if(isEnable()){
			getBluetooth().onExecute();
			getMobileNetwork().onExecute();
			getWifi().onExecute();
			getBrightness().onExecute();
			getDormant().onExecute();
			getVolume().onExecute();
			getRingtoneMode().onExecute();
			getAirplaneMode().onExecute();
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