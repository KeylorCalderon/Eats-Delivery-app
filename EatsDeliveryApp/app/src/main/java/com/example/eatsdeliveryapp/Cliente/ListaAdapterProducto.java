package com.example.eatsdeliveryapp.Cliente;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eatsdeliveryapp.R;
import com.example.eatsdeliveryapp.model.Producto;

import java.util.ArrayList;
import java.util.List;


//Clase publica para el base dapter de lista de productos
public class ListaAdapterProducto   extends BaseAdapter {


    // Declare Variables
    Context context;
    List<Producto> productos;
    LayoutInflater inflater;
    int idCliente;

    //Constructor de esta clase
    public ListaAdapterProducto(Context context, List<Producto> restaurantes, int id) {
        this.context = context;
        this.productos = restaurantes;
        idCliente=id;
    }


    //Getters and setters
    @Override
    public int getCount() {
        return productos.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    //cargar en si los items en la lista en pantalla
    public View getView(int position, View convertView, ViewGroup parent) {

        // Declara Variables
        TextView detalles;
        ImageView imagen;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.lista_formato, parent, false);

        // Locate the TextViews in listview_item.xml
        detalles = (TextView) itemView.findViewById(R.id.tituloLista);
        imagen = (ImageView) itemView.findViewById(R.id.iconLista);

        //carga los datos estructuradamente
        detalles.setText(productos.get(position).getNombre()+": "+productos.get(position).getDescripcion());
        imagen.setImageBitmap(productos.get(position).getFoto());

        return itemView;
    }


}


