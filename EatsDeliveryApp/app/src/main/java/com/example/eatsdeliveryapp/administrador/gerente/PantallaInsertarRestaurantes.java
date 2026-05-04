package com.example.eatsdeliveryapp.administrador.gerente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.eatsdeliveryapp.R;
import com.example.eatsdeliveryapp.dao.DireccionDAO;
import com.example.eatsdeliveryapp.dao.RestauranteDAO;
import com.example.eatsdeliveryapp.dao.sqlite.DireccionSqliteDAOImpl;
import com.example.eatsdeliveryapp.dao.sqlite.RestauranteSqliteDAOImpl;
import com.example.eatsdeliveryapp.database.DBProperties;
import com.example.eatsdeliveryapp.model.Direccion;
import com.example.eatsdeliveryapp.model.Restaurante;
import com.example.eatsdeliveryapp.service.DireccionService;
import com.example.eatsdeliveryapp.service.DireccionServiceImpl;
import com.example.eatsdeliveryapp.service.RestauranteService;
import com.example.eatsdeliveryapp.service.RestauranteServiceImpl;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

//Clase publica para insertar restaurantes
public class PantallaInsertarRestaurantes extends AppCompatActivity {

    //atributos para imagen y propiedad de la base
    private int idGerente;
    private DBProperties dbProperties;
    private byte[] foto;

    //atributos para conectarse a la base y realizar operaciones
    private RestauranteDAO restauranteDAO;
    private RestauranteService restauranteService;
    private DireccionDAO direccionDAO;
    private DireccionService direccionService;

    //referencias a spinners
    private Spinner provincia;
    private Spinner canton;
    private Spinner distrito;
    private Spinner categoria;

    //variables publicas para cargar los spinner
    public static final String[] Provincias = new String[]{"San José", "Alajuela", "Cartago", "Heredia", "Guanacaste", "Puntarenas", "Limón"};
    public static final String[][] Cantones = new String[][]{{"San Jose", "Escazu", "Desamparados", "Puriscal", "Tarrazu", "Aserri", "Mora", "Goicoechea", "Santa Ana", "Alajuelita", "Vazquez de Coronado", "Acosta", "Tibas", "Moravia", "Montes de Oca", "Turrubares", "Dota", "Curridabat", "Perez Zeledon", "Leon Cortes Castro"},
            {"Alajuela", "San Ramon", "Grecia", "San Mateo", "Atenas", "Naranjo", "Palmares", "Poas", "Orotina", "San Carlos", "Alfaro Ruiz", "Valverde Vega", "Upala", "Los Chiles", "Guatuso"},
            {"Cartago", "Paraiso", "La Union", "Jimenez", "Turrialba", "Alvarado", "Oreamuno", "El Guarco"},
            {"Heredia", "Barva", "Santo Domingo", "Santa Barbara", "San Rafael", "San Isidro", "Belen", "Flores", "San Pablo", "Sarapiqui"},
            {"Liberia", "Nicoya", "Santa Cruz", "Bagaces", "Carrillo", "Canas", "Abangares", "Tilaran", "Nandayure", "La Cruz", "Hojancha"},
            {"Puntarenas", "Esparza", "Buenos Aires", "Montes de Oro", "Osa", "Aguirre", "Golfito", "Coto Brus", "Parrita", "Corredores", "Garabito"},
            {"Limon", "Pococi", "Siquirres", "Talamanca", "Matina", "Guacimo"}};
    public  static  final String[][] Distritos= new String [][]{{"Carmen", "Merced", "Hospital", "Catedral", "Zapote", "San Francisco de Dos Rios", "Uruca", "Mata Redonda", "Pavas", "Hatillo", "San Sebastian", "Escazu", "San Antonio", "San Rafael", "Desamparados", "San Miguel", "San Juan de Dios", "San Rafael Arriba", "San Antonio"},
            {"San Luis", "Carara", "Santa Maria", "Jardin"},
            {"Birrisito", "Paraíso", "Oreamuno"},
            {"San Cristobal", "Rosario", "Damas", "San Rafael Abajo"},
            {"Gravilias", "Los Guido", "Santiago", "Mercedes Sur"},
            {"Jaris", "Guadalupe", "San Francisco", "Calle Blancos", "Mata de Platano", "Ipis", "Rancho Redondo", "Purral", "Santa Ana", "Salitral", "Pozos", "Uruca", "Piedades", "Brasil", "Alajuelita", "San Josecito", "San Antonio", "Concepcion"},
            {"Dulce Nombre de Jesus", "Patalillo", "Cascajal", "San Ignacio", "Guaitil", "Palmichal", "Cangrejal", "Sabanillas", "San Juan"}};


