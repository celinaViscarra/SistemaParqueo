package com.grupo13.parqueo.modelo;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Ubicacion {
    @NonNull
    @PrimaryKey
    public int id_ubicacion;
    @NonNull
    public String nombre_ubicacion;
    @NonNull
    public float longitud;
    @NonNull
    public float latitud;

    public Ubicacion(){}

    public Ubicacion(int id_ubicacion, String nombre_ubicacion, float longitud, float latitud){
        this.id_ubicacion = id_ubicacion;
        this.nombre_ubicacion = nombre_ubicacion;
        this.longitud = longitud;
        this.latitud = latitud;
    }

}
