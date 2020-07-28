package zs.xmx.progressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ProgressBar;

/*
 * Author: 默小铭
 * Blog:   https://blog.csdn.net/u012792686
 * Desc:   自定义水平进度条
 *
 */
public class HorizontalProgressbar extends ProgressBar {
    String TAG = "HorizontalProgressbar";
    //定义默认值
    private static final int DEFAULT_TEXT_SIZE = 10;//sp
    private static final int DEFAULT_TEXT_COLOR = 0xFFFC00D1;
    private static final int DEFAULT_COLOR_UNREACH = 0xFFD3D6DA;
    private static final int DEFAULT_HEIGHT_UNREACH = 3;//dp
    private static final int DEFAULT_COLOR_REACH = DEFAULT_TEXT_COLOR;
    private static final int DEFAULT_HEIGHT_REACH = 3;//dp
    private static final int DEFAULT_TEXT_OFFSET = 10;//dp
    //定义变量
    protected int mTextSize = sp2px(DEFAULT_TEXT_SIZE);
    protected int mTextColor = DEFAULT_TEXT_COLOR;
    protected int mUnReachColor = DEFAULT_COLOR_UNREACH;
    protected int mUnReachHeight = dp2px(DEFAULT_HEIGHT_UNREACH);
    protected int mReachColor = DEFAULT_COLOR_REACH;
    protected int mReachHeight = dp2px(DEFAULT_HEIGHT_REACH);
    protected int mTextOffset = dp2px(DEFAULT_TEXT_OFFSET);

    //画笔
    protected Paint mPaint = new Paint();
    private int   mRealWidth;


    public HorizontalProgressbar(Context context) {
        this(context,null);
        Log.i(TAG,"一个参数构造方法");
    }

    public HorizontalProgressbar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
        Log.i(TAG,"两个参数构造方法");
    }

    public HorizontalProgressbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.i(TAG,"三个参数构造方法");
        obtainStyledAttrs(attrs);

    }
    /*
     * 获取自定义属性
     * */
    private void obtainStyledAttrs(AttributeSet attrs) {

        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.HorizontalProgressbar);

        mTextSize = (int)ta.
                getDimension(
                        R.styleable.HorizontalProgressbar_progress_text_size,
                        mTextSize);

        mTextColor = ta.
                getColor(
                        R.styleable.HorizontalProgressbar_progress_text_color,
                        mTextColor);

        mTextOffset = (int) ta.
                getDimension(
                        R.styleable.HorizontalProgressbar_progress_text_offset,
                        mTextOffset);
        mUnReachColor = ta.
                getColor(
                        R.styleable.HorizontalProgressbar_progress_unreach_color,
                        mUnReachColor);
        mUnReachHeight = (int)ta.
                getDimension(R.styleable.HorizontalProgressbar_progress_unreach_height,
                        mUnReachHeight);

        mReachColor = ta.
                getColor(
                        R.styleable.HorizontalProgressbar_progress_reach_color,
                        mReachColor);
        mReachHeight = (int)ta.
                getDimension(
                        R.styleable.HorizontalProgressbar_progress_reach_height,
                        mReachHeight);
        mPaint.setTextSize(mTextSize);
        ta.recycle();
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //开始时进入两次
        //int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthVal = MeasureSpec.getSize(widthMeasureSpec);

        int height =  measureHeight(heightMeasureSpec);

        setMeasuredDimension(widthVal,height);

        mRealWidth = getMeasuredWidth()-getPaddingLeft()-getPaddingRight();
        Log.i(TAG,"mRealWidth = "+mRealWidth);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {

        canvas.save();
        canvas.translate(getPaddingLeft(),getHeight()/2);

        boolean noNeedUnReach = false;
        //draw reach bar
        String text = getProgress()+"%";
        int textWidth = (int)mPaint.measureText(text);
        float radio = getProgress()*1.0f/getMax();
        float progressX = radio * mRealWidth;

        if(progressX + textWidth >mRealWidth){
            progressX = mRealWidth - textWidth;
            noNeedUnReach = true;//不需要绘制后半段
        }

        float endX = radio*mRealWidth - mTextOffset/2;
        if(endX >0 ){
            mPaint.setColor(mReachColor);
            mPaint.setStrokeWidth(mReachHeight);
            canvas.drawLine(0,0,progressX,0,mPaint);
        }
        //draw text
        mPaint.setColor(mTextColor);
        int y = (int)(-(mPaint.descent()+mPaint.ascent())/2);
        canvas.drawText(text,progressX+ mTextOffset/2 ,y,mPaint);
        //draw unreach bar
        if(!noNeedUnReach){
            float start = progressX + mTextOffset/2 + textWidth;
            mPaint.setColor(mUnReachColor);
            mPaint.setStrokeWidth(mReachHeight);
            canvas.drawLine(start + mTextOffset/2,0,mRealWidth,0,mPaint);
        }

        canvas.restore();


    }

    private int measureHeight(int heightMeasureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        Log.i(TAG,"mode = "+mode);
        if(mode == MeasureSpec.EXACTLY){
            result = size;
        }else{
            int textHeight = (int) (mPaint.descent()-mPaint.ascent());
            result = getPaddingTop()
                    +getPaddingBottom()
                    +Math.max(Math.max(mReachHeight,mUnReachHeight),
                    Math.abs(textHeight));
            if(mode == MeasureSpec.AT_MOST){
                result  = Math.min(result,size);
            }
        }
        Log.i(TAG,"result = "+result);
        return result;
    }

    private int dp2px(int dpVal){
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dpVal,
                getResources().getDisplayMetrics());
    }
    private int sp2px(int spVal){
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,spVal,
                getResources().getDisplayMetrics());
    }


}
