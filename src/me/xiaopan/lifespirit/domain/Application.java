package me.xiaopan.lifespirit.domain;

import android.graphics.drawable.Drawable;

public class Application {
	private String name; 
	private String packageName;
	private Drawable icon;
	
	public String getName() {
		return name;
	}
	public void setName(String charSequence) {
		this.name = charSequence;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public Drawable getIcon() {
		return icon;
	}
	public void setIcon(Drawable icon) {
		this.icon = icon;
	}
}