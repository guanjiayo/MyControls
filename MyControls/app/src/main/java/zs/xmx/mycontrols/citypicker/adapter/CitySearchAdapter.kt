package zs.xmx.mycontrols.citypicker.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import zs.xmx.mycontrols.R
import zs.xmx.mycontrols.citypicker.CityPickerEntity

class CitySearchAdapter :
    BaseQuickAdapter<CityPickerEntity, BaseViewHolder>(R.layout.item_city_search) {
    override fun convert(holder: BaseViewHolder, item: CityPickerEntity) {
        holder.getView<TextView>(R.id.tv_city_name).text = item.city?.name
    }

}