package me.xiaopan.lifespirit.widget;

import me.xiaopan.androidlibrary.util.Logger;
import me.xiaopan.lifespirit2.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;

@SuppressLint("DrawAllocation")
public class SlidingButton2 extends View implements OnGestureListener, OnDoubleTapListener{
	private GestureDetector gestureDetector;
	private Bitmap maskBitmap;
	private Bitmap frameBitmap;
	private Bitmap backgroundBitmap;
	private Bitmap sliderBitmap;
	private Paint paint;
	private boolean on;

	public SlidingButton2(Context context) {
		super(context);
		gestureDetector = new GestureDetector(getContext(), this);
		gestureDetector.setOnDoubleTapListener(this);
		maskBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.btn_sliding_mask);
		frameBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.btn_sliding_frame);
		backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.btn_sliding_background);
		sliderBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.btn_sliding_slilder_normal);
		paint = new Paint();
		paint.setFilterBitmap(false);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		//创建一个新的全透明图层，大小同当前视图的大小一样
        canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.MATRIX_SAVE_FLAG | Canvas.CLIP_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.FULL_COLOR_LAYER_SAVE_FLAG | Canvas.CLIP_TO_LAYER_SAVE_FLAG);
        
        //绘制背景层
        canvas.drawBitmap(backgroundBitmap, isOn()?(0-(backgroundBitmap.getWidth() - getWidth())):0, 0, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(maskBitmap, 0, 0, paint);
        paint.setXfermode(null);
        
        //绘制框架层
        canvas.drawBitmap(frameBitmap, 0, 0, paint);

        //绘制按钮层
        canvas.drawBitmap(sliderBitmap, isOn()?(0-(backgroundBitmap.getWidth() - getWidth())):0, 0, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(maskBitmap, 0, 0, paint);
        paint.setXfermode(null);
        
        //合并图层
        canvas.restore();

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
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return gestureDetector.onTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		Logger.w("按下：X="+e.getX()+"；Y="+e.getY());
		return true;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		Logger.w("ShowPress：X="+e.getX()+"；Y="+e.getY());
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		Logger.w("SingleTapUp：X="+e.getX()+"；Y="+e.getY());
		setOn(!isOn());
		return true;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		Logger.w("Scroll：distanceX="+distanceX+"；distanceY="+distanceY);
		return true;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		Logger.w("LongPress：X="+e.getX()+"；Y="+e.getY());
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		Logger.w("Fling：velocityX="+velocityX+"；velocityY="+velocityY);
		return true;
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
		Logger.w("SingleTapConfirmed：X="+e.getX()+"；Y="+e.getY());
		return true;
   }

   @Override
   public boolean onDoubleTap(MotionEvent e) {
        Logger.w("DoubleTap：X="+e.getX()+"；Y="+e.getY());
        return true;
   }

   @Override
   public boolean onDoubleTapEvent(MotionEvent e) {
        Logger.w("DoubleTapEvent：X="+e.getX()+"；Y="+e.getY());
        return true;
   }

	public boolean isOn() {
		return on;
	}
	
	public void setOn(boolean on) {
		this.on = on;
		invalidate();
	}
}
