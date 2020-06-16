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
                ),
                @ForeignKey(
                        entity = Usuario.class,
                        parentColumns = "usuario",
                        childColumns = "usuario",
                        onDelete = CASCADE
                        )
        })

public class Calificacion {
    @NonNull
    //Esta vez no vamos a dejar que se autogeneren.
    @PrimaryKey
    public int id_calificacion;

    @NonNull
    public int id_ubicacion;
    @NonNull
    public String usuario;
    @NonNull
    public float puntuacion;

    public Calificacion(){}

    public Calificacion(int id_calificacion, int id_ubicacion, String usuario, float puntuacion){
        this.id_calificacion = id_calificacion;
        this.id_ubicacion = id_ubicacion;
        this.usuario = usuario;
        this.puntuacion = puntuacion;
    }
}
