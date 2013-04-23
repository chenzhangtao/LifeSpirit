package me.xiaopan.lifespirit.task.repeat;

/**
 * 每隔多长时间重复一次
 */
public abstract class BaseEveryOtherRepeat extends BaseRepeatWay{
	private int space;//间隔
	
	public BaseEveryOtherRepeat(){
		setSpace(1);
	}

	public int getSpace() {
		return space;
	}

	public void setSpace(int space) {
		this.space = space;
	}
}