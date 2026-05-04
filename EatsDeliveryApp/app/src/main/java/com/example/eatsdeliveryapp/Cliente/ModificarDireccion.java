package com.example.eatsdeliveryapp.Cliente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.eatsdeliveryapp.PantallaGestionarDireccion;
import com.example.eatsdeliveryapp.R;
import com.example.eatsdeliveryapp.dao.sqlite.DireccionSqliteDAOImpl;
import com.example.eatsdeliveryapp.database.DBProperties;
import com.example.eatsdeliveryapp.model.Direccion;

//Pantalla para modificar direcciones
public class ModificarDireccion extends AppCompatActivity {


    //atributos propios de la clase
    private int idCliente;
    private DBProperties dbProperties;
    private int idDireccion;
    private Direccion direccion;
    private DireccionSqliteDAOImpl direccionSqliteDAO;

    //referencias a pantalla
    private Spinner provincia;
    private Spinner canton;
    private Spinner distrito;
    private EditText direccionExacta;

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


    //Método por default
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_direccion);

        //captura el id del cliente
        idCliente = getIntent().getExtras().getInt("idCliente");
        idDireccion = getIntent().getExtras().getInt("idDireccion");

        //inicia las propiedad de la base
        dbProperties = new DBProperties(getBaseContext());

        //captura la direccion
        direccionSqliteDAO = new DireccionSqliteDAOImpl(dbProperties);
        this.direccion = direccionSqliteDAO.getDireccion(idDireccion);

        //refrencias a la pantalla
        provincia = findViewById(R.id.provinciaSpinner);
        canton = findViewById(R.id.cantonSpinner);
        distrito = findViewById(R.id.distritoSpinner);
        this.direccionExacta = (EditText) findViewById(R.id.direccionSpinner);

        this.direccionExacta.setText(this.direccion.getDireccionExacta());

        //inicia spinners
        iniciarSpinner();

        //Le carga el onclick al de privincia
        provincia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int a=(int) provincia.getSelectedItemId();
                actualizarSpinner(a);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

    }


    //Metodo para editar la direccion del usuario
    public void EditDireccion(View view){

        //Creamos la direccion
        Direccion d = new Direccion();
        d.setId(this.idDireccion);
        d.setProvincia(provincia.getSelectedItem().toString());
        d.setCanton(canton.getSelectedItem().toString());
        d.setDistrito(distrito.getSelectedItem().toString());
        d.setDireccionExacta(this.direccionExacta.getText().toString());

        //hacemos el update
        int error = direccionSqliteDAO.ActualizarDireccion(d);

        //Valida el resultado
        if (error != -1){

            Toast.makeText(ModificarDireccion.this,"Dirección Modificada", Toast.LENGTH_LONG).show();
            Intent SiguienteActivity= new Intent(this, PantallaGestionarDireccion.class);
            SiguienteActivity.putExtra("idCliente", idCliente);
            startActivity(SiguienteActivity);

        }
    }

    //inica los spinners
    private void iniciarSpinner() {

        //genera los adaptadores y se los mete a los spinners
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Provincias);
        provincia.setAdapter(adapter);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Cantones[0]);
        canton.setAdapter(adapter);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Distritos[0]);
        distrito.setAdapter(adapter);
    }

    //para actualizar spinners
    public void actualizarSpinner(int i){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this
                , android.R.layout.simple_spinner_item, Cantones[i]);
        canton.setAdapter(adapter);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Distritos[i]);
        distrito.setAdapter(adapter);
    }

}