package com.example.evilj.bakingapp.utils;

import android.content.ContentValues;
import android.support.annotation.Nullable;

import com.example.evilj.bakingapp.models.Ingredients;
import com.example.evilj.bakingapp.models.Steps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by JjaviMS on 12/03/2018.
 *
 * @author JJaviMS
 */

public class JSONUtils {

    public static String NAME = "name";
    public static String INGREDIENTS = "ingredients";
    public static String QUANTITY = "quantity";
    public static String MEASURE = "measure";
    public static String INGREDIENT = "ingredient";
    public static String STEPS = "steps";
    public static String SHORT_DESCRIPTION = "shortDescription";
    public static String DESCRIPTION = "description";
    public static String VIDEO_URL = "videoURL";
    public static String THUMBNAIL_URL = "thumbnailURL";

    /**
     * Parse the JSON data into an array of JSON objects which contains the bakery
     * @param jsonData The String to parse
     * @return A JSONArray which contains the bakery
     */
    @Nullable
    public static JSONArray getBakery(String jsonData) {
        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(jsonData);
        } catch (JSONException |NullPointerException e) {
            e.printStackTrace();
            return null;
        }
        return jsonArray;
    }

    /**
     * Get the name of the element
     * @param object The raw JSON of a bakery
     * @return The name of the object
     */
    @Nullable
    public static String getBakeryName(JSONObject object){
        try {
            return object.getString(NAME);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Given a JSON object with a bakery information it parse the ingredients into an Array which
     * contains the different ingredients wrapped into a {@see com.example.evilj.bakingapp.models.Ingredients}
     * @param object The JSON object of the bakery to get the objects
     * @return An Array of the ingredients parsed into Ingredients objects
     */
    @Nullable
    public static Ingredients[] parseIngredients (JSONObject object){
        try {
            JSONArray ingredients = object.getJSONArray(INGREDIENTS);
            int size = ingredients.length();
            Ingredients[] parsed = new Ingredients[size];
            for (int i=0;i<size;i++){
                JSONObject current = ingredients.getJSONObject(i);
                parsed[i] = new Ingredients(current.getDouble(QUANTITY),current.getString(MEASURE),current.getString(INGREDIENT));
            }
            return parsed;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    public static Steps[] parseSteps (JSONObject object){
        try{
            JSONArray steps = object.getJSONArray(STEPS);
            int size = steps.length();
            Steps[] parsed = new Steps[size];
            for (int i=0;i<size;i++){
                JSONObject current = steps.getJSONObject(i);
                parsed[i] = new Steps(current.getString(SHORT_DESCRIPTION),current.getString(DESCRIPTION),
                        current.getString(VIDEO_URL),current.getString(THUMBNAIL_URL));
            }
            return parsed;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


}
