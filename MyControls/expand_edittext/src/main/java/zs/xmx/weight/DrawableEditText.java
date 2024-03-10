package zs.xmx.weight;
/*
 * @创建者     mqm
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2017/6/14
 * @本类描述   EditText扩展 : 设置
 * @内容说明   设置在EditText内的drawableLeft/Right相对位置以及大小
 * @补充内容   使用:
 *        <!--设置android:drawableLeft/Right属性后-->
          <!--命名空间方法设置drawable大小-->
           输入文本与drawable的间距使用android:drawablePadding="30dp"设置
 *
 * ---------------------------------     
 * @更新时间   
 * @新增内容   
 *
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import com.google.android.material.textfield.TextInputEditText;
import android.util.AttributeSet;

import zs.xmx.R;
import zs.xmx.utils.AnimationUtils;


public class DrawableEditText extends TextInputEditText {
    /**
     * 左侧图标的drawable对象
     **/
    private Drawable   mLeftDrawable;
    /**
     * 左侧图标的drawable对象宽高
     **/
    private int        mLeftDrawableWidth;
    private int        mLeftDrawableHeight;
    private TypedArray mTypedArray;
    /**
     * 右侧图标的drawable对象宽高
     **/
    private Drawable   mRightDrawable;
    private int        mRightDrawableWidth;
    private int        mRightDrawableHeight;

    public DrawableEditText(Context context) {
        this(context, null);
    }

    public DrawableEditText(Context context, AttributeSet attrs) {
        this(context, attrs, androidx.appcompat.R.attr.editTextStyle);
    }

    public DrawableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.DrawableEditText);
        initDrawable();
        //回收,避免浪费资源以及由于避免缓存报的错误
        mTypedArray.recycle();
    }

    /**
     * 获取EditText的drawableRight/Left属性
     * 设置相对位置和大小
     * <p>
     * 0,1,2,3对应drawable 的left,top,right,bottom
     * <p>
     * 如果drawable为空,会调用默认样式R.attr.editTextStyle
     */
    private void initDrawable() {
        mLeftDrawable = getCompoundDrawables()[0];
        mRightDrawable = getCompoundDrawables()[2];
        if (mLeftDrawable != null) {
            mLeftDrawableWidth = mTypedArray.getDimensionPixelOffset(R.styleable.DrawableEditText_leftDrawableWidth, mLeftDrawable.getIntrinsicWidth());
            mLeftDrawableHeight = mTypedArray.getDimensionPixelOffset(R.styleable.DrawableEditText_leftDrawableHeight, mLeftDrawable.getIntrinsicHeight());
            mLeftDrawable.setBounds(0, 0, mLeftDrawableWidth, mLeftDrawableHeight);

        }

        if (mRightDrawable != null) {
            mRightDrawableWidth = mTypedArray.getDimensionPixelOffset(R.styleable.DrawableEditText_rightDrawableWidth, mRightDrawable.getIntrinsicWidth());
            mRightDrawableHeight = mTypedArray.getDimensionPixelOffset(R.styleable.DrawableEditText_rightDrawableHeight, mRightDrawable.getIntrinsicHeight());
            mRightDrawable.setBounds(0, 0, mRightDrawableWidth, mRightDrawableHeight);
        }

        setCompoundDrawables(mLeftDrawable, null, mRightDrawable, null);

    }

    /**
     * 开始晃动动画
     */
    public void startShakeAnimation() {
        if (getAnimation() == null) {
            this.setAnimation(AnimationUtils.shakeAnimation(4));
        }
        this.startAnimation(getAnimation());
    }


}
