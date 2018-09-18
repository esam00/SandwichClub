package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {

        ArrayList<String> knownAsList = new ArrayList<>();
        ArrayList<String> ingredientsList = new ArrayList<>();


        JSONObject jsonSandwich = new JSONObject(json);

        // extracting the main name of the sandwich
        JSONObject name = jsonSandwich.getJSONObject("name");
        String mainNameString = name.getString("mainName");

        //extracting also known as list
        JSONArray alsoKnownAsArray = name.getJSONArray("alsoKnownAs");
        if (alsoKnownAsArray.length()>0){
            for (int i = 0; i<alsoKnownAsArray.length(); i++){
                knownAsList.add(alsoKnownAsArray.getString(i));
            }
        }

        // extracting placeOfOrigin String
        String placeOfOriginString = jsonSandwich.getString("placeOfOrigin");

        // extracting description String
        String descriptionString = jsonSandwich.getString("description");

        // extracting the String Url of the image
        String imageString = jsonSandwich.getString("image");

        // extract ingredients Array
        JSONArray ingredientsArray = jsonSandwich.getJSONArray("ingredients");
        if (ingredientsArray.length()>0){
            for (int i = 0; i<ingredientsArray.length(); i++){
                ingredientsList.add(ingredientsArray.getString(i));
            }
        }

            return new Sandwich(mainNameString,placeOfOriginString,descriptionString,imageString,
                    ingredientsList, knownAsList);
    }
}
