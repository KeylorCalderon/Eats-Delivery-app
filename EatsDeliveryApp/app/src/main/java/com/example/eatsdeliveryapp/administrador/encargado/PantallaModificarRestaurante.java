package com.example.eatsdeliveryapp.administrador.encargado;

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
import com.example.eatsdeliveryapp.administrador.gerente.PantallaGerente;
import com.example.eatsdeliveryapp.administrador.gerente.PantallaInsertarRestaurantes;
import com.example.eatsdeliveryapp.dao.DireccionDAO;
import com.example.eatsdeliveryapp.dao.RestauranteDAO;
import com.example.eatsdeliveryapp.dao.sqlite.AdministradorSqliteDAOImpl;
import com.example.eatsdeliveryapp.dao.sqlite.DireccionSqliteDAOImpl;
import com.example.eatsdeliveryapp.dao.sqlite.RestauranteSqliteDAOImpl;
import com.example.eatsdeliveryapp.database.DBProperties;
import com.example.eatsdeliveryapp.model.Administrador;
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

//Clase publica para modifcar restaurantes
public class PantallaModificarRestaurante extends AppCompatActivity {

    //atributos de la clase
    private Restaurante restaurante;
    private Administrador encargado;
    private Bitmap foto;

    //para la base
    private DBProperties dbProperties;

    //Referencia a los spinners de ubicacion y categoria
    private Spinner provincia;
    private Spinner canton;
    private Spinner distrito;
    private Spinner categoria;


    //Variables con las ubicaciones
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


    //Metodo por default
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_modificar_restaurante);

        //guardamos el id del encargado
        int idEncargado = getIntent().getExtras().getInt("idEncargado");

        //Propiedades de la base
        dbProperties = new DBProperties(getBaseContext());

        //Cargamos el administrador y el restaurante
        AdministradorSqliteDAOImpl admin = new AdministradorSqliteDAOImpl(dbProperties);
        this.encargado = admin.RetornarAdmin(idEncargado);

        RestauranteSqliteDAOImpl res = new RestauranteSqliteDAOImpl(dbProperties);
        this.restaurante=res.recuperarRestaurante(encargado.getIdRestaurante());

        //Referenciamos los spinners
        provincia = findViewById(R.id.spinProvincia);
        canton = findViewById(R.id.spinCanton);
        distrito = findViewById(R.id.spinDistrito);
        categoria = findViewById(R.id.spinnerCat);

        //Cargamos los spinners
        CargarSpinners();

        //Colocamos el nombre del encargado en pantalla
        EditText n = (EditText) findViewById(R.id.nombreRest);
        n.setText(restaurante.getNombre());

        //guardamos la foto
        this.foto = restaurante.getFoto();
    }


    //actualizamos los restaurantes, se llama por un boton
    public void ActualizarRestaurante(View view) {

        //cargamos los datos de la pantalla
        EditText nombre = (EditText) findViewById(R.id.nombreRest);
        EditText dirExacta = (EditText) findViewById(R.id.dirExacta);

        //Generamos la direccion
        Direccion direccion = new Direccion();
        direccion.setId(this.restaurante.getIdDireccion());
        direccion.setProvincia(provincia.getSelectedItem().toString());
        direccion.setCanton(canton.getSelectedItem().toString());
        direccion.setDistrito(distrito.getSelectedItem().toString());
        direccion.setDireccionExacta(dirExacta.getText().toString());

        //introducimos la nueva direccion
        DireccionSqliteDAOImpl direccionSqliteDAO = new DireccionSqliteDAOImpl(dbProperties);
        int resultDir = direccionSqliteDAO.ActualizarDireccion(direccion);

        //cargamos el nuevo restaurante
        Restaurante restaurante = new Restaurante();
        restaurante.setId(this.restaurante.getId());
        restaurante.setNombre(nombre.getText().toString());
        restaurante.setCategoria(categoria.getSelectedItem().toString());
        restaurante.setFoto(this.foto);

        //Lo mandamos a actualizar
        RestauranteSqliteDAOImpl restauranteSqliteDAO = new RestauranteSqliteDAOImpl(dbProperties);
        int resultRest = restauranteSqliteDAO.ActualizarRestaurante(restaurante);

        //Validamos la correctitud
        if(resultDir!=-1 && resultRest != -1) {
            Toast.makeText(PantallaModificarRestaurante.this,"RESTAURANTE ACTUALIZADO EXITOSAMENTE", Toast.LENGTH_LONG).show();
        }

        //Instancia la nueva ventana
        Intent SiguienteActivity= new Intent(this, PantallaEncargado.class);
        SiguienteActivity.putExtra("idEncargado", this.encargado.getId());
        startActivity(SiguienteActivity);

    }

    //Seleccionar una imagen de reemplazo
    public void SeleccionarImagen(View view){

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione la aplicación"),10);
    }

    //Metodo que escucha la respuesta de la nueva imagen
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK){
            convertImageToBitMap(data.getData());
        }
    }

    //Convierte la imagen uri a un bitmap
    private void convertImageToBitMap(Uri uri){

        try {
            ContentResolver cr = getBaseContext().getContentResolver();
            InputStream inputStream = cr.openInputStream(uri);
            this.foto = BitmapFactory.decodeStream(inputStream);
            Toast.makeText(PantallaModificarRestaurante.this,"IMAGEN CARGADA EXITOSAMENTE", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Funcion para cargar los spinner
    private void CargarSpinners() {

        //Array para usarlo en el adapter
        ArrayList<String> cats = new ArrayList<>();
        cats.add("Gourmet");
        cats.add("De Especialidad");
        cats.add("Familiar");
        cats.add("Buffet");
        cats.add("Cómida Rapida");
        cats.add("Temático");
        cats.add("Para llevar");

        //cargamos el de categoria
        categoria.setAdapter(new ArrayAdapter(PantallaModificarRestaurante.this, android.R.layout.simple_spinner_dropdown_item, cats));

        //Lo mismo para las provincias
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Provincias);
        provincia.setAdapter(adapter);

        ArrayList<String> cants = new ArrayList<>();

        //Cantones, cargarlos en el spinner
        for (int i = 0; i < Cantones.length; i++) {
            for (int j = 0; j < Cantones[i].length; j++) {
                cants.add(Cantones[i][j]);
            }
        }
        canton.setAdapter(new ArrayAdapter(PantallaModificarRestaurante.this, android.R.layout.simple_spinner_dropdown_item, cants));

        //Y por ultimo los distritos
        ArrayList<String> dist = new ArrayList<>();

        for (int i = 0; i < Distritos.length; i++) {
            for (int j = 0; j < Distritos[i].length; j++) {
                dist.add(Distritos[i][j]);
            }
        }
        distrito.setAdapter(new ArrayAdapter(PantallaModificarRestaurante.this, android.R.layout.simple_spinner_dropdown_item, dist));
    }

}