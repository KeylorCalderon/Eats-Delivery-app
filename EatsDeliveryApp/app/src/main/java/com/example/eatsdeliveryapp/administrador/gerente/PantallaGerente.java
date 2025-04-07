package com.example.eatsdeliveryapp.administrador.gerente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.eatsdeliveryapp.Cliente.PantallaPrincipalCliente;
import com.example.eatsdeliveryapp.R;
import com.example.eatsdeliveryapp.administrador.PantallaAdminDefault;
import com.example.eatsdeliveryapp.dao.sqlite.RestauranteSqliteDAOImpl;
import com.example.eatsdeliveryapp.database.DBProperties;
import com.example.eatsdeliveryapp.model.Restaurante;

import java.util.ArrayList;
import java.util.List;


//Clase publica para la pantalla principal del gerente
public class PantallaGerente extends AppCompatActivity {

    //atributo privado del gerente id
    private int idGerente;

    //atributos de clase y base de datos
    private ListView lista;
    private DBProperties dbProperties;
    private ListaAdapter adapter;

    //Metodo por default
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_gerente);


        //Acá se captura el id del gerente
        idGerente = getIntent().getExtras().getInt("idGerente");

        //Inicializar las variables de la base de datos
        dbProperties = new DBProperties(getBaseContext());

        //Cargar el listView de restaurantes
        RestauranteSqliteDAOImpl res = new RestauranteSqliteDAOImpl(dbProperties);
        ArrayList<String> elementosRes = res.consultaRestauranteGerente();

        //captura todos los restaurantes
        List<Restaurante> restaurantes = res.findAll();

        //obtiene la referencia a la lista en pantalla
        lista = findViewById(R.id.listaRestaurantes);

        //crea un nuevo adaptador
        adapter= new ListaAdapter(this, restaurantes, elementosRes);

        //Se lo setea  a la lista
        lista.setAdapter(adapter);

    }

    //Metodo llamado por un boton, para ingresar restaurantes
    public void IngresarRestaurante(View view){

        //Instancia la nueva ventana
        Intent SiguienteActivity= new Intent(this, PantallaInsertarRestaurantes.class);
        SiguienteActivity.putExtra("idGerente", idGerente);
        startActivity(SiguienteActivity);
    }


    //Metodo para cargar la nueva ventana, para ver las solicitudes de eliminacion
    public void Solicitudes(View view){

        //Instancia la nueva ventana
        Intent SiguienteActivity= new Intent(this, PantallaPeticionEliminar.class);
        SiguienteActivity.putExtra("idGerente", idGerente);
        startActivity(SiguienteActivity);
    }

}