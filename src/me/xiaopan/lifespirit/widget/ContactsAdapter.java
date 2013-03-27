package me.xiaopan.lifespirit.widget;

import java.util.ArrayList;
import java.util.List;

import me.xiaopan.androidlibrary.util.AndroidUtils;
import me.xiaopan.androidlibrary.widget.MyBaseAdapter;
import me.xiaopan.lifespirit.R;
import me.xiaopan.lifespirit.domain.Contacts;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

public class ContactsAdapter extends MyBaseAdapter {
	private Context context;
	private List<Contacts> contactsList;
	private int textColor = -5;
	
	public ContactsAdapter(Context context, List<Contacts> contactsList){
		setContext(context);
		setContactsList(contactsList);
	}
	
	public ContactsAdapter(Context context, Cursor cursor, String name, String number){
		setContext(context);
		setContactsList(new ArrayList<Contacts>());
		while(cursor.moveToNext()){
			getContactsList().add(new Contacts(cursor.getString(cursor.getColumnIndex(name)), cursor.getString(cursor.getColumnIndex(number))));
		}
		cursor.close();
	}
	
	public ContactsAdapter(Context context){
		setContext(context);
		setContactsList(new ArrayList<Contacts>());
		Cursor cursor = AndroidUtils.getContactsNameAndNumber(context);
		while(cursor.moveToNext()){
			String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
			String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
			contactsList.add(new Contacts(name, number));
		}
	}
	
	@Override
	public int getRealCount() {
		return contactsList.size();
	}

	@Override
	public Object getItem(int position) {
		return contactsList.get(position);
	}

	@Override
	public View getRealView(int realPosition, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if(convertView == null){
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.message_contacts_item, null);
			viewHolder = new ViewHolder();
			viewHolder.name = (TextView) convertView.findViewById(R.id.text_messageContactsItem_name);
			viewHolder.number = (TextView) convertView.findViewById(R.id.text_messageContactsItem_phoneNumber);
			viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.base_button_listItemChoice);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if(getTextColor() != -5){
			viewHolder.name.setTextColor(getTextColor());
			viewHolder.number.setTextColor(getTextColor());
		}
		Contacts contacts = getContactsList().get(getRealPosition(realPosition));
		viewHolder.name.setText(contacts.getName());
		viewHolder.number.setText(contacts.getPhoneNumber());
		choiceButtonHandle((viewHolder.checkBox), realPosition);
		return convertView;
	}
	
	static class ViewHolder{
		TextView name;
		TextView number;
		CheckBox checkBox;
	}
	
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public List<Contacts> getContactsList() {
		return contactsList;
	}

	public void setContactsList(List<Contacts> contactsList) {
		this.contactsList = contactsList;
	}

	public int getTextColor() {
		return textColor;
	}

	public void setTextColor(int textColor) {
		this.textColor = textColor;
	}
}