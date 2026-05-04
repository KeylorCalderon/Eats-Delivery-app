package com.example.eatsdeliveryapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.eatsdeliveryapp.dao.AdministradorDAO;
import com.example.eatsdeliveryapp.dao.ClienteDAO;
import com.example.eatsdeliveryapp.dao.CuentaAdministradorDAO;
import com.example.eatsdeliveryapp.dao.CuentaClienteDAO;
import com.example.eatsdeliveryapp.dao.DireccionDAO;
import com.example.eatsdeliveryapp.dao.DireccionXClienteDAO;
import com.example.eatsdeliveryapp.dao.ProductoDAO;
import com.example.eatsdeliveryapp.dao.PuestoDAO;
import com.example.eatsdeliveryapp.dao.RestauranteDAO;
import com.example.eatsdeliveryapp.dao.sqlite.AdministradorSqliteDAOImpl;
import com.example.eatsdeliveryapp.dao.sqlite.ClienteSqliteDAOImpl;
import com.example.eatsdeliveryapp.dao.sqlite.CuentaAdministradorSqliteDAOlmpl;
import com.example.eatsdeliveryapp.dao.sqlite.CuentaClienteSqliteDAOImpl;
import com.example.eatsdeliveryapp.dao.sqlite.DireccionSqliteDAOImpl;
import com.example.eatsdeliveryapp.dao.sqlite.DireccionXClienteSqliteDAOImpl;
import com.example.eatsdeliveryapp.dao.sqlite.ProductoSqliteDAOImpl;
import com.example.eatsdeliveryapp.dao.sqlite.PuestoSqliteDAOImpl;
import com.example.eatsdeliveryapp.dao.sqlite.RestauranteSqliteDAOImpl;
import com.example.eatsdeliveryapp.database.DBProperties;
import com.example.eatsdeliveryapp.model.Administrador;
import com.example.eatsdeliveryapp.model.Cliente;
import com.example.eatsdeliveryapp.model.CuentaAdministrador;
import com.example.eatsdeliveryapp.model.CuentaCliente;
import com.example.eatsdeliveryapp.model.Direccion;
import com.example.eatsdeliveryapp.model.DireccionXCliente;
import com.example.eatsdeliveryapp.model.Producto;
import com.example.eatsdeliveryapp.model.Puesto;
import com.example.eatsdeliveryapp.model.Restaurante;
import com.example.eatsdeliveryapp.service.AdministradorService;
import com.example.eatsdeliveryapp.service.AdministradorServiceImpl;
import com.example.eatsdeliveryapp.service.ClienteService;
import com.example.eatsdeliveryapp.service.ClienteServiceImpl;
import com.example.eatsdeliveryapp.service.CuentaAdministradorService;
import com.example.eatsdeliveryapp.service.CuentaAdministradorServiceImpl;
import com.example.eatsdeliveryapp.service.CuentaClienteService;
import com.example.eatsdeliveryapp.service.CuentaClienteServiceImpl;
import com.example.eatsdeliveryapp.service.DireccionService;
import com.example.eatsdeliveryapp.service.DireccionServiceImpl;
import com.example.eatsdeliveryapp.service.DireccionXClienteService;
import com.example.eatsdeliveryapp.service.DireccionXClienteServiceImpl;
import com.example.eatsdeliveryapp.service.ProductoService;
import com.example.eatsdeliveryapp.service.ProductoServiceImpl;
import com.example.eatsdeliveryapp.service.PuestoService;
import com.example.eatsdeliveryapp.service.PuestoServiceImpl;
import com.example.eatsdeliveryapp.service.RestauranteService;
import com.example.eatsdeliveryapp.service.RestauranteServiceImpl;

//Clase para algunos datos de prueba
public class DatosDePrueba {

    //Variables para las operaciones en la base de datos
    private CuentaClienteDAO cuentaClienteDAO;
    private ClienteDAO clienteDAO;
    private DireccionDAO direccionDAO;
    private DireccionXClienteDAO direccionXClienteDAO;
    private RestauranteDAO restauranteDAO;
    private PuestoDAO puestoDAO;
    private CuentaAdministradorDAO cuentaAdministradorDAO;
    private AdministradorDAO administradorDAO;
    private ProductoDAO productoDAO;
    private DBProperties dbProperties;

    //para los servicios
    private CuentaClienteService cuentaClienteService;
    private ClienteService clienteService;
    private DireccionService direccionService;
    private DireccionXClienteService direccionXClienteService;
    private RestauranteService restauranteService;
    private PuestoService puestoService;
    private CuentaAdministradorService cuentaAdministradorService;
    private AdministradorService administradorService;
    private ProductoService productoService;

    private Context context;

