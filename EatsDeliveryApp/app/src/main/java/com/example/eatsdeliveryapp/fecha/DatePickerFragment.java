package com.example.eatsdeliveryapp.fecha;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;


//clase publica para mostrar un datepicker en pantalla a la hora de solicitar fechas
public class DatePickerFragment extends DialogFragment {

    //atributo privado
    private DatePickerDialog.OnDateSetListener listener;

    //generamos el dialog del fragment para el date picker
    public static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener listener) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setListener(listener);
        return fragment;
    }

    //activamos un listener necesario, si o si
    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }


    //metodo que genera el dialog en pantalla
    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), listener, year, month, day);
    }

}
