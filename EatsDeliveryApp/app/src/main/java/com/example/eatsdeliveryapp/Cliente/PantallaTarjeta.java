package com.example.eatsdeliveryapp.Cliente;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eatsdeliveryapp.BaseDeDatos.AyudanteDB;
import com.example.eatsdeliveryapp.BaseDeDatos.CRUD_BD;
import com.example.eatsdeliveryapp.MainActivity;
import com.example.eatsdeliveryapp.PantallaGestionarDireccion;
import com.example.eatsdeliveryapp.R;
import com.example.eatsdeliveryapp.dao.TarjetaDAO;
import com.example.eatsdeliveryapp.dao.sqlite.TarjetaSqliteDAOImpl;
import com.example.eatsdeliveryapp.database.DBProperties;
import com.example.eatsdeliveryapp.fecha.DatePickerFragment;
import com.example.eatsdeliveryapp.model.Tarjeta;
import com.example.eatsdeliveryapp.model.Telefono;
import com.example.eatsdeliveryapp.service.TarjetaService;
import com.example.eatsdeliveryapp.service.TarjetaServiceImpl;

//Clase para configurar las tarjetas de los clientes
public class PantallaTarjeta extends AppCompatActivity {

    //Atributos propios de la clase
    private int idCliente;


    //carga las variable necesarias para la base de datos
    private DBProperties dbProperties;
    private TarjetaDAO tarjetaDAO;
    private TarjetaService tarjetaService;
    private EditText dia;

    //Método por default
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_tarjeta);

        //Acá se captura el id del empleado
        idCliente = getIntent().getExtras().getInt("idCliente");

        //inicializamos las propiedades de la base
        dbProperties = new DBProperties(getBaseContext());

        //cargamos las refrencias
        tarjetaDAO = new TarjetaSqliteDAOImpl(dbProperties);
        tarjetaService = new TarjetaServiceImpl(tarjetaDAO);
        dia = (EditText) findViewById(R.id.fechaVence);
        dia.setOnClickListener(this::picker);

    }

    //Agregamos la tarjeta, llama a un método para hacerlo en la base de datos
    public void AgregarTarjeta(View view) {

        //Obtenemos los datos ingresados por el usuario
        EditText numeroTarjeta = (EditText) findViewById(R.id.numeroTarjeta);
        EditText nombreTitular = (EditText) findViewById(R.id.nombreTitular);
        EditText vence = (EditText) findViewById(R.id.fechaVence);
        EditText csv = (EditText) findViewById(R.id.csv);

        //lamamos al metodo auxiliar
        InsertarTarjeta(Integer.parseInt(numeroTarjeta.getText().toString()), nombreTitular.getText().toString(),
                        vence.getText().toString(), Integer.parseInt(csv.getText().toString()));

        Toast.makeText(PantallaTarjeta.this,"Cuenta creada exitosamente", Toast.LENGTH_LONG).show();

        //Instanciamos la siguente actividad, para configurar las direcciones del cliente
        Intent SiguienteActivity= new Intent(this, AnadirDireccion.class);
        SiguienteActivity.putExtra("idCliente", (int)(idCliente));

        SiguienteActivity.putExtra("tipo", 0);
        startActivity(SiguienteActivity);
    }


    //Método para insertar la tarjeta
    private void InsertarTarjeta(int numero, String nombre, String fecha, int csv){

        //se crea el objeto tarjeta
        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setIdCliente(idCliente);
        tarjeta.setNumero(numero);
        tarjeta.setNombreTitural(nombre);
        tarjeta.setFechaVencimiento(fecha);
        tarjeta.setCodigoSeguridad(csv);

        //Se inserta en la base
        SQLiteDatabase dbConnection = dbProperties.getWritableConnection();
        try {
            dbConnection.beginTransaction();

            long tarjetaResult = tarjetaService.addnew(tarjeta, dbConnection);

            //valida correctitud
            if(tarjetaResult != -1 ) {
                dbConnection.setTransactionSuccessful();
            }
        } finally {
            dbConnection.endTransaction();
        }
    }

    //metodo para carga un picker a la hora de seleccionar fecha vence
    private void picker(View view) {

        //carga el nuevo datepicker
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                //le da formato
                // +1 because January is zero
                String selectedDate = (month+1)+"/"+year;
                dia.setText(selectedDate);
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

}