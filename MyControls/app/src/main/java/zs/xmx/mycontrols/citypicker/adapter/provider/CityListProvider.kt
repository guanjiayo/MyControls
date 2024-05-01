package zs.xmx.mycontrols.citypicker.adapter.provider

import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import zs.xmx.mycontrols.R
import zs.xmx.mycontrols.citypicker.CityPickerEntity
import zs.xmx.mycontrols.citypicker.adapter.CityPickerAdapter

class CityListProvider : BaseItemProvider<CityPickerEntity>() {

    override val itemViewType: Int = CityPickerAdapter.TYPE_CITY_LIST

    override val layoutId: Int = R.layout.item_city_list
    override fun convert(helper: BaseViewHolder, item: CityPickerEntity) {
        with(helper) {
            item.city?.let { city ->
                setText(R.id.tv_city_name, city.name)
            }
        }
    }


}