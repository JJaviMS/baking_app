package com.example.evilj.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Arrays;

/**
 * Created by JjaviMS on 13/03/2018.
 *
 * @author JJaviMS
 * <p>
 * This is a class to help saving the ingredients
 */

public class Ingredients implements Parcelable {

    private double quantity;
    private String measure;
    private String ingredient;

    public Ingredients(double quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }


    public double getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ingredients that = (Ingredients) o;

        if (Double.compare(that.quantity, quantity) != 0) return false;
        if (measure != null ? !measure.equals(that.measure) : that.measure != null) return false;
        return ingredient != null ? ingredient.equals(that.ingredient) : that.ingredient == null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(quantity);
        parcel.writeString(measure);
        parcel.writeString(ingredient);
    }

    public static final Parcelable.Creator<Ingredients> CREATOR =
            new Creator<Ingredients>() {
                @Override
                public Ingredients createFromParcel(Parcel parcel) {
                    return new Ingredients(parcel);
                }

                @Override
                public Ingredients[] newArray(int i) {
                    return new Ingredients[i];
                }
            };

    public Ingredients(Parcel parcel) {
        this.quantity = parcel.readDouble();
        this.measure = parcel.readString();
        this.ingredient = parcel.readString();
    }

    @NonNull
    public static Ingredients[] parseParcelable(@NonNull Parcelable[] parcelables) {
        return Arrays.copyOf(parcelables, parcelables.length, Ingredients[].class);
    }
}
