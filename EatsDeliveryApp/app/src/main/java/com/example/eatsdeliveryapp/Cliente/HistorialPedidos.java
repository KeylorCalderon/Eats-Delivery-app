package com.example.eatsdeliveryapp.Cliente;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.eatsdeliveryapp.R;
import com.example.eatsdeliveryapp.dao.PedidoDAO;
import com.example.eatsdeliveryapp.dao.sqlite.PedidoSqliteDAOImpl;
import com.example.eatsdeliveryapp.database.DBProperties;
import com.example.eatsdeliveryapp.model.Pedido;
import com.example.eatsdeliveryapp.service.PedidoService;
import com.example.eatsdeliveryapp.service.PedidoServiceImpl;

import java.util.Collections;
import java.util.List;

public class HistorialPedidos extends AppCompatActivity {

    //atributo para guardar el id  del cliente
    private int idCliente;
    private DBProperties dbProperties;
    private PedidoDAO pedidoDAO;
    private PedidoService pedidoService;
    private ListView lista;
    private List<Pedido> pedidos;
    private ListaAdapterPedidosHistorial adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_pedidos);

        //captura el id del cliente
        idCliente = getIntent().getExtras().getInt("idCliente");

        //Nos conectamos a la base
        dbProperties = new DBProperties(getBaseContext());

        //pedidos=new ArrayList<Pedido>();

        pedidoDAO = new PedidoSqliteDAOImpl(dbProperties);
        // Inicializa Services
        pedidoService = new PedidoServiceImpl(pedidoDAO);

        pedidos=pedidoDAO.findAllFinalizados(idCliente);
        Collections.reverse(pedidos);
        //pedidos=pedidoDAO.findAllCopy();
        //Toast.makeText(PedidosActivos.this, "Cantidad de pedidos " + pedidos.size(), Toast.LENGTH_SHORT).show();
        lista = findViewById(R.id.VistaHistorialDePedidos);
        //crea el nuevo adapter
        adapter = new ListaAdapterPedidosHistorial(this, pedidos);
        lista.setAdapter(adapter);

        //activa el metodo onclick en la lista
        lista.setItemsCanFocus(false);
    }
}