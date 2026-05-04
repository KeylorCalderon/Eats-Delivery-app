package com.example.eatsdeliveryapp.administrador.encargado;

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
import com.example.eatsdeliveryapp.model.Direccion;
import com.example.eatsdeliveryapp.model.Pedido;

public class AdapterDetallePedido extends BaseAdapter {

    private Context context;
    private Cliente cliente;
    private Direccion direccion;
    private Pedido pedido;
    private LayoutInflater inflater;

    public AdapterDetallePedido(Context context, Cliente cliente, Direccion direccion, Pedido pedido) {
        this.context = context;
        this.cliente = cliente;
        this.direccion = direccion;
        this.pedido = pedido;
    }

    @Override
    public int getCount() {
        return 1;
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

        TextView detalles;
        ImageView imagen;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.lista_formato, parent, false);

        // Locate the TextViews in listview_item.xml
        detalles = (TextView) itemView.findViewById(R.id.tituloLista);
        imagen = (ImageView) itemView.findViewById(R.id.iconLista);

        detalles.setText("Numero: "+pedido.getId()+ ", Fecha: "+pedido.getFecha()+"\n"+
                         "Cliente: "+cliente.getNombre()+" "+ cliente.getPrimerApellido()+"\n"+
                         "Precio: "+pedido.getTotalPagado()+ "\n"+
                         "Dirección: "+direccion.getProvincia()+", "+direccion.getCanton()+", "+
                         direccion.getDistrito()+", "+direccion.getDireccionExacta());


        Bitmap icon = BitmapFactory.decodeResource(this.context.getResources(),
                R.drawable.pedidos);

        imagen.setImageBitmap(icon);

        return itemView;
    }
}
