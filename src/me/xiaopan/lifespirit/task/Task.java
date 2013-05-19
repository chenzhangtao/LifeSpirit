package me.xiaopan.lifespirit.task;

import java.io.File;

import me.xiaopan.easyjava.util.FileUtils;
import me.xiaopan.easyjava.util.Time;
import android.content.Context;

import com.google.gson.Gson;

/**
 * 任务基类
 */
public abstract class Task{
	public static final String TASK_DIR = "TASK";
	public static final String STATE_ENABLE = "ENABLE";
	public static final String STATE_DISABLE = "DISABLE";
	private boolean enable;
	private long id;
	private String name;
	private Repeat repeat;
	
	/**
	 * 创建一个任务
	 */
	public Task(){
		setEnable(true);
		setRepeat(new Repeat());
	}
	
	/**
	 * 将当前任务保存到本地
	 * @param context 上下文
	 * @return true：保存成功；false：保存失败
	 */
	public boolean saveToLocal(Context context){
		boolean saveSuccess = true;
		
		//先删除旧文件
		File oldSaveFile = new File(context.getFilesDir().getPath()+File.separator+TASK_DIR+File.separator+getId()+"."+getType()+"_"+(enable?STATE_DISABLE:STATE_ENABLE));
		if(oldSaveFile.exists()){
			oldSaveFile.delete();
		}
		
		//再创建新文件
		try {
			File newSaveFile = new File(context.getFilesDir().getPath()+File.separator+TASK_DIR+File.separator+getId()+"."+getType()+"_"+(enable?STATE_ENABLE:STATE_DISABLE));
			if(!newSaveFile.exists()){
				//先确保父目录的存在，如果不存在就创建
				File parentFile= newSaveFile.getParentFile();
				if(!parentFile.exists() && !parentFile.mkdirs()){
					saveSuccess = false;
				}
				
				//创建文件，如果创建失败了就直接结束
				if(saveSuccess && !newSaveFile.createNewFile()){
					saveSuccess = false;
				}
			}

			//写到文件里去
			if(saveSuccess){
				FileUtils.writeString(newSaveFile, new Gson().toJson(this), false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			saveSuccess = false;
		}
		return saveSuccess;
	}
	
	/**
	 * 获取简介
	 * @param context 上下文
	 * @return 信息
	 */
	public abstract String getIntro(Context context);
	
	/**
	 * 执行
	 * @param context 上下文
	 * @param currentTime 当前时间
	 */
	public void execute(Context context, Time currentTime){
		repeat.onExecute(context, currentTime);
		setEnable(!repeat.isExpiry(currentTime));
		saveToLocal(context);
	}
	
	/**
	 * 获取类型
	 * @return
	 */
	public abstract String getType();
	
	/**
	 * 是否提醒
	 * @return
	 */
	public boolean isRemind(){
		return false;
	}
	
	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Repeat getRepeat() {
		return repeat;
	}

	public void setRepeat(Repeat repeat) {
		this.repeat = repeat;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}