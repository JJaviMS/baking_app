package com.example.evilj.bakingapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.evilj.bakingapp.R;
import com.example.evilj.bakingapp.adapter.BakeryAdapter;
import com.example.evilj.bakingapp.idlingResource.SimpleIdlingResource;
import com.example.evilj.bakingapp.utils.JSONUtils;
import com.example.evilj.bakingapp.utils.NetworkUtils;

import org.json.JSONArray;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<JSONArray>, BakeryAdapter.BakeryCallbacks {
    @BindView(R.id.recycler_view_bakery)
    RecyclerView mBakeryRecyclerView;
    private BakeryAdapter mBakeryAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    private final int LOADER_ID = 0;
    private final String KEY_RECYCLE_VIEW_STATE = "Recycler view state";
    public final static String BAKERY_INFORMATION_KEY = "Bakery info";

    //Test variables
    @VisibleForTesting
    @Nullable
    SimpleIdlingResource mSimpleIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mBakeryAdapter = new BakeryAdapter(this, this);

        mBakeryRecyclerView.setAdapter(mBakeryAdapter);
        mBakeryRecyclerView.setLayoutManager(mLinearLayoutManager);
        mBakeryRecyclerView.addItemDecoration(new DividerItemDecoration(this, mLinearLayoutManager.getOrientation()));

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @NonNull
    @Override
    public Loader<JSONArray> onCreateLoader(int id, @Nullable Bundle args) {
        return new MyLoader(MainActivity.this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<JSONArray> loader, JSONArray data) {
        if (data != null)
            mBakeryAdapter.setBakery(data);
        else Toast.makeText(this, "Can´t load the data", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<JSONArray> loader) {
        mBakeryAdapter.setBakery(null);
    }

    @Override
    public void onClick(String unparsedJSON) {
        Intent intent = new Intent(this, BakeryMain.class);
        intent.putExtra(BAKERY_INFORMATION_KEY, unparsedJSON);
        startActivity(intent);
    }

    public static class MyLoader extends AsyncTaskLoader<JSONArray> {
        public MyLoader(@NonNull Context context) {
            super(context);
        }

        @Nullable
        @Override
        public JSONArray loadInBackground() {
            try {
                return JSONUtils.getBakery(NetworkUtils.bakeryServerResponse());
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            forceLoad();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_RECYCLE_VIEW_STATE, mLinearLayoutManager.onSaveInstanceState());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mLinearLayoutManager.onRestoreInstanceState(savedInstanceState.getParcelable(KEY_RECYCLE_VIEW_STATE));
    }

    @VisibleForTesting
    @NonNull
    public SimpleIdlingResource getSimpleIdlingResource(){
        if (mSimpleIdlingResource==null)
            mSimpleIdlingResource= new SimpleIdlingResource();
        return mSimpleIdlingResource;
    }
}
