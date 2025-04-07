package com.example.eatsdeliveryapp.administrador.encargado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eatsdeliveryapp.R;
import com.example.eatsdeliveryapp.administrador.gerente.ListaAdapter;
import com.example.eatsdeliveryapp.dao.SolicitudEliminacionDAO;
import com.example.eatsdeliveryapp.dao.sqlite.AdministradorSqliteDAOImpl;
import com.example.eatsdeliveryapp.dao.sqlite.ProductoSqliteDAOImpl;
import com.example.eatsdeliveryapp.dao.sqlite.RestauranteSqliteDAOImpl;
import com.example.eatsdeliveryapp.dao.sqlite.SolicitudEliminacionDAOImpl;
import com.example.eatsdeliveryapp.database.DBProperties;
import com.example.eatsdeliveryapp.helper.Dialogs;
import com.example.eatsdeliveryapp.model.Administrador;
import com.example.eatsdeliveryapp.model.Producto;
import com.example.eatsdeliveryapp.model.Restaurante;
import com.example.eatsdeliveryapp.model.SolicitudEliminacion;
import com.example.eatsdeliveryapp.service.SolicitudEliminacionService;
import com.example.eatsdeliveryapp.service.SolicitudEliminacionServiceImpl;

import java.util.ArrayList;
import java.util.List;

//Clase pública que administra la pantalla principal del encargado
public class PantallaEncargado extends AppCompatActivity {

    //Atributos del empleado, para guardar temporalmente su informacion
    private int idEncargado;
    private Administrador encargado;
    private Restaurante restaurante;

    //variables necesarias, para conectarse a la BD y para cargar los elementos en pantalla
    private ListView lista;
    private DBProperties dbProperties;
    private ListaAdapter adapter;
    private ListaAdapterPlatos adapterPlatos;
    private ListView listaMenu;
    private List<Producto> productos;


