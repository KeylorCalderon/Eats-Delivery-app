package com.example.eatsdeliveryapp.administrador.repartidor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import com.example.eatsdeliveryapp.R;
import com.example.eatsdeliveryapp.administrador.encargado.PantallaModificarRestaurante;
import com.example.eatsdeliveryapp.dao.sqlite.AdministradorSqliteDAOImpl;
import com.example.eatsdeliveryapp.dao.sqlite.ClienteSqliteDAOImpl;
import com.example.eatsdeliveryapp.dao.sqlite.PedidoSqliteDAOImpl;
import com.example.eatsdeliveryapp.database.DBProperties;
import com.example.eatsdeliveryapp.helper.Dialogs;
import com.example.eatsdeliveryapp.model.Administrador;
import com.example.eatsdeliveryapp.model.Cliente;
import com.example.eatsdeliveryapp.model.Pedido;

import java.util.ArrayList;
import java.util.List;


//Class publica del repartidor para su pantalla principal
public class PantallaRepartidor extends AppCompatActivity {

    //almacena el id propio del repartidor
    private int idRepartidor;

    //variables necesarias, para conectarse a la BD y para cargar los elementos en pantalla
    private ListView lista;
    private DBProperties dbProperties;
    private Administrador repartidor;
    private List<Pedido> pedidos;
    private List<Cliente> clientes;
    private ListaAdapterPedidos adapterPedidos;

    //meotod por default
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_repartidor);

        //Acá se captura el id del repatidor
        idRepartidor = getIntent().getExtras().getInt("idRepartidor");

        //Nos conectamos a la base
        dbProperties = new DBProperties(getBaseContext());

        this.lista = (ListView) findViewById(R.id.listaPedidosRepartidor);

        clientes = new ArrayList<>();

        //Llamamos a los dao, para cargar tanto el encargado como el restaurante
        AdministradorSqliteDAOImpl admin = new AdministradorSqliteDAOImpl(dbProperties);
        this.repartidor = admin.RetornarAdmin(idRepartidor);

        PedidoSqliteDAOImpl p = new PedidoSqliteDAOImpl(dbProperties);
        this.pedidos = p.findAllCopy();

        for(int i=0; i<pedidos.size(); i++){

            ClienteSqliteDAOImpl c = new ClienteSqliteDAOImpl(dbProperties);
            clientes.add(c.getCliente(pedidos.get(i).getIdCliente()));
        }

        //Setiamos el nombre en pantalla
        TextView titulo = (TextView) findViewById(R.id.bienvenidaRepartidor);
        titulo.setText("Bienvenido "+repartidor.getNombre()+" "+repartidor.getPrimerApellido());

        this.adapterPedidos = new ListaAdapterPedidos(this, pedidos, clientes);
        this.lista.setAdapter(adapterPedidos);

        //Setiamos el evento click sobre esta lista, para modificar los datos
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {

                CargarPantalla(pedidos.get(i));

            }
        });
    }


    //metodo que carga la pantalla
    private void CargarPantalla(Pedido p){

        //Despliega un dialogo donde le pregunta al usuario si quiere administrar el pedido
        Dialogs.showConfirmDialog(this, "¿Desea Encargarse del Pedido?", () -> {

            // Al presionar la opción Si
            //Instancia la nueva ventana
            Intent SiguienteActivity= new Intent(this, EncargoPedido.class);
            SiguienteActivity.putExtra("idRepartidor", this.idRepartidor);
            SiguienteActivity.putExtra("idPedido", p.getId());

            startActivity(SiguienteActivity);

        }, () -> {
            // Al presionar la opción No
        });
    }

}