package com.example.evilj.bakingapp;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.evilj.bakingapp.models.Ingredients;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class IngredientsFragment extends Fragment {

    @BindView(R.id.ingredients_fragment_recycler)
    RecyclerView mRecyclerView;
    private final String RECYCLER_VIEW_STATE = "state";


    private LinearLayoutManager mLinearLayoutManager;
    private IngredientsAdapter mIngredientsAdapter;
    private Ingredients [] mIngredients;

    public IngredientsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);
        ButterKnife.bind(this,view);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mIngredientsAdapter= new IngredientsAdapter(getContext());
        mIngredientsAdapter.setIngredients(mIngredients);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mIngredientsAdapter);
        return view;
    }

    public void setIngredients(Ingredients[] ingredients) {
        mIngredients = ingredients;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RECYCLER_VIEW_STATE,mLinearLayoutManager.onSaveInstanceState());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState!=null){
            mLinearLayoutManager.onRestoreInstanceState(savedInstanceState.getParcelable(RECYCLER_VIEW_STATE));
        }
    }
}
