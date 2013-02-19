package me.xiaopan.lifespirit.domain;

import org.json.JSONException;
import org.json.JSONObject;

public class Contacts {
	/**
	 * KEY - 联系人名字
	 */
	private static final String KEY_CONTACTS_NAME = "KEY_CONTACTS_NAME"; 
	/**
	 * KEY - 联系人号码
	 */
	private static final String KEY_CONTACTS_PHONE_NUMBER = "KEY_CONTACTS_PHONE_NUMBER"; 
	/**
	 * 联系人名字
	 */
	private String name;
	/**
	 * 联系人号码
	 */
	private String phoneNumber;
	
	public Contacts(String name, String phoneNumber) {
		setName(name);
		setPhoneNumber(phoneNumber);
	}
	
	public Contacts(String contactsJSON){
		fromJSON(contactsJSON);
	}
	
	@Override
	public String toString(){
		return toJSON();
	}
	
	public String toJSON(){
		JSONObject contacts = new JSONObject();
		try {
			contacts.put(KEY_CONTACTS_NAME, getName());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			contacts.put(KEY_CONTACTS_PHONE_NUMBER, getPhoneNumber());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return contacts.toString();
	}
	
	public void fromJSON(String contactsJSON){
		if(contactsJSON != null){
			try {
				JSONObject cntacts = new JSONObject(contactsJSON);
				try {
					setName(cntacts.getString(KEY_CONTACTS_NAME));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				try {
					setPhoneNumber(cntacts.getString(KEY_CONTACTS_PHONE_NUMBER));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
