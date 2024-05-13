package zs.xmx.mycontrols.citypicker.v2.adapter.provider

import android.util.Log
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import zs.xmx.mycontrols.R
import zs.xmx.mycontrols.citypicker.CityEntity
import zs.xmx.mycontrols.citypicker.CityPickerEntity
import zs.xmx.mycontrols.citypicker.v1.adapter.CityPickerAdapter
import zs.xmx.mycontrols.citypicker.v2.adapter.CityPickerAdapterV2

class HotCityProviderV2 : BaseItemProvider<CityEntity>() {

    override val itemViewType: Int = CityPickerAdapterV2.TYPE_HOT_CITY

    override val layoutId: Int = R.layout.item_hot_city
    override fun convert(helper: BaseViewHolder, item: CityEntity) {
        //https://github.com/lvlife/shoppingDemo
        with(helper) {
            Log.i("TTTT", "item: $item")
        }
    }


}