    //Método por default
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_insertar_restaurantes);

        //Acá se captura el id del gerente
        idGerente = getIntent().getExtras().getInt("idGerente");

        //Inicializar las variables de la base de datos
        dbProperties = new DBProperties(getBaseContext());

        //Conectamos las variables a la base
        restauranteDAO = new RestauranteSqliteDAOImpl(dbProperties);
        restauranteService = new RestauranteServiceImpl(restauranteDAO);

        direccionDAO = new DireccionSqliteDAOImpl(dbProperties);
        direccionService = new DireccionServiceImpl(direccionDAO);

        //referenciamos los spinners
        provincia = findViewById(R.id.spinnerProvincia);
        canton = findViewById(R.id.spinnerCanton);
        distrito = findViewById(R.id.spinnerDistrito);
        categoria = findViewById(R.id.spinnerCategoria);

        //cargamos los spinners
        CargarSpinners();

    }

    //funcion para poder crear una imagen
    public void SeleccionarImage(View view){

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione la aplicación"),10);
    }

    //Metodo que espera la insercion de la imagen
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK){
            convertImageToByte(data.getData());
        }
    }

    //Metodo que convierte imagen uri a bitmap
    private void convertImageToByte(Uri uri){

        try {
            ContentResolver cr = getBaseContext().getContentResolver();
            InputStream inputStream = cr.openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            this.foto = baos.toByteArray();
            Toast.makeText(PantallaInsertarRestaurantes.this,"IMAGEN CARGADA EXITOSAMENTE", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    //Metodo llamado por un boton oara insertar el restaurante
    public void InsertarRestaurante(View view){

        //refrencias a la pantalla
        EditText nombre = (EditText) findViewById(R.id.nombreRestaurante);
        EditText dirExacta = (EditText) findViewById(R.id.senas);

        //Transaccion en la base de datos y su inserción
        SQLiteDatabase dbConnection = dbProperties.getWritableConnection();
        try {

            //Conectamos a la base
            dbConnection.beginTransaction();

            //generamos el restaurante
            Restaurante restaurante = new Restaurante();
            restaurante.setNombre(nombre.getText().toString());
            restaurante.setCategoria(categoria.getSelectedItem().toString());
            restaurante.setFoto(foto);
            restaurante.setActivo(1);

            //generamos la direccion
            Direccion direccion = new Direccion();
            direccion.setProvincia(provincia.getSelectedItem().toString());
            direccion.setCanton(canton.getSelectedItem().toString());
            direccion.setDistrito(distrito.getSelectedItem().toString());
            direccion.setDireccionExacta(dirExacta.getText().toString());

            //insertamos la nueva direccion
            long direccionResult = direccionService.addNew(direccion, dbConnection);

            //inserta el nuevo restaurante
            restaurante.setIdDireccion((int)direccionResult);
            long restResult = restauranteService.addNew(restaurante, dbConnection);

            //valida bueno
            if(restResult!=-1 && direccionResult != -1) {
                dbConnection.setTransactionSuccessful();
                Toast.makeText(PantallaInsertarRestaurantes.this,"RESTAURANTE INGRESADO EXITOSAMENTE", Toast.LENGTH_LONG).show();
            }

        } finally {
            dbConnection.endTransaction();
        }

        //Instancia la nueva ventana
        Intent SiguienteActivity= new Intent(this, PantallaGerente.class);
        SiguienteActivity.putExtra("idGerente", idGerente);
        startActivity(SiguienteActivity);
    }


    //Metodo que carga los spinner
    private void CargarSpinners() {

        //genera un array, lo pasa a un adapter y se lo mete a los spinners
        ArrayList<String> cats = new ArrayList<>();
        cats.add("Gourmet");
        cats.add("De Especialidad");
        cats.add("Familiar");
        cats.add("Buffet");
        cats.add("Comida Rápida");
        cats.add("Temático");
        cats.add("Para llevar");
        categoria.setAdapter(new ArrayAdapter(PantallaInsertarRestaurantes.this, android.R.layout.simple_spinner_dropdown_item, cats));

        //provincias
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Provincias);
        provincia.setAdapter(adapter);

        //cantones
        ArrayList<String> cants = new ArrayList<>();
        for (int i = 0; i < Cantones.length; i++) {
            for (int j = 0; j < Cantones[i].length; j++) {
                cants.add(Cantones[i][j]);
            }
        }
        canton.setAdapter(new ArrayAdapter(PantallaInsertarRestaurantes.this, android.R.layout.simple_spinner_dropdown_item, cants));

        //distritos
        ArrayList<String> dist = new ArrayList<>();

        for (int i = 0; i < Distritos.length; i++) {
            for (int j = 0; j < Distritos[i].length; j++) {
                dist.add(Distritos[i][j]);
            }
        }
        distrito.setAdapter(new ArrayAdapter(PantallaInsertarRestaurantes.this, android.R.layout.simple_spinner_dropdown_item, dist));
    }


}