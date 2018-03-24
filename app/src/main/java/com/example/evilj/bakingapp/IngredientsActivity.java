package com.example.evilj.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.evilj.bakingapp.models.Ingredients;

import butterknife.BindView;

public class IngredientsActivity extends AppCompatActivity {
    private final String RECYCLER_VIEW_RESTORE_STATE = "recycler";

    @BindView(R.id.bakery_main_recycler_view)
    RecyclerView mRecyclerView;

    private LinearLayoutManager mLinearLayoutManager;
    private IngredientsAdapter mIngredientsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bakery_main);

        Intent intentWhichStarted = getIntent();
        if(intentWhichStarted==null)
            throw new RuntimeException("Intent can´t be null");
        mLinearLayoutManager= new LinearLayoutManager(this);
        mIngredientsAdapter = new IngredientsAdapter(this);
        mIngredientsAdapter.setIngredients((Ingredients[]) intentWhichStarted.getParcelableArrayExtra(BakeryMain.KEY_INGREDIENTS));
        mRecyclerView.setAdapter(mIngredientsAdapter);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RECYCLER_VIEW_RESTORE_STATE,mLinearLayoutManager.onSaveInstanceState());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mLinearLayoutManager.onRestoreInstanceState(savedInstanceState.getParcelable(RECYCLER_VIEW_RESTORE_STATE));
    }
}
