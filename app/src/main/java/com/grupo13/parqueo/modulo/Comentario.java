package com.grupo13.parqueo.modulo;

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

public class Comentario {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    public int id_comentario;
    @NonNull
    public int id_ubicacion;
    @NonNull
    public String usuario;
    @NonNull
    public String texto;

    public Comentario(){}

    public Comentario(int id_comentario, int id_ubicacion, String usuario, String texto){
        this.id_comentario = id_comentario;
        this.id_ubicacion = id_ubicacion;
        this.usuario = usuario;
        this.texto = texto;
    }
}
