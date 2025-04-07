package com.example.eatsdeliveryapp.administrador.encargado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.eatsdeliveryapp.R;
import com.example.eatsdeliveryapp.dao.sqlite.ProductoSqliteDAOImpl;
import com.example.eatsdeliveryapp.database.DBProperties;
import com.example.eatsdeliveryapp.model.Producto;

import java.io.FileNotFoundException;
import java.io.InputStream;


//Clase publica para la pantalla de modificar platos
public class PantallaModificarPlato extends AppCompatActivity {

    //Atributos para las clases y la base de datos
    private int idEncargado;
    private Bitmap foto;
    private DBProperties dbProperties;
    private Producto producto;

    //Para referenciar los objetos de la pantalla
    private EditText nombre;
    private EditText ingredientes;
    private EditText precio;
    private EditText cantidad;
    private ImageView imagen;

    //Método por default
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_modificar_plato);

        //Propiedaddes de la base
        dbProperties = new DBProperties(getBaseContext());

        //Acá se captura el id del gerente
        idEncargado = getIntent().getExtras().getInt("idEncargado");
        int idProducto = getIntent().getExtras().getInt("idProducto");

        //cargamos el dao
        ProductoSqliteDAOImpl pro = new ProductoSqliteDAOImpl(dbProperties);

        //cargamos los valores del producto
        this.producto = pro.findOne(idProducto);
        this.foto = producto.getFoto();

        //Referenciamos los items de la pantalla
        this.nombre = (EditText) findViewById(R.id.nombrePlatoMod);
        this.ingredientes = (EditText) findViewById(R.id.ingredientesPlatoMod);
        this.precio = (EditText) findViewById(R.id.precioPlatoMod);
        this.cantidad = (EditText) findViewById(R.id.cantidadPlatoMod);
        this.imagen = (ImageView) findViewById(R.id.imagenProduct);

        //Les seteamos valores
        nombre.setText(producto.getNombre());
        ingredientes.setText(producto.getDescripcion());
        precio.setText(String.valueOf(producto.getPrecio()));
        cantidad.setText(String.valueOf(producto.getCantidad()));

    }


    //Metodo llamado por un boton para modificar platos
    public void ModPlato(View view){

        //Carga el nuevo plato, con los valores de la pantalla
        Producto p = new Producto();
        p.setId(producto.getId());
        p.setIdRestaurante(producto.getIdRestaurante());
        p.setNombre(this.nombre.getText().toString());
        p.setDescripcion(this.ingredientes.getText().toString());
        p.setFoto(this.foto);
        p.setPrecio(Float.parseFloat(this.precio.getText().toString()));
        p.setCantidad(Integer.parseInt(this.cantidad.getText().toString()));
        p.setActivo(1);

        //Se conecta a la base y realiza la actualizacion
        ProductoSqliteDAOImpl pro = new ProductoSqliteDAOImpl(dbProperties);
        int result = pro.ModificarProducto(p);

        //Valida ok
        if (result != -1){

            Toast.makeText(PantallaModificarPlato.this,"ACTUALIZACIÓN EXITOSA DEL PLATO", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, PantallaEncargado.class);
            intent.putExtra("idEncargado", this.idEncargado);
            startActivity(intent);
        }

    }

    //Carga imagen
    public void SeleccionarImagenPlatoMod(View view){

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione la aplicación"),10);
    }

    //recibe la respuesta del activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK){
            convertImageToBitMap(data.getData());
        }
    }

    //Convierte la imagen como tal
    private void convertImageToBitMap(Uri uri){

        try {
            ContentResolver cr = getBaseContext().getContentResolver();
            InputStream inputStream = cr.openInputStream(uri);
            this.foto = BitmapFactory.decodeStream(inputStream);
            Toast.makeText(PantallaModificarPlato.this,"IMAGEN CARGADA EXITOSAMENTE", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}