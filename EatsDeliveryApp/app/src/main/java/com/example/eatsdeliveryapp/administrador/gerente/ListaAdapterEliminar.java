package com.example.eatsdeliveryapp.administrador.gerente;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eatsdeliveryapp.R;
import com.example.eatsdeliveryapp.model.Restaurante;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;


//Clase publica para las listas de eliminacion
public class ListaAdapterEliminar extends BaseAdapter {


    // Declare Variables
    private Context context;
    List<byte[]> imagenes;
    ArrayList<String> informacion;
    LayoutInflater inflater;

    //Constructor de la clase
    public ListaAdapterEliminar(Context context, List<byte[]> imagenes, ArrayList<String> informacion) {
        this.context = context;
        this.imagenes = imagenes;
        this.informacion = informacion;
    }


    //Getters and setters
    @Override
    public int getCount() {
        return informacion.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    //Metodo principal para cargar la lista, correctamente
    public View getView(int position, View convertView, ViewGroup parent) {

        // Declara Variables
        TextView detalles;
        ImageView imagen;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.lista_formato, parent, false);

        // Locate the TextViews in listview_item.xml
        detalles = (TextView) itemView.findViewById(R.id.tituloLista);
        imagen = (ImageView) itemView.findViewById(R.id.iconLista);

        //le ajusta cada valor a la lista view
        detalles.setText(informacion.get(position));
        imagen.setImageBitmap(BitmapFactory.decodeStream(new ByteArrayInputStream(imagenes.get(position))));

        return itemView;
    }
}
