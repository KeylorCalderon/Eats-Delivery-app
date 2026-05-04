package com.example.eatsdeliveryapp.administrador.repartidor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.eatsdeliveryapp.R;
import com.example.eatsdeliveryapp.administrador.encargado.AdapterDetallePedido;
import com.example.eatsdeliveryapp.administrador.gerente.PantallaInsertarRestaurantes;
import com.example.eatsdeliveryapp.dao.sqlite.ClienteSqliteDAOImpl;
import com.example.eatsdeliveryapp.dao.sqlite.DireccionSqliteDAOImpl;
import com.example.eatsdeliveryapp.dao.sqlite.PedidoSqliteDAOImpl;
import com.example.eatsdeliveryapp.database.DBProperties;
import com.example.eatsdeliveryapp.helper.Dialogs;
import com.example.eatsdeliveryapp.model.Cliente;
import com.example.eatsdeliveryapp.model.Direccion;
import com.example.eatsdeliveryapp.model.Pedido;

import java.util.ArrayList;

//Clase para la responsabilidad del pedido
public class EncargoPedido extends AppCompatActivity {

    //atributos propios y privados de la clase
    private int idRepartidor;
    private DBProperties dbProperties;
    private Spinner spinnerEstado;
    private ListView lista;
    private Cliente cliente;
    private Direccion direccion;
    private Pedido pedido;
    private String[] estados;

    //Método por default
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encargo_pedido);

        //Carga las variables
        this.idRepartidor = getIntent().getExtras().getInt("idRepartidor");
        int idPedido = getIntent().getExtras().getInt("idPedido");

        dbProperties = new DBProperties(getBaseContext());

        lista = findViewById(R.id.pedidoView);

        //Se conecta a la base
        PedidoSqliteDAOImpl p = new PedidoSqliteDAOImpl(dbProperties);
        this.pedido = p.getPedido(idPedido);

        ClienteSqliteDAOImpl c = new ClienteSqliteDAOImpl(dbProperties);
        this.cliente = c.getCliente(pedido.getIdCliente());

        DireccionSqliteDAOImpl d = new DireccionSqliteDAOImpl(dbProperties);
        this.direccion = d.getDireccion(pedido.getDireccion());

        //Setea los adpatadores
        AdapterDetallePedido adapter = new AdapterDetallePedido(this, cliente, direccion, pedido);

        lista.setAdapter(adapter);

        spinnerEstado = findViewById(R.id.spinnerCambio);

        this.estados = new String[]{"Recibido", "En Camino", "Entregado"};

        spinnerEstado.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,estados));

        //Metodo seleccion en el spinner
        spinnerEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> spn, android.view.View v, int i, long id) {
                ModEstado(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> spn) { }
        });
    }


    //Metodo para modificar los estados de los pedidos
    private void ModEstado(int i){

        //Conecta los pedidos
        PedidoSqliteDAOImpl ped = new PedidoSqliteDAOImpl(dbProperties);

        if (estados[i].equals("Entregado")){

            //Despliega un dialogo donde le pregunta al usuario si quiere confirmar la entrega
            Dialogs.showConfirmDialog(this, "¿Confirma la entrega del pedido?", () -> {

                //modifica el estado del pedido
                ped.ModEstado(this.pedido.getId(),estados[i]);

                Toast.makeText(EncargoPedido.this,"CAMBIO DE ESTADO EXITOSO", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, PantallaRepartidor.class);
                intent.putExtra("idRepartidor", this.idRepartidor);
                startActivity(intent);

                }, () -> {
                // Al presionar la opción No
            });

        }else{
            ped.ModEstado(this.pedido.getId(),estados[i]);
            Toast.makeText(EncargoPedido.this,"CAMBIO DE ESTADO EXITOSO", Toast.LENGTH_LONG).show();
        }
    }
}