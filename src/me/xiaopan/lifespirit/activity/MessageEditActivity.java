package me.xiaopan.lifespirit.activity;

import java.util.ArrayList;
import java.util.List;

import me.xiaopan.androidlibrary.util.AndroidUtils;
import me.xiaopan.androidlibrary.util.AnimationUtils;
import me.xiaopan.androidlibrary.util.ViewUtils;
import me.xiaopan.androidlibrary.widget.MyBaseAdapter.ChoiceModeListener;
import me.xiaopan.androidlibrary.widget.MyBaseAdapter.ChoiceWay;
import me.xiaopan.androidlibrary.widget.PullListView;
import me.xiaopan.javalibrary.util.CollectionUtils;
import me.xiaopan.lifespirit.MyBaseActivity;
import me.xiaopan.lifespirit.R;
import me.xiaopan.lifespirit.domain.Contacts;
import me.xiaopan.lifespirit.task.item.SendMessage;
import me.xiaopan.lifespirit.widget.ContactsAdapter;
import me.xiaopan.lifespirit.widget.ImageTextButton;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

public class MessageEditActivity extends MyBaseActivity {
	/**
	 * 发送消息key
	 */
	private static final String SEND_MESSAGE = "SEND_MESSAGE";
	/**
	 * 电话号码key
	 */
	private static final String PHONE_NUMBER = "PHONE_NUMBER";
	/**
	 * 短信内容key
	 */
	private static final String MESSAGE_CONTENT = "MESSAGE_CONTENT";
	/**
	 * 联系人列表
	 */
	private PullListView contactsListView;
	/**
	 * 联系人Adapter
	 */
	private ContactsAdapter contactsAdapter;
	/**
	 * 当前编辑的消息对象的副本
	 */
	private SendMessage sendMessage;
	/**
	 * 添加电话号码和确认按钮
	 */
	private ImageButton addPhoneNumberButton, confirmButton;
	/**
	 * 电话号码和短信内容文本编辑框
	 */
	private EditText phoneNumberEditText, messageContentEditText;
	/**
	 * 全选状态
	 */
	private boolean checkAll = true;
	/**
	 * 编辑短信内容部分
	 */
	private LinearLayout editMessageContentLinearLayout;
	/**
	 * 删除联系人按钮
	 */
	private ImageTextButton removeContactsButton;
	
