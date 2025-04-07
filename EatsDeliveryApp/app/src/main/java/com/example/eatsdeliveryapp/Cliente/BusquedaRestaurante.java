package com.example.eatsdeliveryapp.Cliente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.eatsdeliveryapp.MainActivity;
import com.example.eatsdeliveryapp.R;
import com.example.eatsdeliveryapp.administrador.LogInAdmin;
import com.example.eatsdeliveryapp.administrador.gerente.ListaAdapter;
import com.example.eatsdeliveryapp.dao.sqlite.RestauranteSqliteDAOImpl;
import com.example.eatsdeliveryapp.database.DBProperties;
import com.example.eatsdeliveryapp.model.Restaurante;

import java.util.ArrayList;
import java.util.List;

//Clase publica para buscar restaurantes en la pantalla del cliente
public class BusquedaRestaurante extends AppCompatActivity {

    //atrbituos propios de la clase
    int idCliente;

    //carga listas y arraylists
    private List<Restaurante> restaurantes;
    private List<Restaurante> restaurantesRespaldo;
    private ArrayList<String> elementosRes;
    private ArrayList<String> elementosRespaldo;
    private ListView lista;

    //propiedades de la tabla y categorias
    private DBProperties dbProperties;
    Spinner categorias;

    //Carga la lista de spinner y el adptador
    private ListaAdapterCliente adapter;
    public String[] categoriasList = new String[]{"Cualquiera", "Familiar", "Comida Rápida","Gourmet"
                                                 ,"De Especialidad", "Buffet","Temático","Para llevar"};


    //Metodo por default, incializa variables
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_restaurante);

        //Captura el id del cliente
        idCliente = getIntent().getExtras().getInt("idCliente");

        //carga el spinner de categorias
        categorias = (Spinner) findViewById(R.id.Categorias);
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, categoriasList);
        categorias.setAdapter(adapterSpinner);

        //cargar las propiedades de la base y llama los restaurantes
        dbProperties = new DBProperties(getBaseContext());
        RestauranteSqliteDAOImpl restauranteSqliteDAO= new RestauranteSqliteDAOImpl(dbProperties);
        restaurantes=restauranteSqliteDAO.findAll();
        elementosRes = restauranteSqliteDAO.consultaRestauranteGerente();

        //los almacena
        restaurantesRespaldo= new ArrayList<Restaurante>();
        elementosRespaldo=new ArrayList<String>();
        restaurantesRespaldo.addAll(restaurantes);
        elementosRespaldo.addAll(elementosRes);

        //referencia la lista de la pantalla
        lista = findViewById(R.id.ListaVista);

        //crea un adapter
        adapter= new ListaAdapterCliente(this, restaurantes, elementosRes, idCliente);

        //se lo setea a la lista
        lista.setAdapter(adapter);

        //activa el evento onclick
        lista.setItemsCanFocus(false);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                irARestaurante(restaurantes.get(i).getId());
            }
        });


        //activa el eventon presionar
        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView adapterView, View view, int i, long l) {
                //Toast.makeText(BusquedaRestaurante.this, "click Largo " + i, Toast.LENGTH_SHORT).show();
                Log.d("ZZZ","Click largo");
                return false;
            }
        });
    }


    //metodo publico para realizar la busqueda, se llama por un boton
    public void Busqueda(View view){

        //captura la categoria entrante y el get text
        int idCategoria=(int)categorias.getSelectedItemId();
        EditText textoBusquedaCuadro = (EditText)findViewById(R.id.CuadroBusqueda);
        String textoBusqueda= textoBusquedaCuadro.getText().toString();

        //limpia  las listas
        Log.d("PRESUS",Integer.toString(restaurantesRespaldo.size()));
        restaurantes.clear();
        elementosRes.clear();

        //las vuelve a cargar segun las preferencias
        Log.d("POSTSUS",Integer.toString(restaurantesRespaldo.size()));
        for(int i=0;i<restaurantesRespaldo.size();i++){

            if(restaurantesRespaldo.get(i).getNombre().contains(textoBusqueda)){
                if(categoriasList[idCategoria].equals("Cualquiera") || restaurantesRespaldo.get(i).getCategoria().equals(categoriasList[idCategoria])){
                    restaurantes.add(restaurantesRespaldo.get(i));
                    elementosRes.add(elementosRespaldo.get(i));
                }
            }
        }
        //crea el adapter
        adapter= new ListaAdapterCliente(this, restaurantes, elementosRes, idCliente);

        //lo setea de nuevo en la lista
        lista.setAdapter(adapter);
    }


    //carga la pantalla de los restaurantes
    public void irARestaurante(int restaurante){

        //genera un nuevo intent
        Intent SiguienteActivity= new Intent(this, DetalleRestaurante.class);
        SiguienteActivity.putExtra("idCliente", idCliente);
        SiguienteActivity.putExtra("restaurante", restaurante);
        startActivity(SiguienteActivity);

    }
}