package com.dmallcott.photoinspiration.feature.main;

import android.support.test.rule.ActivityTestRule;

import com.dmallcott.photoinspiration.R;
import com.dmallcott.photoinspiration.base.BaseUiTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class MainActivityTest extends BaseUiTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class, true, true);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void artistSearch_showsHint() {

        onView(withId(R.id.recycler_view_main)).check(

        screenshot(activityTestRule.getActivity(), "hint_displayed");
    }
}
