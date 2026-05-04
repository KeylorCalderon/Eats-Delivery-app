package com.example.eatsdeliveryapp.Cliente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.eatsdeliveryapp.R;
import com.example.eatsdeliveryapp.administrador.repartidor.ListaAdapterPedidos;
import com.example.eatsdeliveryapp.dao.PedidoDAO;
import com.example.eatsdeliveryapp.dao.sqlite.PedidoSqliteDAOImpl;
import com.example.eatsdeliveryapp.dao.sqlite.ProductoSqliteDAOImpl;
import com.example.eatsdeliveryapp.database.DBProperties;
import com.example.eatsdeliveryapp.model.Pedido;
import com.example.eatsdeliveryapp.model.Producto;
import com.example.eatsdeliveryapp.service.CuentaClienteServiceImpl;
import com.example.eatsdeliveryapp.service.PedidoService;
import com.example.eatsdeliveryapp.service.PedidoServiceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PedidosActivos extends AppCompatActivity {

    //atributo para guardar el id  del cliente
    private int idCliente;
    private DBProperties dbProperties;
    private PedidoDAO pedidoDAO;
    private PedidoService pedidoService;
    private ListView lista;
    private List<Pedido> pedidos;
    private ListaAdapterPedidosCliente adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos_activos);

        //captura el id del cliente
        idCliente = getIntent().getExtras().getInt("idCliente");

        //Nos conectamos a la base
        dbProperties = new DBProperties(getBaseContext());

        //pedidos=new ArrayList<Pedido>();

        pedidoDAO = new PedidoSqliteDAOImpl(dbProperties);
        // Inicializa Services
        pedidoService = new PedidoServiceImpl(pedidoDAO);

        pedidos=pedidoDAO.findAllActivos(idCliente);
        //pedidos=pedidoDAO.findAllCopy();
        //Toast.makeText(PedidosActivos.this, "Cantidad de pedidos " + pedidos.size(), Toast.LENGTH_SHORT).show();
        lista = findViewById(R.id.ListaVistaPedidosActivos);
        //crea el nuevo adapter
        adapter = new ListaAdapterPedidosCliente(this, pedidos);
        lista.setAdapter(adapter);

        //activa el metodo onclick en la lista
        lista.setItemsCanFocus(false);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                cancelarElPedido(pedidos.get(i).getId(),i);
                //Toast.makeText(DetalleRestaurante.this, "Pulsando el " + lista.getId(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Metodo para cancelar el pedido
    public void cancelarElPedido(int idPedido, int i){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        //String formattedDate = df.format(c);
        String fecha=pedidos.get(i).getFecha();
        Date d=new Date();
        try{
            d= df.parse(fecha);
        }catch (Exception e){
            e.toString();
        }
        Date c = Calendar.getInstance().getTime();
        long l=c.getTime()-d.getTime();
        //long day=l/(24*60*60*1000);
        //long hour=(l/(60*60*1000)-(l/(24*60*60*1000))*24);
        long min=((l/(60*1000))-(l/(24*60*60*1000))*24*60-((l/(60*60*1000)-(l/(24*60*60*1000))*24))*60);

        pedidoDAO.ModEstado(idPedido,"Cancelado");
        //Se válida si han pasado más o menos de 5 minutos
        if(min<5){
            pedidoDAO.ModTotalPagado(idPedido,0f);
            Toast.makeText(PedidosActivos.this, "Pedido cancelado sin cargos", Toast.LENGTH_SHORT).show();
        }else {
            pedidoDAO.ModTotalPagado(idPedido,1000f);
            Toast.makeText(PedidosActivos.this, "Pedido cancelado con multa", Toast.LENGTH_SHORT).show();
        }
        pedidos.remove(i);
        adapter = new ListaAdapterPedidosCliente(this, pedidos);
        lista.setAdapter(adapter);



    }
}