    //Método por deafult, se instacia junto a la clase
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_encargado);

        //Acá se captura el id del gerente
        idEncargado = getIntent().getExtras().getInt("idEncargado");

        //Nos conectamos a la base
        dbProperties = new DBProperties(getBaseContext());

        //Llamamos a los dao, para cargar tanto el encargado como el restaurante
        AdministradorSqliteDAOImpl admin = new AdministradorSqliteDAOImpl(dbProperties);
        this.encargado = admin.RetornarAdmin(idEncargado);

        RestauranteSqliteDAOImpl res = new RestauranteSqliteDAOImpl(dbProperties);
        this.restaurante=res.recuperarRestaurante(encargado.getIdRestaurante());

        //Setiamos el nombre en pantalla
        TextView titulo = (TextView) findViewById(R.id.bienvenidaEncargado);
        titulo.setText("Bienvenido "+encargado.getNombre()+" "+encargado.getPrimerApellido());

        //Nos conectamos a la lista para la info del restaurante
        this.lista = findViewById(R.id.infoRes);
        ArrayList<Restaurante> array = new ArrayList<>();
        array.add(restaurante);

        //Cargamos este elemento
        ArrayList<String> elemento = new ArrayList<>();
        elemento.add(res.infoRestauranteUnico(restaurante.getId()));

        //Le coectamos un adapter para iniciar las imagenes
        adapter= new ListaAdapter(this, array, elemento);
        lista.setAdapter(adapter);

        //Setiamos el evento click sobre esta lista, para modificar los datos
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                ModificarRestaurante();
            }
        });

        //Cargamos la lista con los platos
        this.listaMenu = findViewById(R.id.listaMenu);

        //Cargamos los platos desde la base
        ProductoSqliteDAOImpl productoSqliteDAO= new ProductoSqliteDAOImpl(dbProperties);
        productos=productoSqliteDAO.findAllPorRestaurante(this.restaurante.getId());

        //Hacemos un adaptador y lo instanciamos en la lista
        adapterPlatos = new ListaAdapterPlatos(this, productos);
        listaMenu.setAdapter(adapterPlatos);

        //Configurar el evento click, para modificar
        listaMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                ModificarProducto(productos.get(i));
            }
        });

        //El envento presionado para borrar los productos
        listaMenu.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View v,
                                           int index, long arg3) {
                // TODO Auto-generated method stub
                EliminarProducto(productos.get(index), index);
                return true;
            }
        });

    }

    //Método para activar las solicitudes de eliminacion de restaurantes, se carga por un boton
    public void SolicitarEliminacionRestaurante(View view){

        //Instanciamos el objeto
        SolicitudEliminacion sol = new SolicitudEliminacion();
        sol.setIdEncargado(this.idEncargado);
        sol.setIdRestaurante(this.restaurante.getId());
        sol.setActivo(1);

        //Configuramos las clases necesarias
        SolicitudEliminacionDAO s = new SolicitudEliminacionDAOImpl(dbProperties);
        SolicitudEliminacionService ss= new SolicitudEliminacionServiceImpl(s);

        //Conectamos la base
        SQLiteDatabase dbConnection = dbProperties.getWritableConnection();

        //Insertamos la solicitud
        try {
            dbConnection.beginTransaction();

            long Result = ss.addnew(sol,dbConnection);

            //Verifica que todo saliera bien
            if(Result != -1) {
                dbConnection.setTransactionSuccessful();
                Toast.makeText(PantallaEncargado.this,"SOLICITUD DE ELIMINACIÓN INGRESADA", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(PantallaEncargado.this,"YA EXISTE UNA SOLICITUD DE ELIMINACIÓN", Toast.LENGTH_LONG).show();
            }

        } finally {
            dbConnection.endTransaction();
        }
    }

    //Método para modificar los restaurantes, se pueden modificar todos los datos
    private void ModificarRestaurante(){

        //Despliega un dialogo donde le pregunta al usuario si quiere eliminar el restaurante
        Dialogs.showConfirmDialog(this, "¿Desea Modificar el Restaurante?", () -> {

            // Al presionar la opción Si
            //Instancia la nueva ventana
            Intent SiguienteActivity= new Intent(this, PantallaModificarRestaurante.class);
            SiguienteActivity.putExtra("idEncargado", this.encargado.getId());
            startActivity(SiguienteActivity);

        }, () -> {
            // Al presionar la opción No
        });
    }

    //Metodo para instanciar la clase de añadir platos
    public void AddPlato(View view){

        Intent intent = new Intent(this, PantallaInsertarPlato.class);
        intent.putExtra("idEncargado", this.encargado.getId());
        startActivity(intent);

    }


    //Método para eliminar los productos
    //Recibe el producto como tal y su id
    private void EliminarProducto(Producto producto, int i){

        //Despliega un dialogo donde le pregunta al usuario si quiere eliminar el restaurante
        Dialogs.showConfirmDialog(this, "¿Seguro que desea eliminar este plato?", () -> {

            // Al presionar la opción Si
            ProductoSqliteDAOImpl pro = new ProductoSqliteDAOImpl(dbProperties);
            pro.EliminarProducto(producto.getId());

            //Lo eliminamos
            Toast.makeText(getApplicationContext(), "PRODUCTO ELIMINADO EXITOSAMENTE", Toast.LENGTH_SHORT).show();

            //Actualizamos la lista en pantalla
            this.productos.remove(i);
            this.adapterPlatos.notifyDataSetChanged();

        }, () -> {
            // Al presionar la opción No
        });
    }


    //Método para modificar los productos
    private void ModificarProducto(Producto producto){

        //Despliega un dialogo donde le pregunta al usuario si quiere eliminar el restaurante
        Dialogs.showConfirmDialog(this, "¿Quiere editar este plato?", () -> {

            //En caso sí
            //Carga una nueva ventana para esta funcionalidad
            Intent intent = new Intent(this, PantallaModificarPlato.class);
            intent.putExtra("idEncargado", encargado.getId());
            intent.putExtra("idProducto", producto.getId());
            startActivity(intent);

        }, () -> {
            // Al presionar la opción No
        });

    }

}