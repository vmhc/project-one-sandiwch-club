package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        final String NAME = "name";
        final String MAIN_NAME = "mainName";
        final String ALSO_KNOWN_AS = "alsoKnownAs";
        final String PLACE_OF_ORIGIN = "placeOfOrigin";
        final String DESCRIPTION = "description";
        final String IMAGE = "image";
        final String INGREDIENTS = "ingredients";

        JSONObject jsonObject;
        JSONObject jsonName;

        try {
            Sandwich mySandwich = new Sandwich();
            jsonObject = new JSONObject(json);

            jsonName = jsonObject.getJSONObject(NAME);

            mySandwich.setMainName(jsonName.getString(MAIN_NAME));
            mySandwich.setPlaceOfOrigin(jsonObject.getString(PLACE_OF_ORIGIN));
            mySandwich.setDescription(jsonObject.getString(DESCRIPTION));
            mySandwich.setImage(jsonObject.getString(IMAGE));

            JSONArray alsoKnownAs = jsonName.getJSONArray(ALSO_KNOWN_AS);
            List<String> arrayAlsoKnowAs = new ArrayList<>();
            for (int i = 0; i < alsoKnownAs.length(); i++) {
                arrayAlsoKnowAs.add(alsoKnownAs.get(i).toString());
            }
            mySandwich.setAlsoKnownAs(arrayAlsoKnowAs);

            JSONArray ingredients = jsonObject.getJSONArray(INGREDIENTS);
            List<String> arrayIngredients = new ArrayList<>();
            for (int i = 0; i < ingredients.length(); i++) {
                arrayIngredients.add(ingredients.get(i).toString());
            }
            mySandwich.setIngredients(arrayIngredients);

            return mySandwich;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}
