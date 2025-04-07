package com.example.eatsdeliveryapp.Cliente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eatsdeliveryapp.R;
import com.example.eatsdeliveryapp.dao.ItemsCarritoDAO;
import com.example.eatsdeliveryapp.dao.ProductoDAO;
import com.example.eatsdeliveryapp.dao.sqlite.ItemsCarritoSqliteDAOImpl;
import com.example.eatsdeliveryapp.dao.sqlite.ProductoSqliteDAOImpl;
import com.example.eatsdeliveryapp.database.DBProperties;
import com.example.eatsdeliveryapp.model.Cliente;
import com.example.eatsdeliveryapp.model.CuentaCliente;
import com.example.eatsdeliveryapp.model.ItemsCarrito;
import com.example.eatsdeliveryapp.model.Producto;
import com.example.eatsdeliveryapp.service.CuentaClienteService;
import com.example.eatsdeliveryapp.service.ItemsCarritoService;
import com.example.eatsdeliveryapp.service.ItemsCarritoServiceImpl;


//Clase de detalle de cada producto para los clientes
public class DetalleProducto extends AppCompatActivity {

    //atributos propios
    int idCliente;
    private DBProperties dbProperties;
    private ImageView image;
    private TextView text;
    private EditText cant;

    //para los productos
    private int idProducto;
    private Producto producto;

    //para generar el carrito
    private ItemsCarritoDAO itemsCarritoDAO;
    private ItemsCarritoService itemsCarritoService;


    //metodo por default
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);

        //captura el producto y el cliente, junto a las propiedad de la base
        idCliente = getIntent().getExtras().getInt("idCliente");
        idProducto = getIntent().getExtras().getInt("producto");
        dbProperties = new DBProperties(getBaseContext());

        //carag el carrito de compras
        itemsCarritoDAO = new ItemsCarritoSqliteDAOImpl(dbProperties);
        itemsCarritoService = new ItemsCarritoServiceImpl(itemsCarritoDAO);

        //referencia los items de la pantalla
        image = (ImageView) findViewById(R.id.imagenProduct);
        text = (TextView) findViewById(R.id.textoProduct);
        cant= (EditText) findViewById(R.id.cantidadProduct);

        //carga el producto
        ProductoSqliteDAOImpl productoSqliteDAO = new ProductoSqliteDAOImpl(dbProperties);
        producto = productoSqliteDAO.findOne(idProducto);

        //ajusta sus datos en pantalla
        image.setImageBitmap(producto.getFoto());
        text.setText(producto.getNombre()+System.getProperty ("line.separator")+"Ingredientes: "+
                producto.getDescripcion()+System.getProperty ("line.separator")+"Precio: "+Float.toString(producto.getPrecio()));
    }

    //Metodo para comprar el producto
    public void Comprar(View view){

        //valida la cantidad
        if(!cant.getText().toString().isEmpty()) {
            int cantidad = Integer.parseInt(cant.getText().toString());
            if (cantidad <= producto.getCantidad() && cantidad > 0) {
                producto.setCantidad(producto.getCantidad() - cantidad);
                Toast.makeText(DetalleProducto.this, "Producto añadido al carrito de comprar", Toast.LENGTH_LONG).show();
                anadirProducto(cantidad);
            } else {
                Toast.makeText(DetalleProducto.this, "No hay suficiente stock de este producto", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(DetalleProducto.this,"Ingrese la cantidad", Toast.LENGTH_LONG).show();
        }
    }


    //metodo que añade los productos a los carritos
    private void anadirProducto(int cantidad){

        //crea una especie de items y los carga
        ItemsCarrito itemsCarrito = new ItemsCarrito();
        itemsCarrito.setIdCarrito(idCliente);
        itemsCarrito.setIdProducto(idProducto);
        itemsCarrito.setCantidad(cantidad);
        itemsCarrito.setActivo(1);

        //se conecta  a la base
        SQLiteDatabase dbConnection = dbProperties.getWritableConnection();
        try {
            dbConnection.beginTransaction();

            //realiza el insert
            long itemResult = itemsCarritoService.addNew(itemsCarrito, dbConnection);
            Log.d("PRODUCTO",Long.toString(itemResult));
            if(itemResult != -1) {
                dbConnection.setTransactionSuccessful();
            }

        } finally {
            dbConnection.endTransaction();
        }
    }
}