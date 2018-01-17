package com.project.krishna.bakingapp.util;

import com.project.krishna.bakingapp.data.Ingredients;
import com.project.krishna.bakingapp.data.RecipeDetails;
import com.project.krishna.bakingapp.data.Recipes;
import com.project.krishna.bakingapp.data.Steps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krishna on 1/11/18.
 */

public class ParseUtility {
    private final static String RECIPE_ID="id";
    private final static String RECIPE_NAME="name";
    private final static String RECIPE_SERVINGS="servings";
    private static final String INGREDIENTS ="ingredients";
    private static final String QUANTITY ="quantity";
    private static final String MEASURE="measure";
    private static final String INGREDIENT="ingredient";
    private static final String STEPS ="steps" ;
    private static final String SHORT_DES ="shortDescription" ;
    private static final String LONG_DES ="description" ;
    private static final String VIDEO ="videoURL";
    private static final String STEP_ID ="id" ;
    private static final String RECIPE_IMAGE ="image" ;
    private static final String THUMBNAIL ="thumbnailURL" ;


    public static List<Recipes> getRecipeList(String recipeJson) throws JSONException {
        List<Recipes> recipesList=new ArrayList<>();
        JSONArray json=null;
        try {
             json = new JSONArray(recipeJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for(int i=0;i<json.length();i++){
            Recipes r=new Recipes();
            JSONObject object=json.getJSONObject(i);
            String id=object.getString(RECIPE_ID);
            String name=object.getString(RECIPE_NAME);
            String servings=object.getString(RECIPE_SERVINGS);
            String thumbnailURL=object.getString(RECIPE_IMAGE);
            r.setId(id);
            r.setName(name);
            r.setServings(servings);
            r.setThumnailURL(thumbnailURL);
            recipesList.add(r);
        }
        return  recipesList;

    }

    public static RecipeDetails getRecipeDetails(String jsonRecipe,String recipeId) throws JSONException {

        JSONArray json=null;
        RecipeDetails recipeDetails=null;
        List<Ingredients> ingredientsList=new ArrayList<>();
        List<Steps> stepsList=new ArrayList<>();
        try {
            json = new JSONArray(jsonRecipe);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for(int i=0;i<json.length();i++){
            JSONObject object=json.getJSONObject(i);
            String id=object.getString(RECIPE_ID);
            if(id.equals(recipeId)) {
                recipeDetails = new RecipeDetails();
                recipeDetails.setId(recipeId);
                JSONArray ingredients = object.getJSONArray(INGREDIENTS);
                for (int j = 0; j < ingredients.length(); j++) {
                    JSONObject ingredientsJSONObject = ingredients.getJSONObject(j);
                    String quantity = ingredientsJSONObject.getString(QUANTITY);
                    String measure = ingredientsJSONObject.getString(MEASURE);
                    String ingredient = ingredientsJSONObject.getString(INGREDIENT);
                    Ingredients ingredients1=new Ingredients();
                    ingredients1.setQuantity(quantity);
                    ingredients1.setMeasure(measure);
                    ingredients1.setIngredient(ingredient);
                    ingredientsList.add(ingredients1);
                }
                recipeDetails.setIngredientsList(ingredientsList);
                JSONArray steps=object.getJSONArray(STEPS);
                for(int k=0;k<steps.length();k++){
                    JSONObject stepsJSONObject=steps.getJSONObject(k);
                    String stepId=stepsJSONObject.getString(STEP_ID);
                    String shortDes=stepsJSONObject.getString(SHORT_DES);
                    String longDes=stepsJSONObject.getString(LONG_DES);
                    String videoUrl=stepsJSONObject.getString(VIDEO);
                    String thumbnailURL=stepsJSONObject.getString(THUMBNAIL);
                    Steps steps1=new Steps();
                    steps1.setStepId(stepId);
                    steps1.setShortDescription(shortDes);
                    steps1.setLongDescription(longDes);
                    steps1.setVideoUrl(videoUrl);
                    steps1.setThumbnailURL(thumbnailURL);
                    stepsList.add(steps1);
                }
                String servings=object.getString(RECIPE_SERVINGS);
                recipeDetails.setServings(servings);
                recipeDetails.setStepsList(stepsList);
                String name=object.getString(RECIPE_NAME);
                recipeDetails.setrName(name);

            }

        }
        return recipeDetails;

    }
}
