package zs.xmx.mycontrols.citypicker.adapter.provider

import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import zs.xmx.mycontrols.R
import zs.xmx.mycontrols.citypicker.CityPickerEntity
import zs.xmx.mycontrols.citypicker.adapter.CityPickerAdapter

class CityGroupProvider : BaseItemProvider<CityPickerEntity>() {

    override val itemViewType: Int = CityPickerAdapter.TYPE_CITY_GROUP

    override val layoutId: Int = R.layout.item_city_croup
    override fun convert(helper: BaseViewHolder, item: CityPickerEntity) {
        with(helper) {
            setText(R.id.title_group, item.initial)
        }
    }

}