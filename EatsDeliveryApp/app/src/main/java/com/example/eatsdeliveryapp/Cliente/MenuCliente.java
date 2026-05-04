package com.example.eatsdeliveryapp.Cliente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.eatsdeliveryapp.PantallaGestionarDireccion;
import com.example.eatsdeliveryapp.R;


//Clase publica para el menu del cliente, es decir, para que selccione que quiere realizar
public class MenuCliente extends AppCompatActivity {

    int idCliente;

    //metodo por default
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cliente);

        //guarda su id
        idCliente = getIntent().getExtras().getInt("idCliente");
    }

    //gestiona las direcciones, metodo
    public void GestionarDirecciones(View view){

        //carga la nueva pantalla con el id del cliente
        Intent SiguienteActivity= new Intent(this, PantallaGestionarDireccion.class);
        SiguienteActivity.putExtra("idCliente", idCliente);
        startActivity(SiguienteActivity);

    }

    //historial de pedidos, metodo
    public void irAHistorialDePedidos(View view){

        //carga la nueva pantalla con el id del cliente
        Intent SiguienteActivity= new Intent(this, HistorialPedidos.class);
        SiguienteActivity.putExtra("idCliente", idCliente);
        startActivity(SiguienteActivity);

    }


}