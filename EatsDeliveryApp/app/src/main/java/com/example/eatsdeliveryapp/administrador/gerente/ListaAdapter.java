package com.example.eatsdeliveryapp.administrador.gerente;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eatsdeliveryapp.R;
import com.example.eatsdeliveryapp.model.Restaurante;

import java.util.ArrayList;
import java.util.List;

//Clase publica para el listadapter de los restaurantes
public class ListaAdapter extends BaseAdapter {


    // Declare Variables
    Context context;
    List<Restaurante> restaurantes;
    ArrayList<String> informacion;
    LayoutInflater inflater;


    //Constructor de la clase
    public ListaAdapter(Context context, List<Restaurante> restaurantes,ArrayList<String> informacion) {
        this.context = context;
        this.restaurantes = restaurantes;
        this.informacion = informacion;
    }

    //Getters and setters
    @Override
    public int getCount() {
        return restaurantes.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    //Funcion principal para cargar la lista de la pantalla, con las imagenes, devuelve el view completo
    public View getView(int position, View convertView, ViewGroup parent) {

        // Declara Variables
        TextView detalles;
        ImageView imagen;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.lista_formato, parent, false);

        // Locate the TextViews in listview_item.xml
        detalles = (TextView) itemView.findViewById(R.id.tituloLista);
        imagen = (ImageView) itemView.findViewById(R.id.iconLista);

        //Las carga como tal
        detalles.setText(informacion.get(position));
        imagen.setImageBitmap(restaurantes.get(position).getFoto());

        return itemView;
    }
}
