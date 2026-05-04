package com.example.eatsdeliveryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.CursorWindow;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import android.content.Context;
import com.example.eatsdeliveryapp.Cliente.PantallaPrincipalCliente;
import com.example.eatsdeliveryapp.Cliente.PantallaRegistroCliente;
import com.example.eatsdeliveryapp.administrador.LogInAdmin;
import com.example.eatsdeliveryapp.dao.*;
import com.example.eatsdeliveryapp.dao.sqlite.*;
import com.example.eatsdeliveryapp.database.DBProperties;
import com.example.eatsdeliveryapp.model.*;
import com.example.eatsdeliveryapp.service.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.lang.reflect.Field;
import java.util.List;
import com.example.eatsdeliveryapp.BuildConfig;

//Clase publica del main activity, acá básicamente se da el LogIn
public class MainActivity extends AppCompatActivity {

    //Variables propias de la clase
    private EditText TextUsuario;
    private EditText TextContrasena;
    private DBProperties dbProperties;

    //Variables para las operaciones en la base de datos
    private CuentaClienteDAO cuentaClienteDAO;

    //para los servicios
    private CuentaClienteService cuentaClienteService;



    //Método por default, para generar el activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //carga las propiedades de la base
        dbProperties = new DBProperties(getBaseContext());


        //SECCIÓN QUE CREA LA BASE DE DATOS
        //dbProperties.dropDatabase();

        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 100 * 1024 * 1024); //100MB
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            }
        }

        // Inicializa DAO
        cuentaClienteDAO = new CuentaClienteSqliteDAOImpl(dbProperties);

        // Inicializa Services
        cuentaClienteService = new CuentaClienteServiceImpl(cuentaClienteDAO);


        AdministradorSqliteDAOImpl a = new AdministradorSqliteDAOImpl(this.dbProperties);
        if (a.validaAdminDefault(1) != -1){

            DatosDePrueba datos = new DatosDePrueba(dbProperties, this);
            //Metodos de prueba
            datos.initializeDatabase();
            datos.initializeDatabaseDireccion();
            datos.initializeDatabaseDireccionXCliente();
            datos.initializeDatabaseRestaurante();
            datos.insertarPuestos();
            datos.adminDeafult();
        }

    }


    //Realiza el LogIn
    public void Loguearse(View view){

        //Captura y convierte los datos ingresados por el usuario
        TextUsuario = (EditText)findViewById(R.id.LoginUsuario);
        TextContrasena = (EditText)findViewById(R.id.LoginContrasena);
        String usuario = TextUsuario.getText().toString();
        String contrasena = TextContrasena.getText().toString();

        CuentaClienteSqliteDAOImpl cuentaClienteSqliteDAO= new CuentaClienteSqliteDAOImpl(dbProperties);

        //Consulta si el cliente se encuentra en la base
        int a = cuentaClienteSqliteDAO.verificarCliente(usuario, contrasena);
        if( a != -1){

            Log.d("IDDDDDDDD", String.valueOf(a));

            //Instancia la clase
            Intent SiguienteActivity= new Intent(this, PantallaPrincipalCliente.class);
            SiguienteActivity.putExtra("idCliente", a);
            //Toast.makeText(MainActivity.this,"USUARIO: "+a, Toast.LENGTH_LONG).show();
            startActivity(SiguienteActivity);
        }else{

            //Despliega error, en caso contrario
            Toast.makeText(MainActivity.this,"USUARIO Y/O CONTRASEÑA INVÁLIDOS", Toast.LENGTH_LONG).show();
        }
    }

    //Metodo para el boton de registrar cliente, instancia la nueva activity
    public void RegistroCliente(View view){

        Intent SiguienteActivity= new Intent(this, PantallaRegistroCliente.class);
        startActivity(SiguienteActivity);

    }

    //Metodo para el boton de registrar cliente, instancia la nueva activity
    public void LogInAdmin(View view){

        Intent SiguienteActivity= new Intent(this, LogInAdmin.class);
        startActivity(SiguienteActivity);

    }
}