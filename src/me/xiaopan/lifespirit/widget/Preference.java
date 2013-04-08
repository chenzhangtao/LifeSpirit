package me.xiaopan.lifespirit.widget;

import me.xiaopan.javalibrary.util.StringUtils;
import me.xiaopan.lifespirit2.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Preference extends LinearLayout {
	private TextView titleText;
	private TextView subtitleText;
	private TextView space;
	
	public Preference(Context context, AttributeSet attrs) {
		super(context, attrs);
		setGravity(Gravity.CENTER_VERTICAL);
		
		LinearLayout linearLayout = new LinearLayout(getContext());
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		linearLayout.setGravity(Gravity.CENTER_VERTICAL);
		
		//标题
		titleText = new TextView(getContext());
		titleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getContext().getResources().getDimension(R.dimen.base_textSize_default));
		titleText.setTextColor(getContext().getResources().getColor(R.color.base_black));
		linearLayout.addView(titleText);
		
		//间隔
		space = new TextView(getContext());
		linearLayout.addView(space, new LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 7));
		
		//副标题
		subtitleText = new TextView(context);
		subtitleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getContext().getResources().getDimension(R.dimen.base_textSize_littleSmall));
		subtitleText.setTextColor(getContext().getResources().getColor(R.color.base_gray_dark));
		linearLayout.addView(subtitleText);
		
		addView(linearLayout, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.FILL_PARENT, 1));
		
		ImageView imageView = new ImageView(getContext());
		imageView.setImageResource(R.drawable.ic_arrow_right);
		addView(imageView);
		
		TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.Preference);
		setTitle(typedArray.getString(R.styleable.Preference_title));
		setSubtitle(typedArray.getString(R.styleable.Preference_intro));
		typedArray.recycle();
	}
	
	public void setTitle(String title){
		titleText.setText(title);
	}
	
	public void setSubtitle(String intro){
		subtitleText.setText(intro);
		if(StringUtils.isNotNullAndEmpty(intro)){
			subtitleText.setVisibility(View.VISIBLE);
			space.setVisibility(View.VISIBLE);
		}else{
			subtitleText.setVisibility(View.GONE);
			space.setVisibility(View.GONE);
		}
	}
}