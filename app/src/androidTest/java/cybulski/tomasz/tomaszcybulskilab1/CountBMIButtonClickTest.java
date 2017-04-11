package cybulski.tomasz.tomaszcybulskilab1;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Locale;

import cybulski.tomasz.tomaszcybulskilab1.Abstract.ICountBMI;
import cybulski.tomasz.tomaszcybulskilab1.Activities.MainActivity;
import cybulski.tomasz.tomaszcybulskilab1.Concrete.CountBMIMetric;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by tomcy on 11.04.2017.
 * UI tests
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class CountBMIButtonClickTest {
    private String validTestHeight;
    private String validTestMass;
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Before
    public void initValidTestData(){
        validTestHeight = "1.80";
        validTestMass = "70";
    }

    @Test
    public void countValidBMI_sameActivity(){
        //GIVEN
        ICountBMI countBMI = new CountBMIMetric();
        float result = countBMI.countBMI(Float.parseFloat(validTestMass), Float.parseFloat(validTestHeight));
        String stringResult = String.format(Locale.getDefault(), "%.2f", result);

        //WHERE
        onView(withId(R.id.edit_text_mass))
                .perform(replaceText(validTestMass), closeSoftKeyboard());
        onView(withId(R.id.edit_text_height))
                .perform(replaceText(validTestHeight), closeSoftKeyboard());
        onView(withText(R.string.count_bmi)).perform(click());

        //THEN
        onView(withId(R.id.text_view_result_bmi)).check(matches(withText(stringResult)));
    }

}
