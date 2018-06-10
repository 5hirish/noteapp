package com.shirish.noteapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.shirish.noteapp.activity.MainActivity;
import com.shirish.noteapp.database.NotesDatabase;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItem;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class NoteCheckUnCheckTest {

    private Context context;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Before
    public void createDb() {
        context = InstrumentationRegistry.getTargetContext();
        //NotesDatabase.getDbInstance(context).clearAllTables();
    }

    @Test
    public void markNoteAndUnMarkInList() throws Exception {

        List<String> inputNotes = new ArrayList<>();
        inputNotes.add("Test_1");
        inputNotes.add("Test_2");
        inputNotes.add("Test_3");

        for (String noteContent: inputNotes) {
            onView(ViewMatchers.withId(R.id.rvNotePending)).perform(actionOnItem(hasDescendant(withText(noteContent)), ViewHolderViewAction.clickChildViewWithId(R.id.cbNoteMarked)));
        }

        for (String noteContent: inputNotes) {
            onView(ViewMatchers.withId(R.id.rvNoteComplete)).check(matches(hasDescendant(withText(noteContent))));
        }

    }

    @After
    public void closeDb() throws IOException {
        //NotesDatabase.getDbInstance(context).close();
    }
}



