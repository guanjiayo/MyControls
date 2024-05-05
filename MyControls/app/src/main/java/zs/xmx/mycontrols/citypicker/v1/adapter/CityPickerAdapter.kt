package zs.xmx.mycontrols.citypicker.v1.adapter

import com.chad.library.adapter.base.BaseProviderMultiAdapter
import zs.xmx.mycontrols.citypicker.CityPickerEntity
import zs.xmx.mycontrols.citypicker.v1.adapter.provider.CityGroupProvider
import zs.xmx.mycontrols.citypicker.v1.adapter.provider.CityListProvider

class CityPickerAdapter : BaseProviderMultiAdapter<CityPickerEntity>() {

    init {
        addItemProvider(CityGroupProvider())
        addItemProvider(CityListProvider())
    }


    override fun getItemType(data: List<CityPickerEntity>, position: Int): Int {
        return if (data[position].initial != null) {
            TYPE_CITY_GROUP
        } else {
            TYPE_CITY_LIST
        }
    }

    companion object {
        const val TYPE_CITY_GROUP = 0
        const val TYPE_CITY_LIST = 1
    }


}