package zs.xmx.controls.dialog

import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import zs.xmx.controls.R
import zs.xmx.controls.dialog.base.BaseDialog
import zs.xmx.controls.dialog.base.BaseDialogVH

/*
 * @创建者     默小铭
 * @本类描述	   LoadingDialog
 * @内容说明
 *
 */
class LoadingDialog private constructor() : BaseDialog() {
    private lateinit var mSpinView: SpinView//旋转菊花
    private var mLabelText: TextView? = null
    private var mLabel: String? = null
    private var mDetailLabelText: TextView? = null
    private var mDetailLabel: String? = null
    private var mLabelColor = Color.WHITE
    private var mDetailColor = Color.WHITE
    private var mAnimateSpeed: Int = 1//动画速度
    private var mDismissed: Boolean = false//标记位,防止重复调用dismiss
    private val mDismissTimer: Handler by lazy { Handler(Looper.getMainLooper()) }
    private var mStartTime = -1L//开始显示弹窗的时间戳

    override fun setLayout(): Int {
        return R.layout.dialog_loading
    }

    override fun convertView(holder: BaseDialogVH, dialog: BaseDialog) {
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
    fun scheduleDismiss(duration: Long, listener: (() -> Unit)? = null): LoadingDialog {
        mDismissTimer.postDelayed({
            listener?.invoke()
            dismiss()
        }, duration)
        return this
    }


    override fun show(manager: FragmentManager): BaseDialog {
        if (!isShowing) {
            mDismissed = false
            mStartTime = System.currentTimeMillis()
            super.show(manager, tag)
        }
        return this
    }

    override fun dismiss() {
        if (isShowing) {
            val diff = System.currentTimeMillis() - mStartTime
            if (diff >= MIN_DELAY_MS) {
                Log.d("TTTT", "隐藏弹窗222")
                super.dismiss()
            } else {
                if (!mDismissed) {
                    mDismissTimer.postDelayed({
                        mDismissed = false
                        Log.d("TTTT", "隐藏弹窗333")
                        super.dismiss()
                    }, MIN_DELAY_MS - diff)
                    mDismissed = true
                }
            }
        }
    }

    override fun onDestroyView() {
        mDismissed = false
        mStartTime = -1
        mDismissTimer.removeCallbacksAndMessages(null)
        super.onDestroyView()
    }

    companion object {
        private const val MIN_DELAY_MS = 500L//显示到隐藏的最小可见时间
        fun newInstance() = LoadingDialog().apply {
            setDimAmount(0.3f)//背景暗度
            setGravity(Gravity.CENTER)//显示位置
        }

    }
}