	@Override
	public void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.message_edit);
		getActionBar().setBackgroundDrawable(getDrawable(R.drawable.shape_comm_titlebar));
		getActionBar().setDisplayHomeAsUpEnabled(true);
		contactsListView = (PullListView) findViewById(android.R.id.list);
		phoneNumberEditText = (EditText) findViewById(R.id.edit_messageEdit_phoneNumber);
		messageContentEditText = (EditText) findViewById(R.id.edit_messageEdit_content);
		addPhoneNumberButton = (ImageButton) findViewById(R.id.button_messageEdit_add);
		confirmButton = (ImageButton) findViewById(R.id.button_messageEdit_confirm);
		editMessageContentLinearLayout = (LinearLayout) findViewById(R.id.layout_messageEdit_editContent);
		removeContactsButton = (ImageTextButton) findViewById(R.id.button_messageEdit_remove);
	}

	@Override
	public void onInitListener(Bundle savedInstanceState) {
		//设置添加电话号码按钮点击事件监听器
		addPhoneNumberButton.setOnClickListener(new OnClickListener() { public void onClick(View v) {
			String phoneNumber = phoneNumberEditText.getEditableText().toString();
			//如果电话号码为空就提示不能为空，否则将号码添加到联系人列表里
			if("".equals(phoneNumber)){
				AnimationUtils.shake(phoneNumberEditText);
				toastL(R.string.hint_phoneNumberNull);
			}else{
				addContacts(phoneNumber);
			}
		}});
		
		//设置删除联系人按钮点击事件监听器
		removeContactsButton.setOnClickListener(new OnClickListener() { public void onClick(View v) {
			CollectionUtils.removes(sendMessage.getContactsList(), contactsAdapter.getSelectedIndexs());
			//退出多选模式
			contactsAdapter.exitChoiceMode();
		}});
		
		//设置确定按钮点击事件监听器
		confirmButton.setOnClickListener(new OnClickListener() { public void onClick(View v) {
			String messageContent = messageContentEditText.getEditableText().toString();
			//如果短信内容为空就提示不能为空，否则进行下一步验证
			if("".equals(messageContent)){
				AnimationUtils.shake(messageContentEditText);
				toastL(R.string.hint_messageContentNull);
			}else{
				String number = phoneNumberEditText.getEditableText().toString();
				//如果号码不为空，就添加号码
				if(!"".equals(number)){
					addContacts(number);
				}
				//如果联系人列表里没有联系人就提示不能没有联系人，否则保存信息并退出当前界面
				if(sendMessage.getContactsList().size() <= 0){
					AnimationUtils.shake(phoneNumberEditText);
					toastL(R.string.hint_notContacts);
				}else{
					sendMessage.setContent(messageContent);
					getIntent().putExtra(SendMessage.KEY, sendMessage.toJSON());
					setResult(RESULT_OK, getIntent());
					finishActivity();
				}
			}
		}});
	}

	@Override
	public void onInitData(Bundle savedInstanceState) {
		//初始化消息对象
		if(savedInstanceState == null){
			sendMessage = new SendMessage(this, null, getIntent().getStringExtra(SendMessage.KEY));
			messageContentEditText.setText(sendMessage.getContent());
		}else{
			sendMessage = new SendMessage(this, null, savedInstanceState.getString(SEND_MESSAGE));
			messageContentEditText.setText(savedInstanceState.getString(MESSAGE_CONTENT));
			phoneNumberEditText.setText(savedInstanceState.getString(PHONE_NUMBER));
		}
		
		//创建联系人Adapter并设置选择模式
		contactsAdapter = new ContactsAdapter(getBaseContext(), sendMessage.getContactsList());
		contactsAdapter.setChoiceWay(ChoiceWay.MULTI_CHOICE);
		contactsAdapter.setAbsListView(contactsListView);
		contactsAdapter.setChoiceModeListener(new ChoiceModeListener() {
			@Override
			public void onUpdateSelectedNumber(int selectedNumber) {}
			@Override
			public void onSelectAll() {}
			@Override
			public void onOpen() {}
			@Override
			public void onItemClick(int realPosition, boolean isChecked) {}
			
			@Override
			public void onInto() {
				editMessageContentLinearLayout.setVisibility(View.GONE);
				removeContactsButton.setVisibility(View.VISIBLE);
			}
			
			@Override
			public void onExit() {
				editMessageContentLinearLayout.setVisibility(View.VISIBLE);
				removeContactsButton.setVisibility(View.GONE);
			}
			
			@Override
			public void onDeselectAll() {}
			
			@Override
			public void onClose() {}
		});
		contactsAdapter.openChiceMode();
		
		contactsListView.openListHeaderReboundMode();
		contactsListView.openListFooterReboundMode();
		
		//将列表与联系人Adapter绑定
		contactsListView.setAdapter(contactsAdapter);
	}
	
	/**
	 * 添加联系人
	 * @param phoneNumber
	 */
	private void addContacts(String phoneNumber){
		sendMessage.getContactsList().add(new Contacts(null, phoneNumber));
		contactsAdapter.intoChoiceMode();
		phoneNumberEditText.setText(null);
	}
	
	@Override
	public void onBackPressed() {
		//如果是多选模式就推出多选模式，否则就退出当前Activity
		if(contactsAdapter.isOpenedChoiceMode() && contactsAdapter.isIntoChoiceMode()){
			contactsAdapter.exitChoiceMode();
		}else{
			super.onBackPressed();
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		//保存消息对象的数据，以及电话号码和短信内容输入框的数据
		outState.putString(SEND_MESSAGE, sendMessage.toJSON());
		outState.putString(PHONE_NUMBER, phoneNumberEditText.getEditableText().toString());
		outState.putString(MESSAGE_CONTENT, messageContentEditText.getEditableText().toString());
		super.onSaveInstanceState(outState);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.message_edit, menu);
		return super.onCreateOptionsMenu(menu);
	}

	private AlertDialog alertDialog = null;

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_messageEdit_contacts:
				//读取联系人
				Cursor cursor = AndroidUtils.getContactsNameAndNumber(getBaseContext());
				//如果读取到的联系人个数大于0
				if(cursor.getCount() > 0){
					//组织联系人数据
					final List<Contacts> contactsList = new ArrayList<Contacts>();
					while(cursor.moveToNext()){
						String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
						String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						contactsList.add(new Contacts(name, number));
					}
					
					
					//创建联系人列表、联系人适配器并绑定
					ListView listView = new ListView(getBaseContext());
					final ContactsAdapter contactsAdapterTemp = new ContactsAdapter(getBaseContext(), contactsList);
					contactsAdapterTemp.setAbsListView(listView);
					contactsAdapterTemp.openChiceMode();
					contactsAdapterTemp.intoChoiceMode();
					contactsAdapterTemp.setTextColor(getResources().getColor(R.color.base_white));
					listView.setAdapter(contactsAdapterTemp);
					
					//创建对话框构建器并设置标题以及要显示的联系人列表
					AlertDialog.Builder builder = new AlertDialog.Builder(MessageEditActivity.this);
					builder.setTitle(R.string.messageEdit_selectContacts);
					builder.setView(listView);
					
					//设置确定按钮
					builder.setPositiveButton(R.string.base_confirm, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							//将选中的联系人全部添加到消息对象的联系人列表中
							int[] slecteds = contactsAdapterTemp.getSelectedIndexs();
							if(slecteds != null && slecteds.length > 0){
								for(int in : slecteds){
									sendMessage.getContactsList().add(contactsList.get(in));
								}
								//关闭对话框
								ViewUtils.setDialogClickClose(alertDialog, true);
								//刷新消息对象的联系人列表
								contactsAdapter.notifyDataSetChanged();
							}else{
								ViewUtils.setDialogClickClose(alertDialog, false);
								toastS(R.string.messageEdit_hint_peleaseSelect);
							}
						}
					});
					
					//设置全选按钮
					builder.setNeutralButton(R.string.base_selectAll, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Button button = alertDialog.getButton(which);
							//如果为true就执行全选操作，否则执行全部取消操作
							if(checkAll){
								contactsAdapterTemp.selectAll();
								button.setText(R.string.base_deselectAll);
							}else{
								contactsAdapterTemp.deselectAll();
								button.setText(R.string.base_selectAll);
							}
							//刷新联系人列表
							contactsAdapterTemp.notifyDataSetChanged();
							//改变全选状态
							checkAll = !checkAll;
							//不关闭对话框
							ViewUtils.setDialogClickClose(alertDialog, false);
						}
					});
					
					//设置取消按钮
					builder.setNegativeButton(R.string.base_cancel, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							//关闭对话框
							ViewUtils.setDialogClickClose(alertDialog, true);
						}
					});
					
					//创建并显示对话框
					(alertDialog = builder.create()).show();
				}else{
					toastL(R.string.hint_notReadingContacts);
				}
				break;
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}
}