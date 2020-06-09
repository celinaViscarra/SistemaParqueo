package com.grupo13.parqueo.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.grupo13.parqueo.modulo.Comentario;

import java.util.List;

@Dao
public interface ComentarioDao {
    @Query("SELECT * FROM Comentario")
    List<Comentario> obtenerComentarios();

    @Query("SELECT * FROM Comentario WHERE id_comentario=:id_comentario")
    Comentario consultarComentario(int id_comentario);

    @Insert
    long insertarComentario(Comentario id_comentario);

    @Update
    int actualizarComentario(Comentario id_comentario);

    @Delete
    int eliminarComentario(Comentario id_comentario);

    @Query("DELETE FROM Comentario")
    void limpiarTabla();
}
