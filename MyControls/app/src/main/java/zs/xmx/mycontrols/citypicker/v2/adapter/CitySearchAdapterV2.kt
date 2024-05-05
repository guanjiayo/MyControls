package zs.xmx.mycontrols.citypicker.v2.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import zs.xmx.mycontrols.R
import zs.xmx.mycontrols.citypicker.CityEntity

class CitySearchAdapterV2 :
    BaseQuickAdapter<CityEntity, BaseViewHolder>(R.layout.item_city_search) {
    override fun convert(holder: BaseViewHolder, item: CityEntity) {
        holder.getView<TextView>(R.id.tv_city_name).text = item.name
    }

}