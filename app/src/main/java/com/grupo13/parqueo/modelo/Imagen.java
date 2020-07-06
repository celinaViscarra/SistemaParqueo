package com.grupo13.parqueo.modelo;

import java.util.Calendar;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import static androidx.room.ForeignKey.CASCADE;

@Entity
public class Imagen {
    @NonNull
    @PrimaryKey
    @SerializedName("id_imagen")
    public int id_imagen;
    //Estos dos atributos van como nulos porque de cualquiera de estos puede derivarse.
    @SerializedName("id_ubicacion")
    public int id_ubicacion;
    @SerializedName("id_comentario")
    public int id_comentario;

    @NonNull
    @SerializedName("fecha_imagen")
    public Calendar fecha_imagen;
    //A partir de ahora usaremos Calendar, para guardar fecha y hora.
    //public Date fecha_imagen;

    //Ingreso un nuevo atributo del nombre del archivo.
    @NonNull
    @SerializedName("filename")
    public String filename;

    public  Imagen(){}

    public Imagen(int id_imagen, int id_ubicacion, int id_comentario, Calendar fecha_imagen, String filename){
        this.id_imagen = id_imagen;
        this.id_ubicacion = id_ubicacion;
        this.id_comentario = id_comentario;
        this.fecha_imagen = fecha_imagen;
        this.filename = filename;
    }
}
