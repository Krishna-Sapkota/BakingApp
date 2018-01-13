package com.project.krishna.bakingapp.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.project.krishna.bakingapp.R;

/**
 * Created by Krishna on 1/13/18.
 */

public class RecipeDetailsAdapter extends RecyclerView.Adapter<RecipeDetailsAdapter.RecipeDetailViewHolder> {
    private static final int INGREDIENTS_VIEW =20 ;
    private static final int STEPS_VIEW =30 ;
    RecipeDetails recipeDetails;
    Context mContext;
    RecipeDetailClickListener mOnClickListener;


    public interface RecipeDetailClickListener{
        public void onRecipeStepsClick(int position);
    }

    public RecipeDetailsAdapter(Context context,RecipeDetails details,RecipeDetailClickListener listener){
        mContext=context;
        mOnClickListener=listener;
        recipeDetails=details;

    }

    @Override
    public RecipeDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context c=parent.getContext();
        int layout_poster= R.layout.recipe_details_list;
        LayoutInflater layoutInflater=LayoutInflater.from(c);
        View root=layoutInflater.inflate(layout_poster,parent,false);

        return new RecipeDetailsAdapter.RecipeDetailViewHolder(root);


    }

    @Override
    public void onBindViewHolder(RecipeDetailViewHolder holder, int position) {

        String ingredients="";
        if(position==0&&getItemViewType(position)==INGREDIENTS_VIEW) {
            for (int i = 0; i < recipeDetails.getIngredientsList().size(); i++) {
                String quantity = recipeDetails.getIngredientsList().get(i).getQuantity();
                String measure = recipeDetails.getIngredientsList().get(i).getMeasure();
                String ingr = recipeDetails.getIngredientsList().get(i).getIngredient();
                ingredients = ingredients + " "+quantity + " "+measure + " "+ingr + "\n \n";
            }
            holder.ingredients.setText(ingredients);

        }
        String recipeSteps=recipeDetails.getStepsList().get(position).getStepId();
        recipeSteps=recipeSteps+ " "+recipeDetails.getStepsList().get(position).getShortDescription();
        holder.recipeSteps.setText(recipeSteps);
    }

    @Override
    public int getItemCount() {
        if(recipeDetails!=null)
            return recipeDetails.getStepsList().size();
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return INGREDIENTS_VIEW;
        }
        else{
            return STEPS_VIEW;
        }
    }

    public class RecipeDetailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView ingredients;
        Button recipeSteps;
        public RecipeDetailViewHolder(View itemView) {
            super(itemView);
            ingredients=itemView.findViewById(R.id.tv_recipe_ingredients);
            recipeSteps=itemView.findViewById(R.id.btn_recipe_steps);
            recipeSteps.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position=getAdapterPosition();
            mOnClickListener.onRecipeStepsClick(position);

        }
    }
}
