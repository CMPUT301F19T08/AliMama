package com.example.alimama;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

/**
 * This is a class that uses the date picker fragment to be able to select a date in the AddEditMoodActivity
 */


public class DatePickerFragment extends DialogFragment {
        
        private DatePickerDialog.OnDateSetListener listener;
        
        public DatePickerFragment(DatePickerDialog.OnDateSetListener listener) {
            this.listener = listener;
        }

    /**
     * This function retrieve the image
     * @return year picked by participant.
     * @return month picked by participant.
     * @return day of month picked by participant.
     */

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), listener, year, month, day);
        }
    }