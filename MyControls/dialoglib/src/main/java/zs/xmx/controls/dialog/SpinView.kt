package zs.xmx.controls.dialog

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import zs.xmx.controls.R

/**
 * 旋转的菊花
 */
internal class SpinView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatImageView(context, attrs) {

    private var mRotateDegrees = 0f
    private var mFrameTime = 0
    private var mNeedToUpdateView = false
    private var mUpdateViewRunnable: Runnable? = null

    init {
        setImageResource(R.drawable.rotate_daisy)
        mFrameTime = 1000 / 12
        mUpdateViewRunnable = object : Runnable {
            override fun run() {
                mRotateDegrees += 30f
                mRotateDegrees = if (mRotateDegrees < 360) mRotateDegrees else mRotateDegrees - 360
                invalidate()
                if (mNeedToUpdateView) {
                    postDelayed(this, mFrameTime.toLong())
                }
            }
        }
    }

    fun setAnimationSpeed(scale: Float) {
        mFrameTime = (1000 / 12 / scale).toInt()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.rotate(mRotateDegrees, (width / 2).toFloat(), (height / 2).toFloat())
        super.onDraw(canvas)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mNeedToUpdateView = true
        post(mUpdateViewRunnable)
    }

    override fun onDetachedFromWindow() {
        mNeedToUpdateView = false
        super.onDetachedFromWindow()
    }

}