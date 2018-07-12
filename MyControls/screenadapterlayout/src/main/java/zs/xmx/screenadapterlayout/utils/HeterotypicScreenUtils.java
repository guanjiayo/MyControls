package zs.xmx.screenadapterlayout.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * 异形屏 处理API
 * 横屏: 全屏沉浸式
 * //todo 每个厂商的异形屏处理方案要做一下
 */
public class HeterotypicScreenUtils {


    //public static final String DISPLAY_NOTCH_STATUS = "display_notch_status";
    //int mIsNotchSwitchOpen = Settings.Secure.getInt(context.getContentResolver(),DISPLAY_NOTCH_STATUS, 0);
    // 0表示“默认”，1表示“隐藏显示区域”
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    public static void getHeterotypicSize(Activity activity) {
        View contentView = activity.getWindow().getDecorView().findViewById(android.R.id.content).getRootView();
        contentView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            @Override
            @SuppressLint("NewApi")
            public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                DisplayCutout cutout = null;
                if (android.os.Build.VERSION.SDK_INT >= 28) {
                    cutout = windowInsets.getDisplayCutout();

                    if (cutout == null) {
                        Log.e(TAG, "cutout==null, is not notch screen");//通过cutout是否为null判断是否刘海屏手机
                    } else {
                        List<Rect> rects = cutout.getBoundingRects();
                        if (rects == null || rects.size() == 0) {
                            Log.e(TAG, "rects==null || rects.size()==0, is not notch screen");
                        } else {
                            Log.e(TAG, "rect size:" + rects.size());//注意：刘海的数量可以是多个
                            for (Rect rect : rects) {
                                Log.e(TAG, "cutout.getSafeInsetTop():" + cutout.getSafeInsetTop()
                                        + ", cutout.getSafeInsetBottom():" + cutout.getSafeInsetBottom()
                                        + ", cutout.getSafeInsetLeft():" + cutout.getSafeInsetLeft()
                                        + ", cutout.getSafeInsetRight():" + cutout.getSafeInsetRight()
                                        + ", cutout.rects:" + rect
                                );
                            }
                        }
                    }
                }
                return windowInsets;
            }
        });
    }


    //在使用LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES的时候，状态栏会显示为白色，这和主内容区域颜色冲突,
    //所以我们要开启沉浸式布局模式，即真正的全屏模式,以实现状态和主体内容背景一致
    public static void openFullScreenModel(Activity mAc) {
        if (android.os.Build.VERSION.SDK_INT >= 28) {
            mAc.requestWindowFeature(Window.FEATURE_NO_TITLE);
            WindowManager.LayoutParams lp = mAc.getWindow().getAttributes();
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            mAc.getWindow().setAttributes(lp);
            View decorView = mAc.getWindow().getDecorView();
            int systemUiVisibility = decorView.getSystemUiVisibility();
            int flags = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            systemUiVisibility |= flags;
            mAc.getWindow().getDecorView().setSystemUiVisibility(systemUiVisibility);
        }
    }
}
