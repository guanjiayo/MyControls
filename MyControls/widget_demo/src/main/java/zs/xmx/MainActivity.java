package zs.xmx;

import android.content.ComponentName;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import zs.xmx.receiver.MyAppWidget;

/**
 * todo 桌面小部件,添加到
 */
public class MainActivity extends AppCompatActivity {
    MyAppWidget appWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appWidget = new MyAppWidget();
    }


    public void register(View view) {
        registerWidgetReceiver();
    }

    private void registerWidgetReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        try {

            intentFilter.addAction("android.appwidget.action.APPWIDGET_UPDATE");
            ComponentName componentName = new ComponentName(this, MyAppWidget.class);
            ActivityInfo info = getApplicationContext().getPackageManager().getReceiverInfo(componentName, PackageManager.GET_META_DATA);

            info.metaData.putInt("android.appwidget.provider",R.xml.my_app_widget_info);
            //Toast.makeText(this, ""+anInt, Toast.LENGTH_SHORT).show();

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        registerReceiver(appWidget, intentFilter);
    }


    public void cancel(View view) {
        unregisterReceiver(appWidget);
    }
}
