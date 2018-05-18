package com.example.evilj.bakingapp.activities;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.evilj.bakingapp.R;
import com.example.evilj.bakingapp.adapter.StepsAdapter;
import com.example.evilj.bakingapp.fragment.IngredientsFragment;
import com.example.evilj.bakingapp.fragment.StepFragment;
import com.example.evilj.bakingapp.models.Ingredients;
import com.example.evilj.bakingapp.models.Steps;
import com.example.evilj.bakingapp.utils.JSONUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BakeryMain extends AppCompatActivity implements StepsAdapter.StepsCallback, StepFragment.StepListener {

    public static final String KEY_INGREDIENTS = "ingredients";
    @BindView(R.id.bakery_title)
    TextView mIngredientsTv;
    @BindView(R.id.bakery_main_recycler_view)
    RecyclerView mRecyclerView;
    @Nullable
    @BindView(R.id.master_flow_linear)
    LinearLayout mLinearLayout;

    public static final String STEP_EXTRA_KEY = "steps";
    public static final String STEP_POSTION_EXTRA_KEY = "pos";

    private final String RESTORE_RECYCLER_VIEW = "restore";

    private LinearLayoutManager mLinearLayoutManager;
    private StepsAdapter mStepsAdapter;

    private Ingredients[] mIngredients;
    private Steps[] mSteps;
    private boolean mTwoPane;
    private FragmentManager mFragmentManager;
    private StepFragment mStepFragment;
    private int currentPos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bakery_main);
        ButterKnife.bind(this);
        Intent started = getIntent();
        if (started == null) throw new RuntimeException();
        String json = started.getStringExtra(MainActivity.BAKERY_INFORMATION_KEY);
        if (json == null) throw new RuntimeException();
        mTwoPane = mLinearLayout != null;
        mIngredients = JSONUtils.parseIngredients(json);
        mSteps = JSONUtils.parseSteps(json);

        mIngredientsTv.setText(R.string.ingredients);

        mStepsAdapter = new StepsAdapter(mSteps, this, this);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setAdapter(mStepsAdapter);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, mLinearLayoutManager.getOrientation()));
        if (mTwoPane) {
            mFragmentManager = getSupportFragmentManager();
            mStepFragment = new StepFragment();
            mStepFragment.setSteps(mSteps[0]);
            currentPos = 0;
            mFragmentManager.beginTransaction().add(R.id.master_flow_frame_layout, mStepFragment).commit();
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null) actionBar.setDisplayHomeAsUpEnabled(true);

    }



    @OnClick(R.id.ingredients_layout)
    void seeIngredients() {
        if (!mTwoPane) {
            Intent intent = new Intent(this, IngredientsActivity.class);
            intent.putExtra(KEY_INGREDIENTS, mIngredients);
            startActivity(intent);
        }else{
            IngredientsFragment ingredientsFragment = new IngredientsFragment();
            ingredientsFragment.setIngredients(mIngredients);
            mFragmentManager.beginTransaction().replace(R.id.master_flow_frame_layout,ingredientsFragment).commit();
            mStepFragment = null;
            currentPos=-1;
        }
    }

    @Override
    public void onClick(int position) {
        if (mTwoPane) {
            changeFragment(position);
        } else {
            Intent intent = new Intent(this, StepsActivity.class);
            intent.putExtra(STEP_EXTRA_KEY, mSteps);
            intent.putExtra(STEP_POSTION_EXTRA_KEY, position);
            startActivity(intent);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RESTORE_RECYCLER_VIEW, mLinearLayoutManager.onSaveInstanceState());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mLinearLayoutManager.onRestoreInstanceState(savedInstanceState.getParcelable(RESTORE_RECYCLER_VIEW));
    }

    @Override
    public void videoCompleted() {
        if (currentPos != mSteps.length - 1) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    nextStepFragment();
                }
            }, 3000);//Wait 3 seconds before going into the next Step
        }
    }


    private void nextStepFragment() {
        currentPos++;
        mStepFragment = new StepFragment();
        mStepFragment.setSteps(mSteps[currentPos]);
        mFragmentManager.beginTransaction().replace(R.id.master_flow_frame_layout, mStepFragment).commit();
    }

    private void changeFragment(int position) {
        currentPos = position;
        mStepFragment = new StepFragment();
        mStepFragment.setSteps(mSteps[currentPos]);
        mFragmentManager.beginTransaction().replace(R.id.master_flow_frame_layout, mStepFragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
