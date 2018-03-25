package com.example.evilj.bakingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.evilj.bakingapp.models.Steps;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JjaviMS on 25/03/2018.
 *
 * @author JJaviMS
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {

    private Steps[] mSteps;
    private Context mContext;
    private StepsCallback mStepsCallback;

    interface StepsCallback {
        void onClick(int position);
    }

    public StepsAdapter(Steps[] steps, Context context, StepsCallback stepsCallback) {
        mSteps = steps;
        mContext = context;
        mStepsCallback = stepsCallback;
    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.bakery_item,parent,false);
        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder holder, int position) {
        holder.mTitleTV.setText(mSteps[position].getShorDesc());
    }

    @Override
    public int getItemCount() {
        if (mSteps==null) return 0;
        else return mSteps.length;
    }

    class StepsViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.bakery_title)
        TextView mTitleTV;
        StepsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mStepsCallback.onClick(getAdapterPosition());
        }
    }
}
