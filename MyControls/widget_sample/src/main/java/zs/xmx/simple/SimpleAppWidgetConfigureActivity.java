package zs.xmx.simple;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import zs.xmx.R;

/**
 * The configuration screen for the {@link SimpleAppWidget ExampleAppWidget} AppWidget.
 */
public class SimpleAppWidgetConfigureActivity extends Activity implements View.OnClickListener {

    private static final String PREFS_NAME      = "zs.xmx.ExampleAppWidget";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    /**
     * Widget ID
     **/
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    EditText mAppWidgetText;


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // 当您的配置活动首次打开时,将活动结果与EXTRA_APPWIDGET_ID一起设置为RESULT_CANCELED,
        // 当用户在到达结束之前退出活动,会通知App Widget Host 配置已取消,
        // 并且将不会添加App Widget.(Configuration Activity退出,不再创建App Widget)
        setResult(RESULT_CANCELED);

        setContentView(R.layout.simple_app_widget_configure);
        mAppWidgetText = (EditText) findViewById(R.id.appwidget_text);
        findViewById(R.id.add_button).setOnClickListener(this);

        //1.从Launched Activity(主屏) 找到 Widget ID (保存在Launched Activity Intent extras 的EXTRA_APPWIDGET_ID)
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        //判断app widget ID是否为空.(App Widget 是否添加到Launched Activity)
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

        mAppWidgetText.setText(loadTitlePref(SimpleAppWidgetConfigureActivity.this, mAppWidgetId));
    }

    @Override
    public void onClick(View v) {
        final Context context = SimpleAppWidgetConfigureActivity.this;

        //2.App Widget 配置逻辑,这边按需配置即可
        String widgetText = mAppWidgetText.getText().toString();

        //保存配置到SP中
        saveTitlePref(context, mAppWidgetId, widgetText);

        //3.配置完 Widget 配置,拿到AppWidgetManager实例
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        //4.通过调用updateAppWidget（int,RemoteViews）通过RemoteViews布局更新App Widget
        /*ExampleAppWidget.updateAppWidget(..)这里写过了 :
        RemoteViews views = new RemoteViews(context.getPackageName(),
                R.layout.example_appwidget);
        appWidgetManager.updateAppWidget(mAppWidgetId, views);*/
        SimpleAppWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);

        //5.结束 Activity,将设置的参数用 inent.putXX 传递出去(要做回显的可以保存一份到SP)
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }

    public SimpleAppWidgetConfigureActivity() {
        super();
    }

    /**
     * 下面都是SP数据处理
     **/
    // Write the prefix to the SharedPreferences object for this widget
    static void saveTitlePref(Context context, int appWidgetId, String text) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(PREF_PREFIX_KEY + appWidgetId, text);
        prefs.apply();
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static String loadTitlePref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String titleValue = prefs.getString(PREF_PREFIX_KEY + appWidgetId, null);
        if (titleValue != null) {
            return titleValue;
        } else {
            return context.getString(R.string.appwidget_text);
        }
    }

    static void deleteTitlePref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId);
        prefs.apply();
    }

}

