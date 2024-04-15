package zs.xmx.controls.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
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
    private lateinit var loadingView: ImageView
    private var mLabelText: TextView? = null
    private var mLabel: String? = null
    private var mDetailLabelText: TextView? = null
    private var mDetailLabel: String? = null
    private var mLabelColor = Color.WHITE
    private var mDetailColor = Color.WHITE
    private lateinit var animDrawable: AnimationDrawable
    private var mDismissTimer: Handler? = null

    override fun setLayout(): Int {
        return R.layout.dialog_loading
    }

    override fun convertView(holder: BaseDialogVH, dialog: BaseDialog) {
        //和获取动画视图
        loadingView = holder.getView(R.id.iv_loading)
        mLabelText = holder.getView(R.id.label)
        setLabel(mLabel)
        mDetailLabelText = holder.getView(R.id.detail_label)
        setDetailLabel(mDetailLabel)
        animDrawable = loadingView.background as AnimationDrawable
        animDrawable.start()
    }

    /**
     * 一级提示文本
     */
    fun setLabel(label: String?): LoadingDialog {
        mLabel = label
        mLabelText?.apply {
            visibility = if (TextUtils.isEmpty(label)) View.GONE else View.VISIBLE
            text = label
        }
        return this
    }

    /**
     * 二级提示文本
     */
    fun setDetailLabel(detailsLabel: String?): LoadingDialog {
        mDetailLabel = detailsLabel
        mDetailLabelText?.apply {
            visibility = if (TextUtils.isEmpty(detailsLabel)) View.GONE else View.VISIBLE
            text = detailsLabel
        }
        return this
    }

    /**
     * 设置自动消失时间
     * @param duration 毫秒
     */
    fun scheduleDismiss(duration: Long): LoadingDialog {
        if (mDismissTimer == null) {
            mDismissTimer = Handler(Looper.getMainLooper())
        }
        mDismissTimer?.postDelayed({
            if (isShowing) dismissAllowingStateLoss()
        }, duration)
        return this
    }

    companion object {

        fun newInstance(): LoadingDialog {
            val dialog = LoadingDialog()
            dialog.setDimAmount(0.3f)//背景暗度
            dialog.setGravity(Gravity.CENTER)//显示位置
            dialog.setAnimStyle(R.style.DialogScaleAnim)//动画样式
            return dialog
        }


    }

    /**
     * 隐藏加载对话框，动画停止
     */
    override fun dismiss() {
        animDrawable.stop()
        if (mDismissTimer != null) {
            mDismissTimer!!.removeCallbacksAndMessages(null)
            mDismissTimer = null
        }
        super.dismiss()
    }
}
