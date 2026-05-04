package com.example.eatsdeliveryapp.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.eatsdeliveryapp.sqlite.contracts.AdministradorContract;
import com.example.eatsdeliveryapp.sqlite.contracts.CantonContract;
import com.example.eatsdeliveryapp.sqlite.contracts.CarritoContract;
import com.example.eatsdeliveryapp.sqlite.contracts.CategoriaContract;
import com.example.eatsdeliveryapp.sqlite.contracts.ClienteContract;
import com.example.eatsdeliveryapp.sqlite.contracts.CuentaAdministradorContract;
import com.example.eatsdeliveryapp.sqlite.contracts.CuentaClienteContract;
import com.example.eatsdeliveryapp.sqlite.contracts.DireccionContract;
import com.example.eatsdeliveryapp.sqlite.contracts.DireccionXClienteContract;
import com.example.eatsdeliveryapp.sqlite.contracts.DistritoContract;
import com.example.eatsdeliveryapp.sqlite.contracts.EntregaContract;
import com.example.eatsdeliveryapp.sqlite.contracts.EstadoPedidoContract;
import com.example.eatsdeliveryapp.sqlite.contracts.ItemsCarritoContract;
import com.example.eatsdeliveryapp.sqlite.contracts.ItemsPedidoContract;
import com.example.eatsdeliveryapp.sqlite.contracts.MenuContract;
import com.example.eatsdeliveryapp.sqlite.contracts.PedidoContract;
import com.example.eatsdeliveryapp.sqlite.contracts.ProductoContract;
import com.example.eatsdeliveryapp.sqlite.contracts.ProductoXMenuContract;
import com.example.eatsdeliveryapp.sqlite.contracts.ProvinciaContract;
import com.example.eatsdeliveryapp.sqlite.contracts.PuestoContract;
import com.example.eatsdeliveryapp.sqlite.contracts.RestauranteContract;
import com.example.eatsdeliveryapp.sqlite.contracts.RestauranteEliminadoContract;
import com.example.eatsdeliveryapp.sqlite.contracts.RestauranteXCategoriaContract;
import com.example.eatsdeliveryapp.sqlite.contracts.SolicitudEliminacionContract;
import com.example.eatsdeliveryapp.sqlite.contracts.TarjetaContract;
import com.example.eatsdeliveryapp.sqlite.contracts.TelefonoContract;


//clase publica para los eats delivery options
public class EatsDeliveryDbHelper extends SQLiteOpenHelper {

    private final Context context;

    // Instruccion SQL para crear la tabla
    private static final String[] SQL_CREATE_ENTRIES = new String[] {
            CuentaClienteContract.SQL_CREATE_ENTRIES,
            ClienteContract.SQL_CREATE_ENTRIES,
            TarjetaContract.SQL_CREATE_ENTRIES,
            ProvinciaContract.SQL_CREATE_ENTRIES,
            CantonContract.SQL_CREATE_ENTRIES,
            DistritoContract.SQL_CREATE_ENTRIES,
            DireccionContract.SQL_CREATE_ENTRIES,
            DireccionXClienteContract.SQL_CREATE_ENTRIES,
            CategoriaContract.SQL_CREATE_ENTRIES,
            RestauranteContract.SQL_CREATE_ENTRIES,
            RestauranteXCategoriaContract.SQL_CREATE_ENTRIES,
            CuentaAdministradorContract.SQL_CREATE_ENTRIES,
            PuestoContract.SQL_CREATE_ENTRIES,
            AdministradorContract.SQL_CREATE_ENTRIES,
            SolicitudEliminacionContract.SQL_CREATE_ENTRIES,
            RestauranteEliminadoContract.SQL_CREATE_ENTRIES,
            MenuContract.SQL_CREATE_ENTRIES,
            ProductoContract.SQL_CREATE_ENTRIES,
            ProductoXMenuContract.SQL_CREATE_ENTRIES,
            EstadoPedidoContract.SQL_CREATE_ENTRIES,
            PedidoContract.SQL_CREATE_ENTRIES,
            ItemsPedidoContract.SQL_CREATE_ENTRIES,
            CarritoContract.SQL_CREATE_ENTRIES,
            ItemsCarritoContract.SQL_CREATE_ENTRIES,
            EntregaContract.SQL_CREATE_ENTRIES
    };

    // Instruccion SQL para eliminar la tabla
    private static final String[] SQL_DELETE_ENTRIES = new String[] {
            CuentaClienteContract.SQL_DELETE_ENTRIES,
            ClienteContract.SQL_DELETE_ENTRIES,
            TarjetaContract.SQL_DELETE_ENTRIES,
            TelefonoContract.SQL_DELETE_ENTRIES,
            ProvinciaContract.SQL_DELETE_ENTRIES,
            CantonContract.SQL_DELETE_ENTRIES,
            DistritoContract.SQL_DELETE_ENTRIES,
            DireccionContract.SQL_DELETE_ENTRIES,
            DireccionXClienteContract.SQL_DELETE_ENTRIES,
            CategoriaContract.SQL_DELETE_ENTRIES,
            RestauranteContract.SQL_DELETE_ENTRIES,
            RestauranteXCategoriaContract.SQL_DELETE_ENTRIES,
            CuentaAdministradorContract.SQL_DELETE_ENTRIES,
            PuestoContract.SQL_DELETE_ENTRIES,
            AdministradorContract.SQL_DELETE_ENTRIES,
            AdministradorContract.SQL_DELETE_ENTRIES,
            SolicitudEliminacionContract.SQL_DELETE_ENTRIES,
            RestauranteEliminadoContract.SQL_DELETE_ENTRIES,
            MenuContract.SQL_DELETE_ENTRIES,
            ProductoContract.SQL_DELETE_ENTRIES,
            ProductoXMenuContract.SQL_DELETE_ENTRIES,
            EstadoPedidoContract.SQL_DELETE_ENTRIES,
            PedidoContract.SQL_DELETE_ENTRIES,
            ItemsPedidoContract.SQL_DELETE_ENTRIES,
            CarritoContract.SQL_DELETE_ENTRIES,
            ItemsCarritoContract.SQL_DELETE_ENTRIES,
            EntregaContract.SQL_DELETE_ENTRIES
    };

    // Si modificamos el esquema de base de datos (contratos), debemos incrementar la versión
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "EatsDelivery.db";

    public EatsDeliveryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public void onCreate(SQLiteDatabase db) {
        for(String sql : SQL_CREATE_ENTRIES) {
//            Log.d("MYAPP", sql);
            db.execSQL(sql);
        }
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        for(String sql : SQL_DELETE_ENTRIES) {
            db.execSQL(sql);
        }
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void dropDatabase() {
        context.deleteDatabase(DATABASE_NAME);
    }
}