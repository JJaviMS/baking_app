package com.example.evilj.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;

public class BakeryMain extends AppCompatActivity {

    public static final String KEY_INGREDIENTS = "ingredients";
    @BindView(R.id.bakery_title)
    TextView mIngredientsTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bakery_main);
    }


    @OnClick(R.id.bakery_title)
    void seeIngredients(){
        Intent intent = new Intent(this,IngredientsActivity.class);
        //TODO put extra
    }
}
