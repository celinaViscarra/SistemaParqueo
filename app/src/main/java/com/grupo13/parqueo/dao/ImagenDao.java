package com.grupo13.parqueo.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.grupo13.parqueo.modelo.Imagen;

import java.util.List;

@Dao
public interface ImagenDao {
    @Query("SELECT * FROM Imagen")
    List<Imagen> obtenerImagenes();

    @Query("SELECT * FROM Imagen WHERE id_ubicacion=:id_ubicacion AND es_galeria = 1")
    List<Imagen> obtenerImagenesPorUbicacion(int id_ubicacion);

    @Query("SELECT * FROM Imagen WHERE id_imagen=:id_imagen")
    Imagen consultarImagen(int id_imagen);

    @Query("SELECT * FROM Imagen WHERE id_ubicacion=:id_ubicacion AND es_galeria = 0")
    Imagen consultarImagenPorUbicacion(int id_ubicacion);

    @Insert
    long insertarImagen(Imagen imagen);

    @Insert
    void insertarImagenes(List<Imagen> imagenes);

    @Update
    int actualizarImagen(Imagen id_imagen);

    @Delete
    int eliminarImagen(Imagen id_imagen);

    @Query("DELETE FROM Imagen")
    void limpiarTabla();
}
