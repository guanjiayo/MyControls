package zs.xmx.mycontrols.citypicker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dylanc.viewbinding.binding
import zs.xmx.mycontrols.citypicker.v1.CityPickerV1Activity
import zs.xmx.mycontrols.citypicker.v2.CityPickerV2Activity
import zs.xmx.mycontrols.databinding.ActivityCityPickerBinding

class CityPickerActivity : AppCompatActivity() {

    private val mBinding by binding<ActivityCityPickerBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.actionCityPickerV1.setOnClickListener {
            startActivity(Intent(this@CityPickerActivity, CityPickerV1Activity::class.java))
        }
        mBinding.actionCityPickerV2.setOnClickListener {
            startActivity(Intent(this@CityPickerActivity, CityPickerV2Activity::class.java))
        }
    }

}