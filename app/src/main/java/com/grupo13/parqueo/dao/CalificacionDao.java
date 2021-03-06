package com.grupo13.parqueo.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.grupo13.parqueo.modelo.Calificacion;

import java.util.List;

@Dao
public interface CalificacionDao {
    @Query("SELECT * FROM Calificacion")
    List<Calificacion> obtenerCalificaciones();

    @Query("SELECT * FROM Calificacion WHERE id_calificacion=:id_calificacion")
    Calificacion consultarCalificacion(int id_calificacion);

    @Query("SELECT AVG(puntuacion) FROM Calificacion WHERE id_ubicacion=:id_ubicacion")
    float obtenerPromedioPorUbicacion(int id_ubicacion);
    @Insert
    long insertarCalificacion(Calificacion calificacion);

    @Insert
    void insertarCalificaciones(List<Calificacion> calificaciones);

    @Update
    int actualizarCalificacion(Calificacion id_parqueo);

    @Delete
    int eliminarCalificacion(Calificacion id_parqueo);

    @Query("DELETE FROM Calificacion")
    void limpiarTabla();
}
