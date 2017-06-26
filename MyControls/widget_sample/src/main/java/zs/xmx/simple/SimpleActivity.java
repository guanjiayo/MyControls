package zs.xmx.simple;

import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import static android.util.Log.d;

public class SimpleActivity extends AppCompatActivity {
    private AppWidgetHost    mAppWidgetHost;
    private AppWidgetManager mAppWidgetManager;
    private FrameLayout      frameLayout;
    private static final int    REQUEST_PICK_APPWIDGET   = 1;
    private static final int    REQUEST_CREATE_APPWIDGET = 2;
    private static final int    APPWIDGET_HOST_ID        = 0x100;     //用于标识
    private static final String EXTRA_CUSTOM_WIDGET      = "custom_widget";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //1.设置要长按监听的布局
        //这里不一定学我直接监听根布局,写一个ViewGroup重写那三个方法(扩展性更高)
        frameLayout = new FrameLayout(this);

        setContentView(frameLayout);

        frameLayout.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                showWidgetChooser();
                return true;
            }
        });


        mAppWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
        mAppWidgetHost = new AppWidgetHost(getApplicationContext(), APPWIDGET_HOST_ID);

        //2.使用AppWidgetHost.startListening() 监听widget的状态变化
        mAppWidgetHost.startListening();


    }

    /**
     * 4.接收添加appwidget和appwidget的配置activity的返回值.分类处理添加widget的逻辑业务
     * <p>
     * 选中了某个widget之后,根据是否有配置来决定直接添加还是弹出配置activity
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_PICK_APPWIDGET:
                    addAppWidget(data);
                    break;
                case REQUEST_CREATE_APPWIDGET:
                    completeAddAppWidget(data);
                    break;
            }
        } else if (requestCode == REQUEST_PICK_APPWIDGET &&
                resultCode == RESULT_CANCELED && data != null) {
            //如果configure Actvity finish掉,已经添加过Widget,就不添加widget
            int appWidgetId = data.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
            if (appWidgetId != -1) {
                mAppWidgetHost.deleteAppWidgetId(appWidgetId);
            }
        }
    }

    /**
     * 选中了某个widget之后，根据是否有配置来决定直接添加还是弹出配置activity
     *
     * @param data
     */
    private void addAppWidget(Intent data) {
        int appWidgetId = data.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);

        String customWidget = data.getStringExtra(EXTRA_CUSTOM_WIDGET);
        d("addAppWidget", "data:" + customWidget);

        AppWidgetProviderInfo appWidget = mAppWidgetManager.getAppWidgetInfo(appWidgetId);

        d("addAppWidget", "configure:" + appWidget.configure);
        if (appWidget.configure != null) {
            //有配置，弹出配置
            Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_CONFIGURE);
            intent.setComponent(appWidget.configure);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

            startActivityForResult(intent, REQUEST_CREATE_APPWIDGET);
        } else {
            //没有配置，直接添加
            completeAddAppWidget(data);
        }
    }


    /**
     * 3.请求添加一个新的widget:用于选取系统中的widget
     * <p>
     * 实际上系统 Launch ,选择widget之后弹出的widget列表是一个Activity,
     * <p>
     * 需要用带上Extra的,AppWidgetManager.ACTION_APPWIDGET_PICK这个Intent来启动
     * <p>
     * 这里我们用startActivityForResult()传参分类处理
     */
    private void showWidgetChooser() {
        int appWidgetId = mAppWidgetHost.allocateAppWidgetId();
        Intent pickIntent = new Intent(AppWidgetManager.ACTION_APPWIDGET_PICK);
        pickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        startActivityForResult(pickIntent, REQUEST_PICK_APPWIDGET);
    }

    /**
     * 添加widget
     *
     * @param data
     */
    private void completeAddAppWidget(Intent data) {
        Bundle extras = data.getExtras();
        int appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);

        d("completeAddAppWidget", "dumping extras content=" + extras.toString());
        d("completeAddAppWidget", "appWidgetId:" + appWidgetId);
        AppWidgetProviderInfo appWidgetInfo = mAppWidgetManager.getAppWidgetInfo(appWidgetId);

        View hostView = mAppWidgetHost.createView(this, appWidgetId, appWidgetInfo);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, appWidgetInfo.minHeight));
        frameLayout.addView(hostView);
    }

}
