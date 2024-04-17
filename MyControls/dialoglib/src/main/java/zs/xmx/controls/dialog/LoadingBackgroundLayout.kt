package zs.xmx.controls.dialog

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import zs.xmx.controls.R

/**
 * 加载弹窗背景视图
 */
class LoadingBackgroundLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private var mCornerRadius = 0f
    private var mBackgroundColor = 0

    init {
        val color = ContextCompat.getColor(context, R.color.loadingBackground_default_color)
        initBackground(color, mCornerRadius)
    }

    private fun initBackground(color: Int, cornerRadius: Float) {
        val drawable = GradientDrawable()
        drawable.shape = GradientDrawable.RECTANGLE
        drawable.setColor(color)
        drawable.cornerRadius = cornerRadius
        background = drawable
    }

    fun setBackgroundRadius(radius: Float) {
        mCornerRadius = dp2px(context, radius).toFloat()
        initBackground(mBackgroundColor, mCornerRadius)
    }

    fun setBaseColor(color: Int) {
        mBackgroundColor = color
        initBackground(mBackgroundColor, mCornerRadius)
    }

    /**
     * dp转px
     */
    private fun dp2px(context: Context, dipValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }

}