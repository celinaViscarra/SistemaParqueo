package com.grupo13.parqueo.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.grupo13.parqueo.modelo.Comentario;

import java.util.List;

@Dao
public interface ComentarioDao {
    @Query("SELECT * FROM Comentario")
    List<Comentario> obtenerComentarios();

    @Query("SELECT * FROM Comentario WHERE id_comentario=:id_comentario")
    Comentario consultarComentario(int id_comentario);

    @Query("SELECT * FROM Comentario WHERE id_ubicacion=:id_ubicacion")
    List<Comentario> obtenerComentariosPorUbicacion(int id_ubicacion);

    @Insert
    long insertarComentario(Comentario comentario);

    @Insert
    void insertarComentarios(List<Comentario> comentarios);

    @Update
    int actualizarComentario(Comentario id_comentario);

    @Delete
    int eliminarComentario(Comentario id_comentario);

    @Query("DELETE FROM Comentario")
    void limpiarTabla();
}
