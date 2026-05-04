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

import java.util.List;

public class ListaAdapterCarrito    extends BaseAdapter {


    // Declare Variables
    Context context;
    List<Producto> productos;
    LayoutInflater inflater;
    int idCliente;

    public ListaAdapterCarrito(Context context, List<Producto> restaurantes, int id) {
        this.context = context;
        this.productos = restaurantes;
        idCliente=id;
    }

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

    public View getView(int position, View convertView, ViewGroup parent) {

        // Declara Variables

        TextView detalles;
        ImageView imagen;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.lista_formato, parent, false);

        // Locate the TextViews in listview_item.xml
        detalles = (TextView) itemView.findViewById(R.id.tituloLista);
        imagen = (ImageView) itemView.findViewById(R.id.iconLista);

        detalles.setText(productos.get(position).getNombre()+System.getProperty ("line.separator")+
                "Precio unitario: "+Float.toString(productos.get(position).getPrecio())+System.getProperty ("line.separator")+
                "Coste de envío: 750.00"+System.getProperty ("line.separator")+
                "Cantidad: "+productos.get(position).getCantidad()+System.getProperty ("line.separator")+
                "Total a pagar: "+(productos.get(position).getCantidad()*productos.get(position).getPrecio()+750));
        imagen.setImageBitmap(productos.get(position).getFoto());

        return itemView;
    }


}


