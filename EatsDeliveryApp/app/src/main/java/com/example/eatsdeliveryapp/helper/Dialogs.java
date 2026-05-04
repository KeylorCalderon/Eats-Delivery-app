package com.example.eatsdeliveryapp.helper;

import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;

//clase publica de dialogs, para mostrar un dialogo más intuitivo para el datepicker
public class Dialogs {

    //funcion estatica para los eventos
    public static void showConfirmDialog(Context context, String title, DialogCallback onPositiveClick, DialogCallback onNegativeClick) {

        // Construye el evento de confirmación y lo muestra
        DialogInterface.OnClickListener onClickListener = (dialog, which) -> {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    onPositiveClick.emit();
                    break;
                default:
                    onNegativeClick.emit();
                    break;
            }
        };

        //envia una alerta segun el contexto que se le brinde
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder
                .setMessage(title)
                .setPositiveButton("Sí", onClickListener)
                .setNegativeButton("No", onClickListener)
                .show();
    }

}