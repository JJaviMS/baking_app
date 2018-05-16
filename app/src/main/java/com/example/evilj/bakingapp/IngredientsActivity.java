package com.example.evilj.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.evilj.bakingapp.models.Ingredients;

import java.util.ArrayList;

public class IngredientsActivity extends AppCompatActivity {


    private IngredientsFragment mIngredientsFragment;

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingredients_activity);
        Intent intentWhichStarted = getIntent();
        if (intentWhichStarted == null)
            throw new RuntimeException("Intent can´t be null");
        mIngredientsFragment = new IngredientsFragment();
        Ingredients[] ingredients = Ingredients.parseParcelable(intentWhichStarted.getParcelableArrayExtra(BakeryMain.KEY_INGREDIENTS));
        sendNewIngredientsToWidget(ingredients);
        mIngredientsFragment.setIngredients(ingredients);
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction().replace(R.id.ingredient_fragment,mIngredientsFragment).commit();


    }

    private void sendNewIngredientsToWidget (Ingredients[] ingredients){
        ArrayList<String> strings = new ArrayList<>();
        for(Ingredients ingredient:ingredients){
            strings.add(ingredient.getIngredient());
        }
        BakingService.startBakingService(this,strings);
    }


}
