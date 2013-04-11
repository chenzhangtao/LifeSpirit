package me.xiaopan.lifespirit.widget;

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
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Scroller;

@SuppressLint("DrawAllocation")
public class SlidingButton2 extends View implements OnGestureListener, OnDoubleTapListener{
	private static final int DURATION = 300;
	private static final int MIN_ROLLING_DISTANCE = 30;//滚动最小生效距离
	private GestureDetector gestureDetector;
	private Bitmap maskBitmap;
	private Bitmap frameBitmap;
	private Bitmap backgroundBitmap;
	private Bitmap sliderBitmap;
	private Paint paint;
	private boolean on;
	private int left;
	private int onLeft;
	private int offLeft;
	private Scroller scroller;
	private int scrollDistanceCount;
	private boolean needHandle;//当在一组时件中发生了滚动操作时，在弹起或者取消的时候就需要根据滚动的距离来切换状态或者回滚

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
		scroller = new Scroller(getContext(), new AccelerateDecelerateInterpolator());
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		//创建一个新的全透明图层，大小同当前视图的大小一样
        canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.MATRIX_SAVE_FLAG | Canvas.CLIP_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.FULL_COLOR_LAYER_SAVE_FLAG | Canvas.CLIP_TO_LAYER_SAVE_FLAG);
        
        //绘制背景层
        canvas.drawBitmap(backgroundBitmap, left, 0, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(maskBitmap, 0, 0, paint);
        paint.setXfermode(null);
        
        //绘制框架层
        canvas.drawBitmap(frameBitmap, 0, 0, paint);

        //绘制按钮层
        canvas.drawBitmap(sliderBitmap, left, 0, paint);
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
		
		onLeft = -1 * (backgroundBitmap.getWidth() - getWidth());
	}
	
	@Override
	public void computeScroll() {
		if(scroller.computeScrollOffset()){
			left = scroller.getCurrX();
			invalidate();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		gestureDetector.onTouchEvent(event);
		//如果允许处理,并且当前事件使弹起或者取消
		if(needHandle && (event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP)){
			//如果本次滚动的距离超过的最小生效距离，就切换状态，否则就回滚
			if(Math.abs(scrollDistanceCount) >= MIN_ROLLING_DISTANCE){
				setState(scrollDistanceCount > 0, left);
			}else{
				setState(isOn(), left);
			}
			needHandle = false;
		}
		return true;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		scrollDistanceCount = 0;
		needHandle = false;
		return true;
	}

	@Override
	public void onShowPress(MotionEvent e) {
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		switchState();
		return true;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		needHandle = true;
		scrollDistanceCount += distanceX;
		left -= distanceX;
		if(left >= offLeft){
			left = offLeft;
		}else if(left <= onLeft){
			left = onLeft;
		}
		invalidate();
		return true;
	}

	@Override
	public void onLongPress(MotionEvent e) {
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		needHandle = false;
		setState(e2.getX() < e1.getX(), left);
		return true;
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
		return true;
   }

   @Override
   public boolean onDoubleTap(MotionEvent e) {
        return true;
   }

   @Override
   public boolean onDoubleTapEvent(MotionEvent e) {
        return true;
   }

	public boolean isOn() {
		return on;
	}
	
	public void setOn(boolean on) {
		this.on = on;
	}
	
	/**
	 * 滚动
	 * @param startX 开始X坐标
	 * @param endX 结束Y坐标
	 */
	private void scroll(int startX, int endX){
		if(startX != endX){
			scroller.startScroll(startX, 0, endX - startX, 0, DURATION);
			invalidate();
		}
	}
	
	/**
	 * 设置状态
	 * @param on 开启还是关闭
	 * @param startX 开始滚动的位置
	 */
	private void setState(boolean on, int startX){
		setOn(on);
		//如果是要开启
		if(isOn()){
			scroll(startX, onLeft);
		}else{
			scroll(startX, offLeft);
		}
	}
	
	/**
	 * 设置状态
	 * @param on 开启还是关闭
	 */
	private void setState(boolean on){
		setState(on, on?offLeft:onLeft);
	}
	
	/**
	 * 切换状态
	 */
	private void switchState(){
		setState(!isOn());
	}
}