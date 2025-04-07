package com.example.eatsdeliveryapp.Cliente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.eatsdeliveryapp.MainActivity;
import com.example.eatsdeliveryapp.PantallaGestionarDireccion;
import com.example.eatsdeliveryapp.R;
import com.example.eatsdeliveryapp.dao.DireccionDAO;
import com.example.eatsdeliveryapp.dao.DireccionXClienteDAO;
import com.example.eatsdeliveryapp.dao.sqlite.DireccionSqliteDAOImpl;
import com.example.eatsdeliveryapp.dao.sqlite.DireccionXClienteSqliteDAOImpl;
import com.example.eatsdeliveryapp.database.DBProperties;
import com.example.eatsdeliveryapp.model.Direccion;
import com.example.eatsdeliveryapp.model.DireccionXCliente;
import com.example.eatsdeliveryapp.service.DireccionService;
import com.example.eatsdeliveryapp.service.DireccionServiceImpl;
import com.example.eatsdeliveryapp.service.DireccionXClienteService;
import com.example.eatsdeliveryapp.service.DireccionXClienteServiceImpl;


//Clase publica para añadir direcciones
public class AnadirDireccion extends AppCompatActivity {

    //atributos propios de la clase
    int idCliente;
    int tipo;

    //referencias a los spinners
    Spinner sProvincia;
    Spinner sCanton;
    Spinner sDistrito;
    DBProperties dbProperties;

    //para conexiones a bases de datos
    private DireccionDAO direccionDAO;
    private DireccionService direccionService;
    private DireccionXClienteDAO direccionXClienteDAO;
    private DireccionXClienteService direccionXClienteService;

    //Variables para cargar los spinners
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
    //se cargan datos y variables
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_direccion);

        //referencia los spinners
        sProvincia = (Spinner) findViewById(R.id.Provincia);
        sCanton = (Spinner) findViewById(R.id.Canton);
        sDistrito = (Spinner) findViewById(R.id.Distrito);

        //captura el id cliente
        idCliente = getIntent().getExtras().getInt("idCliente");
        tipo = getIntent().getExtras().getInt("tipo");

        //carga las propiedades de la base
        dbProperties = new DBProperties(getBaseContext());

        //inicializa las variables para realizar las operaciones sobre tablas
        direccionDAO = new DireccionSqliteDAOImpl(dbProperties) ;
        direccionService = new DireccionServiceImpl(direccionDAO);

        direccionXClienteDAO  = new DireccionXClienteSqliteDAOImpl(dbProperties);
        direccionXClienteService = new DireccionXClienteServiceImpl(direccionXClienteDAO);

        //inicia spinners
        iniciarSpinner();

        //Le carga el onclick al de privincia
        sProvincia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int a=(int)sProvincia.getSelectedItemId();
                actualizarSpinner(a);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //inica los spinners
    private void iniciarSpinner() {

        //genera los adaptadores y se los mete a los spinners
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Provincias);
        sProvincia.setAdapter(adapter);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Cantones[0]);
        sCanton.setAdapter(adapter);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Distritos[0]);
        sDistrito.setAdapter(adapter);
    }

    //para actualizar spinners
    public void actualizarSpinner(int i){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this
                , android.R.layout.simple_spinner_item, Cantones[i]);
        sCanton.setAdapter(adapter);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Distritos[i]);
        sDistrito.setAdapter(adapter);
    }

    //Meotod para añadir direcciones
    public void Anadir(View view){

        //obtiene los items seleccionados
        int sP=(int)sProvincia.getSelectedItemId();
        int sC=(int)sCanton.getSelectedItemId();
        int sD=(int)sDistrito.getSelectedItemId();
        EditText direccionE=(EditText)findViewById(R.id.direccionExacta);

        //carga la direccion nueva
        Direccion direccion = new Direccion();
        direccion.setCanton(Cantones[sP][sC]);
        direccion.setDistrito(Distritos[sP][sD]);
        direccion.setProvincia(Provincias[sP]);
        direccion.setDireccionExacta(direccionE.getText().toString());

        //Se conecta a la base de datos
        SQLiteDatabase dbConnection = dbProperties.getWritableConnection();
        long direccionResult;
        try {
            dbConnection.beginTransaction();

            //inserta la direccion y valida su resultado
            direccionResult = direccionService.addNew(direccion, dbConnection);
            if(direccionResult != -1) {
                dbConnection.setTransactionSuccessful();
                Toast.makeText(AnadirDireccion.this,"Direccion ingresada correctamente", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(AnadirDireccion.this,"Fallo al ingresar dirección", Toast.LENGTH_LONG).show();
            }

        } finally {
            dbConnection.endTransaction();
        }


        //carga la tabla intermedia direccion x clliente
        DireccionXCliente direccionXCliente = new DireccionXCliente();
        direccionXCliente.setIdCliente(idCliente);
        direccionXCliente.setIdDireccion((int)direccionResult);
        direccionXCliente.setActivo("1");

        try {
            dbConnection.beginTransaction();

            //inserta nuevamente y revisa resultado
            long direccionXClienteResult = direccionXClienteService.addNew(direccionXCliente, dbConnection);
            //Toast.makeText(MainActivity.this,"Direccion: "+direccionResult, Toast.LENGTH_LONG).show();
            if(direccionXClienteResult != -1) {
                dbConnection.setTransactionSuccessful();

            }

        } finally {
            dbConnection.endTransaction();
        }

        //carga la siguiente pantalla segun el tipo
        Intent SiguienteActivity;
        switch(tipo) {
            case 0:
                SiguienteActivity= new Intent(this, MainActivity.class);
                startActivity(SiguienteActivity);
                break;
            case 1:
                SiguienteActivity= new Intent(this, PantallaGestionarDireccion.class);
                SiguienteActivity.putExtra("idCliente", idCliente);
                startActivity(SiguienteActivity);
                break;
            default:
                Log.d("A","No pasó xd");
                break;
        }

    }
}