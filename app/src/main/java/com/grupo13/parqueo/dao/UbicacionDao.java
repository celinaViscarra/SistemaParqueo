package com.grupo13.parqueo.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.grupo13.parqueo.modelo.Ubicacion;

import java.util.List;

@Dao
public interface UbicacionDao {
    @Query("SELECT * FROM Ubicacion")
    List<Ubicacion> obtenerUbicaciones();

    @Query("SELECT * FROM Ubicacion WHERE id_ubicacion=:id_ubicacion")
    Ubicacion consultarUbicacion(int id_ubicacion);

    @Insert
    long insertarUbicacion(Ubicacion id_ubicacion);

    @Update
    int actualizarUbicacion(Ubicacion id_ubicacion);

    @Delete
    int eliminarUbicacion(Ubicacion id_ubicacion);

    @Query("DELETE FROM Ubicacion")
    void limpiarTabla();
}
