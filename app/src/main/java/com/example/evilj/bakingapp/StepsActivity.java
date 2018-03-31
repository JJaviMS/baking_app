package com.example.evilj.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.evilj.bakingapp.models.Steps;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StepsActivity extends AppCompatActivity implements StepFragment.StepListener {
    @BindView(R.id.next_button)
    Button mNextButton;
    @BindView(R.id.previous_button)
    Button mPrevButton;


    private int currentPos;
    private FragmentManager mFragmentManager;
    private Steps[] mSteps;
    StepFragment mStepFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        ButterKnife.bind(this);
        mFragmentManager = getSupportFragmentManager();
        Intent intentWhichStartedActivity = getIntent();
        if (intentWhichStartedActivity== null)throw new RuntimeException();
        mSteps = Steps.parseParcelable(intentWhichStartedActivity.getParcelableArrayExtra(BakeryMain.STEP_EXTRA_KEY));
        currentPos = intentWhichStartedActivity.getIntExtra(BakeryMain.STEP_POSTION_EXTRA_KEY,0);
        if (currentPos==mSteps.length-1) mNextButton.setEnabled(false);
        if (currentPos==0) mPrevButton.setEnabled(false);
        mStepFragment = new StepFragment();
        mStepFragment.setSteps(mSteps[currentPos]);

        mFragmentManager.beginTransaction().add(R.id.frame_layout_step,mStepFragment).commit();
    }

    @OnClick(R.id.next_button)
    void nextStepFragment (){
        currentPos++;
        if (currentPos==mSteps.length-1) mNextButton.setEnabled(false);
        if (!mPrevButton.isEnabled()) mPrevButton.setEnabled(true);
        changeFragment();
    }

    @OnClick(R.id.previous_button)
    void previousStepFragment(){
        currentPos--;
        if (currentPos==0) mPrevButton.setEnabled(false);
        if (!mNextButton.isEnabled()) mNextButton.setEnabled(true);
        changeFragment();
    }

    private void changeFragment(){
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.remove(mStepFragment);
        StepFragment stepFragment = new StepFragment();
        stepFragment.setSteps(mSteps[currentPos]);
        fragmentTransaction.add(R.id.frame_layout_step,stepFragment).commit();
        mStepFragment=stepFragment;
    }

    @Override
    public void videoCompleted() {
        if (currentPos!=mSteps.length-1){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    nextStepFragment();
                }
            },3000);//Wait 3 seconds before going into the next Step
        }
    }
}
