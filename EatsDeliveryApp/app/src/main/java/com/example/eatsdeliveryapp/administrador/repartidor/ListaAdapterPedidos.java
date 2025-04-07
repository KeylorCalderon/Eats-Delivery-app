package com.example.eatsdeliveryapp.administrador.repartidor;

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
import com.example.eatsdeliveryapp.model.Cliente;
import com.example.eatsdeliveryapp.model.Pedido;
import com.example.eatsdeliveryapp.model.Producto;

import java.util.List;

public class ListaAdapterPedidos extends BaseAdapter {

    // atributos de la clase
    Context context;
    List<Pedido> pedidos;
    List<Cliente> clientes;
    LayoutInflater inflater;

    public ListaAdapterPedidos(Context context, List<Pedido> pedidos, List<Cliente> clientes) {
        this.context = context;
        this.pedidos = pedidos;
        this.clientes = clientes;
    }

    @Override
    public int getCount() {
        return pedidos.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        // Declara Variables

        TextView detalles;
        ImageView imagen;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.lista_formato, parent, false);

        // Locate the TextViews in listview_item.xml
        detalles = (TextView) itemView.findViewById(R.id.tituloLista);
        imagen = (ImageView) itemView.findViewById(R.id.iconLista);

        detalles.setText("Numero Pedido: "+ Integer.toString(pedidos.get(position).getId())+"\n"+
                         "Solicitado el: "+pedidos.get(position).getFecha()+"\n"+
                         "Por el cliente: "+clientes.get(position).getNombre()+" "+
                          clientes.get(position).getPrimerApellido());

        Bitmap icon = BitmapFactory.decodeResource(this.context.getResources(),
                R.drawable.pedidos);

        imagen.setImageBitmap(icon);

        return itemView;
    }
}
