package zs.xmx.mycontrols.shape_shadow

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.internal.ViewUtils
import com.google.android.material.shape.CutCornerTreatment
import com.google.android.material.shape.RoundedCornerTreatment
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.shape.TriangleEdgeTreatment
import zs.xmx.mycontrols.R

class MaterialShapeActivity : AppCompatActivity() {

    @SuppressLint("RestrictedApi")
    fun Int.dp(): Float = ViewUtils.dpToPx(this@MaterialShapeActivity, this)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material_shape)
        findViewById<ShapeTextView>(R.id.view1).shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setAllCorners(RoundedCornerTreatment())
            .setAllCornerSizes(40.dp())
            .build()

        findViewById<ShapeTextView>(R.id.view2).shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setAllCorners(CutCornerTreatment())
            .setAllCornerSizes(50.dp())
            .build()

        findViewById<ShapeTextView>(R.id.view3).shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setTopLeftCorner(RoundedCornerTreatment())
            .setTopLeftCornerSize(80.dp())
            .setTopRightCorner(RoundedCornerTreatment())
            .setTopRightCornerSize(80.dp())
            .setBottomLeftCorner(RoundedCornerTreatment())
            .setBottomLeftCornerSize(80.dp())
            .build()

        findViewById<ShapeTextView>(R.id.view4).shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setRightEdge(TriangleEdgeTreatment(100.dp(), true))
            .build()

        findViewById<ShapeTextView>(R.id.view5).shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setTopLeftCorner(RoundedCornerTreatment())
            .setAllCornerSizes(50.dp())
            .build()

        findViewById<ShapeTextView>(R.id.view6).shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setAllCorners(RoundedCornerTreatment())
            .setAllCornerSizes(100.dp())
            .build()

        findViewById<ShapeTextView>(R.id.view7).shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setAllCorners(CutCornerTreatment())
            .setAllCornerSizes(100.dp())
            .build()

        findViewById<ShapeTextView>(R.id.view8).shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setTopLeftCorner(RoundedCornerTreatment())
            .setAllCornerSizes(50.dp())
            .setTopEdge(TriangleEdgeTreatment(24.dp(), false))
            .setBottomEdge(TriangleEdgeTreatment(24.dp(), true))
            .build()

        findViewById<ShapeTextView>(R.id.view9).shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setAllCorners(RoundedCornerTreatment())
            .setAllCornerSizes(24.dp())
            .setAllEdges(ArgEdgeTreatment(24.dp(), true))
            .build()

        findViewById<ShapeTextView>(R.id.view10).shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setAllCorners(InnerRoundCornerTreatment())
            .setAllCornerSizes(24.dp())
            .setAllEdges(ArgEdgeTreatment(24.dp(), false))
            .build()

        findViewById<ShapeTextView>(R.id.view11).shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setAllCorners(InnerCutCornerTreatment())
            .setAllCornerSizes(60.dp())
            .build()

        findViewById<ShapeTextView>(R.id.view12).shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setAllCorners(InnerRoundCornerTreatment())
            .setAllCornerSizes(60.dp())
//            .setAllEdges(QuadEdgeTreatment(40.dp()))
            .build()

        findViewById<ShapeTextView>(R.id.view13).shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setAllCorners(ExtraRoundCornerTreatment())
            .setAllCornerSizes(24.dp())
            .setAllEdges(ArgEdgeTreatment(24.dp(), false))
            .build()

        findViewById<ShapeTextView>(R.id.view14).shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setAllCorners(InnerRoundCornerTreatment())
            .setAllCornerSizes(24.dp())
            .setAllEdges(ArgEdgeTreatment(24.dp(), true))
            .build()
    }
}