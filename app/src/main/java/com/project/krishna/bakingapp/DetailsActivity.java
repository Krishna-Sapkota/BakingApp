package com.project.krishna.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.project.krishna.bakingapp.data.RecipeDetails;
import com.project.krishna.bakingapp.util.ParseUtility;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity implements MasterRecipeDetailFragment.OnRecipeStepsClickListener  {
    private static final String DETAILS ="detail_frag" ;
    public static final String VIDEO_URL ="video_url" ;
    public static final String LONG_DESCRIPTION ="long_des" ;
    private static final String TAG=DetailsActivity.class.getSimpleName();
    public static final String THUMBNAIL_URL ="thumbnail" ;
    String recipeId;
    String recipeJson;
    String videoUrl;
    String longDes;
    boolean twoPane=false;
    @Nullable @BindView(R.id.baking_app_linear_layout)
    LinearLayout tablet_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        recipeId=getIntent().getStringExtra(MainActivity.CLICKED_RECIPE);
        recipeJson=getIntent().getStringExtra(MainActivity.RECIPE_JSON);
        //videoUrl=getIntent().getStringExtra(VIDEO_URL);
        //longDes=getIntent().getStringExtra(LONG_DESCRIPTION);


        if(tablet_layout!=null) {
            twoPane = true;
            if(savedInstanceState==null) {
                MasterRecipeDetailFragment detailFragment = new MasterRecipeDetailFragment();

                detailFragment.setRecipeJson(recipeJson);
                detailFragment.setRecipeId(recipeId);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.recipe_list_container, detailFragment)
                        .commit();
                VideoPlayerFragment newFragment = new VideoPlayerFragment();
                RecipeDetails details = null;
                try {
                    details = ParseUtility.getRecipeDetails(recipeJson, recipeId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                newFragment.setVideoUrl(details.getStepsList().get(0).getVideoUrl());
                newFragment.setLongDes(details.getStepsList().get(0).getLongDescription());
                newFragment.setThumnailUrl(details.getStepsList().get(0).getThumbnailURL());
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.video_container, newFragment)
                        .commit();
            }
        }
        else {
            twoPane=false;
            if(savedInstanceState==null) {
                MasterRecipeDetailFragment detailFragment = new MasterRecipeDetailFragment();
                detailFragment.setRecipeJson(recipeJson);
                detailFragment.setRecipeId(recipeId);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.add(R.id.recipe_list_container, detailFragment, DETAILS);
                transaction.commit();
                Log.i(TAG, "fragment added");
            }

        }


    }

    @Override
    public void onStepsSelected(RecipeDetails recipeDetails, int position) {
        String videoURL;
        String longDes;
        String thumbnailUrl;
        videoURL=recipeDetails.getStepsList().get(position).getVideoUrl();
        longDes=recipeDetails.getStepsList().get(position).getLongDescription();
        thumbnailUrl=recipeDetails.getStepsList().get(position).getThumbnailURL();

        if(twoPane) {

            VideoPlayerFragment newFragment = new VideoPlayerFragment();
            newFragment.setVideoUrl(videoURL);
            newFragment.setLongDes(longDes);
            newFragment.setThumnailUrl(thumbnailUrl);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.video_container, newFragment)
                    .commit();
        }
        else{
            Intent intent = new Intent(this, RecipeVideoActivity.class);
            intent.putExtra(VIDEO_URL,videoURL);
            intent.putExtra(LONG_DESCRIPTION,longDes);
            intent.putExtra(THUMBNAIL_URL,thumbnailUrl);
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
