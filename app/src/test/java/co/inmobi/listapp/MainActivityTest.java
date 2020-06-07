package co.inmobi.listapp;

import android.content.Intent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ActivityController;

import co.inmobi.listapp.ui.MainActivity;
import co.inmobi.listapp.ui.list.ListActivity;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

    private MainActivity activity;
    private ActivityController<MainActivity> activityController;

    @Before
    public void setup() {
        activityController = Robolectric.buildActivity(MainActivity.class);
        activity = activityController.get();
        activityController.create();
        activityController.start();
    }

    @Test
    public void testStartButtonClickToStartActivity() {
        activity.findViewById(R.id.btnStart).performClick();

        Intent expectedIntent = new Intent(activity, ListActivity.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }
}
