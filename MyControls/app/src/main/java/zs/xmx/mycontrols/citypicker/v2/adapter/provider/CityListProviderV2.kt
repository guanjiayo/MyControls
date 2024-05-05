package zs.xmx.mycontrols.citypicker.v2.adapter.provider

import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import zs.xmx.mycontrols.R
import zs.xmx.mycontrols.citypicker.CityEntity
import zs.xmx.mycontrols.citypicker.CityPickerEntity
import zs.xmx.mycontrols.citypicker.v1.adapter.CityPickerAdapter
import zs.xmx.mycontrols.citypicker.v2.adapter.CityPickerAdapterV2

class CityListProviderV2 : BaseItemProvider<CityEntity>() {

    override val itemViewType: Int = CityPickerAdapterV2.TYPE_CITY_LIST

    override val layoutId: Int = R.layout.item_city_list
    override fun convert(helper: BaseViewHolder, item: CityEntity) {
        with(helper) {
            setText(R.id.tv_city_name, item.name)
        }
    }


}