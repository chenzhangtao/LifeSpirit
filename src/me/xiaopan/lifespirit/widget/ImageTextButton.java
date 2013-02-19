package me.xiaopan.lifespirit.widget;

import me.xiaopan.lifespirit.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ImageTextButton extends LinearLayout {

	private TextView textView;
	private ImageView imageView;
	
	public ImageTextButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}

	public ImageTextButton(Context context) {
		super(context);
		init(null);
	}
	
	private void init(AttributeSet attrs){
		setOrientation(HORIZONTAL);
		imageView = new ImageView(getContext());
		addView(imageView);
		textView = new TextView(getContext());
		addView(textView);
		setClickable(true);
		setGravity(Gravity.CENTER);
		TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ImageTextButton);
		setImageDrawable(typedArray.getDrawable(R.styleable.ImageTextButton_android_src));
		setText(typedArray.getString(R.styleable.ImageTextButton_android_text));
		setTextColor(typedArray.getColor(R.styleable.ImageTextButton_android_textColor, android.R.color.white));
		setTextSize(typedArray.getDimension(R.styleable.ImageTextButton_android_textSize, 0));
		typedArray.recycle();
	}

	public void setText(String text){
		if(text != null){
			textView.setText(text);
			textView.setVisibility(View.VISIBLE);
			textView.invalidate();
		}else{
			textView.setText(text);
			textView.setVisibility(View.GONE);
			textView.invalidate();
		}
	}
	
	public void setText(int resId){
		setText(getContext().getString(resId));
	}
	
	public void setTextColor(int color){
		textView.setTextColor(color);
		textView.setVisibility(View.VISIBLE);
		textView.invalidate();
	}
	
	public void setTextSize(float size){
		textView.setTextSize(size);
		textView.setVisibility(View.VISIBLE);
		textView.invalidate();
	}
	
	public void setImageDrawable(Drawable drawable){
		if(drawable != null){
			imageView.setImageDrawable(drawable);
			imageView.setVisibility(View.VISIBLE);
			imageView.invalidate();
		}else{
			imageView.setImageDrawable(drawable);
			imageView.setVisibility(View.GONE);
			imageView.invalidate();
		}
	}
	
	public void setImageBitmap(Bitmap bm){
		if(bm != null){
			imageView.setImageBitmap(bm);
			imageView.setVisibility(View.VISIBLE);
			imageView.invalidate();
		}else{
			imageView.setImageBitmap(bm);
			imageView.setVisibility(View.GONE);
			imageView.invalidate();
		}
	}
	
	public void setImageResource(int resId){
		setImageDrawable(getContext().getResources().getDrawable(resId));
	}
}
