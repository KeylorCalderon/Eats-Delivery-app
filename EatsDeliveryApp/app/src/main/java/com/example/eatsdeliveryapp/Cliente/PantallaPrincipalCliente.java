package com.example.eatsdeliveryapp.Cliente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.eatsdeliveryapp.R;
import com.example.eatsdeliveryapp.dao.sqlite.ClienteSqliteDAOImpl;
import com.example.eatsdeliveryapp.database.DBProperties;
import com.example.eatsdeliveryapp.model.Cliente;

//Clase para la pantalla principal del cliente
public class PantallaPrincipalCliente extends AppCompatActivity {

    //atributo para guardar el id  del cliente
    private int idCliente;
    private Cliente cliente;
    private DBProperties dbProperties;


    //metodo por default
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);

        //captura el id del cliente
        idCliente = getIntent().getExtras().getInt("idCliente");

        //Nos conectamos a la base
        dbProperties = new DBProperties(getBaseContext());

        ClienteSqliteDAOImpl c = new ClienteSqliteDAOImpl(dbProperties);
        this.cliente=c.getCliente(idCliente);

        //Setiamos el nombre en pantalla
        TextView titulo = (TextView) findViewById(R.id.bienvenidaCliente);
        titulo.setText("Bienvenido "+cliente.getNombre()+" "+cliente.getPrimerApellido());

    }

    //Metodo para el boton de menu del cliente, instancia la nueva activity
    public void MenuCliente(View view){

        Intent SiguienteActivity= new Intent(this, MenuCliente.class);
        SiguienteActivity.putExtra("idCliente", idCliente);
        startActivity(SiguienteActivity);

    }

    //Metodo para el boton de buscar cliente, instancia la nueva activity
    public void BusquedaRestaurante(View view){

        Intent SiguienteActivity= new Intent(this, BusquedaRestaurante.class);
        SiguienteActivity.putExtra("idCliente", idCliente);
        startActivity(SiguienteActivity);

    }

    //Metodo para el boton de ver carrito, instancia la nueva activity
    public void irACarrito(View view){

        Intent SiguienteActivity= new Intent(this, VerCarrito.class);
        SiguienteActivity.putExtra("idCliente", idCliente);
        startActivity(SiguienteActivity);

    }

    //Metodo para el boton de ver pedidos activos, instancia la nueva activity
    public void pedidosActivos(View view){

        Intent SiguienteActivity= new Intent(this, PedidosActivos.class);
        SiguienteActivity.putExtra("idCliente", idCliente);
        startActivity(SiguienteActivity);

    }
}