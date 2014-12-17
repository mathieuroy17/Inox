package ca.qc.cstj.android.inox.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;

import ca.qc.cstj.android.inox.helpers.DateParser;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {


    private static final String ARG_DATE = "date";

    private EditText editText;
    private DateTime dateEdit;

    public DatePickerFragment() {

        dateEdit =  DateTime.now();
    }

    public static DatePickerFragment newInstance(String date) {
        DatePickerFragment newFragment = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DATE, date);
        newFragment.dateEdit = DateParser.Parse(date);
        newFragment.setArguments(args);
        return newFragment;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }


    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        // Create a new instance of DatePickerDialog and return it

        return new DatePickerDialog(getActivity(), this, dateEdit.getYear(), dateEdit.getMonthOfYear(), dateEdit.getDayOfMonth());
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        dateEdit = new DateTime(year,month,day,0,0);
        this.editText.setText(dateEdit.toString("yyyy-MM-dd"));
    }

}
