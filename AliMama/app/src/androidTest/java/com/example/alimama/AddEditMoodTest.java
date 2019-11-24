package com.example.alimama;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.example.alimama.addEditMood.AddEditMoodActivity;
import com.example.alimama.addEditMood.DatePickerFragment;
import com.example.alimama.moodHistory.MoodHistory;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Date;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import static org.junit.Assert.assertTrue;


public class AddEditMoodTest {

    private Solo solo;

    /**
     * This function creates activity which sets USERNAME as "test"
     */
    @Rule
    public ActivityTestRule<AddEditMoodActivity> rule = new ActivityTestRule<AddEditMoodActivity>(AddEditMoodActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Intent intent = new Intent();
                    intent.putExtra("USERNAME", "test");
                    return intent;
                }
            };

    /**
     * Runs before all tests and creates solo instance.
     */
    @Before
    public void setUp() {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }


    /**
     * Check if selected emoticon is correct using the emotional state spinner
     */
    @Test
    public void checkEmoticonSpinner(){
        solo.assertCurrentActivity("Wrong Activity", AddEditMoodActivity.class);

        //select the first emotion on the emotional spinner - happy emotion)
        solo.pressSpinnerItem(0,0);
        assertTrue(solo.searchText("happy", true));


        //select the third emotion on the emotional spinner - heart emotion)
        solo.pressSpinnerItem(0,2);
        assertTrue(solo.searchText("heart", true));

        //select the 2nd item in the emotional state spinner
        solo.pressSpinnerItem(0,-1);
        assertTrue(solo.searchText("tears", true));
    }

    /**
     * Check if selected social situation is correct using the social state spinner
     */
    @Test
    public void checkSocialState(){
        solo.assertCurrentActivity("Wrong Activity", AddEditMoodActivity.class);

        //select the second social state emotion on the social state spinner - with one other person)
        solo.pressSpinnerItem(1,1);
        assertTrue(solo.isSpinnerTextSelected(1,"with one other person"));

        //select the fourth social state emotion on the social state spinner - with a crowd)
        solo.pressSpinnerItem(1,2);
        assertTrue(solo.isSpinnerTextSelected(1,"with a crowd"));

        //select the third social state emotion on the social state spinner - with teo to several ppl)
        solo.pressSpinnerItem(1,-1);
        assertTrue(solo.isSpinnerTextSelected(1,"with two to several ppl"));

    }

//    @Test
//    public void checkDatePickerDialog(){
////        solo.assertCurrentActivity("Wrong Activity", AddEditMoodActivity.class);
////        //solo.setDatePicker(0, 2014, 8, 8);
////
////        DatePicker datePicker = solo.getView(DatePicker.class, 0);
////        solo.setDatePicker(datePicker, 2014, 8, 8);
////        assertTrue(solo.searchText("2014-8-8"));
////        solo.clickOnView(solo.getView(R.id.etDate));
////        solo.assertCurrentActivity("Wrong Activity", DatePickerFragment.class);
//
//        solo.clickOnView(solo.getView(R.id.etDate));
//
//        solo.setDatePicker(0, 2000, 1, 1);
//
//        DatePickerFragment dialogFragment = (DatePickerFragment) DatePickerFragment.getFragmentManager()
//                .findFragmentByTag(datePicker.TAG);
//        DatePickerDialog dialog = (DatePickerDialog) dialogFragment.getDialog();
//        Button okButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
//        solo.clickOnView(okButton);
//
//        solo.waitForDialogToClose();
//    }


}
