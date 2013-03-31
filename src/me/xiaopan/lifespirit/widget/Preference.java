package me.xiaopan.lifespirit.widget;

import me.xiaopan.javalibrary.util.StringUtils;
import me.xiaopan.lifespirit.R;
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
	private TextView introText;
	
	public Preference(Context context, AttributeSet attrs) {
		super(context, attrs);
		setGravity(Gravity.CENTER_VERTICAL);
		
		LinearLayout linearLayout = new LinearLayout(getContext());
		linearLayout.setGravity(Gravity.CENTER_VERTICAL);
		titleText = new TextView(getContext());
		titleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getContext().getResources().getDimension(R.dimen.base_textSize_default));
		titleText.setTextColor(getContext().getResources().getColor(R.color.base_black));
		linearLayout.addView(titleText);
		introText = new TextView(context);
		introText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getContext().getResources().getDimension(R.dimen.base_textSize_small));
		introText.setTextColor(getContext().getResources().getColor(R.color.base_gray_dark));
		linearLayout.addView(introText);
		addView(linearLayout, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.FILL_PARENT, 1));
		
		ImageView imageView = new ImageView(getContext());
		imageView.setImageResource(R.drawable.ic_arrow_right);
		addView(imageView);
		
		TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.Preference);
		updateTitle(typedArray.getString(R.styleable.Preference_title));
		updateIntro(typedArray.getString(R.styleable.Preference_intro));
		typedArray.recycle();
	}
	
	public void updateTitle(String title){
		titleText.setText(title);
	}
	
	public void updateIntro(String intro){
		introText.setText(intro);
		if(!StringUtils.isNotNullAndEmpty(intro)){
			introText.setVisibility(View.GONE);
		}
	}
}