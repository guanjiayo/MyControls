package zs.xmx.mycontrols.shape_shadow

import com.google.android.material.shape.CornerTreatment
import com.google.android.material.shape.EdgeTreatment
import com.google.android.material.shape.ShapePath


/**
 * Author: 默小铭
 * Blog:   https://blog.csdn.net/u012792686
 * Desc:   自定义
 *
 */
class InnerCutCornerTreatment : CornerTreatment() {
    override fun getCornerPath(shapePath: ShapePath, angle: Float, f: Float, size: Float) {
        val radius = size * f
        shapePath.reset(0f, radius, 180f, 180 - angle)
        shapePath.lineTo(radius, radius)
        shapePath.lineTo(radius, 0f)
    }
}

class InnerRoundCornerTreatment : CornerTreatment() {
    override fun getCornerPath(shapePath: ShapePath, angle: Float, f: Float, size: Float) {
        val radius = size * f
        shapePath.reset(0f, radius, 180f, 180 - angle)
        shapePath.addArc(-radius, -radius, radius, radius, angle, -90f)
    }
}

class ExtraRoundCornerTreatment : CornerTreatment() {
    override fun getCornerPath(shapePath: ShapePath, angle: Float, f: Float, size: Float) {
        val radius = size * f
        shapePath.reset(0f, radius, 180f, 180 - angle)
        shapePath.addArc(-radius, -radius, radius, radius, angle, 270f)
    }
}

class ArgEdgeTreatment(val size: Float, val inside: Boolean) : EdgeTreatment() {
    override fun getEdgePath(length: Float, center: Float, f: Float, shapePath: ShapePath) {
        val radius = size * f
        shapePath.lineTo(center - radius, 0f)
        shapePath.addArc(
            center - radius, -radius,
            center + radius, radius,
            180f,
            if (inside) -180f else 180f
        )
        shapePath.lineTo(length, 0f)
    }
}

class QuadEdgeTreatment(val size: Float) : EdgeTreatment() {
    override fun getEdgePath(length: Float, center: Float, f: Float, shapePath: ShapePath) {
        shapePath.quadToPoint(center, size * f, length, 0f)
    }
}