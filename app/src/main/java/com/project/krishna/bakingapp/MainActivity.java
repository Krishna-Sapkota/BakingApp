package com.project.krishna.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.project.krishna.bakingapp.data.RecipeListAdapter;
import com.project.krishna.bakingapp.data.Recipes;
import com.project.krishna.bakingapp.idlingresource.SimpleIdlingResource;
import com.project.krishna.bakingapp.util.NetworkUtility;
import com.project.krishna.bakingapp.util.ParseUtility;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Recipes>>,RecipeListAdapter.RecipeClickListener {
    private static final int RECIPE_LOADER =10 ;
    public static final String CLICKED_RECIPE ="recipe_clicked" ;
    public static final String RECIPE_JSON ="recipe_json" ;
    @BindView(R.id.rv_recipe_list)
    RecyclerView recipeListRecycler;
    @Nullable @BindView(R.id.landscape_layout)
    FrameLayout landscapeLayout;
    @Nullable@BindView(R.id.tv_internet_error)
    TextView internetError;
    RecyclerView.LayoutManager layoutManager;
    RecipeListAdapter adapter;
    List<Recipes> recipes;
    private static String jsonRecipe;

    @Nullable
    private SimpleIdlingResource mIdlingResource;

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        recipeListRecycler=findViewById(R.id.rv_recipe_list);
        if(landscapeLayout!=null){
            layoutManager=new GridLayoutManager(this,numberOfColumns());
        }
        else {
            layoutManager = new LinearLayoutManager(this);
        }
        recipeListRecycler.setLayoutManager(layoutManager);
        if(isOnline()) {
            internetError.setVisibility(View.GONE);
            getSupportLoaderManager().initLoader(RECIPE_LOADER, null, this);
        }
        else {
            internetError.setVisibility(View.VISIBLE);
            internetError.setText(R.string.internet_error_string);
        }



    }

    /**
     checking online code used from
     https://www.google.com/url?q=http://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-timeouts&sa=D&ust=1510813688269000&usg=AFQjCNF0VNbteS1wdDxpwk5kwL7t-zQlyA
     **/

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if(netInfo==null||!netInfo.isConnected()){
            return false;
        }
        else {
            return true;
        }
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

       /* Intent intent = new Intent(this, BakingAppWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra(CLICKED_RECIPE,recipeId);
        intent.putExtra(RECIPE_JSON,jsonRecipe);
        //intent.setAction("android.appwidget.action.APPWIDGET_UPDATE2");
        sendBroadcast(intent);*/
       WidgetUpdateService.startBakingService(this,recipeId,jsonRecipe);



        startActivity(recipeStepsActivity);

    }
}
