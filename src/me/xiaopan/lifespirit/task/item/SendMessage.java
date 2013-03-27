package me.xiaopan.lifespirit.task.item;

import java.util.ArrayList;
import java.util.List;

import me.xiaopan.lifespirit.R;
import me.xiaopan.lifespirit.domain.Contacts;
import me.xiaopan.lifespirit.task.Task;
import me.xiaopan.lifespirit.task.TaskItemImpl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.telephony.SmsManager;


public class SendMessage extends  TaskItemImpl {
	/**
	 * KEY - 表示当前任务项
	 */
	public static final String KEY = "KEY_SEND_MESSAGE";
	/**
	 * KEY - 联系人列表
	 */
	private static final String KEY_CONTACTS_LIST = "KEY_CONTACTS_LIST";
	/**
	 * 联系人列表
	 */
	private List<Contacts> contactsList;
	
	public SendMessage(Context context, Task task) {
		super(context, task, context.getString(R.string.taskItem_sendMessage));
		setContactsList(new ArrayList<Contacts>());
	}
	
	public SendMessage(Context context, Task task, String sendMessageJSON)  {
		this(context, task);
		fromJSON(sendMessageJSON);
	}

	@Override
	public void execute() {
		if(isChecked()){
			SmsManager smsManager = SmsManager.getDefault();
			List<String> contentList = smsManager.divideMessage(getContent());
			for(Contacts contacts : getContactsList()){
				String number = contacts.getPhoneNumber();
				for(String content : contentList){
					smsManager.sendTextMessage(number, null, content, null, null);
				}
			}
		}
	}

	@Override
	public String getHintText() {
		String result = getString(R.string.base_none);
		if(contactsList.size() > 0){
			result = contactsList.size()+": ";
			result += getContent();
		}
		return result;
	}
	
	@Override
	public String toJSON(){
		JSONObject sendMessage = new JSONObject();
		try {
			sendMessage.put(KEY_CHECKED, isChecked());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			sendMessage.put(KEY_CONTENT, getContent());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			sendMessage.put(KEY_CONTACTS_LIST, new JSONArray(getContactsList()));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return sendMessage.toString();
	}
	
	@Override
	public void fromJSON(String sendMessageJSON)  {
		if(sendMessageJSON != null){
			try {
				JSONObject sendMessage = new JSONObject(sendMessageJSON);
				try {
					setChecked(sendMessage.getBoolean(KEY_CHECKED));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				try {
					setContent(sendMessage.getString(KEY_CONTENT));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				try {
					JSONArray jsonArray = sendMessage.getJSONArray(KEY_CONTACTS_LIST);
					getContactsList().clear();
					for(int w = 0; w < jsonArray.length(); w++){
						getContactsList().add(new Contacts(jsonArray.getString(w)));
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	public List<Contacts> getContactsList() {
		return contactsList;
	}

	public void setContactsList(List<Contacts> contactsList) {
		this.contactsList = contactsList;
	}
}