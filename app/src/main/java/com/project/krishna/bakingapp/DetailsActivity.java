package com.project.krishna.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.project.krishna.bakingapp.data.RecipeDetails;
import com.project.krishna.bakingapp.util.ParseUtility;

import org.json.JSONException;

public class DetailsActivity extends AppCompatActivity implements MasterRecipeDetailFragment.OnRecipeStepsClickListener  {
    private static final String DETAILS ="detail_frag" ;
    public static final String VIDEO_URL ="video_url" ;
    public static final String LONG_DESCRIPTION ="long_des" ;
    private static final String TAG=DetailsActivity.class.getSimpleName();
    String recipeId;
    String recipeJson;
    String videoUrl;
    String longDes;
    boolean twoPane=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        recipeId=getIntent().getStringExtra(MainActivity.CLICKED_RECIPE);
        recipeJson=getIntent().getStringExtra(MainActivity.RECIPE_JSON);
        //videoUrl=getIntent().getStringExtra(VIDEO_URL);
        //longDes=getIntent().getStringExtra(LONG_DESCRIPTION);



        if(findViewById(R.id.baking_app_linear_layout)!=null) {
            twoPane = true;
            MasterRecipeDetailFragment detailFragment=new MasterRecipeDetailFragment();

            detailFragment.setRecipeJson(recipeJson);
            detailFragment.setRecipeId(recipeId);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.recipe_list_container,detailFragment)
                    .commit();
            VideoPlayerFragment newFragment = new VideoPlayerFragment();
            RecipeDetails details=null;
            try {
                details = ParseUtility.getRecipeDetails(recipeJson,recipeId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            newFragment.setVideoUrl(details.getStepsList().get(0).getVideoUrl());
            newFragment.setLongDes(details.getStepsList().get(0).getLongDescription());
            getSupportFragmentManager().beginTransaction()
                   .add(R.id.video_container, newFragment)
                   .commit();
        }
        else {
            twoPane=false;
            MasterRecipeDetailFragment detailFragment=new MasterRecipeDetailFragment();

            detailFragment.setRecipeJson(recipeJson);
            detailFragment.setRecipeId(recipeId);
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction transaction=fragmentManager.beginTransaction();
            transaction.add(R.id.recipe_list_container,detailFragment,DETAILS);
            transaction.commit();
            Log.i(TAG,"fragment added");

        }


    }

    @Override
    public void onStepsSelected(RecipeDetails recipeDetails, int position) {
        String videoURL;
        String longDes;
        videoURL=recipeDetails.getStepsList().get(position).getVideoUrl();
        longDes=recipeDetails.getStepsList().get(position).getLongDescription();

        if(twoPane) {

            VideoPlayerFragment newFragment = new VideoPlayerFragment();
            newFragment.setVideoUrl(videoURL);
            newFragment.setLongDes(longDes);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.video_container, newFragment)
                    .commit();
        }
        else{
            Intent intent = new Intent(this, RecipeVideoActivity.class);
            intent.putExtra(VIDEO_URL,videoURL);
            intent.putExtra(LONG_DESCRIPTION,longDes);
            startActivity(intent);

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }

        return true;
    }
}
