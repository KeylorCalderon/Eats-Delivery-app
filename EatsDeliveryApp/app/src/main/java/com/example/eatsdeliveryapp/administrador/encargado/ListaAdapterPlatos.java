package com.example.eatsdeliveryapp.administrador.encargado;

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


//Clase listAdapte, heredada de base adapter, para desplegar exitosamente las listview de platos
public class ListaAdapterPlatos extends BaseAdapter {

    // atributos de la clase
    Context context;
    List<Producto> productos;
    LayoutInflater inflater;


    //Constructor de la clase
    public ListaAdapterPlatos(Context context, List<Producto> restaurantes) {
        this.context = context;
        this.productos = restaurantes;
    }


    //Getters and setters, por default
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

    //Método que se encarga de desplegar los elementos en la lista como tal
    //Retorna el view de la misma, utiliza el context del constructor
    public View getView(int position, View convertView, ViewGroup parent) {

        // Declara Variables

        TextView detalles;
        ImageView imagen;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.lista_formato, parent, false);

        // Locate the TextViews in listview_item.xml
        detalles = (TextView) itemView.findViewById(R.id.tituloLista);
        imagen = (ImageView) itemView.findViewById(R.id.iconLista);

        detalles.setText(productos.get(position).getNombre()+" \nIngredientes:\n"+productos.get(position).getDescripcion());
        imagen.setImageBitmap(productos.get(position).getFoto());

        return itemView;
    }
}
