package zs.xmx.mycontrols.citypicker

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dylanc.viewbinding.binding
import org.json.JSONArray
import zs.xmx.mycontrols.citypicker.adapter.CityPickerAdapter
import zs.xmx.mycontrols.citypicker.adapter.CitySearchAdapter
import zs.xmx.mycontrols.databinding.ActivityCityPickerV1Binding
import java.io.BufferedReader
import java.io.InputStreamReader

/*  参考资料
    https://github.com/xiaopengs/IndexBar
    https://github.com/qdxxxx/IndexBarLayout
    https://blog.csdn.net/u013700040/article/details/78180096
    https://blog.csdn.net/wh445306/article/details/130117140

    //粘性头实现方案
    1. 监听RecycleView滑动事件,对一个和标题类型的itemViewType布局做动画
    https://www.jianshu.com/p/fe69a53502ab
    https://blog.csdn.net/little762/article/details/79391807
    2.
 */
/**
 * 接口做好分类,数组嵌套数组 直接多itemViewType实现
 * 如 标题+热门城市+定位城市+城市列表
 * 黏性标题使用监听RecyclerView滑动事件监听实现
 */
class CityPickerV1Activity : AppCompatActivity() {

    private val mBinding by binding<ActivityCityPickerV1Binding>()

    private val mCityPickerList = mutableListOf<CityPickerEntity>()
    private val mSearchCityList = mutableListOf<CityPickerEntity>()//搜索结果

    private lateinit var mCityPickerAdapter: CityPickerAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private var mStickyHeaderHeight = 0
    private var mCurrentPosition = 0

    private lateinit var mCitySearchAdapter: CitySearchAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAdapter()
        initData()
        initEvent()
    }


    private fun initAdapter() {
        //城市列表
        mCityPickerAdapter = CityPickerAdapter()
        layoutManager = LinearLayoutManager(this)
        mBinding.rvCityPicker.isNestedScrollingEnabled = false
        mBinding.rvCityPicker.layoutManager = layoutManager
        mBinding.rvCityPicker.adapter = mCityPickerAdapter

        //城市列表(搜索)
        mCitySearchAdapter = CitySearchAdapter()
        mBinding.rvCitySearch.layoutManager = LinearLayoutManager(this)
        mBinding.rvCitySearch.isNestedScrollingEnabled = false
        mBinding.rvCitySearch.adapter = mCitySearchAdapter
    }

    private fun initData() {
        val heads = mutableListOf<String>()
        val jsonString = getJson(this@CityPickerV1Activity, "city.json")
        val jsonArray = JSONArray(jsonString)
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val initial = jsonObject.getString("initial")
            mCityPickerList.add(CityPickerEntity(initial = initial))
            heads.add(initial)
            val cityList = jsonObject.getJSONArray("list")
            for (j in 0 until cityList.length()) {
                val cityObject = cityList.getJSONObject(j)
                val code = cityObject.optString("code")
                val name = cityObject.optString("name")
                val pinyin = cityObject.optString("pinyin")
                val label = cityObject.optString("label")
                mCityPickerList.add(CityPickerEntity(city = City(code, name, pinyin, label)))
            }
        }
        mCityPickerAdapter.setList(mCityPickerList)
        //indexBarLayout
        /*mBinding.indexBarLayout.apply {
            setIndexBarHeightRatio(0.6f)
            getIndexBar().setIndexsList(heads)
            setCircleTextColor(Color.WHITE)
            setCircleRadius(100f)
            setCirCleTextSize(100)
            setCircleColor(ContextCompat.getColor(this@CityPickerActivity, R.color.color_666))
            getIndexBar().setIndexChangeListener(object : IndexBar.IndexChangeListener {
                override fun indexChanged(indexName: String?) {
                    mCityPickerList.forEachIndexed { index, cityPickerEntity ->
                        if (indexName.equals(cityPickerEntity.initial)) {
                            layoutManager.scrollToPositionWithOffset(index, 0)
                        }
                    }
                }
            })
        }*/
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
        //todo city.json 的数据还是做成单数组多对象,不然不好筛选数据
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

        //RecyclerView滑动监听实现粘性标题
        mBinding.rvCityPicker.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                mStickyHeaderHeight = mBinding.stickyHeader.root.height
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                //获取第二个显示的分组头
                if (mCityPickerAdapter.getItemViewType(firstVisibleItemPosition + 1) == CityPickerAdapter.TYPE_CITY_GROUP) {

                    val groupView = layoutManager.findViewByPosition(firstVisibleItemPosition + 1)
                    if (groupView != null) {
                        if (groupView.top <= mStickyHeaderHeight) {
                            mBinding.stickyHeader.root.y =
                                (-(mStickyHeaderHeight - groupView.top).toFloat())
                        } else {
                            mBinding.stickyHeader.root.y = 0f
                        }
                    }

                }

                if (mCurrentPosition != layoutManager.findFirstVisibleItemPosition()) {
                    mCurrentPosition = layoutManager.findFirstVisibleItemPosition()
                    mBinding.stickyHeader.root.y = 0f
                    if (null != mCityPickerAdapter.getItem(mCurrentPosition).city) {
                        mBinding.stickyHeader.titleGroup.text =
                            mCityPickerAdapter.getItem(mCurrentPosition).city?.pinyin?.first()
                                .toString()
                    } else {
                        mBinding.stickyHeader.titleGroup.text =
                            mCityPickerAdapter.getItem(firstVisibleItemPosition).initial
                    }
                }

            }
        })

        mCityPickerAdapter.setOnItemClickListener { adapter, view, position ->

        }

        mCitySearchAdapter.setOnItemClickListener { adapter, view, position ->

        }
    }

    private fun performSearch(keyWord: String) {
        if (!TextUtils.isEmpty(keyWord)) {
            mSearchCityList.clear()
            mCityPickerList.forEach { entity ->
                entity.city?.let {
                    if (it.pinyin.lowercase().startsWith(keyWord.lowercase()) ||
                        it.name.lowercase().startsWith(keyWord)
                    ) {
                        mSearchCityList.add(entity)
                        mCitySearchAdapter.setList(mSearchCityList)
                    }
                }
            }
        }
    }

}