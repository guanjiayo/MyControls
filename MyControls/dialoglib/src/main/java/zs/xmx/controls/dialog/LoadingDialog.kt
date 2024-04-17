package zs.xmx.controls.dialog

import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import zs.xmx.controls.R
import zs.xmx.controls.dialog.base.BaseDialog
import zs.xmx.controls.dialog.base.BaseDialogVH

/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @本类描述	   LoadingDialog
 * @内容说明
 *
 */
class LoadingDialog private constructor() : BaseDialog() {
    private var mBackgroundLayout: LoadingBackgroundLayout? = null
    private var mBackgroundCornerRadius: Float = 10f//背景圆角
    private var mBackgroundColor = Color.BLACK
    private lateinit var mSpinView: SpinView//旋转菊花
    private var mLabelText: TextView? = null
    private var mLabel: String? = null
    private var mDetailLabelText: TextView? = null
    private var mDetailLabel: String? = null
    private var mLabelColor = Color.WHITE
    private var mDetailColor = Color.WHITE
    private var mAnimateSpeed: Int = 1//动画速度
    private var mDismissTimer: Handler? = null

    override fun setLayout(): Int {
        return R.layout.dialog_loading
    }

    override fun convertView(holder: BaseDialogVH, dialog: BaseDialog) {
        mBackgroundLayout = holder.getView(R.id.background)
        mBackgroundLayout!!.setBaseColor(mBackgroundColor)
        mBackgroundLayout!!.setBackgroundRadius(mBackgroundCornerRadius)
        //动画视图
        mSpinView = holder.getView(R.id.spinView)
        mSpinView.setAnimationSpeed(mAnimateSpeed.toFloat())
        //一级标签
        mLabelText = holder.getView(R.id.label)
        setLabel(mLabel)
        //二级标签
        mDetailLabelText = holder.getView(R.id.detail_label)
        setDetailLabel(mDetailLabel)
    }

    /**
     * 一级提示文本
     */
    fun setLabel(label: String?, color: Int = Color.WHITE): LoadingDialog {
        mLabel = label
        mLabelColor = color
        mLabelText?.apply {
            visibility = if (TextUtils.isEmpty(label)) View.GONE else View.VISIBLE
            text = label
            setTextColor(color)
        }
        return this
    }

    /**
     * 二级提示文本
     */
    fun setDetailLabel(detailsLabel: String?, color: Int = Color.WHITE): LoadingDialog {
        mDetailLabel = detailsLabel
        mDetailColor = color
        mDetailLabelText?.apply {
            visibility = if (TextUtils.isEmpty(detailsLabel)) View.GONE else View.VISIBLE
            text = detailsLabel
            setTextColor(color)
        }
        return this
    }

    /**
     * 设置背景颜色
     * @param color ARGB color
     */
    fun setBackgroundColor(color: Int): LoadingDialog {
        mBackgroundColor = color
        return this
    }

    /**
     * 设置背景圆角
     * 默认10dp
     */
    fun setBackgroundRadius(radius: Float): LoadingDialog {
        mBackgroundCornerRadius = radius
        return this
    }

    /**
     * 设置动画速度
     */
    fun setAnimationSpeed(scale: Int): LoadingDialog {
        mAnimateSpeed = scale
        return this
    }

    /**
     * 设置Dialog自动消失时间
     * @param duration 毫秒
     */
    fun scheduleDismiss(duration: Long): LoadingDialog {
        if (mDismissTimer == null) {
            mDismissTimer = Handler(Looper.getMainLooper())
        }
        mDismissTimer?.postDelayed({
            if (isShowing) dismiss()
        }, duration)
        return this
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        if (mDismissTimer != null) {
            mDismissTimer!!.removeCallbacksAndMessages(null)
            mDismissTimer = null
        }
        super.onPause()
    }

    companion object {
        fun newInstance() = LoadingDialog().apply {
            setDimAmount(0.3f)//背景暗度
            setGravity(Gravity.CENTER)//显示位置
            setAnimStyle(R.style.DialogScaleAnim)//动画样式
        }

    }
}
