package com.project.krishna.bakingapp.data;


import java.util.List;

/**
 * Created by Krishna on 1/13/18.
 */

public class RecipeDetails  {

    private String id;
    private String rName;
    private String servings;
    private List<Ingredients> ingredientsList;
    private List<Steps> stepsList;

    public RecipeDetails(){

    }

    public List<Ingredients> getIngredientsList() {
        return ingredientsList;
    }

    public void setIngredientsList(List<Ingredients> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public List<Steps> getStepsList() {
        return stepsList;
    }

    public void setStepsList(List<Steps> stepsList) {
        this.stepsList = stepsList;
    }



    public String getrName() {
        return rName;
    }

    public void setrName(String rName) {
        this.rName = rName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }


}