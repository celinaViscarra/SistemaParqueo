package com.grupo13.parqueo.modelo;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import static androidx.room.ForeignKey.CASCADE;

@Entity
        (foreignKeys = {
                @ForeignKey(
                        entity = Ubicacion.class,
                        parentColumns = "id_ubicacion",
                        childColumns = "id_ubicacion",
                        onDelete = CASCADE
                )
        })

public class Parqueo {
    @NonNull
    @PrimaryKey
    public int id_parqueo;
    @NonNull
    public int id_ubicacion;
    @NonNull
    public String nombre_parqueo;
    @NonNull
    public int ocupado;

    public Parqueo(){}

    public Parqueo(int id_parqueo, int id_ubicacion, String nombre_parqueo, int ocupado){
        this.id_parqueo = id_parqueo;
        this.id_ubicacion = id_ubicacion;
        this.nombre_parqueo = nombre_parqueo;
        this.ocupado = ocupado;
    }


}
