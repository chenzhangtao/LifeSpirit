package me.xiaopan.lifespirit.widget;

import me.xiaopan.androidlibrary.util.Colors;
import me.xiaopan.javalibrary.util.StringUtils;
import me.xiaopan.lifespirit2.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Preference extends LinearLayout implements View.OnClickListener{
	private static final int TYPE_NONE = 1;
	private static final int TYPE_NEXT = 2;
	private static final int TYPE_ENABLE = 3;
	private TextView titleText;
	private TextView space;
	private TextView subtitleText;
	private OnClickListener onNextButtonClickListener;
	
	public Preference(Context context, AttributeSet attrs) {
		super(context, attrs);
		setGravity(Gravity.CENTER_VERTICAL);
		TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.Preference);
		
		LinearLayout linearLayout = new LinearLayout(getContext());
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		linearLayout.setGravity(Gravity.CENTER_VERTICAL);
		
		//标题
		titleText = new TextView(getContext());
		titleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getContext().getResources().getDimension(R.dimen.base_textSize_default));
		titleText.setTextColor(getContext().getResources().getColor(R.color.base_black));
		titleText.setSingleLine();
		titleText.setEllipsize(TruncateAt.MARQUEE);
		titleText.setText(typedArray.getString(R.styleable.Preference_title));
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
		subtitleText.setText(typedArray.getString(R.styleable.Preference_intro));
		linearLayout.addView(subtitleText);
		
		addView(linearLayout, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.FILL_PARENT, 1));
		
		switch(typedArray.getInt(R.styleable.Preference_type, TYPE_NONE)){
			case TYPE_ENABLE : 
				addView(new SlidingButton2(getContext()));
				break;
			case TYPE_NEXT : 
				ImageButton nextImageButton = new ImageButton(getContext());
				nextImageButton.setBackgroundColor(Colors.TRANSPARENT);
				nextImageButton.setImageResource(R.drawable.selector_btn_preference_next);
				nextImageButton.setOnClickListener(this);
				addView(nextImageButton, new LinearLayout.LayoutParams(50, LinearLayout.LayoutParams.WRAP_CONTENT));
				break;
			default : 
				//箭头
				ImageView arrowImage = new ImageView(getContext());
				arrowImage.setImageResource(R.drawable.ic_arrow_right);
				addView(arrowImage);
				break;
		}
		
		typedArray.recycle();
	}
	
	public void setTitle(String title){
		titleText.setText(title);
	}
	
	public void setSubtitle(String intro){
		subtitleText.setText(intro);
		referesh();
	}
	
	private void referesh(){
		//刷新副标题
		if(StringUtils.isNotNullAndEmpty((String) subtitleText.getText())){
			subtitleText.setVisibility(View.VISIBLE);
			space.setVisibility(View.VISIBLE);
		}else{
			subtitleText.setVisibility(View.GONE);
			space.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		if(getOnNextButtonClickListener() != null){
			getOnNextButtonClickListener().onClick(v);
		}
	}

	public OnClickListener getOnNextButtonClickListener() {
		return onNextButtonClickListener;
	}

	public void setOnNextButtonClickListener(
			OnClickListener onNextButtonClickListener) {
		this.onNextButtonClickListener = onNextButtonClickListener;
	}
}