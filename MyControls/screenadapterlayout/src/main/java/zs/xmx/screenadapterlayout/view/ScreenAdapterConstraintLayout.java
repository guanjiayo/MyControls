package zs.xmx.screenadapterlayout.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import zs.xmx.screenadapterlayout.utils.UIUtils;

/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @创建时间   2018/7/11 14:57
 * @本类描述	  自定义控件解决屏幕适配问题
 * @内容说明   测量该布局下所有子控件,然后宽高乘以我们给定的比例系数
 *
 * //todo 其他Layout同理
 *
 */
public class ScreenAdapterConstraintLayout extends ConstraintLayout {

    public ScreenAdapterConstraintLayout(Context context) {
        this(context, null);
    }

    public ScreenAdapterConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScreenAdapterConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    static boolean isFlag = true;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //todo 在测量时加一个判断,横竖屏,异形屏

        if (isFlag) {
            int count = this.getChildCount();
            float scaleX = UIUtils.getInstance(this.getContext()).getHorizontalScaleValue();
            float scaleY = UIUtils.getInstance(this.getContext()).getVerticalScaleValue();

            Log.i("testbarry", "x系数:" + scaleX);
            Log.i("testbarry", "y系数:" + scaleY);
            for (int i = 0; i < count; i++) {
                View child = this.getChildAt(i);

                //代表的是当前控件下所有子控件的属性列表
                LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
                layoutParams.width = (int) (layoutParams.width * scaleX);
                layoutParams.height = (int) (layoutParams.height * scaleY);
                layoutParams.rightMargin = (int) (layoutParams.rightMargin * scaleX);
                layoutParams.leftMargin = (int) (layoutParams.leftMargin * scaleX);
                layoutParams.topMargin = (int) (layoutParams.topMargin * scaleY);
                layoutParams.bottomMargin = (int) (layoutParams.bottomMargin * scaleY);
            }
            isFlag = false;
        }


        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
