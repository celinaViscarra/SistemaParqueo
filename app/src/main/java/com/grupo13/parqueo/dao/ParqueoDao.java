package com.grupo13.parqueo.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.grupo13.parqueo.modelo.Parqueo;

import java.util.List;

@Dao
public interface ParqueoDao {
    @Query("SELECT * FROM Parqueo")
    List<Parqueo> obtenerParqueos();

    @Query("SELECT * FROM Parqueo WHERE id_parqueo=:id_parqueo")
    Parqueo consultarParqueo(int id_parqueo);

    @Query("SELECT * FROM Parqueo WHERE id_ubicacion=:id_ubicacion")
    List<Parqueo> obtenerParqueosPorUbicacion(int id_ubicacion);

    @Insert
    long insertarParqueo(Parqueo parqueo);

    @Insert
    void insertarParqueos(List<Parqueo> parqueos);

    @Update
    int actualizarParqueo(Parqueo id_parqueo);

    @Delete
    int eliminarParqueo(Parqueo id_parqueo);

    @Query("DELETE FROM Parqueo")
    void limpiarTabla();
}
