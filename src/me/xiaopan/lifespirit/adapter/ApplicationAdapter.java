package me.xiaopan.lifespirit.adapter;

import java.util.List;

import me.xiaopan.androidlibrary.widget.MyBaseAdapter;
import me.xiaopan.lifespirit.R;
import me.xiaopan.lifespirit.domain.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ApplicationAdapter extends MyBaseAdapter {

	private Context context;
	private List<Application> applicationList;
	
	public ApplicationAdapter(Context context, List<Application> applicationList){
		setContext(context);
		setApplicationList(applicationList);
		setTotalEntries(applicationList.size());
	}
	
	@Override
	public int getRealCount() {
		return applicationList.size();
	}

	@Override
	public Object getItem(int position) {
		return applicationList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getRealView(int realPosition, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if(convertView == null){
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_edit_application_item, null);
			viewHolder = new ViewHolder();
			viewHolder.icon = (ImageView) convertView.findViewById(R.id.image_taskEditApplicationItem_icon);
			viewHolder.name = (TextView) convertView.findViewById(R.id.text_taskEditApplicationItem_name);
			viewHolder.packageName = (TextView) convertView.findViewById(R.id.text_taskEditApplicationItem_packageName);
			viewHolder.choiceButton = (CompoundButton)  convertView.findViewById(R.id.base_button_listItemChoice);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		Application application = getApplicationList().get(getRealPosition(realPosition));
		
		viewHolder.icon.setImageDrawable(application.getIcon());
		viewHolder.name.setText(application.getName());
		viewHolder.packageName.setText(application.getPackageName());
		
		choiceButtonHandle(viewHolder.choiceButton, realPosition);
		
		return convertView;
	}
	
	static class ViewHolder{
		ImageView icon;
		TextView name;
		TextView packageName;
		CompoundButton choiceButton;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public List<Application> getApplicationList() {
		return applicationList;
	}

	public void setApplicationList(List<Application> applicationList) {
		this.applicationList = applicationList;
	}
}
