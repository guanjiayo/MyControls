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
import kotlinx.android.synthetic.main.activity_material_shape.*
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
        view1.shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setAllCorners(RoundedCornerTreatment())
            .setAllCornerSizes(40.dp())
            .build()

        view2.shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setAllCorners(CutCornerTreatment())
            .setAllCornerSizes(50.dp())
            .build()

        view3.shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setTopLeftCorner(RoundedCornerTreatment())
            .setTopLeftCornerSize(80.dp())
            .setTopRightCorner(RoundedCornerTreatment())
            .setTopRightCornerSize(80.dp())
            .setBottomLeftCorner(RoundedCornerTreatment())
            .setBottomLeftCornerSize(80.dp())
            .build()

        view4.shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setRightEdge(TriangleEdgeTreatment(100.dp(), true))
            .build()

        view5.shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setTopLeftCorner(RoundedCornerTreatment())
            .setAllCornerSizes(50.dp())
            .build()

        view6.shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setAllCorners(RoundedCornerTreatment())
            .setAllCornerSizes(100.dp())
            .build()

        view7.shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setAllCorners(CutCornerTreatment())
            .setAllCornerSizes(100.dp())
            .build()

        view8.shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setTopLeftCorner(RoundedCornerTreatment())
            .setAllCornerSizes(50.dp())
            .setTopEdge(TriangleEdgeTreatment(24.dp(), false))
            .setBottomEdge(TriangleEdgeTreatment(24.dp(), true))
            .build()

        view9.shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setAllCorners(RoundedCornerTreatment())
            .setAllCornerSizes(24.dp())
            .setAllEdges(ArgEdgeTreatment(24.dp(), true))
            .build()

        view10.shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setAllCorners(InnerRoundCornerTreatment())
            .setAllCornerSizes(24.dp())
            .setAllEdges(ArgEdgeTreatment(24.dp(), false))
            .build()

        view11.shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setAllCorners(InnerCutCornerTreatment())
            .setAllCornerSizes(60.dp())
            .build()

        view12.shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setAllCorners(InnerRoundCornerTreatment())
            .setAllCornerSizes(60.dp())
//            .setAllEdges(QuadEdgeTreatment(40.dp()))
            .build()

        view13.shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setAllCorners(ExtraRoundCornerTreatment())
            .setAllCornerSizes(24.dp())
            .setAllEdges(ArgEdgeTreatment(24.dp(), false))
            .build()

        view14.shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setAllCorners(InnerRoundCornerTreatment())
            .setAllCornerSizes(24.dp())
            .setAllEdges(ArgEdgeTreatment(24.dp(), true))
            .build()
    }
}