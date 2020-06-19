package com.grupo13.parqueo.modelo;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

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
    @SerializedName("id_calificacion")
    public int id_calificacion;

    @NonNull
    @SerializedName("id_ubicacion")
    public int id_ubicacion;
    @NonNull
    @SerializedName("usuario")
    public String usuario;
    @NonNull
    @SerializedName("puntuacion")
    public float puntuacion;

    public Calificacion(){}

    public Calificacion(int id_calificacion, int id_ubicacion, String usuario, float puntuacion){
        this.id_calificacion = id_calificacion;
        this.id_ubicacion = id_ubicacion;
        this.usuario = usuario;
        this.puntuacion = puntuacion;
    }
}
