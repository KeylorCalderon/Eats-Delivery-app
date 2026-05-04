package com.example.eatsdeliveryapp.administrador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eatsdeliveryapp.Cliente.PantallaPrincipalCliente;
import com.example.eatsdeliveryapp.MainActivity;
import com.example.eatsdeliveryapp.R;
import com.example.eatsdeliveryapp.administrador.encargado.PantallaEncargado;
import com.example.eatsdeliveryapp.administrador.gerente.PantallaGerente;
import com.example.eatsdeliveryapp.administrador.repartidor.PantallaRepartidor;
import com.example.eatsdeliveryapp.dao.CuentaAdministradorDAO;
import com.example.eatsdeliveryapp.dao.sqlite.CuentaAdministradorSqliteDAOlmpl;
import com.example.eatsdeliveryapp.database.DBProperties;

//Clase publica para el logueo de administradores
public class LogInAdmin extends AppCompatActivity {

    //Referencias a la base de datos y a la pantall
    private EditText identificador;
    private EditText contrasenna;
    private DBProperties dbProperties;


    //metodo por default
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_admin);

        //carga las propiedad de sqlite
        dbProperties = new DBProperties(getBaseContext());

    }


    //Funcion que valida el login
    public void LogInAdmin(View view){

        //Obtiene los datos ingresados
        this.identificador = (EditText) findViewById(R.id.identificador);
        this.contrasenna = (EditText) findViewById(R.id.contrasena);

        //llama al dao de la cuenta
        CuentaAdministradorSqliteDAOlmpl cuenta = new CuentaAdministradorSqliteDAOlmpl(dbProperties);

        //llama al metodo que valida el login
        int [] a = cuenta.verificarAdmin(identificador.getText().toString(), contrasenna.getText().toString());

        //a[0], es el id del admin y a[1] es el puesto del mismo
        if(a[0] != -1){

            //Admin por default
            if (a[0]==1){
                Intent SiguienteActivity= new Intent(this, PantallaAdminDefault.class);
                startActivity(SiguienteActivity);
            }

            //Debe cargar pantalla de gerente
            else if (a[1] == 1){

                Intent SiguienteActivity= new Intent(this, PantallaGerente.class);
                SiguienteActivity.putExtra("idGerente", a[0]);
                startActivity(SiguienteActivity);

            }

            //En este caso la de el encargado
            else if (a[1] == 2){
                Intent SiguienteActivity= new Intent(this, PantallaEncargado.class);
                SiguienteActivity.putExtra("idEncargado", a[0]);
                startActivity(SiguienteActivity);
            }

            //Y acá la del repartidor
            else if (a[1] == 3){
                Intent SiguienteActivity= new Intent(this, PantallaRepartidor.class);
                SiguienteActivity.putExtra("idRepartidor", a[0]);
                startActivity(SiguienteActivity);
            }
        } else{

            //Despliega error, en caso contrario
            Toast.makeText(LogInAdmin.this,"Identificador Y/O CONTRASEÑA INVÁLIDOS", Toast.LENGTH_LONG).show();
        }
    }
}