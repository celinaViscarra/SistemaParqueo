package com.grupo13.parqueo;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.grupo13.parqueo.dao.CalificacionDao;
import com.grupo13.parqueo.dao.ComentarioDao;
import com.grupo13.parqueo.dao.ImagenDao;
import com.grupo13.parqueo.dao.UbicacionDao;
import com.grupo13.parqueo.dao.UsuarioDao;
import com.grupo13.parqueo.modelo.Calificacion;
import com.grupo13.parqueo.modelo.Comentario;
import com.grupo13.parqueo.modelo.Imagen;
import com.grupo13.parqueo.modelo.Parqueo;
import com.grupo13.parqueo.modelo.Ubicacion;
import com.grupo13.parqueo.modelo.Usuario;

@Database(entities = {
        Calificacion.class,
        Comentario.class,
        Imagen.class,
        Parqueo.class,
        Ubicacion.class,
        Usuario.class
},exportSchema = false, version = 1)
public abstract class ControlBD extends RoomDatabase {
    private static final String DB_NAME = "grupo13_parqueo.db";
    private static ControlBD instance;

    public static synchronized ControlBD getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),ControlBD.class,DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract CalificacionDao calificacionDao();
    public abstract ComentarioDao comentarioDao();
    public abstract ImagenDao imagenDao();
    public abstract UbicacionDao ubicacionDao();
    public abstract UsuarioDao usuarioDao();
}
