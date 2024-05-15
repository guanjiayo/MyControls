package zs.xmx.mycontrols.citypicker.v2.adapter.provider

import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import zs.xmx.mycontrols.R
import zs.xmx.mycontrols.citypicker.CityEntity
import zs.xmx.mycontrols.citypicker.v2.adapter.CityPickerAdapterV2


class HotCityProviderV2 : BaseItemProvider<CityEntity>() {

    override val itemViewType: Int = CityPickerAdapterV2.TYPE_HOT_CITY

    override val layoutId: Int = R.layout.item_hot_city
    override fun convert(helper: BaseViewHolder, item: CityEntity) {
        //https://github.com/lvlife/shoppingDemo
        with(helper) {
            Log.i("TTTT", "item: ${item.name}")//还是得按我们箱门的结构来做
            val mRecyclerView = getView<RecyclerView>(R.id.recyclerview)
            mRecyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            val adapter = GridAdapter()
            adapter.addData(item)
            mRecyclerView.adapter = adapter
        }

    }

    inner class GridAdapter :
        BaseQuickAdapter<CityEntity, BaseViewHolder>(R.layout.item_city_search) {
        override fun convert(holder: BaseViewHolder, item: CityEntity) {
            holder.getView<TextView>(R.id.tv_city_name).text = item.name
        }

    }


}