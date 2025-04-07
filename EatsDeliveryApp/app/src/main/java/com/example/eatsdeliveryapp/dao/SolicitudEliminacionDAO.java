package com.example.eatsdeliveryapp.dao;

import com.example.eatsdeliveryapp.model.SolicitudEliminacion;

import java.util.ArrayList;
import java.util.List;


//interfaz publica para la solicitud de eliminacion
public interface SolicitudEliminacionDAO extends GenericDAO<SolicitudEliminacion,Integer> {

    //funciones implementadas en su clase
    ArrayList<String> consultaSolicitudes(List<SolicitudEliminacion> solicitudes);
    ArrayList<byte[]> consultarImagenes(List<SolicitudEliminacion> solicitudes);
    void EliminarSolicitud(int id);
}
