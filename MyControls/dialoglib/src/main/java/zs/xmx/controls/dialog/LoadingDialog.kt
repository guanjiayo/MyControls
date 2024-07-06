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
    private var mDismissTimer: Handler? = null
    private var mDismissed: Boolean = false //是否隐藏Dialog标记位
    private var mGraceTimer: Handler? = null
    private var mGraceTimeMs: Long = 0L //宽限时间

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
     * 宽限期
     * 如果执行任务在宽限期之前完成,则不显示弹窗
     */
    fun setGraceTime(graceTimeMs: Long): LoadingDialog {
        mGraceTimeMs = graceTimeMs
        return this
    }

    /**
     * 设置Dialog自动消失时间
     * @param duration 毫秒
     */
    fun scheduleDismiss(duration: Long, listener: (() -> Unit)? = null): LoadingDialog {
        if (mDismissTimer == null) {
            mDismissTimer = Handler(Looper.getMainLooper())
        }
        mDismissTimer!!.postDelayed({
            listener?.invoke()
            dismiss()
        }, duration)
        return this
    }


    override fun show(manager: FragmentManager): BaseDialog {
        if (!isShowing) {
            mDismissed = false
            if (mGraceTimeMs == 0L) {
                super.show(manager, LoadingDialog::class.java.simpleName)
            } else {
                if (mGraceTimer == null) {
                    mGraceTimer = Handler(Looper.getMainLooper())
                }
                mGraceTimer!!.postDelayed({
                    if (!mDismissed) {
                        super.show(manager, LoadingDialog::class.java.simpleName)
                    }

                }, mGraceTimeMs)
            }
        }
        return this
    }

    override fun dismiss() {
        mDismissed = true
        if (isShowing) {
            super.dismiss()
        }
        if (mGraceTimer != null) {
            mGraceTimer!!.removeCallbacksAndMessages(null)
            mGraceTimer = null
        }
        if (mDismissTimer != null) {
            mDismissTimer!!.removeCallbacksAndMessages(null)
            mDismissTimer = null
        }
    }

    override fun onDestroyView() {
        if (mGraceTimer != null) {
            mGraceTimer!!.removeCallbacksAndMessages(null)
            mGraceTimer = null
        }
        if (mDismissTimer != null) {
            mDismissTimer!!.removeCallbacksAndMessages(null)
            mDismissTimer = null
        }
        super.onDestroyView()
    }

    companion object {
        fun newInstance() = LoadingDialog().apply {
            setDimAmount(0.3f)//背景暗度
            setGravity(Gravity.CENTER)//显示位置
        }
    }
}
