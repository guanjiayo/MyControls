package zs.xmx.simple;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

import zs.xmx.R;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link SimpleAppWidgetConfigureActivity ExampleAppWidgetConfigureActivity}
 */
public class SimpleAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        //获取SP中保存的数据
        CharSequence widgetText = SimpleAppWidgetConfigureActivity.loadTitlePref(context, appWidgetId);
        // Create an Intent to launch ExampleActivity
        Intent intent = new Intent(context, SimpleActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        // Get the layout for the App Widget and attach an on-click listener
        // to the button
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.simple_app_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);

        final Intent clickIntent = new Intent(context, SimpleAppWidget.class);
        clickIntent.setAction("TOAST_ACTION");
        final PendingIntent clickPendingIntent = PendingIntent.getBroadcast(context, 0,
                clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.button,clickPendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals("TOAST_ACTION")) {

            Toast.makeText(context, "Touched view ", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // 删除widget时,把保存的SP删除
        for (int appWidgetId : appWidgetIds) {
            SimpleAppWidgetConfigureActivity.deleteTitlePref(context, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

