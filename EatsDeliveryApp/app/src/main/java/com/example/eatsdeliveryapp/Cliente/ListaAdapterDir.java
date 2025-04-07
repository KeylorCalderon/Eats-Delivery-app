package com.example.eatsdeliveryapp.Cliente;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eatsdeliveryapp.R;
import com.example.eatsdeliveryapp.model.Direccion;

import java.util.ArrayList;

public class ListaAdapterDir extends BaseAdapter {

    Context context;
    ArrayList<Direccion> direcciones;
    LayoutInflater inflater;

    public ListaAdapterDir(Context context, ArrayList<Direccion> direcciones) {
        this.context = context;
        this.direcciones = direcciones;
    }

    @Override
    public int getCount() {
        return direcciones.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        // Declara Variables
        TextView detalles;
        ImageView imagen;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.lista_formato, parent, false);

        // Locate the TextViews in listview_item.xml
        detalles = (TextView) itemView.findViewById(R.id.tituloLista);
        imagen = (ImageView) itemView.findViewById(R.id.iconLista);

        //estructura los datos y los pinta en pantalla
        detalles.setText(direcciones.get(i).getProvincia()+", "+direcciones.get(i).getCanton() +
                        ", "+direcciones.get(i).getDistrito()+", "+direcciones.get(i).getDireccionExacta());

        Bitmap icon = BitmapFactory.decodeResource(this.context.getResources(),
                R.drawable.mapa);

        imagen.setImageBitmap(icon);

        return itemView;
    }
}
