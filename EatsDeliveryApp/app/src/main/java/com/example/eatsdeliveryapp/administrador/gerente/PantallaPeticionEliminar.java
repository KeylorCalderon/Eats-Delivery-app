package com.example.eatsdeliveryapp.administrador.gerente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.eatsdeliveryapp.R;
import com.example.eatsdeliveryapp.dao.SolicitudEliminacionDAO;
import com.example.eatsdeliveryapp.dao.sqlite.RestauranteSqliteDAOImpl;
import com.example.eatsdeliveryapp.dao.sqlite.SolicitudEliminacionDAOImpl;
import com.example.eatsdeliveryapp.database.DBProperties;
import com.example.eatsdeliveryapp.helper.Dialogs;
import com.example.eatsdeliveryapp.model.Restaurante;
import com.example.eatsdeliveryapp.model.SolicitudEliminacion;

import org.json.JSONException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

//clase publica para la pantalla donde se muestran las solicitudes de eliminacion de restaurantes
public class PantallaPeticionEliminar extends AppCompatActivity {

    //atributos privados de la clase
    private int idGerente;
    private ArrayList<String> elementos;
    private ArrayList<byte[]> imagenes;

    //referencia a pantalla y base
    private ListView lista;
    private DBProperties dbProperties;
    private ListaAdapterEliminar adapter;
    List<SolicitudEliminacion> solicitudEliminacion;

    //metodo por default
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_peticion_eliminar);

        //Acá se captura el id del gerente
        idGerente = getIntent().getExtras().getInt("idGerente");

        //Inicializar las variables de la base de datos
        dbProperties = new DBProperties(getBaseContext());

        //Cargar el listView de restaurantes
        SolicitudEliminacionDAOImpl sos = new SolicitudEliminacionDAOImpl(dbProperties);

        //busca todas las solicitudes
        this.solicitudEliminacion = sos.findAll();

        //genera los elementos, una lista de solicitudes
        elementos = sos.consultaSolicitudes(solicitudEliminacion);

        //carga las imagens
        imagenes = sos.consultarImagenes(solicitudEliminacion);

        //Cargar el listView de restaurantes
        lista = findViewById(R.id.ContenlistView);

        //crea el adapter
        adapter= new ListaAdapterEliminar(this, imagenes, elementos);

        //se lo setea
        lista.setAdapter(adapter);

        //activa metodo onclick
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                EliminarRestaurante(solicitudEliminacion.get(i), i);
            }
        });
    }


    //metodo para eliminar restaurantes, privado para evitar problemas
    private void EliminarRestaurante(SolicitudEliminacion solicitud, int i){

        //Despliega un dialogo donde le pregunta al usuario si quiere eliminar el restaurante
        Dialogs.showConfirmDialog(this, "¿Seguro que desea eliminar este restaurante?", () -> {

            // Al presionar la opción Si

            //se carga el restaurante
            RestauranteSqliteDAOImpl res = new RestauranteSqliteDAOImpl(dbProperties);
            res.EliminarRestaurante(solicitud.getIdRestaurante());

            //se carga la eliminacion
            SolicitudEliminacionDAOImpl sol = new SolicitudEliminacionDAOImpl(dbProperties);
            sol.EliminarSolicitud(solicitud.getId());

            //a ambos se les hizo la eliminacion

            Toast.makeText(getApplicationContext(), "RESTAURANTE ELIMINADO EXITOSAMENTE", Toast.LENGTH_SHORT).show();

            //carga la nueva pantalla
            Intent SiguienteActivity= new Intent(this, PantallaGerente.class);
            SiguienteActivity.putExtra("idGerente", this.idGerente);
            startActivity(SiguienteActivity);

        }, () -> {
            // Al presionar la opción No
        });
    }
}