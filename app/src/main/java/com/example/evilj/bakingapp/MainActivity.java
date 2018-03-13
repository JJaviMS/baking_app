package com.example.evilj.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.evilj.bakingapp.utils.JSONUtils;
import com.example.evilj.bakingapp.utils.NetworkUtils;

import org.json.JSONArray;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<JSONArray>,BakeryAdapter.BakeryCallbacks {
    @BindView(R.id.recycler_view_bakery)
    RecyclerView mBakeryRecyclerView;
    private BakeryAdapter mBakeryAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    private final int LOADER_ID = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mLinearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mBakeryAdapter = new BakeryAdapter(this,this);

        mBakeryRecyclerView.setAdapter(mBakeryAdapter);
        mBakeryRecyclerView.setLayoutManager(mLinearLayoutManager);

        getSupportLoaderManager().initLoader(LOADER_ID,null,this);
    }

    @NonNull
    @Override
    public Loader<JSONArray> onCreateLoader(int id, @Nullable Bundle args) {
        return new MyLoader(MainActivity.this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<JSONArray> loader, JSONArray data) {
        mBakeryAdapter.setBakery(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<JSONArray> loader) {
        mBakeryAdapter.setBakery(null);
    }

    @Override
    public void onClick(String unparsedJSON) {
        //TODO Make intent
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
}
