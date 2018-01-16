package com.project.krishna.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.project.krishna.bakingapp.data.RecipeDetails;

import static com.project.krishna.bakingapp.BakingAppWidget.recipeDetails;


/**
 * Created by Krishna on 1/16/18.
 */

public class GridWidgetService extends RemoteViewsService {
    RecipeDetails ingredientsList;

    @Override
    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this.getApplicationContext(),intent);
    }


    class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        Context mContext = null;

        public GridRemoteViewsFactory(Context context,Intent intent) {
            mContext = context;

        }

        @Override
        public void onCreate() {
        }

        @Override
        public void onDataSetChanged() {
            ingredientsList = recipeDetails;
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {

            return ingredientsList.getIngredientsList().size();
        }

        @Override
        public RemoteViews getViewAt(int position) {

            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_grid_view_item);
            Log.i("TAG",ingredientsList.getIngredientsList().get(position).getIngredient());
            String ingredient ="";

            ingredient = ingredient + ingredientsList.getIngredientsList().get(position).getQuantity() + " ";
            ingredient = ingredient + ingredientsList.getIngredientsList().get(position).getMeasure() + " ";
            ingredient = ingredient + ingredientsList.getIngredientsList().get(position).getIngredient();



           views.setTextViewText(R.id.widget_grid_view_item, ingredient);
           // views.setTextViewText(R.id.widget_grid_view_item,"HELLO WORLD");

            Intent fillInIntent = new Intent();
            views.setOnClickFillInIntent(R.id.widget_grid_view_item, fillInIntent);

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }




    }


}