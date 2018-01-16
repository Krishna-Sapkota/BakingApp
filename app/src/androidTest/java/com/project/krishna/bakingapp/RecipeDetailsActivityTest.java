package com.project.krishna.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import com.project.krishna.bakingapp.idlingresource.SimpleIdlingResource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;

/**
 * Created by Krishna on 1/15/18.
 */
@RunWith(AndroidJUnit4.class)
public class RecipeDetailsActivityTest {
    private static final String recipeId="1";
    IdlingResource idlingResource;
    @Rule

    public IntentsTestRule<MainActivity> mActivityRule = new IntentsTestRule<MainActivity>(
            MainActivity.class);


    @Before
    public void setup() {

        idlingResource=new SimpleIdlingResource();
        Espresso.registerIdlingResources(idlingResource);
    }




    @Test
    public void check_intent_is_launched() {

        onView(ViewMatchers.withId(R.id.rv_recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        intended(hasComponent(DetailsActivity.class.getName()));
    }
    @After
    public void tearDown(){
        if(idlingResource!=null) {
            Espresso.unregisterIdlingResources(idlingResource);
        }
    }


}
