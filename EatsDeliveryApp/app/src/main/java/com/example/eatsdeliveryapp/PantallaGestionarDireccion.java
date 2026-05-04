package com.example.eatsdeliveryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.eatsdeliveryapp.Cliente.AnadirDireccion;
import com.example.eatsdeliveryapp.Cliente.ListaAdapterDir;
import com.example.eatsdeliveryapp.Cliente.ModificarDireccion;
import com.example.eatsdeliveryapp.dao.sqlite.DireccionXClienteSqliteDAOImpl;
import com.example.eatsdeliveryapp.database.DBProperties;
import com.example.eatsdeliveryapp.helper.Dialogs;
import com.example.eatsdeliveryapp.model.Direccion;

import java.util.ArrayList;

//Clase para registrar las direcciones
public class PantallaGestionarDireccion extends AppCompatActivity {

    //atributos propios de la clase
    private int idCliente;
    private DBProperties dbProperties;
    private ArrayList<Direccion> direcciones;

    private ListView lista;
    private ListaAdapterDir listaAdapter;

    //Metodos por default
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_gestionar_direccion);

        //captura el id del cliente
        idCliente = getIntent().getExtras().getInt("idCliente");

        //inicia las propiedad de la base
        dbProperties = new DBProperties(getBaseContext());

        //consulta las direcciones del cliente
        DireccionXClienteSqliteDAOImpl direccionXClienteSqliteDAO= new DireccionXClienteSqliteDAOImpl(dbProperties);
        direcciones=direccionXClienteSqliteDAO.verificarDireccion(idCliente);

        //Cargamos la lista de direcciones
        this.lista = findViewById(R.id.listaDireccionesCliente);
        listaAdapter = new ListaAdapterDir(this, direcciones);
        this.lista.setAdapter(listaAdapter);

        //Evento del click
        this.lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                ModificarDir(i);
            }
        });

        //Evento presionado
        this.lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View v,
                                           int index, long arg3) {
                // TODO Auto-generated method stub
                EliminarDir(index);
                return true;
            }
        });
    }


    private void ModificarDir(int i){
        //Despliega un dialogo donde le pregunta al usuario si quiere modificar la direccion
        Dialogs.showConfirmDialog(this, "¿Desea Modificar la dirección?", () -> {
            Intent SiguienteActivity= new Intent(this, ModificarDireccion.class);
            SiguienteActivity.putExtra("idCliente", idCliente);
            SiguienteActivity.putExtra("idDireccion", direcciones.get(i).getId());
            startActivity(SiguienteActivity);
        }, () -> {
            // Al presionar la opción No
        });

    }

    //metodo para eliminar las direcciones
    private void EliminarDir(int i){

        //Despliega un dialogo donde le pregunta al usuario si quiere eliminar la direccion
        Dialogs.showConfirmDialog(this, "¿Desea Eliminar la dirección?", () -> {

            DireccionXClienteSqliteDAOImpl direccionXClienteSqliteDAO= new DireccionXClienteSqliteDAOImpl(dbProperties);
            int result = direccionXClienteSqliteDAO.EliminarDireccion(direcciones.get(i).getId());

            if (result != -1){
               direcciones.remove(i);
               listaAdapter.notifyDataSetChanged();
               Toast.makeText(PantallaGestionarDireccion.this,"Direccion Eliminada", Toast.LENGTH_LONG).show();
            }
        }, () -> {
            // Al presionar la opción No
        });
    }

    //metodo para agregar las direcciones
    public void Agregar(View view){

        Intent SiguienteActivity= new Intent(this, AnadirDireccion.class);
        SiguienteActivity.putExtra("idCliente", idCliente);
        SiguienteActivity.putExtra("tipo", 1);
        startActivity(SiguienteActivity);

    }
}