package zs.xmx.mycontrols.citypicker

data class City(
    val code: String,
    val name: String,
    val pinyin: String,
    val label: String
)

data class CityPickerEntity(
    val initial: String? = null,
    val currentCity: String? = null,
    val city: City? = null
)

