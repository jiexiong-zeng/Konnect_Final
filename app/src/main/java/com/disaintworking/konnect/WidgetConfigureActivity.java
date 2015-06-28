//Widget change (whole file)

package com.disaintworking.konnect;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RemoteViews;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class WidgetConfigureActivity extends ActionBarActivity {

    int mAppWidgetId= AppWidgetManager.INVALID_APPWIDGET_ID;
    private String mCurrentName;
    private Bitmap mCurrentImage;
    private String mCurrentNumber;
    private Boolean mSelectionMade = false;
    private AppWidgetManager appWidgetManager;

    public WidgetConfigureActivity(){
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_configure);

        setResult(RESULT_CANCELED);

        appWidgetManager = AppWidgetManager.getInstance(this.getApplicationContext());

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        GridView gridView = (GridView) findViewById(R.id.gridview);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectionMade = true;
                People currentPerson = MyAdapter.mItems.get(position);
                mCurrentName = currentPerson.getName();
                mCurrentNumber = currentPerson.getNumber();
                try {
                    File Temp = new File(currentPerson.getURI(), currentPerson.name + ".jpg");
                    mCurrentImage = BitmapFactory.decodeStream(new FileInputStream(Temp));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                if (mSelectionMade) {
                    appWidgetManager.updateAppWidget(mAppWidgetId, buildUpdate(getApplicationContext(), mCurrentName, mCurrentImage, mCurrentNumber));
                    Intent resultValue = new Intent();
                    resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                    setResult(RESULT_OK, resultValue);
                    finish();
                }
            }
        });

        gridView.setAdapter(new MyAdapter(this));
    }

    private RemoteViews buildUpdate(Context context, String mName, Bitmap mPicture, String mNumber) {
        RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        updateViews.setTextViewText(R.id.text, mName);
        updateViews.setImageViewBitmap(R.id.picture, mPicture);

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" +mNumber));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        updateViews.setOnClickPendingIntent(R.id.picture, pendingIntent);

        return updateViews;
    }
}
