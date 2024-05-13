package zs.xmx.mycontrols.citypicker.v2

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dylanc.viewbinding.binding
import org.json.JSONArray
import zs.xmx.mycontrols.citypicker.CityEntity
import zs.xmx.mycontrols.citypicker.v1.adapter.CityPickerAdapter
import zs.xmx.mycontrols.citypicker.v1.adapter.CitySearchAdapter
import zs.xmx.mycontrols.citypicker.v2.adapter.CityPickerAdapterV2
import zs.xmx.mycontrols.citypicker.v2.adapter.CitySearchAdapterV2
import zs.xmx.mycontrols.databinding.ActivityCityPickerV2Binding
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 *  数据源就一个数组  多itemViewType实现(但标题用itemDecoration)
 * 如 手机通讯录做分组 (拿出来的数据源就是一个对象列表)
 * 如 热门城市+定位城市+城市列表(itemDecoration分组)
 *
 */
class CityPickerV2Activity : AppCompatActivity() {

    private val mBinding by binding<ActivityCityPickerV2Binding>()

    private val mCityPickerList = mutableListOf<CityEntity>()
    private val mSearchCityList = mutableListOf<CityEntity>()//搜索结果

    private lateinit var mCityPickerAdapter: CityPickerAdapterV2
    private lateinit var layoutManager: LinearLayoutManager

    private lateinit var mCitySearchAdapter: CitySearchAdapterV2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAdapter()
        initData()
        initEvent()
    }


    private fun initAdapter() {
        //城市列表
        mCityPickerAdapter = CityPickerAdapterV2()
        layoutManager = LinearLayoutManager(this)
        mBinding.rvCityPicker.isNestedScrollingEnabled = false
        mBinding.rvCityPicker.layoutManager = layoutManager
        mBinding.rvCityPicker.adapter = mCityPickerAdapter
        //城市列表(搜索)
        mCitySearchAdapter = CitySearchAdapterV2()
        mBinding.rvCitySearch.layoutManager = LinearLayoutManager(this)
        mBinding.rvCitySearch.isNestedScrollingEnabled = false
        mBinding.rvCitySearch.adapter = mCitySearchAdapter
    }

    private fun initData() {
        mCityPickerList.add(CityEntity(initial = "当前定位城市", name = "正在定位中..."))
        mCityPickerList.add(CityEntity(initial = "热门城市", name = "深圳"))
        mCityPickerList.add(CityEntity(initial = "热门城市", name = "北京"))
        mCityPickerList.add(CityEntity(initial = "热门城市", name = "上海"))
        mCityPickerList.add(CityEntity(initial = "热门城市", name = "广州"))
        mCityPickerList.add(CityEntity(initial = "热门城市", name = "中山"))
        val jsonString = getJson(this@CityPickerV2Activity, "city2.json")
        val jsonArray = JSONArray(jsonString)
        for (i in 0 until jsonArray.length()) {
            val cityObject = jsonArray.getJSONObject(i)
            val initial = cityObject.optString("initial")
            val code = cityObject.optString("code")
            val name = cityObject.optString("name")
            val pinyin = cityObject.optString("pinyin")
            val label = cityObject.optString("label")
            mCityPickerList.add(CityEntity(initial, code, name, pinyin, label))
        }
        mCityPickerAdapter.setList(mCityPickerList)
    }

    /**
     * 从asserts目录将json文件转成字符串
     */
    private fun getJson(context: Context, fileName: String): String {
        val stringBuilder = StringBuilder()
        val assetManager = context.assets
        val bf = BufferedReader(InputStreamReader(assetManager.open(fileName)))
        bf.use {
            var line: String?
            while (it.readLine().also { newLine -> line = newLine } != null) {
                stringBuilder.append(line)
            }
        }
        bf.close()
        return stringBuilder.toString()
    }

    private fun initEvent() {
        //搜索输入文本框监听
        mBinding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                performSearch(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                if (s?.length == 0) {
                    mBinding.rvCitySearch.visibility = View.GONE
                } else {
                    mBinding.rvCitySearch.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun performSearch(keyWord: String) {
        if (!TextUtils.isEmpty(keyWord)) {
            mSearchCityList.clear()
            mCityPickerList.forEach { entity ->
                if (entity.pinyin.lowercase().startsWith(keyWord.lowercase()) ||
                    entity.name.lowercase().startsWith(keyWord)
                ) {
                    mSearchCityList.add(entity)
                    mCitySearchAdapter.setList(mSearchCityList)
                }

            }
        }
    }
}