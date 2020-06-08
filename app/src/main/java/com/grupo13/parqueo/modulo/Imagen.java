package com.grupo13.parqueo.modulo;

import java.sql.Date;
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
    @PrimaryKey(autoGenerate = true)
    public int id_imagen;
    @NonNull
    public int id_ubicacion;
    @NonNull
    public int id_comentario;
    @NonNull
    public Date fecha_imagen;

    public  Imagen(){}

    public Imagen(int id_imagen, int id_ubicacion, int id_comentario, Date fecha_imagen){
        this.id_imagen = id_imagen;
        this.id_ubicacion = id_ubicacion;
        this.id_comentario = id_comentario;
        this.fecha_imagen = fecha_imagen;
    }
}
