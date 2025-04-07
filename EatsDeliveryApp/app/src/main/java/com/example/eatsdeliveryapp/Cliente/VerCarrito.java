package com.example.eatsdeliveryapp.Cliente;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.eatsdeliveryapp.CuadroDialogo;
import com.example.eatsdeliveryapp.R;
import com.example.eatsdeliveryapp.dao.PedidoDAO;
import com.example.eatsdeliveryapp.dao.ProductoDAO;
import com.example.eatsdeliveryapp.dao.sqlite.ClienteSqliteDAOImpl;
import com.example.eatsdeliveryapp.dao.sqlite.DireccionXClienteSqliteDAOImpl;
import com.example.eatsdeliveryapp.dao.sqlite.ItemsCarritoSqliteDAOImpl;
import com.example.eatsdeliveryapp.dao.sqlite.PedidoSqliteDAOImpl;
import com.example.eatsdeliveryapp.dao.sqlite.ProductoSqliteDAOImpl;
import com.example.eatsdeliveryapp.dao.sqlite.RestauranteSqliteDAOImpl;
import com.example.eatsdeliveryapp.database.DBProperties;
import com.example.eatsdeliveryapp.helper.Dialogs;
import com.example.eatsdeliveryapp.model.Direccion;
import com.example.eatsdeliveryapp.model.ItemsCarrito;
import com.example.eatsdeliveryapp.model.Pedido;
import com.example.eatsdeliveryapp.model.Producto;
import com.example.eatsdeliveryapp.service.CuentaClienteService;
import com.example.eatsdeliveryapp.service.CuentaClienteServiceImpl;
import com.example.eatsdeliveryapp.service.PedidoService;
import com.example.eatsdeliveryapp.service.PedidoServiceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class VerCarrito extends AppCompatActivity implements CuadroDialogo.FinalizoCuadroDialogo{

    int idCliente;
    DBProperties dbProperties;
    private ListView lista;
    private List<Producto> productos;
    private List<ItemsCarrito> itemsCarritos;
    private ListaAdapterCarrito adapter;
    private PedidoDAO pedidoDAO;
    private PedidoService pedidoService;
    ProductoSqliteDAOImpl productoSqliteDAO;
    ItemsCarritoSqliteDAOImpl itemsCarritoSqliteDAO;
    private Spinner spinner;
    private ArrayAdapter<String> adapterS;
    private ArrayList<String> datos;
    private ArrayList<Direccion> direcciones;
    DireccionXClienteSqliteDAOImpl direccionXClienteSqliteDAO;

    int indexModificar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_carrito);
        idCliente = getIntent().getExtras().getInt("idCliente");
        dbProperties = new DBProperties(getBaseContext());
        lista = findViewById(R.id.listaCarrito);
        //referencia el spinner
        spinner = (Spinner) findViewById(R.id.spinnerDireccionCompra);

        itemsCarritoSqliteDAO= new ItemsCarritoSqliteDAOImpl(dbProperties);
        itemsCarritos=itemsCarritoSqliteDAO.findAll();
        productos=new ArrayList<Producto>();

        pedidoDAO = new PedidoSqliteDAOImpl(this.dbProperties);
        pedidoService = new PedidoServiceImpl(pedidoDAO);
        productoSqliteDAO = new ProductoSqliteDAOImpl(dbProperties);
        Log.d("WWW",Integer.toString(itemsCarritos.size()));

        //Producto prueba=new Producto();
        for(int i=0;i<itemsCarritos.size();i++){
            Log.d("WWW2",itemsCarritos.get(i).getIdCarrito()+"  "+idCliente);
            if(itemsCarritos.get(i).getIdCarrito()==idCliente){
                Log.d("WWW3",itemsCarritos.get(i).getIdProducto()+"");
                //prueba=productoSqliteDAO.findOne(itemsCarritos.get(i).getIdProducto());
                //Log.d("WWW4",prueba.getNombre());
                Producto arreglo=productoSqliteDAO.findOne(itemsCarritos.get(i).getIdProducto());
                arreglo.setCantidad(itemsCarritos.get(i).getCantidad());
                productos.add(arreglo);
            }
        }
        cargarLista();
        iniciarSpinner();
        metodosLista();
    }

    //incia el spinner
    private void iniciarSpinner(){

        //consulta las direcciones del cliente
        direccionXClienteSqliteDAO= new DireccionXClienteSqliteDAOImpl(dbProperties);
        direcciones=direccionXClienteSqliteDAO.verificarDireccion(idCliente);

        //carga las direcciones
        String text="";
        //datos = new String[direcciones.size()];
        datos = new ArrayList<>();

        for(int i=0;i<direcciones.size();i++){
            text=direcciones.get(i).getProvincia()+"; "+direcciones.get(i).getCanton()+"; "+direcciones.get(i).getDistrito()+"; "+
                    direcciones.get(i).getDireccionExacta();
            datos.add(text);
        }

        //ajusta el adapter
        adapterS = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, datos);

        //le setea el spinner
        spinner.setAdapter(adapterS);
    }

    private void cargarLista(){
        adapter = new ListaAdapterCarrito(this, productos, idCliente);
        lista.setAdapter(adapter);
    }

    public void  comprar(View view){
        //String a=view;
        //Toast.makeText(BusquedaRestaurante.this,"Pulsando el "+view.getId(), Toast.LENGTH_LONG).show();
        //Intent SiguienteActivity= new Intent(this, VerCarrito.class);
        //SiguienteActivity.putExtra("idCliente", idCliente);
        //SiguienteActivity.putExtra("producto", idProducto);
        //startActivity(SiguienteActivity);
        if(productos.size()>0){
            float totalAPagar=0f;
            String email="Eats Delivery"+"<br/>";
            ClienteSqliteDAOImpl clienteSqliteDAO=new ClienteSqliteDAOImpl(dbProperties);
            RestauranteSqliteDAOImpl restauranteSqliteDAO=new RestauranteSqliteDAOImpl(dbProperties);
            email+= clienteSqliteDAO.getCliente(idCliente).getNombre()+"<br />"+"<br/>";
            for(int i=0;i<productos.size();i++){
                totalAPagar+=productos.get(i).getPrecio()*productos.get(i).getCantidad();
                email+="Restaurante: "+restauranteSqliteDAO.recuperarRestaurante(productos.get(i).getIdRestaurante()).getNombre()+"<br/>";
                email+="Nombre del producto: "+productos.get(i).getNombre()+"<br/>"+"Precio unario: "+productos.get(i).getPrecio()+"<br/>";
                itemsCarritoSqliteDAO.EliminarProducto(productos.get(i).getId(), this.idCliente);
                totalAPagar+=750f;
            }
            email+="<br/>"+"Total a pagar: "+Float.toString(totalAPagar);
            EnviarMail enviarMail=new EnviarMail(clienteSqliteDAO.getCliente(idCliente).getCorreo(),"Factura Eats Delivery", email);
            productos=new ArrayList<Producto>();
            Date c = Calendar.getInstance().getTime();
            System.out.println("Current time => " + c);

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String formattedDate = df.format(c);

            int a=(int)spinner.getSelectedItemId();

            Pedido pedido = new Pedido();
            pedido.setIdCliente(idCliente);
            Log.d("ZZZ", a+"---"+direcciones.get(a).getId()+"---"+direcciones.get(a).getDireccionExacta());
            pedido.setDireccion(direcciones.get(a).getId());
            pedido.setIdEstado("En Espera");
            pedido.setFecha(formattedDate);
            pedido.setTotalPagado(totalAPagar);
            SQLiteDatabase dbConnection = dbProperties.getWritableConnection();
            try {
                dbConnection.beginTransaction();

                long direccionResult = pedidoService.addNew(pedido, dbConnection);
                //Toast.makeText(MainActivity.this,"Direccion: "+direccionResult, Toast.LENGTH_LONG).show();
                if(direccionResult != -1) {
                    dbConnection.setTransactionSuccessful();
                    Toast.makeText(VerCarrito.this,"Pedido concretado con éxito ", Toast.LENGTH_LONG).show();
                    cargarLista();
                }

            } finally {
                dbConnection.endTransaction();
            }
        }else{
            Toast.makeText(VerCarrito.this,"El carrito se encuentra vacío", Toast.LENGTH_LONG).show();
        }
    }


    private void metodosLista(){

        this.lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                ModificarItem(i);
            }
        });

        //El envento presionado para borrar los productos
        this.lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View v,
                                           int index, long arg3) {
                // TODO Auto-generated method stub
                EliminarItem(index);
                return true;
            }
        });

    }


    private void ModificarItem(int index){

        //Despliega un dialogo donde le pregunta al usuario si quiere eliminar el restaurante
        Dialogs.showConfirmDialog(this, "¿Desea Modificar la cantidad del Item?", () -> {
            this.indexModificar = index;
            new CuadroDialogo(this, VerCarrito.this);
        }, () -> {
            // Al presionar la opción No
        });

    }

    private void EliminarItem(int index){

        //Despliega un dialogo donde le pregunta al usuario si quiere eliminar el restaurante
        Dialogs.showConfirmDialog(this, "¿Desea Eliminar el Item?", () -> {

            itemsCarritoSqliteDAO.EliminarProducto(this.productos.get(index).getId(),this.idCliente);
            this.productos.remove(index);
            this.adapter.notifyDataSetChanged();
            Toast.makeText(VerCarrito.this,"Item Eliminado", Toast.LENGTH_LONG).show();

        }, () -> {
            // Al presionar la opción No
        });
    }

    @Override
    public void ResultadoCuadroDialogo(int cantidad) {

        itemsCarritoSqliteDAO.ModificarItem(this.productos.get(this.indexModificar).getId(), this.idCliente, cantidad);
        this.productos.get(indexModificar).setCantidad(cantidad);
        this.adapter.notifyDataSetChanged();
        Toast.makeText(VerCarrito.this,"Item Modificado", Toast.LENGTH_LONG).show();

    }
}