    public DatosDePrueba(DBProperties properties, Context context) {

        //propiedades
        this.dbProperties = properties;
        this.context = context;

        // Inicializa DAO
        cuentaClienteDAO = new CuentaClienteSqliteDAOImpl(dbProperties);
        clienteDAO  = new ClienteSqliteDAOImpl(dbProperties);
        direccionDAO  = new DireccionSqliteDAOImpl(dbProperties);
        direccionXClienteDAO  = new DireccionXClienteSqliteDAOImpl(dbProperties);
        restauranteDAO  = new RestauranteSqliteDAOImpl(dbProperties);
        puestoDAO = new PuestoSqliteDAOImpl(dbProperties) ;
        cuentaAdministradorDAO = new CuentaAdministradorSqliteDAOlmpl(dbProperties);
        administradorDAO = new AdministradorSqliteDAOImpl(dbProperties);
        productoDAO = new ProductoSqliteDAOImpl(this.dbProperties);

        // Inicializa Services
        cuentaClienteService = new CuentaClienteServiceImpl(cuentaClienteDAO);
        clienteService = new ClienteServiceImpl(clienteDAO);
        direccionService = new DireccionServiceImpl(direccionDAO);
        direccionXClienteService = new DireccionXClienteServiceImpl(direccionXClienteDAO);
        restauranteService = new RestauranteServiceImpl(restauranteDAO);

        puestoService = new PuestoServiceImpl(puestoDAO);
        cuentaAdministradorService = new CuentaAdministradorServiceImpl(cuentaAdministradorDAO);
        administradorService = new AdministradorServiceImpl(administradorDAO);
        productoService=new ProductoServiceImpl(productoDAO);

    }



    //incializa algunos restaurantes
    public void initializeDatabaseRestaurante() {

        Restaurante restaurante = new Restaurante();
        restaurante.setIdDireccion(1);
        restaurante.setNombre("El amanecer alegre");
        restaurante.setCategoria("Familiar");

        Bitmap icon = BitmapFactory.decodeResource(this.context.getResources(),
                R.drawable.restaurante1);
        restaurante.setFoto(icon);
        restaurante.setActivo(1);

        Restaurante restaurante2 = new Restaurante();
        restaurante2.setIdDireccion(2);
        restaurante2.setNombre("El buen almuerzo");
        restaurante2.setCategoria("Comida Rápida");
        icon = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.restaurante2);
        restaurante2.setFoto(icon);
        restaurante2.setActivo(1);

