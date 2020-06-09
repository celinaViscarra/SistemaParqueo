package com.grupo13.parqueo.modulo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity
public class Ubicacion {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    public int id_ubicacion;
    @NonNull
    public String nom_parqueo;
    @NonNull
    public float longitud;
    @NonNull
    public float latitud;

    public Ubicacion(){}

    public Ubicacion(int id_ubicacion, String nom_parqueo, float longitud, float latitud){
        this.id_ubicacion = id_ubicacion;
        this.nom_parqueo = nom_parqueo;
        this.longitud = longitud;
        this.latitud = latitud;
    }

}
