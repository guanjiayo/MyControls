package zs.xmx.mycontrols.shape_shadow

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.shape.*
import zs.xmx.mycontrols.R


/**
 * Author: 默小铭
 * Blog:   https://blog.csdn.net/u012792686
 * Desc:   MaterialShapeDrawable
 *         轻松实现Shape同时支持使用差值浮动属性控制来实现角和边缘的动画
 *         本质就是一个Drawable
 */
class ShapeDrawableActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shape_drawable)
        val tvTest1 = findViewById<TextView>(R.id.test1)
        val tvTest2 = findViewById<TextView>(R.id.test2)
        val tvTest3 = findViewById<TextView>(R.id.test3)
        val tvTest4 = findViewById<TextView>(R.id.test4)
        val tvTest5 = findViewById<TextView>(R.id.test5)
        val mBtnMore = findViewById<Button>(R.id.btn_more)
        mBtnMore.setOnClickListener {
            startActivity(Intent(this,MaterialShapeActivity::class.java))
        }

        simpleUse(tvTest1)
        edge(tvTest2)
        customTreatment(tvTest3)
        unilateral(tvTest4)
        shadow(tvTest5)
    }

    /**
     * 单边处理
     */
    private fun unilateral(tvTest: TextView) {
        val shapePathModel = ShapeAppearanceModel.builder()
            .setAllCorners(RoundedCornerTreatment())
            .setAllCornerSizes(16f)
            .setRightEdge(object : TriangleEdgeTreatment(8f, false) {
                override fun getEdgePath(
                    length: Float,
                    center: Float,
                    interpolation: Float,
                    shapePath: ShapePath
                ) {
                    super.getEdgePath(length, 12f, interpolation, shapePath)
                }
            })
            .build()

        val backgroundDrawable = MaterialShapeDrawable(shapePathModel).apply {
            setTint(Color.parseColor("#bebebe"))
            paintStyle = Paint.Style.FILL
        }
        (tvTest.parent as? ViewGroup)?.clipChildren = false
        tvTest.background = backgroundDrawable
    }

    private fun customTreatment(tvTest: TextView) {
        val shapePathModel = ShapeAppearanceModel.builder()
            .setAllCorners(InnerCutCornerTreatment())
            .setAllCornerSizes(10f)
            .build()
        val backgroundDrawable = MaterialShapeDrawable(shapePathModel).apply {
            setTint(Color.parseColor("#bebebe"))
            paintStyle = Paint.Style.FILL
        }
        tvTest.background = backgroundDrawable
    }

    private fun edge(tvTest: TextView) {
        /*
         TriangleEdgeTreatment()
         超出布局边界的内容是会被裁剪的，所以这里在使用TriangleEdgeTreatment(8.dp(), true)，
         第二个参数isInside设置的是true，如果设置成false，就需要指定parent view的clipChildren属性为false了。
         (test1.parent as? ViewGroup)?.clipChildren = false
         如果是封装的自定义View，通常可以在attachToWindow中进行设置。
         在源码中，已经内置了很多不同种类的EdgeTreatment和CornerTreatment，这些基本的Edge和Corner的处理，可以满足大部分的使用场景。
         */
        val shapePathModel = ShapeAppearanceModel.builder()
            .setAllCorners(RoundedCornerTreatment())
            .setAllCornerSizes(10f)
            .setAllEdges(TriangleEdgeTreatment(8f, true))
            .build()
        val backgroundDrawable = MaterialShapeDrawable(shapePathModel).apply {
            setTint(Color.parseColor("#bebebe"))
            paintStyle = Paint.Style.FILL_AND_STROKE
            strokeWidth = 2f
        }
        tvTest.background = backgroundDrawable
    }

    private fun simpleUse(tvTest: TextView) {

        //设置左上角为圆角
        val shapePathModel1 = ShapeAppearanceModel.builder()
            .setTopLeftCorner(RoundedCornerTreatment())
            .setTopLeftCornerSize(20f)
            .build()

        //设置4个角都是圆角
        //更简写法 setAllCorners(RoundedCornerTreatment(),10f)
        val shapePathModel2 = ShapeAppearanceModel.builder()
            .setAllCorners(RoundedCornerTreatment())
            .setAllCornerSizes(10f)
            .build()
        val backgroundDrawable = MaterialShapeDrawable(shapePathModel1).apply {
            setTint(Color.parseColor("#bebebe"))
            paintStyle = Paint.Style.FILL
        }
        tvTest.background = backgroundDrawable
    }
    /**
     * 阴影方案(有API版本限制,同时不支持自定义显示阴影的样式)
     *
     * 阴影通用方案看 ShadowLayout
     */
    @SuppressLint("RestrictedApi")
    private fun shadow(tvTest: TextView) {
        //ShapeAppearanceModel.builder()可以很方便的控制边角的Shape
        val shapePathModel = ShapeAppearanceModel.builder()
            .setAllCorners(RoundedCornerTreatment())
            .setAllCornerSizes(16f)
            .build()

        //MaterialShapeDrawable实际上充当了一个Drawable的角色，用于创建一个指定Shape的背景。
        val backgroundDrawable = MaterialShapeDrawable(shapePathModel).apply {
            setTint(Color.parseColor("#ffffff"))
            paintStyle = Paint.Style.FILL
            shadowCompatibilityMode = MaterialShapeDrawable.SHADOW_COMPAT_MODE_ALWAYS
            initializeElevationOverlay(this@ShapeDrawableActivity)
            elevation = 25f
            setShadowColor(Color.parseColor("#ff0000"))
            shadowVerticalOffset = 25

        }
        (tvTest.parent as? ViewGroup)?.clipChildren = false
        tvTest.background = backgroundDrawable
    }
}