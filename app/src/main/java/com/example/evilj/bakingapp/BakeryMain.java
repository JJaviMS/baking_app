package com.example.evilj.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.evilj.bakingapp.models.Ingredients;
import com.example.evilj.bakingapp.models.Steps;
import com.example.evilj.bakingapp.utils.JSONUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BakeryMain extends AppCompatActivity implements StepsAdapter.StepsCallback {

    public static final String KEY_INGREDIENTS = "ingredients";
    @BindView(R.id.bakery_title)
    TextView mIngredientsTv;
    @BindView(R.id.bakery_main_recycler_view)
    RecyclerView mRecyclerView;

    public static final String STEP_EXTRA_KEY = "steps";
    public static final String STEP_POSTION_EXTRA_KEY = "pos";

    private final String RESTORE_RECYCLER_VIEW = "restore";

    private LinearLayoutManager mLinearLayoutManager;
    private StepsAdapter mStepsAdapter;

    private Ingredients[] mIngredients;
    private Steps[] mSteps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bakery_main);
        ButterKnife.bind(this);
        Intent started = getIntent();
        if (started==null) throw new RuntimeException();
        String json = started.getStringExtra(MainActivity.BAKERY_INFORMATION_KEY);
        if (json==null) throw new RuntimeException();
        mIngredients = JSONUtils.parseIngredients(json);
        mSteps = JSONUtils.parseSteps(json);
        mIngredientsTv.setText(R.string.ingredients);
        mStepsAdapter = new StepsAdapter(mSteps,this,this);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setAdapter(mStepsAdapter);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,mLinearLayoutManager.getOrientation()));
    }


    @OnClick(R.id.ingredients_layout)
    void seeIngredients(){
        Intent intent = new Intent(this,IngredientsActivity.class);
        intent.putExtra(KEY_INGREDIENTS,mIngredients);
        startActivity(intent);
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(this,StepsActivity.class);
        intent.putExtra(STEP_EXTRA_KEY,mSteps);
        intent.putExtra(STEP_POSTION_EXTRA_KEY,position);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RESTORE_RECYCLER_VIEW,mLinearLayoutManager.onSaveInstanceState());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mLinearLayoutManager.onRestoreInstanceState(savedInstanceState.getParcelable(RESTORE_RECYCLER_VIEW));
    }
}
