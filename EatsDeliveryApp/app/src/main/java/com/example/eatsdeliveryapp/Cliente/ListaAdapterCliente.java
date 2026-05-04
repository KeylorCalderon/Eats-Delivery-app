package com.example.eatsdeliveryapp.Cliente;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eatsdeliveryapp.R;
import com.example.eatsdeliveryapp.model.Restaurante;

import java.util.ArrayList;
import java.util.List;

//clase publica para el base adadpter de cliente
public class ListaAdapterCliente  extends BaseAdapter {


    // Declare Variables
    Context context;
    List<Restaurante> restaurantes;
    ArrayList<String> informacion;
    LayoutInflater inflater;
    int idCliente;

    //Constructor de la clase
    public ListaAdapterCliente(Context context, List<Restaurante> restaurantes, ArrayList<String> informacion, int id) {
        this.context = context;
        this.restaurantes = restaurantes;
        this.informacion = informacion;
        idCliente=id;
    }

    //getters and setters
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


    //metodo importante para printar los datos en la lista
    public View getView(int position, View convertView, ViewGroup parent) {

        // Declara Variables
        TextView detalles;
        ImageView imagen;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.lista_formato, parent, false);

        // Locate the TextViews in listview_item.xml
        detalles = (TextView) itemView.findViewById(R.id.tituloLista);
        imagen = (ImageView) itemView.findViewById(R.id.iconLista);

        //estructura los datos y los pinta en pantalla
        detalles.setText(informacion.get(position));
        imagen.setImageBitmap(restaurantes.get(position).getFoto());

        return itemView;
    }


}

