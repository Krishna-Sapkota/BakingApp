package com.project.krishna.bakingapp.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.project.krishna.bakingapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Krishna on 1/11/18.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {
    List<Recipes> recipesList;
    Context mContext;
    RecipeClickListener mOnClickListener;

    public interface RecipeClickListener{
        public void onRecipeClick(int position);
    }
    public RecipeListAdapter(Context context,List<Recipes> recipes,RecipeClickListener listener){
        mContext=context;
        mOnClickListener=listener;
        recipesList=recipes;

    }


    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context c=parent.getContext();
        int layout_poster= R.layout.recipe_list_view;
        LayoutInflater layoutInflater=LayoutInflater.from(c);
        View root=layoutInflater.inflate(layout_poster,parent,false);

        return new RecipeViewHolder(root);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        String name=recipesList.get(position).getName();
        holder.recipeName.setText(name);

    }

    @Override
    public int getItemCount() {
        if(recipesList!=null)
            return recipesList.size();
        else return  0;
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder   implements View.OnClickListener {
        @BindView(R.id.tv_recipe_name)
        Button recipeName;
        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            recipeName.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition=getAdapterPosition();
            mOnClickListener.onRecipeClick(clickedPosition);

        }
    }

}
