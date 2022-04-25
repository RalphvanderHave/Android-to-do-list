package com.example.group5_to_day;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.Toast;

import java.util.Calendar;

public class CommonUserInterfaceMethods {
    public static void shortToast(Context context, String string) {
        Toast.makeText(
                context,
                string,
                Toast.LENGTH_SHORT
        ).show();
    }

    public static void showDatePickerDialog(Context context, DatePickerDialog.OnDateSetListener listener) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                listener,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    public static void showTimePickerDialog(Context context, TimePickerDialog.OnTimeSetListener listener) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                context,
                listener,
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                true
        );
        timePickerDialog.show();
    }
}
