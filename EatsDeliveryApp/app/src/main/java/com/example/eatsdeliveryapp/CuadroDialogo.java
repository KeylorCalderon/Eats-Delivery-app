package com.example.eatsdeliveryapp;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class CuadroDialogo {


    public interface  FinalizoCuadroDialogo{
        void ResultadoCuadroDialogo(int cantidad);
    }

    private FinalizoCuadroDialogo interfaz;

    public CuadroDialogo(Context context, FinalizoCuadroDialogo actividad) {

        interfaz= actividad;

        final Dialog dialogo = new Dialog(context);
        dialogo.requestWindowFeature( Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);
        dialogo.setContentView(R.layout.cuadro_dialogo);

        final EditText cantidad = (EditText) dialogo.findViewById(R.id.cantidad);
        final Button aceptar = (Button) dialogo.findViewById(R.id.aceptar);

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaz.ResultadoCuadroDialogo(Integer.parseInt(cantidad.getText().toString()));
                dialogo.dismiss();
            }
        });

        dialogo.show();
    }
}
