package zs.xmx.mycontrols.citypicker

//方案一: json数据是嵌套数组
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

//方案二: json数据就一个数组(推荐)
//ps: 建议如果后台返回的是方案一的数据,也转成方案二的结构,方便排序查询
data class CityEntity(
    val initial: String = "",//首字母(分组标题)
    val code: String = "",
    val name: String = "",
    val pinyin: String = "",//全拼
    val label: String = ""
)