package com.project.krishna.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.project.krishna.bakingapp.data.RecipeDetails;
import com.project.krishna.bakingapp.data.RecipeDetailsAdapter;
import com.project.krishna.bakingapp.util.ParseUtility;

import org.json.JSONException;

public class RecipeDetailsActivity extends AppCompatActivity implements RecipeDetailsAdapter.RecipeDetailClickListener {
    RecyclerView detailsRecycler;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        detailsRecycler=findViewById(R.id.rv_recipe_details);
        layoutManager=new LinearLayoutManager(this);
        detailsRecycler.setLayoutManager(layoutManager);
        String recipeId=getIntent().getStringExtra(MainActivity.CLICKED_RECIPE);
        String recipeJson=getIntent().getStringExtra(MainActivity.RECIPE_JSON);
        RecipeDetails recipeDetails=null;
        try {
            recipeDetails = ParseUtility.getRecipeDetails(recipeJson,recipeId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RecipeDetailsAdapter detailsAdapter=new RecipeDetailsAdapter(this,recipeDetails,this);
        detailsRecycler.setAdapter(detailsAdapter);
    }

    @Override
    public void onRecipeStepsClick(int position) {

    }
}
