package me.xiaopan.lifespirit.task.repeat;

/**
 * 每隔多长时间重复一次
 */
public abstract class EveryOtherRepeat extends BaseRepeat{
	private static final long serialVersionUID = 1L;
	private int space;//间隔
	
	public EveryOtherRepeat(RepeatWay repeatWay){
		super(repeatWay);
		setSpace(1);
	}

	public int getSpace() {
		return space;
	}

	public void setSpace(int space) {
		this.space = space;
	}
}