package com.project.krishna.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.krishna.bakingapp.data.RecipeDetails;
import com.project.krishna.bakingapp.data.RecipeDetailsAdapter;
import com.project.krishna.bakingapp.util.ParseUtility;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Krishna on 1/14/18.
 */

public class MasterRecipeDetailFragment extends Fragment implements RecipeDetailsAdapter.RecipeDetailClickListener,LoaderManager.LoaderCallbacks<RecipeDetails> {

    public static final String VIDEO_URL ="video_url" ;
    public static final String LONG_DESCRIPTION ="long_des" ;
    private static final int DETAILS_LOADER =12;
    private static final String LIST_STATE ="list_state" ;
    @BindView(R.id.rv_recipe_details) RecyclerView detailsRecycler;
    RecyclerView.LayoutManager layoutManager;
    RecipeDetails recipeDetails=null;
    @BindView(R.id.tv_recipe_heading) TextView headingText;
    private Parcelable scrollState;
    private  String recipeId;
    private  String recipeJson;

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeJson() {
        return recipeJson;
    }

    public void setRecipeJson(String recipeJson) {
        this.recipeJson = recipeJson;
    }



    public MasterRecipeDetailFragment(){


    }

    // Define a new interface OnImageClickListener that triggers a callback in the host activity
    OnRecipeStepsClickListener mCallback;

    @Override
    public void onRecipeStepsClick(RecipeDetails recipeDetails,int position) {
        mCallback.onStepsSelected(recipeDetails,position);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_master_recipe_detail, container, false);
        ButterKnife.bind(this,rootView);
        layoutManager=new LinearLayoutManager(getContext());
        detailsRecycler.setLayoutManager(layoutManager);
        getActivity().getSupportLoaderManager().initLoader(DETAILS_LOADER,null,this);
        return rootView;

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(LIST_STATE,detailsRecycler.getLayoutManager().onSaveInstanceState());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState!=null) {
            scrollState = savedInstanceState.getParcelable(LIST_STATE);
            detailsRecycler.getLayoutManager().onRestoreInstanceState(scrollState);
            Log.i("TAG","restored");

        }

    }

    @Override
    public Loader<RecipeDetails> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<RecipeDetails>(getContext()) {
            RecipeDetails details=null;

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if(details!=null){
                    deliverResult(details);
                }
                else {
                    forceLoad();
                }
            }

            @Override
            public RecipeDetails loadInBackground() {
                try {
                    recipeDetails = ParseUtility.getRecipeDetails(recipeJson,recipeId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return recipeDetails;
            }

            @Override
            public void deliverResult(RecipeDetails data) {
                details=data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<RecipeDetails> loader, RecipeDetails data) {
        Log.i("TAG","load finished");
        recipeDetails=data;
        headingText.setText(recipeDetails.getrName());
        RecipeDetailsAdapter detailsAdapter=new RecipeDetailsAdapter(getActivity(),recipeDetails,MasterRecipeDetailFragment.this);
        detailsRecycler.setAdapter(detailsAdapter);


    }

    @Override
    public void onLoaderReset(Loader<RecipeDetails> loader) {

    }


    // OnImageClickListener interface, calls a method in the host activity named onImageSelected
    public interface OnRecipeStepsClickListener {
        void onStepsSelected(RecipeDetails recipeDetails,int position);
    }

    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (OnRecipeStepsClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnRecipeStepsClickListener");
        }
    }


}
