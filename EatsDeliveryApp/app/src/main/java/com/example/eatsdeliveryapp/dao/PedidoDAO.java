package com.example.eatsdeliveryapp.dao;

import com.example.eatsdeliveryapp.model.Pedido;

import java.util.List;

//interfaz publica para el pedido
public interface PedidoDAO extends GenericDAO<Pedido, Integer>{

    public List<Pedido> findAllCopy();
    public List<Pedido> findAllActivos(int idCliente);
    public List<Pedido> findAllFinalizados(int idCliente);
    public Pedido getPedido(int id);
    public void ModEstado(int id, String estado);
    public void ModTotalPagado(int id, float nuevoTotal);

}
