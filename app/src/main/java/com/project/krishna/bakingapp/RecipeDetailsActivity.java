package com.project.krishna.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import com.project.krishna.bakingapp.data.RecipeDetails;
import com.project.krishna.bakingapp.data.RecipeDetailsAdapter;
import com.project.krishna.bakingapp.util.ParseUtility;

import org.json.JSONException;

public class RecipeDetailsActivity extends AppCompatActivity implements RecipeDetailsAdapter.RecipeDetailClickListener {
    public static final String VIDEO_URL ="video_url" ;
    public static final String LONG_DESCRIPTION ="long_des" ;
    RecyclerView detailsRecycler;
    RecyclerView.LayoutManager layoutManager;
    RecipeDetails recipeDetails=null;
    TextView headingText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        detailsRecycler=findViewById(R.id.rv_recipe_details);
        headingText=(TextView)findViewById(R.id.tv_recipe_heading);
        layoutManager=new LinearLayoutManager(this);
        detailsRecycler.setLayoutManager(layoutManager);
        String recipeId=getIntent().getStringExtra(MainActivity.CLICKED_RECIPE);
        String recipeJson=getIntent().getStringExtra(MainActivity.RECIPE_JSON);
        try {
            recipeDetails = ParseUtility.getRecipeDetails(recipeJson,recipeId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        headingText.setText(recipeDetails.getrName());
        RecipeDetailsAdapter detailsAdapter=new RecipeDetailsAdapter(this,recipeDetails,this);
        detailsRecycler.setAdapter(detailsAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }

        return true;
    }

    @Override
    public void onRecipeStepsClick(int position) {
        Intent videoActivity=new Intent(this,RecipeVideoActivity.class);
        String videoURL=recipeDetails.getStepsList().get(position).getVideoUrl();
        String longDes=recipeDetails.getStepsList().get(position).getLongDescription();

        videoActivity.putExtra(VIDEO_URL,videoURL);
        videoActivity.putExtra(LONG_DESCRIPTION,longDes);
        startActivity(videoActivity);

    }
}
