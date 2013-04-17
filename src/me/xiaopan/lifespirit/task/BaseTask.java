package me.xiaopan.lifespirit.task;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.xiaopan.javalibrary.io.FileScanner;
import me.xiaopan.javalibrary.util.FileUtils;
import me.xiaopan.javalibrary.util.StringUtils.StringCheckUpWayEnum;
import android.content.Context;

import com.google.gson.Gson;

/**
 * 任务基类
 */
public abstract class BaseTask{
	public static final String TASK_DIR = "TASK";
	public static final String STATE_ENABLE = "ENABLE";
	public static final String STATE_DISABLE = "DISABLE";
	private boolean enable;
	private CreateTime createTime;
	private Name name;
	private Repeat repeat;
	
	/**
	 * 创建一个任务
	 */
	public BaseTask(){
		setEnable(true);
		setCreateTime(new CreateTime());
		setName(new Name());
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
		File oldSaveFile = new File(context.getFilesDir().getPath()+File.separator+TASK_DIR+File.separator+getCreateTime().getTimeInMillis()+"."+onGetType()+"_"+(enable?STATE_DISABLE:STATE_ENABLE));
		if(oldSaveFile.exists()){
			oldSaveFile.delete();
		}
		
		//再创建新文件
		try {
			File newSaveFile = new File(context.getFilesDir().getPath()+File.separator+TASK_DIR+File.separator+getCreateTime().getTimeInMillis()+"."+onGetType()+"_"+(enable?STATE_ENABLE:STATE_DISABLE));
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
	public abstract String onGetIntro(Context context);
	
	/**
	 * 执行
	 * @param context 上下文
	 */
	public abstract void onExecute(Context context);
	
	/**
	 * 获取类型
	 * @return
	 */
	public abstract String onGetType();

	public static List<BaseTask> readEnableTasks(Context context) {
		FileScanner fileScanner = new FileScanner(new File(context.getFilesDir().getPath()+File.separator+TASK_DIR));
		fileScanner.setFileTypeFilterWay(StringCheckUpWayEnum.ENDS_WITH_KEYWORDS);
		fileScanner.addFileTypeKeyWords(STATE_ENABLE);
		List<File> files = fileScanner.scan();
		if(files != null && files.size() > 0){
			List<BaseTask> tasks = new ArrayList<BaseTask>(files.size());
			Gson gson = new Gson();
			String string = null;
			String fileName = null;
			for(File file : files){
				try {
					string = FileUtils.readString(file);
					fileName = file.getName();
					if(fileName.contains(ScenarioMode.TYPE)){
						tasks.add(gson.fromJson(string, ScenarioMode.class));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return tasks;
		}else{
			return null;
		}
	}
	
	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public CreateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(CreateTime createTime) {
		this.createTime = createTime;
	}

	public Repeat getRepeat() {
		return repeat;
	}

	public void setRepeat(Repeat repeat) {
		this.repeat = repeat;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}
}