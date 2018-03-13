package com.example.evilj.bakingapp.models;

/**
 * Created by JjaviMS on 13/03/2018.
 *
 * @author JJaviMS
 *
 * This is a class to help saving the ingredients
 */

public class Ingredients {

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

}
