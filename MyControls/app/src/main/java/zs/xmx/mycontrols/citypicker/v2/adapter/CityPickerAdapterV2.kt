package zs.xmx.mycontrols.citypicker.v2.adapter

import com.chad.library.adapter.base.BaseProviderMultiAdapter
import zs.xmx.mycontrols.citypicker.CityEntity
import zs.xmx.mycontrols.citypicker.v2.adapter.provider.CityListProviderV2
import zs.xmx.mycontrols.citypicker.v2.adapter.provider.HotCityProviderV2
import zs.xmx.mycontrols.citypicker.v2.adapter.provider.LocationCityProviderV2

class CityPickerAdapterV2 : BaseProviderMultiAdapter<CityEntity>() {

    init {
        addItemProvider(LocationCityProviderV2())
        addItemProvider(HotCityProviderV2())
        addItemProvider(CityListProviderV2())
    }

    override fun getItemType(data: List<CityEntity>, position: Int): Int {
        return when (position) {
            0 -> {
                TYPE_LOCATION_CITY
            }

            1 -> {
                TYPE_HOT_CITY
            }

            else -> {
                TYPE_CITY_LIST
            }
        }
    }

    companion object {
        const val TYPE_LOCATION_CITY = 0//定位
        const val TYPE_HOT_CITY = 1//热门城市
        const val TYPE_CITY_LIST = 2//城市列表
    }
}