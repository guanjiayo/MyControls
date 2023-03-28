package zs.xmx.mycontrols.bottom_sheet

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import zs.xmx.mycontrols.R

class BottomSheetActivity : AppCompatActivity() {

    private lateinit var bottomSheetBehavior: DrawerBottomSheetBehavior<LinearLayout>
    private lateinit var mBottomSheet: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_sheet)
        mBottomSheet = findViewById(R.id.bottom_sheet)
        initBottomBehavior()
        initEvent()
    }

    private fun initEvent() {
        /* //底部滑块
         mBinding.slideBar.singleClick {
             val state = bottomSheetBehavior.state
             if (state == BottomSheetBehavior.STATE_COLLAPSED) {
                 bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
             } else {
                 bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
             }
         }*/
    }

    private fun initBottomBehavior() {
        bottomSheetBehavior = DrawerBottomSheetBehavior.from(mBottomSheet)
        bottomSheetBehavior.isHideable = false//设置BottomSheet是否可隐藏

        /*bottomSheetBehavior.addBottomSheetCallback(object : DefaultBottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    mBinding.llLuraLog.setVisible(true)
                } else {
                    mBinding.llLuraLog.setVisible(false)
                }
            }
        })*/
    }
}