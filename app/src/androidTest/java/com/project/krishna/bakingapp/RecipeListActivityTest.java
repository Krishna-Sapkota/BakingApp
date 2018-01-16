package com.project.krishna.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.project.krishna.bakingapp.util.TestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;

/**
 * Created by Krishna on 1/15/18.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeListActivityTest {
    private static final String recipeName ="Nutella Pie";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void recipe_list_diplayed() {


        onView(TestUtils.withRecyclerView(R.id.rv_recipe_list)
                .atPositionOnView(0, R.id.tv_recipe_name))
                .check(matches(withText(recipeName)));

    }
    @Test
    public void check_player_visible(){

        onView(withId(R.id.rv_recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(1,click()));
        onView(withId(R.id.rv_recipe_details)).perform(RecyclerViewActions.actionOnItemAtPosition(1,click()));
        onView(allOf(instanceOf(SimpleExoPlayerView.class), withId(R.id.playerView))).check(matches(isDisplayed()));

    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }


}
