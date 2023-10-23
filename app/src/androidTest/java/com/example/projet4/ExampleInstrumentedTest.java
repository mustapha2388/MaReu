package com.example.projet4;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EditText;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.projet4.controllers.ui.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */


@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {


    @Rule
    public ActivityScenarioRule<MainActivity> rule =
            new ActivityScenarioRule<>(MainActivity.class);

    private final int NUMBER_OF_MEETINGS = 3;


    @Test
    public void deleteMeeting() {

        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));

        onView(withId(R.id.recyclerView)).check(matches(hasChildCount(NUMBER_OF_MEETINGS)))
                .perform(RecyclerViewActions.actionOnItemAtPosition(
                        0,
                        new ViewAction() {
                            @Override
                            public Matcher<View> getConstraints() {
                                return null;
                            }

                            @Override
                            public String getDescription() {
                                return "Click on specific button";
                            }

                            @Override
                            public void perform(UiController uiController, View view) {
                                View btnDelete = view.findViewById(R.id.trash);
                                if (btnDelete != null) {
                                    btnDelete.performClick();
                                }
                            }
                        }));
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed())).check(matches(hasChildCount(NUMBER_OF_MEETINGS - 1)));

    }

    @Test
    public void addMeeting() {

        onView(withId(R.id.recyclerView)).check(matches(isDisplayed())).check(matches(hasChildCount(NUMBER_OF_MEETINGS)));

        onView(withId(R.id.addButton)).perform(click());


        onView(allOf(withId(R.id.set_time), withId(R.id.set_time), childAtPosition(childAtPosition(withClassName(is("androidx.appcompat.widget.LinearLayoutCompat")), 1), 0), isDisplayed())).perform(click());

        onView(allOf(withId(com.example.colorpicker.R.id.material_timepicker_mode_button), withContentDescription("Passer en mode saisie de texte pour la saisie de l'heure."), childAtPosition(childAtPosition(withId(android.R.id.content), 0), 4), isDisplayed())).perform(click());


        onView(
                allOf(childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed())).perform(replaceText("14"), closeSoftKeyboard());

        onView(
                allOf(withText("00"),
                        childAtPosition(
                                allOf(withId(com.example.colorpicker.R.id.material_minute_text_input),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                2)),
                                0),
                        isDisplayed())).perform(click());

        onView(
                allOf(childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed())).perform(replaceText("30"), closeSoftKeyboard());


        onView(allOf(withId(com.example.colorpicker.R.id.material_timepicker_ok_button), withText("OK"), childAtPosition(childAtPosition(withId(android.R.id.content), 0), 6), isDisplayed())).perform(click());

        String itemToSelect = "Peach";

        onView(ViewMatchers.withId(R.id.room_selected))
                .perform(ViewActions.click());

        onView(withText(itemToSelect))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click());

        onView(ViewMatchers.withId(R.id.room_selected)).check(ViewAssertions.matches(withText(itemToSelect)));


        onView(allOf(isDescendantOfA(withId(R.id.subject)), isAssignableFrom(EditText.class))).perform(typeText("Reunion D"), closeSoftKeyboard());

        onView(allOf(isDescendantOfA(withId(R.id.emails)), isAssignableFrom(EditText.class))).perform(typeText("test@gmail.com"), closeSoftKeyboard());

        onView((withId(R.id.insert_meeting))).check(matches(isDisplayed())).perform(click());

        onView((withId(R.id.recyclerView))).check(matches(isDisplayed())).check(matches(hasChildCount(NUMBER_OF_MEETINGS + 1)));

        onView(withText("Réunion D - 14:30 - Peach")).check(matches(isDisplayed()));
    }

    @Test
    public void FilterMeetingByRoom() {

        onView(withId(R.id.recyclerView)).check(matches(ViewMatchers.isDisplayed()));

        onView(withId(R.id.action_create_order)).check(matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.action_create_order)).perform(click());


        ViewInteraction materialTextView = onView(
                Matchers.allOf(withId(androidx.transition.R.id.title), withText(R.string.filter_by_room),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.content),
                                        1),
                                0),
                        isDisplayed()));
        materialTextView.perform(click());

        DataInteraction materialTextView2 = onData(anything())
                .inAdapterView(Matchers.allOf(withId(R.id.listView),
                        childAtPosition(
                                withId(R.id.dialog_list),
                                0)))
                .atPosition(0);
        materialTextView2.perform(click());

        onView(withId(R.id.recyclerView)).check(matches(isDisplayed())).check(matches(hasChildCount(1)));


        onView(withText("Réunion B - 15:00 - Mario")).check(matches(isDisplayed()));

        onView(withId(R.id.recyclerView))
                .check(matches(
                        hasDescendant(withText(containsString("Réunion B - 15:00 - Mario")))));


    }


    @Test
    public void FilterMeetingByHour() {

        onView(withId(R.id.recyclerView)).check(matches(ViewMatchers.isDisplayed()));

        onView(withId(R.id.action_create_order)).check(matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.action_create_order)).perform(click());


        ViewInteraction materialTextView = onView(
                Matchers.allOf(withId(androidx.transition.R.id.title), withText(R.string.filter_by_hour),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.content),
                                        1),
                                0),
                        isDisplayed()));
        materialTextView.perform(click());

        onView(allOf(withId(com.example.colorpicker.R.id.material_timepicker_mode_button), withContentDescription("Passer en mode saisie de texte pour la saisie de l'heure."), childAtPosition(childAtPosition(withId(android.R.id.content), 0), 4), isDisplayed())).perform(click());


        onView(
                allOf(childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed())).perform(replaceText("15"), closeSoftKeyboard());

        onView(
                allOf(withText("00"),
                        childAtPosition(
                                allOf(withId(com.example.colorpicker.R.id.material_minute_text_input),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                2)),
                                0),
                        isDisplayed())).perform(click());

        onView(
                allOf(childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed())).perform(replaceText("00"), closeSoftKeyboard());


        onView(allOf(withId(com.example.colorpicker.R.id.material_timepicker_ok_button), withText("OK"), childAtPosition(childAtPosition(withId(android.R.id.content), 0), 6), isDisplayed())).perform(click());


        onView(withId(R.id.recyclerView)).check(matches(isDisplayed())).check(matches(hasChildCount(1)));


        onView(withText("Réunion B - 15:00 - Mario")).check(matches(isDisplayed()));

        onView(withId(R.id.recyclerView))
                .check(matches(
                        hasDescendant(withText(containsString("Réunion B - 15:00 - Mario")))));


    }


    public static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
