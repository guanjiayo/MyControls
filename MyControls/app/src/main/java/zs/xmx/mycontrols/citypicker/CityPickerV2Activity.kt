package zs.xmx.mycontrols.citypicker

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import zs.xmx.mycontrols.R

/**
 *  数据源就一个数组  多itemViewType实现(但标题用itemDecoration)
 * 如 手机通讯录做分组 (拿出来的数据源就是一个对象列表)
 * 如 热门城市+定位城市+城市列表(itemDecoration分组)
 *
 */
class CityPickerV2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_picker_v2)
    }
}