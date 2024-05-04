package zs.xmx.mycontrols.toolbar.headbar

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import zs.xmx.mycontrols.R
import zs.xmx.mycontrols.databinding.LayoutHeaderBarBinding
import zs.xmx.mycontrols.ext.setVisible
import zs.xmx.mycontrols.ext.singleClick

class HeadBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private lateinit var mBinding: LayoutHeaderBarBinding

    //Title文字
    private var titleText: String? = null
    private var titleColor: Int? = null

    //左侧文字/图片
    private var leftText: String? = null
    private var leftColor: Int? = null
    private var leftImage: Drawable? = null

    //右侧文字/图片
    private var rightText: String? = null
    private var rightColor: Int? = null
    private var rightImage: Drawable? = null

    //是否显示分割线
    private var isShowDivide: Boolean = false

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.HeadBar)

        titleText = typedArray.getString(R.styleable.HeadBar_titleText)
        titleColor = typedArray.getColor(
            R.styleable.HeadBar_titleColor,
            ContextCompat.getColor(context, R.color.color_black)
        )

        rightText = typedArray.getString(R.styleable.HeadBar_rightText)
        rightColor = typedArray.getColor(
            R.styleable.HeadBar_rightColor,
            ContextCompat.getColor(context, R.color.color_black)
        )
        rightImage = typedArray.getDrawable(R.styleable.HeadBar_rightImage)

        leftText = typedArray.getString(R.styleable.HeadBar_leftText)
        leftColor = typedArray.getColor(
            R.styleable.HeadBar_leftColor,
            ContextCompat.getColor(context, R.color.color_black)
        )
        leftImage = typedArray.getDrawable(R.styleable.HeadBar_leftImage)

        isShowDivide = typedArray.getBoolean(R.styleable.HeadBar_showDivide, false)

        initView()
        //释放资源
        typedArray.recycle()
    }

    private fun initView() {

        //布局文件,根布局对象,是否作为根布局
        mBinding = LayoutHeaderBarBinding.inflate(LayoutInflater.from(context), this, true)
        mBinding.vDivide.setVisible(isShowDivide)

        //标题不为空，设置值
        titleText?.let {
            mBinding.mTitleTv.text = it
        }
        titleColor?.let {
            mBinding.mTitleTv.setTextColor(it)
        }

        //左侧文字不为空，设置值
        leftText?.let {
            mBinding.mLeftTv.text = it
            mBinding.mLeftTv.visibility = View.VISIBLE
        }
        leftColor?.let {
            mBinding.mLeftTv.setTextColor(it)
        }

        //左侧图片不为空，设置值
        //图片显示时,不显示文字
        leftImage?.let {
            mBinding.mLeftIv.setImageDrawable(it)
            mBinding.mLeftIv.visibility = View.VISIBLE
            mBinding.mLeftTv.visibility = View.GONE
        }

        //右侧文字不为空，设置值
        rightText?.let {
            mBinding.mRightTv.text = it
            mBinding.mRightTv.visibility = View.VISIBLE
        }

        //左侧图片不为空，设置值
        rightImage?.let {
            mBinding.mRightIv.setImageDrawable(it)
            mBinding.mRightIv.visibility = View.VISIBLE
            mBinding.mRightTv.visibility = View.GONE
        }
        rightColor?.let {
            mBinding.mRightTv.setTextColor(it)
        }
    }

    fun setLeftImageViewListener(listener: (view: ImageView) -> Unit) {
        mBinding.mLeftIv.singleClick { listener(it) }
    }

    fun setLeftTextViewListener(listener: (view: TextView) -> Unit) {
        mBinding.mLeftTv.singleClick { listener(it) }
    }

    fun setRightImageViewListener(listener: (view: ImageView) -> Unit) {
        mBinding.mRightIv.singleClick { listener(it) }
    }

    fun setRightTextViewListener(listener: (view: TextView) -> Unit) {
        mBinding.mRightTv.singleClick { listener(it) }
    }

    fun setLeftImageViewResource(resId: Int) {
        mBinding.mLeftIv.setImageResource(resId)
    }

    fun setRightImageViewResource(resId: Int) {
        mBinding.mRightIv.setImageResource(resId)
    }

    /**
     * 设置 Title 文本
     */
    fun setTitle(title: String) {
        mBinding.mTitleTv.text = title
    }

    fun setRightText(rightText: String) {
        mBinding.mRightTv.text = rightText
    }

    /*
        获取左侧 ImageView
     */
    fun getLeftImageView(): ImageView {
        return mBinding.mLeftIv
    }

    /*
        获取左侧 TextView
     */
    fun getLeftTextView(): TextView {
        return mBinding.mLeftTv
    }

    /*
        获取右侧 ImageView
     */
    fun getRightImageView(): ImageView {
        return mBinding.mRightIv
    }

    /*
        获取右侧 TextView
     */
    fun getRightTextView(): TextView {
        return mBinding.mRightTv
    }

    /*
        分割线显示
     */
    fun showDivide(isShow: Boolean) {
        mBinding.vDivide.setVisible(isShow)
    }

}