package com.project.krishna.bakingapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;

import com.project.krishna.bakingapp.data.RecipeDetails;
import com.project.krishna.bakingapp.data.RecipeListAdapter;
import com.project.krishna.bakingapp.data.Recipes;
import com.project.krishna.bakingapp.util.NetworkUtility;
import com.project.krishna.bakingapp.util.ParseUtility;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Recipes>>,RecipeListAdapter.RecipeClickListener {
    private static final int RECIPE_LOADER =10 ;
    public static final String CLICKED_RECIPE ="recipe_clicked" ;
    public static final String RECIPE_JSON ="recipe_json" ;
    RecyclerView recipeListRecycler;
    RecyclerView.LayoutManager layoutManager;
    RecipeListAdapter adapter;
    List<Recipes> recipes;
    private static String jsonRecipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recipeListRecycler=findViewById(R.id.rv_recipe_list);
        if(findViewById(R.id.landscape_layout)!=null){
            layoutManager=new GridLayoutManager(this,numberOfColumns());
        }
        else {
            layoutManager = new LinearLayoutManager(this);
        }
        recipeListRecycler.setLayoutManager(layoutManager);

        getSupportLoaderManager().initLoader(RECIPE_LOADER,null,this);



    }
    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // You can change this divider to adjust the size of the poster
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2;
        return nColumns;
    }

    @Override
    public Loader<List<Recipes>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<List<Recipes>>(this) {
            List<Recipes> recipesList;
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if(recipesList!=null){
                    deliverResult(recipesList);
                }
                else {
                    forceLoad();
                }
            }

            @Override
            public List<Recipes> loadInBackground() {
                URL recipeUrl=null;
                try {
                     recipeUrl=new URL(NetworkUtility.RECIPE_URL);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                jsonRecipe=null;
                try {
                     jsonRecipe=NetworkUtility.getRespsonseFromHttpUrl(recipeUrl);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                List<Recipes> recipesList=null;

                try {
                    recipesList= ParseUtility.getRecipeList(jsonRecipe);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return recipesList;
            }

            @Override
            public void deliverResult(List<Recipes> data) {
                recipesList=data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<Recipes>> loader, List<Recipes> data) {
        recipes=data;
        adapter=new RecipeListAdapter(this,data,MainActivity.this);
        recipeListRecycler.setAdapter(adapter);

    }

    @Override
    public void onLoaderReset(Loader<List<Recipes>> loader) {

    }

    @Override
    public void onRecipeClick(int position) {
        String recipeId=recipes.get(position).getId();
        Intent recipeStepsActivity=new Intent(this,DetailsActivity.class);
        recipeStepsActivity.putExtra(CLICKED_RECIPE,recipeId);
        recipeStepsActivity.putExtra(RECIPE_JSON,jsonRecipe);
        if(jsonRecipe==null){
            Log.i("TAG","null json");
        }
        startActivity(recipeStepsActivity);

    }
}
