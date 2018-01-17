package com.project.krishna.bakingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class RecipeVideoActivity extends AppCompatActivity {


    private static final String PLAYER_FRAG ="player_fragment" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_video);


      String videoUrl=getIntent().getStringExtra(DetailsActivity.VIDEO_URL);
      String longDes=getIntent().getStringExtra(DetailsActivity.LONG_DESCRIPTION);
      String thumnailURL=getIntent().getStringExtra(DetailsActivity.THUMBNAIL_URL);


      VideoPlayerFragment playerFragment=new VideoPlayerFragment();
      playerFragment.setVideoUrl(videoUrl);
      playerFragment.setLongDes(longDes);
      playerFragment.setThumnailUrl(thumnailURL);
      if(savedInstanceState==null) {
          getSupportFragmentManager().beginTransaction()
                  .replace(R.id.player_container, playerFragment, PLAYER_FRAG)
                  .commit();
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
