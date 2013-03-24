package me.xiaopan.lifespirit.task;

import me.xiaopan.lifespirit.task.scenariomode.AirplaneMode;
import me.xiaopan.lifespirit.task.scenariomode.Bluetooth;
import me.xiaopan.lifespirit.task.scenariomode.MobileNetwork;
import me.xiaopan.lifespirit.task.scenariomode.RingnoteMode;
import me.xiaopan.lifespirit.task.scenariomode.ScreenBrightness;
import me.xiaopan.lifespirit.task.scenariomode.ScreenDormant;
import me.xiaopan.lifespirit.task.scenariomode.Volume;
import me.xiaopan.lifespirit.task.scenariomode.Wifi;
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
	private ScreenBrightness screenBrightness;
	private ScreenDormant screenDormant;
	private Wifi wifi;
	private Volume volume;
	private RingnoteMode ringnoteMode;
	
	/**
	 * 创建一个任务
	 * @param context 上下文
	 */
	public ScenarioMode(Context context){
		super(context);
		setBluetooth(new Bluetooth(context, this));
		setAirplaneMode(new AirplaneMode(context, this));
		setMobileNetwork(new MobileNetwork(context, this));
		setScreenBrightness(new ScreenBrightness(context, this));
		setScreenDormant(new ScreenDormant(context, this));
		setWifi(new Wifi(context, this));
		setVolume(new Volume(context, this));
		setRingnoteMode(new RingnoteMode(context, this));
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
			getBluetooth().execute();
			getMobileNetwork().execute();
			getWifi().execute();
			getScreenBrightness().execute();
			getScreenDormant().execute();
			getVolume().execute();
			getRingnoteMode().execute();
			getAirplaneMode().execute();
			super.execute();
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
}