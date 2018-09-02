package zs.xmx.weight;
/*
 * @创建者     mqm
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2017/6/14
 * @本类描述
 * @内容说明  这里的图片建议使用矢量图
 * @补充内容  这个类只是补充:
 *
 *   1.解决当使用TextInputLayout的 app:passwordToggleEnabled="true" 属性后,
 *   使用drawableLeft属性不能设置大小的问题
 *
 *   2.EditText的setError()与TextInputLayout的passwordToggle布局冲突,建议都用TextInputLayout的
 *
 * ---------------------------------
 * @更新时间
 * @新增内容
 *
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;

import zs.xmx.R;


public class PasswordEditText extends ClearEditText {

    //资源
    private final int INVISIBLE = R.drawable.close;
    private final int VISIBLE   = R.drawable.open;
    //按钮宽度dp
    private int    mWidth;
    //按钮的bitmap
    private Bitmap mBitmap_invisible;
    private Bitmap mBitmap_visible;
    //间隔
    private int    Interval;
    //内容是否可见
    private boolean isVisible = false;


    public PasswordEditText(final Context context) {
        this(context, null);
    }

    public PasswordEditText(final Context context, final AttributeSet attrs) {
        this(context, attrs, R.attr.editTextStyle);
    }

    public PasswordEditText(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        //设置EditText文本为隐藏的(注意！需要在setSingleLine()之后调用)
        setTransformationMethod(PasswordTransformationMethod.getInstance());

        mWidth = getWidth_clear();
        Interval = getInterval();
        addRight(mWidth + Interval);
        mBitmap_invisible = createBitmap(INVISIBLE, context);
        mBitmap_visible = createBitmap(VISIBLE, context);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int right = getWidth() + getScrollX() - Interval;
        int left = right - mWidth;
        int top = (getHeight() - mWidth) / 2;
        int bottom = top + mWidth;
        Rect rect = new Rect(left, top, right, bottom);

        if (isVisible) {
            canvas.drawBitmap(mBitmap_visible, null, rect, null);
        } else {
            canvas.drawBitmap(mBitmap_invisible, null, rect, null);
        }
    }

    /**
     * 改写父类的方法
     */
    @Override
    protected void drawClear(int translationX, Canvas canvas) {
        float scale = 1f - (float) (translationX) / (float) (getWidth_clear() + Interval);
        int right = (int) (getWidth() + getScrollX() - Interval - mWidth - Interval - getWidth_clear() * (1f - scale) / 2f);
        int left = (int) (getWidth() + getScrollX() - Interval - mWidth - Interval - getWidth_clear() * (scale + (1f - scale) / 2f));
        int top = (int) ((getHeight() - getWidth_clear() * scale) / 2);
        int bottom = (int) (top + getWidth_clear() * scale);
        Rect rect = new Rect(left, top, right, bottom);
        canvas.drawBitmap(getBitmap_clear(), null, rect, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            boolean touchable = (getWidth() - mWidth - Interval < event.getX()) && (event.getX() < getWidth() - Interval);
            if (touchable) {
                isVisible = !isVisible;
                if (isVisible) {
                    //设置EditText文本为可见的
                    setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //设置EditText文本为隐藏的
                    setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        }
        return super.onTouchEvent(event);
    }
}
