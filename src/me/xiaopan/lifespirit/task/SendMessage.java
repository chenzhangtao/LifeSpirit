package me.xiaopan.lifespirit.task;

import java.util.ArrayList;
import java.util.List;

import me.xiaopan.lifespirit.domain.Contacts;
import android.content.Context;
import android.telephony.SmsManager;

/**
 * 发送短信
 */
public class SendMessage extends BaseTask{
	private List<Contacts> contactsList;
	private String messageContent;
	
	public SendMessage(Context context) {
		super(context);
		setContactsList(new ArrayList<Contacts>());
	}

	@Override
	public String getIntro() {
		return null;
	}

	@Override
	public void execute() {
		if(isEnable()){
			SmsManager smsManager = SmsManager.getDefault();
			List<String> contentList = smsManager.divideMessage(messageContent);
			for(Contacts contacts : getContactsList()){
				String number = contacts.getPhoneNumber();
				for(String content : contentList){
					smsManager.sendTextMessage(number, null, content, null, null);
				}
			}
		}
	}

	public List<Contacts> getContactsList() {
		return contactsList;
	}

	public void setContactsList(List<Contacts> contactsList) {
		this.contactsList = contactsList;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
}