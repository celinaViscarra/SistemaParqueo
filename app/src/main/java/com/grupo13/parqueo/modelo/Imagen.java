package com.grupo13.parqueo.modelo;

import java.sql.Date;
import java.util.Calendar;

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
                        entity = Comentario.class,
                        parentColumns = "id_comentario",
                        childColumns = "id_comentario",
                        onDelete = CASCADE
                )
        })
public class Imagen {
    @NonNull
    @PrimaryKey
    public int id_imagen;
    //Estos dos atributos van como nulos porque de cualquiera de estos puede derivarse.
    public int id_ubicacion;
    public int id_comentario;

    @NonNull
    public Calendar fecha_imagen;
    //A partir de ahora usaremos Calendar, para guardar fecha y hora.
    //public Date fecha_imagen;

    //Ingreso un nuevo atributo del nombre del archivo.
    @NonNull
    public String filename;

    public  Imagen(){}

    public Imagen(int id_imagen, int id_ubicacion, int id_comentario, Calendar fecha_imagen, String filename){
        this.id_imagen = id_imagen;
        this.id_ubicacion = id_ubicacion;
        this.id_comentario = id_comentario;
        this.fecha_imagen = fecha_imagen;
    }
}
