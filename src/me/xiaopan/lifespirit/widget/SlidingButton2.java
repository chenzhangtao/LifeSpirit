package me.xiaopan.lifespirit.widget;

import me.xiaopan.lifespirit2.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.view.View;

@SuppressLint("DrawAllocation")
public class SlidingButton2 extends View {
	private Bitmap maskBitmap;
	private Bitmap frameBitmap;
	private Bitmap backgroundBitmap;
	private Bitmap sliderBitmap;
	private Paint paint;

	public SlidingButton2(Context context) {
		super(context);
		maskBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.btn_sliding_mask);
		frameBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.btn_sliding_frame);
		backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.btn_sliding_background);
		sliderBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.btn_sliding_slilder_normal);
		paint = new Paint();
		paint.setFilterBitmap(false);
	}
	
//	setBackgroundResource(R.drawable.btn_sliding_slilder_frame);
//	
//	backgroundImage = new ImageView(getContext());
//	backgroundImage.setImageResource(R.drawable.btn_sliding_background);
//	backgroundImage.setScaleType(ScaleType.FIT_START);
//	addView(backgroundImage);
//
//	sliderImage = new ImageView(getContext());
//	sliderImage.setImageResource(R.drawable.selector_btn_sliding_slider);
//	sliderImage.setScaleType(ScaleType.FIT_START);
//	addView(sliderImage);
//	
//	setLayoutParams(new LayoutParams(getBackground().getMinimumWidth(), LayoutParams.WRAP_CONTENT));
	


	@Override
	protected void onDraw(Canvas canvas) {
//		canvas.drawBitmap(frameBitmap, 0, 0, null);
//		canvas.drawBitmap(backgroundBitmap, 0, 0, null);
//		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
//		canvas.drawBitmap(maskBitmap, 0, 0, paint);
//		paint.setXfermode(null);
////		canvas.drawBitmap(sliderBitmap, 0, 0, maskPaint);
		
		canvas.drawColor(Color.WHITE);
		
		//绘制背景层  
        canvas.drawBitmap(backgroundBitmap, 0, 0, null);	
        
        //绘制进度层
        int sc = canvas.saveLayer(0, 0, backgroundBitmap.getWidth(), backgroundBitmap.getHeight(), null,  Canvas.MATRIX_SAVE_FLAG | Canvas.CLIP_SAVE_FLAG  | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG  | Canvas.FULL_COLOR_LAYER_SAVE_FLAG  | Canvas.CLIP_TO_LAYER_SAVE_FLAG);  
        canvas.drawRect(0, 0, backgroundBitmap.getWidth() / 2, backgroundBitmap.getHeight(), paint);  
        
        //绘制遮罩层
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));  
        canvas.drawBitmap(maskBitmap, 0f, 0f, paint);  
        paint.setXfermode(null);  
        canvas.restoreToCount(sc);  
		
		super.onDraw(canvas);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//计算宽度
				int realWidthSize = 0;
				int widthMode = MeasureSpec.getMode(widthMeasureSpec);//解析宽度参考类型
				int widthSize = MeasureSpec.getSize(widthMeasureSpec);//解析宽度尺寸
				switch (widthMode) {
					case MeasureSpec.AT_MOST://如果widthSize是当前视图可使用的最大宽度
						realWidthSize = frameBitmap.getWidth();
						break;
					case MeasureSpec.EXACTLY://如果widthSize是当前视图可使用的绝对宽度
						realWidthSize = widthSize;
						break;
					case MeasureSpec.UNSPECIFIED://如果widthSize对当前视图宽度的计算没有任何参考意义
						realWidthSize = frameBitmap.getWidth();
						break;
				}
				
				//计算高度
				int realHeightSize = 0;
				int heightMode = MeasureSpec.getMode(heightMeasureSpec);//解析参考类型
				int heightSize = MeasureSpec.getSize(heightMeasureSpec);//解析高度尺寸
				switch (heightMode) {
					case MeasureSpec.AT_MOST://如果heightSize是当前视图可使用的最大高度
						realHeightSize = frameBitmap.getHeight();
						break;
					case MeasureSpec.EXACTLY://如果heightSize是当前视图可使用的绝对高度
						realHeightSize = heightSize;
						break;
					case MeasureSpec.UNSPECIFIED://如果heightSize对当前视图高度的计算没有任何参考意义
						realHeightSize = frameBitmap.getHeight();
						break;
				}
				
				setMeasuredDimension(realWidthSize, realHeightSize);
	}
}