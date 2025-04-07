package com.example.eatsdeliveryapp.model;

//Clase publica de productoXmenu
public class ProductoXMenu {

    //atributos de est clase
    private int IdProducto;
    private int IdMenu;
    private int CantidadStock;

    //Constructor
    public ProductoXMenu(int idProducto, int idMenu, int cantidadStock) {
        IdProducto = idProducto;
        IdMenu = idMenu;
        CantidadStock = cantidadStock;
    }

    public ProductoXMenu() {
    }

    //Getters and setters
    public int getIdProducto() {
        return IdProducto;
    }

    public void setIdProducto(int idProducto) {
        IdProducto = idProducto;
    }

    public int getIdMenu() {
        return IdMenu;
    }

    public void setIdMenu(int idMenu) {
        IdMenu = idMenu;
    }

    public int getCantidadStock() {
        return CantidadStock;
    }

    public void setCantidadStock(int cantidadStock) {
        CantidadStock = cantidadStock;
    }
}
