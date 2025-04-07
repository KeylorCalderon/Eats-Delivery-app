package com.example.eatsdeliveryapp.administrador;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.eatsdeliveryapp.MainActivity;
import com.example.eatsdeliveryapp.R;
import com.example.eatsdeliveryapp.administrador.encargado.PantallaEncargado;
import com.example.eatsdeliveryapp.dao.AdministradorDAO;
import com.example.eatsdeliveryapp.dao.CuentaAdministradorDAO;
import com.example.eatsdeliveryapp.dao.sqlite.AdministradorSqliteDAOImpl;
import com.example.eatsdeliveryapp.dao.sqlite.CuentaAdministradorSqliteDAOlmpl;
import com.example.eatsdeliveryapp.dao.sqlite.RestauranteSqliteDAOImpl;
import com.example.eatsdeliveryapp.database.DBProperties;
import com.example.eatsdeliveryapp.model.Administrador;
import com.example.eatsdeliveryapp.model.CuentaAdministrador;
import com.example.eatsdeliveryapp.model.Restaurante;
import com.example.eatsdeliveryapp.service.AdministradorService;
import com.example.eatsdeliveryapp.service.AdministradorServiceImpl;
import com.example.eatsdeliveryapp.service.CuentaAdministradorService;
import com.example.eatsdeliveryapp.service.CuentaAdministradorServiceImpl;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

//Clase para el admin por default
public class PantallaAdminDefault extends AppCompatActivity {

    //Atributos propios de la clase
    private int idPuesto;
    private int idRestaurante;
    private List<Restaurante> restaurantes;

    //Refrencia a objetos de la pantalla
    private Spinner mySpinnerRes;
    private Spinner mySpinnerPuesto;

    //Referencia a clases de la base de datos
    private DBProperties dbProperties;
    private CuentaAdministradorDAO cuentaAdministradorDAO;
    private AdministradorDAO administradorDAO;
    private CuentaAdministradorService cuentaAdministradorService;
    private AdministradorService administradorService;


    //Método por default se activa cuando se crea la clase
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_admin_default);

        //Inicializar las variables de la base de datos
        dbProperties = new DBProperties(getBaseContext());
        cuentaAdministradorDAO = new CuentaAdministradorSqliteDAOlmpl(dbProperties);
        administradorDAO = new AdministradorSqliteDAOImpl(dbProperties);
        cuentaAdministradorService = new CuentaAdministradorServiceImpl(cuentaAdministradorDAO);
        administradorService = new AdministradorServiceImpl(administradorDAO);

        //Cargar el spinner de puestos
        mySpinnerPuesto = findViewById(R.id.spinnerPuesto);

        ArrayList<String> elementos = new ArrayList<>();
        elementos.add("Gerente");
        elementos.add("Encargado Restaurante");
        elementos.add("Repartidor");

        //Les setea el adpater
        mySpinnerPuesto.setAdapter(new ArrayAdapter(PantallaAdminDefault.this, android.R.layout.simple_spinner_dropdown_item, elementos));

        //Activa el metodo onclick
        mySpinnerPuesto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idPuesto=i+1;   //Obtengo el id del puesto seleccionado
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });


        //Cargar el spinner de restaurantes
        RestauranteSqliteDAOImpl res = new RestauranteSqliteDAOImpl(dbProperties);
        this.restaurantes= res.findAll();

        //lo refrencia
        mySpinnerRes = findViewById(R.id.spinnerRestaurante);

        //genera el array y carga el adapter
        ArrayList<String> elementosRes = new ArrayList<>();

        for (int i=0; i<restaurantes.size(); i++){
            elementosRes.add(restaurantes.get(i).getNombre());
        }
        mySpinnerRes.setAdapter(new ArrayAdapter(PantallaAdminDefault.this, android.R.layout.simple_spinner_dropdown_item, elementosRes));

        //activa metodos onclick
        mySpinnerRes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idRestaurante = restaurantes.get(i).getId();   //Obtengo el id del puesto seleccionado
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }



    //Inserta los administraores, consulta los datos en pantalla
    public void InsertarAdmin(View view){

        EditText identificador = (EditText)this.findViewById(R.id.identificador);
        EditText contrasena = (EditText)this.findViewById(R.id.password);
        EditText apellido1 = (EditText)this.findViewById(R.id.apellido1);
        EditText apellido2 = (EditText)this.findViewById(R.id.apellido2);
        EditText nombre = (EditText)this.findViewById(R.id.nombre);
        EditText cedula = (EditText)this.findViewById(R.id.cedula);


        //Transaccion en la base de datos y su inserción
        SQLiteDatabase dbConnection = dbProperties.getWritableConnection();
        try {
            dbConnection.beginTransaction();

            CuentaAdministrador cuenta= new CuentaAdministrador();
            cuenta.setIdLaboral(identificador.getText().toString());
            cuenta.setContrasenna(contrasena.getText().toString());

            Administrador admin = new Administrador();
            admin.setIdRestaurante(this.idRestaurante);
            admin.setIdLaboral(identificador.getText().toString());
            admin.setIdPuesto(idPuesto);
            admin.setCedula(Integer.parseInt(cedula.getText().toString()));
            admin.setNombre(nombre.getText().toString());
            admin.setPrimerApellido(apellido1.getText().toString());
            admin.setSegundoApellido(apellido2.getText().toString());

            long cuentaResult = cuentaAdministradorService.addNew(cuenta, dbConnection);
            long adminResult = administradorService.addNew(admin, dbConnection);

            if(cuentaResult != -1 && adminResult != -1) {
                dbConnection.setTransactionSuccessful();
                Toast.makeText(PantallaAdminDefault.this,"ADMINISTRADOR INGRESADO EXITOSAMENTE", Toast.LENGTH_LONG).show();
            }

        } finally {
            dbConnection.endTransaction();
        }

        identificador.setText("");
        contrasena.setText("");
        apellido1.setText("");
        apellido2.setText("");
        cedula.setText("");
        nombre.setText("");
    }

}