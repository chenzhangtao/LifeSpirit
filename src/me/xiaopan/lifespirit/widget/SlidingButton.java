package me.xiaopan.lifespirit.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class SlidingButton extends View implements OnTouchListener {

    private boolean mNowChecked = false;

    private boolean mOnSlip = false;

    private float mDownX, mNowX;

    private Rect mBtnOn, mBtnOff;

    private boolean isChgLsnOn = false;

    private OnChangedListener mChgLsn;

    private Bitmap mOnBg, mOffBg, mSlipBg;

    public SlidingButton(Context context) {
        super(context);
        init();
    }

    public SlidingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
//        mOnBg = BitmapFactory.decodeResource(getResources(), R.drawable.slip_bg_on);
//        mOffBg = BitmapFactory.decodeResource(getResources(), R.drawable.slip_bg_off);
//        mSlipBg = BitmapFactory.decodeResource(getResources(), R.drawable.slip_btn);
        mBtnOn = new Rect(0, 0, mSlipBg.getWidth(), mSlipBg.getHeight());
        mBtnOff = new Rect(mOffBg.getWidth() - mSlipBg.getWidth(), 0, mOffBg.getWidth(), mSlipBg.getHeight());
        setOnTouchListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Matrix matrix = new Matrix();
        Paint paint = new Paint();
        float x;

        {
            if (!mNowChecked)
                canvas.drawBitmap(mOffBg, matrix, paint);
            else
                canvas.drawBitmap(mOnBg, matrix, paint);

            if (mOnSlip)
            {
                if (mNowX >= mOnBg.getWidth())
                    x = mOnBg.getWidth() - mSlipBg.getWidth() / 2;
                else
                    x = mNowX - mSlipBg.getWidth() / 2;
            } else {
                if (mNowChecked)
                    x = mBtnOff.left;
                else
                    x = mBtnOn.left;
            }
            if (x < 0)
                x = 0;
            else if (x > mOnBg.getWidth() - mSlipBg.getWidth())
                x = mOnBg.getWidth() - mSlipBg.getWidth();
            canvas.drawBitmap(mSlipBg, x, 0, paint);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_MOVE:
                mNowX = event.getX();
                break;
            case MotionEvent.ACTION_DOWN:
                if (event.getX() > mOnBg.getWidth() || event.getY() > mOnBg.getHeight())
                    return false;
                mDownX = event.getX();
                mNowX = mDownX;
                break;
            case MotionEvent.ACTION_UP:
                mOnSlip = false;
                boolean LastChoose = mNowChecked;
                if (!mNowChecked)
                    mNowChecked = true;
                else
                    mNowChecked = false;
                if (isChgLsnOn && (LastChoose != mNowChecked))
                    mChgLsn.OnChanged(mNowChecked);
                break;
            default:

        }
        invalidate();
        return true;
    }

    public void setChecked(boolean checked) {
        if (mNowChecked != checked) {
            mNowChecked = checked;
            invalidate();
        }
    }

    public void setOnChangedListener(OnChangedListener l) {
        isChgLsnOn = true;
        mChgLsn = l;
    }

    public interface OnChangedListener {
        abstract void OnChanged(boolean CheckState);
    }
}