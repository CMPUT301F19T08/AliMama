package com.example.alimama;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

/**
 * This is a class that uses the time picker fragment to be able to select a time in the AddEditMoodActivity
 */

public class TimePickerFragment extends DialogFragment {

        private TimePickerDialog.OnTimeSetListener listener;

        public TimePickerFragment(TimePickerDialog.OnTimeSetListener listener) {
            this.listener = listener;
        }

    /**
     * This function retrieve the image
     * @return hour picked by participant.
     * @return minute picked by participant.
     */
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), listener, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }


}