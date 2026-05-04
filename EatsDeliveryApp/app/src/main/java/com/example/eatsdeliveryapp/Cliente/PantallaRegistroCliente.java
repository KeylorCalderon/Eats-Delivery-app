package com.example.eatsdeliveryapp.Cliente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eatsdeliveryapp.BaseDeDatos.AyudanteDB;
import com.example.eatsdeliveryapp.BaseDeDatos.CRUD_BD;
import com.example.eatsdeliveryapp.R;
import com.example.eatsdeliveryapp.dao.ClienteDAO;
import com.example.eatsdeliveryapp.dao.CuentaClienteDAO;
import com.example.eatsdeliveryapp.dao.TelefonoDAO;
import com.example.eatsdeliveryapp.dao.sqlite.ClienteSqliteDAOImpl;
import com.example.eatsdeliveryapp.dao.sqlite.CuentaClienteSqliteDAOImpl;
import com.example.eatsdeliveryapp.dao.sqlite.TelefonoSqliteDAOImpl;
import com.example.eatsdeliveryapp.database.DBProperties;
import com.example.eatsdeliveryapp.model.Cliente;
import com.example.eatsdeliveryapp.model.CuentaCliente;
import com.example.eatsdeliveryapp.model.Telefono;
import com.example.eatsdeliveryapp.service.ClienteService;
import com.example.eatsdeliveryapp.service.ClienteServiceImpl;
import com.example.eatsdeliveryapp.service.CuentaClienteService;
import com.example.eatsdeliveryapp.service.CuentaClienteServiceImpl;
import com.example.eatsdeliveryapp.service.TelefonoService;
import com.example.eatsdeliveryapp.service.TelefonoServiceImpl;

import java.util.List;


//Clase para el registro del usuario cliente
public class PantallaRegistroCliente extends AppCompatActivity {

    //Atributos de la clase, para la base de datos
    private CuentaClienteDAO cuentaClienteDAO;
    private ClienteDAO clienteDAO;
    private TelefonoDAO telefonoDAO;
    private CuentaClienteService cuentaClienteService;
    private ClienteService clienteService;
    private TelefonoService telefonoService;

    //para cargar las propiedades de la base
    private DBProperties dbProperties;

    //Método por default instanciado al crear la clase
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_registro);

        //carga las propiedades de la base
        dbProperties = new DBProperties(getBaseContext());

        // Inicializa DAO
        cuentaClienteDAO = new CuentaClienteSqliteDAOImpl(dbProperties);
        clienteDAO  = new ClienteSqliteDAOImpl(dbProperties);
        telefonoDAO = new TelefonoSqliteDAOImpl(dbProperties);

        // Inicializa Services
        cuentaClienteService = new CuentaClienteServiceImpl(cuentaClienteDAO);
        clienteService = new ClienteServiceImpl(clienteDAO);
        telefonoService = new TelefonoServiceImpl(telefonoDAO);
    }


    //Método de registrar, configurado con el Onclick del boton de la activity
    public void Registrar(View view){

        //Capturamos los datos ingresados por el cliente
        EditText user = (EditText)findViewById(R.id.username);
        EditText password = (EditText)findViewById(R.id.password);
        EditText nombre = (EditText)findViewById(R.id.nombre);
        EditText apellido1 = (EditText)findViewById(R.id.apellido1);
        EditText apellido2 = (EditText)findViewById(R.id.apellido2);
        EditText correo = (EditText)findViewById(R.id.correo);
        EditText telefono = (EditText)findViewById(R.id.telefono);
        String t = telefono.getText().toString();

        //insertamos el registro en la base de datos
        int id = insertarRegistro(user.getText().toString(), password.getText().toString(), nombre.getText().toString(),
                         apellido1.getText().toString(), apellido2.getText().toString(), correo.getText().toString(),
                         Integer.parseInt(t));

        //Instanciamos la actividad siguiente, para la inserción de la tarjeta
        //Le pasamos el id del cliente por putExtra
        Intent SiguienteActivity= new Intent(this, PantallaTarjeta.class);
        SiguienteActivity.putExtra("idCliente", id);
        startActivity(SiguienteActivity);
    }


    //metodo que realiza el propio insert en la base de datos
    private int insertarRegistro(String user, String password, String nombre, String apellido1, String apellido2,
                                  String correo, int movil) {

        //carga el id negativo del cliente, en caso de error
        int idCliente=-1;

        //cargamos una cuenta cliente
        CuentaCliente cuenta = new CuentaCliente();
        cuenta.setNombreUsuario(user);
        cuenta.setContrasena(password);

        //cargamos el objeto cliente
        Cliente cliente = new Cliente();
        cliente.setNombreUsuario((cuenta.getNombreUsuario()));
        cliente.setNombre(nombre);
        cliente.setPrimerApellido(apellido1);
        cliente.setSegundoApellido(apellido2);
        cliente.setCorreo(correo);

        //conectamos la base de datos
        SQLiteDatabase dbConnection = dbProperties.getWritableConnection();
        try {
            dbConnection.beginTransaction();

            //realizamos ambos inserts
            long cuentaResult = cuentaClienteService.addNew(cuenta, dbConnection);
            long clienteResult = clienteService.addNew(cliente, dbConnection);
            idCliente = (int) clienteResult;

            //insertamos el telefono además
            Telefono telefono = new Telefono();
            telefono.setIdCliente(idCliente);
            telefono.setNumero(movil);

            //long movilResult = telefonoService.addNew(telefono, dbConnection);

            if(cuentaResult != -1 && clienteResult != -1) {
                dbConnection.setTransactionSuccessful();
                Log.d("ERROR","ERROR insertar clientes");
            }

        } finally {
            dbConnection.endTransaction();
        }

        return idCliente;
    }

}