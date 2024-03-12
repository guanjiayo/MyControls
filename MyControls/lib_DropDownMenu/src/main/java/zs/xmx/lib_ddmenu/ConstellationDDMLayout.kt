package zs.xmx.lib_ddmenu

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * 也可以省略,直接注入布局,findViewById实现相关功能
 * final View constellationView = getLayoutInflater().inflate(R.layout.custom_layout, null);
 */
class ConstellationDDMLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private var mRootView: View
    private lateinit var mConstellationAdapter: ConstellationDDMAdapter

    init {
        mRootView =
            LayoutInflater.from(context).inflate(R.layout.layout_constellation_ddm, this, true)
        initAdapter()

    }

    private fun initAdapter() {
        val recyclerView = mRootView.findViewById<RecyclerView>(R.id.constellation)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        mConstellationAdapter = ConstellationDDMAdapter(context)
        recyclerView.adapter = mConstellationAdapter
    }


    fun setDataList(dataList: MutableList<String>) {
        mConstellationAdapter.setData(dataList)
    }

    fun getAdapter(): ConstellationDDMAdapter = mConstellationAdapter

}