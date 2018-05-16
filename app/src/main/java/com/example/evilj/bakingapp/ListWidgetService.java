package com.example.evilj.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

/**
 * Created by JjaviMS on 15/05/2018.
 *
 * @author JJaviMS
 */
public class ListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewFactory(this.getApplicationContext(),intent.getStringArrayListExtra(BakingService.INGREDIENTS_EXTRA));
    }
}

class ListRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory{

    public ListRemoteViewFactory(Context context,ArrayList<String> strings) {
        mContext = context;
        mStrings = strings;
    }

    Context mContext;
    ArrayList<String> mStrings;

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        mStrings = IngredientsWidget.ingredientes;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (mStrings==null) return 0;
        else return mStrings.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        if (mStrings ==null || mStrings.size()==0) return null;
        String string = mStrings.get(i);
        RemoteViews view = new RemoteViews(mContext.getPackageName(),R.layout.ingredients_widget);
        view.setTextViewText(R.id.widget_text_view,string);
        return view;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
