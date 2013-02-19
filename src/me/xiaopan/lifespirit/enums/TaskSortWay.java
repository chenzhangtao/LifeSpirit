package me.xiaopan.lifespirit.enums;

public enum TaskSortWay {
	/**
	 * 按触发时间升序
	 */
	TIGGER_TIME_ASC(0), 
	/**
	 * 按触发时间降序
	 */
	TIGGER_TIME_DESC(1), 
	/**
	 * 按创建时间升序
	 */
	CREATE_TIME_ASC(2), 
	/**
	 * 按创建时间降序
	 */
	CREATE_TIME_DESC(3);
	
	private int index = -5;
	
	TaskSortWay(int index){
		this.index = index;
	}
	
	public int getIndex(){
		return index;
	}
}
