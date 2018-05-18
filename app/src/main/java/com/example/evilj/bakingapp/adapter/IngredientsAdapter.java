package com.example.evilj.bakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.evilj.bakingapp.R;
import com.example.evilj.bakingapp.models.Ingredients;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JjaviMS on 14/03/2018.
 *
 * @author JJaviMS
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder> {

    private Ingredients[] mIngredients;
    private Context mContext;

    public IngredientsAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.ingredient_item,parent,false);
        view.setFocusable(false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Ingredients ingredients = mIngredients [position];
        holder.mIngredientNameTv.setText(ingredients.getIngredient());
        holder.mMeasureTv.setText(ingredients.getMeasure());
        holder.mQuantityTv.setText(String.format(String.valueOf(Locale.getDefault()),ingredients.getQuantity()));
    }

    @Override
    public int getItemCount() {
        if (mIngredients==null) return 0;
        else return mIngredients.length;
    }

    public void setIngredients (Ingredients[] ingredients){
        mIngredients = ingredients;
        notifyDataSetChanged();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ingredient_name_text_view)
        TextView mIngredientNameTv;
        @BindView(R.id.measure_text_view)
        TextView mMeasureTv;
        @BindView(R.id.quantity_desc_text_view)
        TextView mQuantityTv;
        IngredientViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
