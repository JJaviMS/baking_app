package com.example.evilj.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.evilj.bakingapp.models.Ingredients;
import com.example.evilj.bakingapp.models.Steps;
import com.example.evilj.bakingapp.utils.JSONUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BakeryMain extends AppCompatActivity {

    public static final String KEY_INGREDIENTS = "ingredients";
    @BindView(R.id.bakery_title)
    TextView mIngredientsTv;
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
    }


    @OnClick(R.id.ingredients_layout)
    void seeIngredients(){
        Intent intent = new Intent(this,IngredientsActivity.class);
        intent.putExtra(KEY_INGREDIENTS,mIngredients);
        startActivity(intent);
    }
}
