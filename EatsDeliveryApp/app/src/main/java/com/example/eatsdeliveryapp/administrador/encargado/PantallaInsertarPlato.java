package com.example.eatsdeliveryapp.administrador.encargado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.eatsdeliveryapp.R;
import com.example.eatsdeliveryapp.administrador.gerente.PantallaInsertarRestaurantes;
import com.example.eatsdeliveryapp.dao.sqlite.AdministradorSqliteDAOImpl;
import com.example.eatsdeliveryapp.dao.sqlite.ProductoSqliteDAOImpl;
import com.example.eatsdeliveryapp.dao.sqlite.RestauranteSqliteDAOImpl;
import com.example.eatsdeliveryapp.database.DBProperties;
import com.example.eatsdeliveryapp.model.Administrador;
import com.example.eatsdeliveryapp.model.Producto;
import com.example.eatsdeliveryapp.model.Restaurante;

import java.io.FileNotFoundException;
import java.io.InputStream;

//Clase para la insercion de nuevos platos
public class PantallaInsertarPlato extends AppCompatActivity {

    //Atributos propios de la clase
    private int idEncargado;
    private Administrador encargado;
    private Restaurante restaurante;

    //Para la imagen y base de datos
    private DBProperties dbProperties;
    private Bitmap foto;


    //Método por default
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_insertar_plato);

        //Acá se captura el id del gerente
        idEncargado = getIntent().getExtras().getInt("idEncargado");

        //Cargamos las propiedades de la bd
        dbProperties = new DBProperties(getBaseContext());

        //Cargamos las variables de la clase
        AdministradorSqliteDAOImpl admin = new AdministradorSqliteDAOImpl(dbProperties);
        this.encargado = admin.RetornarAdmin(idEncargado);

        RestauranteSqliteDAOImpl res = new RestauranteSqliteDAOImpl(dbProperties);
        this.restaurante=res.recuperarRestaurante(encargado.getIdRestaurante());

    }


    //Método para registrar el plato
    public void RegistrarPlato(View view) {

        //Validamos que haya escogido foto
        if (foto == null) {
            Toast.makeText(PantallaInsertarPlato.this, "INGRESE UNA IMAGEN, POR FAVOR", Toast.LENGTH_LONG).show();
            return;
        }

        //Capturamos los datos desde la pantalla
        EditText nombre = (EditText) findViewById(R.id.nombrePlato);
        EditText ingredientes = (EditText) findViewById(R.id.ingredientesPlato);
        EditText precio = (EditText) findViewById(R.id.precioPlato);
        EditText cantidad = (EditText) findViewById(R.id.cantidadPlato);

        //Inicializamos un nuevo producto
        Producto p = new Producto();

        //Le cargamos los valores
        p.setIdRestaurante(this.restaurante.getId());
        p.setNombre(nombre.getText().toString());
        p.setFoto(this.foto);
        p.setDescripcion(ingredientes.getText().toString());
        p.setPrecio(Float.parseFloat(precio.getText().toString()));
        p.setCantidad(Integer.parseInt(cantidad.getText().toString()));
        p.setActivo(1);

        //Conectamos la base de datos, e insertamos el plato
        SQLiteDatabase dbConnection = dbProperties.getWritableConnection();
        ProductoSqliteDAOImpl productoSqliteDAO = new ProductoSqliteDAOImpl(this.dbProperties);
        long result= productoSqliteDAO.save(p,dbConnection);

        //Validamos que se ejecutara bien
        if (result != -1){
            Toast.makeText(PantallaInsertarPlato.this,"PRODUCTO INGRESADO", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, PantallaEncargado.class);
            intent.putExtra("idEncargado", this.encargado.getId());
            startActivity(intent);
        }

    }


    //Método para poder seleccionar la imagen
    public void SeleccionarImagenPlato(View view){

        //Crea un nuevo intent
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione la aplicación"),10);
    }

    //Recibe la imagen, o al menos una respuesta
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Válida que se a una imagen
        if (resultCode==RESULT_OK){
            convertImageToBitMap(data.getData());
        }
    }


    //Convierte la imagen ingresada de un uri a un bitmap
    //Despliega mensaje de confirmacion
    private void convertImageToBitMap(Uri uri){

        try {
            ContentResolver cr = getBaseContext().getContentResolver();
            InputStream inputStream = cr.openInputStream(uri);
            this.foto = BitmapFactory.decodeStream(inputStream);
            Toast.makeText(PantallaInsertarPlato.this,"IMAGEN CARGADA EXITOSAMENTE", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}