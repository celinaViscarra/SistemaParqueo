package com.grupo13.parqueo.modelo;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class Ubicacion {
    @NonNull
    @PrimaryKey
    @SerializedName("id_ubicacion")
    public int id_ubicacion;
    @NonNull
    @SerializedName("nombre_ubicacion")
    public String nombre_ubicacion;
    @NonNull
    @SerializedName("longitud")
    public float longitud;
    @NonNull
    @SerializedName("latitud")
    public float latitud;

    public Ubicacion(){}

    public Ubicacion(int id_ubicacion, String nombre_ubicacion, float longitud, float latitud){
        this.id_ubicacion = id_ubicacion;
        this.nombre_ubicacion = nombre_ubicacion;
        this.longitud = longitud;
        this.latitud = latitud;
    }

}
