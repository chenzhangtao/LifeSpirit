package me.xiaopan.lifespirit.widget;

import me.xiaopan.javalibrary.util.StringUtils;
import me.xiaopan.lifespirit2.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Preference extends LinearLayout {
	private TextView titleText;
	private TextView space;
	private TextView subtitleText;
	private CheckBox enableCheckBox;
	private ImageView arrowImage;
	private boolean enablePreference;
	
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
		titleText.setSingleLine();
		titleText.setEllipsize(TruncateAt.MARQUEE);
		linearLayout.addView(titleText);
		
		//间隔
		space = new TextView(getContext());
		linearLayout.addView(space, new LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 7));
		
		//副标题
		subtitleText = new TextView(context);
		subtitleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getContext().getResources().getDimension(R.dimen.base_textSize_littleSmall));
		subtitleText.setTextColor(getContext().getResources().getColor(R.color.base_gray_dark));
		subtitleText.setSingleLine();
		subtitleText.setEllipsize(TruncateAt.END);
		linearLayout.addView(subtitleText);
		
		addView(linearLayout, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.FILL_PARENT, 1));
		
		//激活复选框
		enableCheckBox = new CheckBox(getContext());
		addView(enableCheckBox);
		
		//箭头
		arrowImage = new ImageView(getContext());
		arrowImage.setImageResource(R.drawable.ic_arrow_right);
		addView(arrowImage);
		
		//初始化数据
		TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.Preference);
		setTitle(typedArray.getString(R.styleable.Preference_title));
		setSubtitle(typedArray.getString(R.styleable.Preference_intro));
		typedArray.recycle();
		
		invalidate();
	}
	
	public void setTitle(String title){
		titleText.setText(title);
	}
	
	public void setSubtitle(String intro){
		subtitleText.setText(intro);
		invalidate();
	}
	
	@Override
	public void invalidate() {
		super.invalidate();
		
		//刷新副标题
		if(StringUtils.isNotNullAndEmpty((String) subtitleText.getText())){
			subtitleText.setVisibility(View.VISIBLE);
			space.setVisibility(View.VISIBLE);
		}else{
			subtitleText.setVisibility(View.GONE);
			space.setVisibility(View.GONE);
		}
		
		//刷新复选框
		if(isEnablePreference()){
			enableCheckBox.setVisibility(View.VISIBLE);
			arrowImage.setVisibility(View.GONE);
		}else{
			enableCheckBox.setVisibility(View.GONE);
			arrowImage.setVisibility(View.VISIBLE);
		}
	}

	public boolean isEnablePreference() {
		return enablePreference;
	}

	public void setEnablePreference(boolean enablePreference) {
		this.enablePreference = enablePreference;
		invalidate();
	}
}