package com.example.eatsdeliveryapp.Cliente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eatsdeliveryapp.R;
import com.example.eatsdeliveryapp.dao.sqlite.RestauranteSqliteDAOImpl;
import com.example.eatsdeliveryapp.dao.sqlite.ProductoSqliteDAOImpl;
import com.example.eatsdeliveryapp.database.DBProperties;
import com.example.eatsdeliveryapp.model.Producto;
import com.example.eatsdeliveryapp.model.Restaurante;

import java.util.List;


//Clase publica para los detalles del restaurantes
public class DetalleRestaurante extends AppCompatActivity {

    //variables propias y privadas de la clase
    private int idCliente;
    private DBProperties dbProperties;
    private ImageView image;
    private TextView text;

    //para almacenar productos, temporalmente
    private int idRestaurante;
    private ListView lista;
    private List<Producto> productos;
    private ListaAdapterProducto adapter;

    //Metodo por default para inicializar datos
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_restaurante);

        //captura e idcliente y el restaruante
        idCliente = getIntent().getExtras().getInt("idCliente");
        idRestaurante = getIntent().getExtras().getInt("restaurante");
        //restaurante= (Restaurante) getIntent().getExtras().getSerializable("restaurante");

        //captura las propiedades de la base
        dbProperties = new DBProperties(getBaseContext());

        //genera un restaurante para guardar la consulta
        Restaurante restaurante;
        RestauranteSqliteDAOImpl restauranteSqliteDAO = new RestauranteSqliteDAOImpl(dbProperties);
        restaurante = restauranteSqliteDAO.recuperarRestaurante(idRestaurante);

        //referencia los objetos de la pantalla
        image = (ImageView) findViewById(R.id.imagenR);
        text = (TextView) findViewById(R.id.texto);
        lista = findViewById(R.id.ListaVistaDeProductos);

        //carga de nuevo la bae
        dbProperties = new DBProperties(getBaseContext());
        text.setText(restaurante.getNombre());
        image.setImageBitmap(restaurante.getFoto());

        //realiza de nuevo una consulta, para productos del restaurante
        ProductoSqliteDAOImpl productoSqliteDAO = new ProductoSqliteDAOImpl(dbProperties);
        productos = productoSqliteDAO.findAllPorRestaurante(idRestaurante);

        //crea el nuevo adapter
        adapter = new ListaAdapterProducto(this, productos, idCliente);
        lista.setAdapter(adapter);

        //activa el metodo onclick en la lista
        lista.setItemsCanFocus(false);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                irAProducto(productos.get(i).getId());
                //Toast.makeText(DetalleRestaurante.this, "Pulsando el " + lista.getId(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    //metodo para cargar la nueva pantalla de productos
    public void irAProducto(int idProducto){
        //String a=view;
        //Toast.makeText(BusquedaRestaurante.this,"Pulsando el "+view.getId(), Toast.LENGTH_LONG).show();
        Intent SiguienteActivity= new Intent(this, DetalleProducto.class);
        SiguienteActivity.putExtra("idCliente", idCliente);
        SiguienteActivity.putExtra("producto", idProducto);
        startActivity(SiguienteActivity);

    }

}