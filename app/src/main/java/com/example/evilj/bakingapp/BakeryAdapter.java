package com.example.evilj.bakingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.evilj.bakingapp.utils.JSONUtils;

import org.json.JSONArray;
import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JjaviMS on 13/03/2018.
 *
 * @author JJaviMS
 */

public class BakeryAdapter extends RecyclerView.Adapter<BakeryAdapter.BakeryViewHolder> {

    private JSONArray bakery;
    private Context mContext;
    private BakeryCallbacks mBakeryCallbacks;
    interface BakeryCallbacks{
        void onClick(String unparsedJSON);
    }

    public BakeryAdapter(Context context, BakeryCallbacks bakeryCallbacks) {
        mContext = context;
        mBakeryCallbacks = bakeryCallbacks;
    }

    @NonNull
    @Override
    public BakeryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.bakery_item,parent,false);
        view.setFocusable(true);
        return new BakeryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BakeryViewHolder holder, int position) {
        try {
            holder.mTitle.setText(JSONUtils.getBakeryName(bakery.getJSONObject(position)));
        } catch (JSONException e) {
            e.printStackTrace();
            throw new RuntimeException("Error parsing data");
        }
    }

    @Override
    public int getItemCount() {
        if (bakery==null)return 0;
        else return bakery.length();
    }

    public void setBakery (JSONArray jsonArray){
        bakery = jsonArray;
        notifyDataSetChanged();
    }

    class BakeryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.bakery_title)
        TextView mTitle;
        BakeryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            try {
                String baker = bakery.getString(getAdapterPosition());
                mBakeryCallbacks.onClick(baker);
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(mContext,"Error",Toast.LENGTH_SHORT).show();
            }

        }
    }
}