        Restaurante restaurante3 = new Restaurante();
        restaurante3.setIdDireccion(1);
        restaurante3.setNombre("Un gustito mañanero");
        restaurante3.setCategoria("Familiar");
        icon = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.restaurante3);
        restaurante3.setFoto(icon);
        restaurante3.setActivo(1);

        Restaurante restaurante4 = new Restaurante();
        restaurante4.setIdDireccion(1);
        restaurante4.setNombre("Un rincón feliz");
        restaurante4.setCategoria("Familiar");
        icon = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.restaurante4);
        restaurante4.setFoto(icon);
        restaurante4.setActivo(1);

        //Se insertan
        SQLiteDatabase dbConnection = dbProperties.getWritableConnection();
        try {
            dbConnection.beginTransaction();

            long direccionResult = restauranteService.addNew(restaurante, dbConnection);
            long direccionResult2 = restauranteService.addNew(restaurante2, dbConnection);
            long direccionResult3 = restauranteService.addNew(restaurante3, dbConnection);
            long direccionResult4 = restauranteService.addNew(restaurante4, dbConnection);

            if(direccionResult != -1) {
                dbConnection.setTransactionSuccessful();

            }

        } finally {
            dbConnection.endTransaction();
        }

        producto();
    }


    //Inserta algunos platos
    public void producto() {

        Producto p = new Producto();
        p.setIdRestaurante(1);
        p.setNombre("Porción Pollo Frito");

        Bitmap icon = BitmapFactory.decodeResource(this.context.getResources(),
                R.drawable.pollo_frito);
        p.setFoto(icon);
        p.setDescripcion("Muslo de pollo \nAla de pollo \nSalsa Buffalo");
        p.setCantidad(7);
        p.setActivo(1);
        p.setPrecio(2500F);

        Producto p2 = new Producto();
        p2.setIdRestaurante(1);
        p2.setNombre("Hamburguesa con queso");

        Bitmap icon2 = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.hambur);
        p2.setFoto(icon2);
        p2.setDescripcion("Carne de vaca \nTomate \nQueso \nAderezos");
        p2.setCantidad(19);
        p2.setActivo(1);
        p2.setPrecio(3750F);

        Producto p3 = new Producto();
        p3.setIdRestaurante(2);
        p3.setNombre("Sushi artesanal");
        Bitmap icon3 = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.sushi_artesanal);
        p3.setFoto(icon3);
        p3.setDescripcion("Arroz \nPescado \nAceite \nVinagre");
        p3.setCantidad(13);
        p3.setActivo(1);
        p3.setPrecio(8950F);

        SQLiteDatabase dbConnection = dbProperties.getWritableConnection();
        try {
            dbConnection.beginTransaction();

            long direccionResult = productoService.addNew(p,dbConnection);
            long direccionResult2 = productoService.addNew(p2,dbConnection);
            long direccionResult3 = productoService.addNew(p3,dbConnection);

            //Toast.makeText(MainActivity.this,"Direccion: "+direccionResult, Toast.LENGTH_LONG).show();
            if(direccionResult != -1) {
                dbConnection.setTransactionSuccessful();

            }

        } finally {
            dbConnection.endTransaction();
        }
    }

    //inserta algunas direcciones
    public void initializeDatabaseDireccion() {
        Direccion direccion = new Direccion();
        direccion.setCanton("Dulce Nombre");
        direccion.setDistrito("Caballo Blanco");
        direccion.setProvincia("Cartago");
        direccion.setDireccionExacta("50 este del Mac Donalds y 200 norte");

        Direccion direccion2 = new Direccion();
        direccion2.setCanton("Vázquez de Coronado");
        direccion2.setDistrito("San Isidro");
        direccion2.setProvincia("San José");
        direccion2.setDireccionExacta("50 este de la granja HuevosDorados, 200 sur, a mano izq de la iglesia");
        //Toast.makeText(MainActivity.this,"Direccion intento", Toast.LENGTH_LONG).show();
        SQLiteDatabase dbConnection = dbProperties.getWritableConnection();
        try {
            dbConnection.beginTransaction();

            long direccionResult = direccionService.addNew(direccion, dbConnection);
            long direccionResult2 = direccionService.addNew(direccion2, dbConnection);
            //Toast.makeText(MainActivity.this,"Direccion: "+direccionResult, Toast.LENGTH_LONG).show();
            if(direccionResult != -1) {
                dbConnection.setTransactionSuccessful();

            }

        } finally {
            dbConnection.endTransaction();
        }
    }

    //inicia algunas direcciones para el cliente
    public void initializeDatabaseDireccionXCliente() {
        DireccionXCliente direccionXCliente = new DireccionXCliente();
        direccionXCliente.setIdCliente(1);
        direccionXCliente.setIdDireccion(1);
        direccionXCliente.setActivo("1");

        SQLiteDatabase dbConnection = dbProperties.getWritableConnection();
        try {
            dbConnection.beginTransaction();

            long direccionResult = direccionXClienteService.addNew(direccionXCliente, dbConnection);
            direccionXCliente.setIdDireccion(2);
            long direccionResult2 = direccionXClienteService.addNew(direccionXCliente, dbConnection);
            //Toast.makeText(MainActivity.this,"Direccion: "+direccionResult, Toast.LENGTH_LONG).show();
            if(direccionResult != -1) {
                dbConnection.setTransactionSuccessful();

            }

        } finally {
            dbConnection.endTransaction();
        }
    }

    private void showAllClientes() {
        for(Cliente cliente : clienteService.getAll()) {
            Log.d("MYAPP", cliente.getNombre());
        }
    }

    public void initializeDatabase() {
        CuentaCliente cuenta = new CuentaCliente();
        cuenta.setNombreUsuario("MrMike");
        //cuenta.setContrasena("AirForceOne17");
        cuenta.setContrasena("1234");

        Cliente cliente = new Cliente();
        cliente.setNombreUsuario(cuenta.getNombreUsuario());
        cliente.setNombre("Mike");
        cliente.setPrimerApellido("Wazowski");
        cliente.setSegundoApellido("Rodriguez");
        cliente.setCorreo("jhonnypv14@gmail.com");

        SQLiteDatabase dbConnection = dbProperties.getWritableConnection();
        try {
            dbConnection.beginTransaction();

            long cuentaResult = cuentaClienteService.addNew(cuenta, dbConnection);
            long clienteResult = clienteService.addNew(cliente, dbConnection);

            if(cuentaResult != -1 && clienteResult != -1) {
                dbConnection.setTransactionSuccessful();
            }

        } finally {
            dbConnection.endTransaction();
        }
    }


    //Inserta el catalogo de puestos
    public void insertarPuestos(){

        String [] names = {"Gerente", "Encargado", "Repartidor", "Administrador Total"};

        for (int i =0; i<4; i++){

            SQLiteDatabase dbConnection = dbProperties.getWritableConnection();
            try {
                dbConnection.beginTransaction();

                Puesto puesto = new Puesto();
                puesto.setNombre(names[i]);

                long puestoResult = puestoService.addnew(puesto, dbConnection);


                if(puestoResult != -1) {
                    dbConnection.setTransactionSuccessful();
                }

            } finally {
                dbConnection.endTransaction();
            }
        }
    }


    //inserta el administrador por default
    public void adminDeafult(){


        SQLiteDatabase dbConnection = dbProperties.getWritableConnection();
        try {
            dbConnection.beginTransaction();

            CuentaAdministrador cuenta= new CuentaAdministrador();
            cuenta.setIdLaboral("administrador");
            cuenta.setContrasenna("123");

            Administrador admin = new Administrador();
            admin.setIdRestaurante(1);
            admin.setIdLaboral("administrador");
            admin.setIdPuesto(4);
            admin.setCedula(303330333);
            admin.setNombre("Juan");
            admin.setPrimerApellido("Venegas");
            admin.setSegundoApellido("Salazar");

            long cuentaResult = cuentaAdministradorService.addNew(cuenta, dbConnection);
            long adminResult = administradorService.addNew(admin, dbConnection);

            if(cuentaResult != -1 && adminResult != -1) {
                dbConnection.setTransactionSuccessful();
            }
        } finally {
            dbConnection.endTransaction();
        }
    }
}
