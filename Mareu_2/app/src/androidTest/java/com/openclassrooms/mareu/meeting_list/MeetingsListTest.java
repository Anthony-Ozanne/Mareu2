
package com.openclassrooms.mareu.meeting_list;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.ui.mareu.ListMeetingActivity;
import com.openclassrooms.mareu.utils.DeleteViewAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.ViewPagerActions.scrollRight;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNull.notNullValue;




/**
 * Test class for list of meetings
 */
@RunWith(AndroidJUnit4.class)
public class MeetingsListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;

    private ListMeetingActivity mActivity;

    @Rule
    public ActivityTestRule<ListMeetingActivity> mActivityRule =
            new ActivityTestRule(ListMeetingActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myMeetingsList_shouldNotBeEmpty() {
        // Faites d'abord défiler jusqu'à la position qui doit être mise en correspondance et cliquez dessus.
        onView(ViewMatchers.withId(R.id.list_meeting))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * Lorsque nous supprimons un élément, l'élément n'est plus affiché
     */
    @Test
    public void myMeetingsList_deleteAction_shouldRemoveItem() {
        // On enlève l'élément à la position 2
        onView(ViewMatchers.withId(R.id.list_meeting)).check(withItemCount(ITEMS_COUNT));
        // Lorsque vous effectuez un clic sur une icône de suppression
        onView(ViewMatchers.withId(R.id.list_meeting))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Alors : le nombre d'élément est 11
        onView(ViewMatchers.withId(R.id.list_meeting)).check(withItemCount(ITEMS_COUNT-1));
    }

    }




