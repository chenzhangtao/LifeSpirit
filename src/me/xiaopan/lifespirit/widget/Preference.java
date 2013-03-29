package me.xiaopan.lifespirit.widget;

import me.xiaopan.lifespirit.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Preference extends LinearLayout {
	private TextView title;
	private TextView intro;
	
	public Preference(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOrientation(LinearLayout.VERTICAL);
		int padding = (int) getContext().getResources().getDimension(R.dimen.base_textSize_large);
		setPadding(padding, padding, padding, padding);
		
		TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.Preference);
		
		title = new TextView(getContext());
		title.setTextSize(TypedValue.COMPLEX_UNIT_PX, getContext().getResources().getDimension(R.dimen.base_textSize_default));
		title.setTextColor(getContext().getResources().getColor(R.color.base_black));
		title.setText(typedArray.getString(R.styleable.Preference_title));
		
		intro = new TextView(context);
		intro.setText(typedArray.getString(R.styleable.Preference_intro));
		
		typedArray.recycle();
		
		addView(title);
		addView(intro);
	}
}