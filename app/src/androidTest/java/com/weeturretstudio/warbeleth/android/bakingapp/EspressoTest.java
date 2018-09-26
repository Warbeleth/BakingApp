package com.weeturretstudio.warbeleth.android.bakingapp;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class EspressoTest {

    /**
     * The ActivityTestRule is a rule provided by Android used for functional testing of a single
     * activity. The activity that will be tested will be launched before each test that's annotated
     * with @Test and before methods annotated with @Before. The activity will be terminated after
     * the test and methods annotated with @After are complete. This rule allows you to directly
     * access the activity during the test.
     */
    @Rule
    public ActivityTestRule<RecipeListActivity> mActivityTestRule = new ActivityTestRule<>(RecipeListActivity.class);

    @Test
    public void DataHasLoaded() throws InterruptedException {
        //Wait for content to load (5s is typically more than enough time)
        Thread.sleep(5000);
    }

    @Test
    public void CheckFirstItem() throws InterruptedException {
        onView(withId(R.id.recipe_list)).perform(
                RecyclerViewActions.actionOnItemAtPosition(
                        0,
                        MyViewAction.clickChildViewWithId(
                                R.id.recipe_list_item_container)));

        ViewInteraction check = onView(withId(R.id.recipesteplistactivity_list)).check(new RecyclerViewItemCountAssertion(8));

        pressBack();
    }

    @Test
    public void CheckSecondItem() throws InterruptedException {
        onView(withId(R.id.recipe_list)).perform(
                RecyclerViewActions.actionOnItemAtPosition(
                        1,
                        MyViewAction.clickChildViewWithId(
                                R.id.recipe_list_item_container)));

        onView(withId(R.id.recipesteplistactivity_list)).check(new RecyclerViewItemCountAssertion(11));

        pressBack();

    }

    @Test
    public void CheckThirdItem() throws InterruptedException {
        onView(withId(R.id.recipe_list)).perform(
                RecyclerViewActions.actionOnItemAtPosition(
                        2,
                        MyViewAction.clickChildViewWithId(
                                R.id.recipe_list_item_container)));

        onView(withId(R.id.recipesteplistactivity_list)).check(new RecyclerViewItemCountAssertion(14));

        pressBack();
    }

    @Test
    public void CheckFourthItem() throws InterruptedException {
        onView(withId(R.id.recipe_list)).perform(
                RecyclerViewActions.actionOnItemAtPosition(
                        3,
                        MyViewAction.clickChildViewWithId(
                                R.id.recipe_list_item_container)));

        onView(withId(R.id.recipesteplistactivity_list)).check(new RecyclerViewItemCountAssertion(14));

        pressBack();
    }
}
