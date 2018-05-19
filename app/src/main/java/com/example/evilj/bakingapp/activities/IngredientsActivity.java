package com.example.evilj.bakingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.evilj.bakingapp.R;
import com.example.evilj.bakingapp.fragment.IngredientsFragment;
import com.example.evilj.bakingapp.models.Ingredients;
import com.example.evilj.bakingapp.widget.BakingService;

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
        mFragmentManager.beginTransaction().replace(R.id.ingredient_fragment, mIngredientsFragment).commit();


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);

    }

    private void sendNewIngredientsToWidget(Ingredients[] ingredients) {
        ArrayList<String> strings = new ArrayList<>();
        for (Ingredients ingredient : ingredients) {
            strings.add(ingredient.getIngredient());
        }
        BakingService.startBakingService(this, strings);
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
