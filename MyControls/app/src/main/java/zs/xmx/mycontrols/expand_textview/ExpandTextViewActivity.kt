package zs.xmx.mycontrols.expand_textview

import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.*
import android.text.style.AlignmentSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import zs.xmx.mycontrols.R

/**
 * 这个考虑的比较全:
 * 直接手撸TextView方案(可以再TextView 最后一行加...展开)
 * 1. https://www.jianshu.com/p/348fa44a4ca0
 * 2. https://www.jianshu.com/p/53d47c54177e
 * 3. https://github.com/MZCretin/ExpandableTextView
 * 对应blog  https://juejin.cn/post/6844903667158482952
 * RecycleView内使用一定要记住展开收起状态,有可能造成空白或者显示异常问题
 * 方案二:
 * 两个 TextView 拼接  后面做个ExpandTextLayout
 * https://github.com/Manabu-GT/ExpandableTextView
 * https://www.jb51.net/article/114607.htm
 *
 */
class ExpandTextViewActivity : AppCompatActivity(), View.OnClickListener {

    private val maxLine = 3
    private lateinit var elipseString: SpannableString //收起的文字
    private lateinit var notElipseString: SpannableString//展开的文字

    private lateinit var tv: TextView

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expand_text_view)
        tv = findViewById(R.id.expanded_text)
        val content =
            """
            在全球，随着Flutter被越来越多的知名公司应用在自己的商业APP中，Flutter这门新技术也逐渐进入了移动开发者的视野，尤其是当Google在2018年IO大会上发布了第一个Preview版本后，国内刮起来一股学习Flutter的热潮。
            
            为了更好的方便帮助中国开发者了解这门新技术，我们，Flutter中文网，前后发起了Flutter翻译计划、Flutter开源计划，前者主要的任务是翻译Flutter官方文档，后者则主要是开发一些常用的包来丰富Flutter生态，帮助开发者提高开发效率。而时至今日，这两件事取得的效果还都不错！
            """.trimIndent()
        tv.text = content


        //获取TextView的画笔对象
        val paint: TextPaint = tv.paint
        //每行文本的布局宽度
        val width = resources.displayMetrics.widthPixels - dip2px(this, 0f)
        //实例化StaticLayout 传入相应参数
        /*
        CharSequence source：需要分行的字符串
        int bufstart：需要分行的字符串从第几个位置开始
        int bufend：需要分行的字符串到哪里结束
        TextPaint paint：画笔对象
        int outerwidth：layout的宽度，字符超出宽度时自动换行，也就是内容要显示的宽度
        Alignment align：对齐方式，有 ALIGN_CENTER、ALIGN_NORMAL、ALIGN_OPPOSITE 三种
        float spacingmult：行间距倍数，相当于android:lineSpacingMultiplier
        float spacingadd：额外增加的行间距，相当于android:lineSpacingExtra
        boolean includepad：是否包含padding
        TextUtils.TruncateAt ellipsize：省略的位置，TruncateAt是一个enum，有START、MIDDLE、END、MARQUEE（跑马灯），还有END_SMALL但是被隐藏了
        int ellipsizedWidth：开始省略的位置

         */
//        val staticLayout =
//            StaticLayout(content, paint, width, Layout.Alignment.ALIGN_NORMAL, 1f, 0f, false)

        val staticLayout = StaticLayout.Builder.obtain(content, 0, content.length, paint, width)
            .setAlignment(Layout.Alignment.ALIGN_NORMAL)
            .setLineSpacing(1f, 0f)
            .setIncludePad(false)
            .build()
        //判断content是行数是否超过最大限制行数3行
        if (staticLayout.lineCount > maxLine) {
            //定义展开后的文本内容
            val string1 = "$content    收起"
            notElipseString = SpannableString(string1)
            //给收起两个字设成蓝色
            notElipseString.setSpan(
                ForegroundColorSpan(Color.parseColor("#0079e2")),
                string1.length - 2,
                string1.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            //"收起" 右对齐
//            val alignmentSpan=AlignmentSpan.Standard(Layout.Alignment.ALIGN_OPPOSITE)
//            notElipseString.setSpan(alignmentSpan,notElipseString.length-2,notElipseString.length,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            //获取到第三行最后一个文字的下标
            val index: Int = staticLayout.getLineStart(maxLine) - 1
            //定义收起后的文本内容
            val substring = content.substring(0, index - 2) + "..." + "更多"
            elipseString = SpannableString(substring)
            //给查看全部设成蓝色
            elipseString.setSpan(
                ForegroundColorSpan(Color.parseColor("#0079e2")),
                substring.length - 5,
                substring.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            //设置收起后的文本内容
            tv.text = elipseString
            tv.setOnClickListener(this)
            //将textview设成选中状态 true用来表示文本未展示完全的状态,false表示完全展示状态，用于点击时的判断
            tv.isSelected = true
        } else {
            //没有超过 直接设置文本
            tv.text = content
            tv.setOnClickListener(null)
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dip2px(mContext: Context, dpValue: Float): Int {
        val scale: Float = mContext.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    override fun onClick(v: View?) {
        if (v!!.id == R.id.expanded_text) {
            if (v.isSelected) {
                //如果是收起的状态
                tv.text = notElipseString
                tv.isSelected = false
            } else {
                //如果是展开的状态
                tv.text = elipseString
                tv.isSelected = true
            }
        }
    }
}