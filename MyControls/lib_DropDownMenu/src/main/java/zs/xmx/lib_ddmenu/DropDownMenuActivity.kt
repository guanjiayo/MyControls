package zs.xmx.lib_ddmenu

import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/*
 * 参考代码:
 *  https://github.com/dongjunkun/DropDownMenu
 */
class DropDownMenuActivity : AppCompatActivity() {

    private lateinit var mDropDownMenu: DropDownMenu

    private val mddMenuTabs = listOf("城市", "星座")
    private val cityList = arrayListOf(
        "不限",
        "武汉",
        "北京",
        "上海",
        "成都",
        "广州",
        "深圳",
        "重庆",
        "天津",
        "西安",
        "南京",
        "杭州"
    )
    private val constellationList = arrayListOf(
        "不限",
        "白羊座",
        "金牛座",
        "双子座",
        "巨蟹座",
        "狮子座",
        "处女座",
        "天秤座",
        "天蝎座",
        "射手座",
        "摩羯座",
        "水瓶座",
        "双鱼座"
    )

    private lateinit var mCityAdapter: CityDropDownAdapter
    private lateinit var mConstellationDDMLayout: ConstellationDDMLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drop_down_menu)
        initDropMenu()
        initEvent()
    }

    private fun initDropMenu() {
        mDropDownMenu = findViewById(R.id.dropDownMenu)
        val popupView = mutableListOf<View>()

        //自定义View
        val cityView = RecyclerView(this)
        cityView.layoutManager = LinearLayoutManager(this)
        mCityAdapter = CityDropDownAdapter(this, cityList)
        cityView.adapter = mCityAdapter

        popupView.add(cityView)

        //自定义FrameLayout
        mConstellationDDMLayout = ConstellationDDMLayout(this)
        mConstellationDDMLayout.setDataList(constellationList)
        popupView.add(mConstellationDDMLayout)

        //init context view
        val contentView = TextView(this)
        contentView.setLayoutParams(
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
        contentView.text = "内容显示区域"
        contentView.setGravity(Gravity.CENTER)
        contentView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)

        mDropDownMenu.setDropDownMenu(mddMenuTabs, popupView, contentView)
    }

    private fun initEvent() {
        mCityAdapter.setOnItemClickListener(object : CityDropDownAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                mCityAdapter.setCheckItem(position)
                mDropDownMenu.setTabText(if (position == 0) mddMenuTabs[0] else cityList[position])
                mDropDownMenu.closeMenu()
            }

        })

        mConstellationDDMLayout.getAdapter()
            .setOnItemClickListener(object : ConstellationDDMAdapter.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    mConstellationDDMLayout.getAdapter().setCheckItem(position)
                    mDropDownMenu.setTabText(if (position == 0) mddMenuTabs[1] else constellationList[position])
                    mDropDownMenu.closeMenu()
                }

            })
    }


    override fun onBackPressed() {
        //退出activity前关闭菜单
        if (mDropDownMenu.isShowing()) {
            mDropDownMenu.closeMenu()
        } else {
            super.onBackPressed()
        }
    }

}