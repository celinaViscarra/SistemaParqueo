package com.grupo13.parqueo;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.grupo13.parqueo.dao.CalificacionDao;
import com.grupo13.parqueo.dao.ComentarioDao;
import com.grupo13.parqueo.dao.ImagenDao;
import com.grupo13.parqueo.dao.ParqueoDao;
import com.grupo13.parqueo.dao.UbicacionDao;
import com.grupo13.parqueo.dao.UsuarioDao;
import com.grupo13.parqueo.modelo.Calificacion;
import com.grupo13.parqueo.modelo.Comentario;
import com.grupo13.parqueo.modelo.ConversorFecha;
import com.grupo13.parqueo.modelo.Imagen;
import com.grupo13.parqueo.modelo.Parqueo;
import com.grupo13.parqueo.modelo.Ubicacion;
import com.grupo13.parqueo.modelo.Usuario;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Database(entities = {
        Calificacion.class,
        Comentario.class,
        Imagen.class,
        Parqueo.class,
        Ubicacion.class,
        Usuario.class
},exportSchema = false, version = 1)
@TypeConverters({ConversorFecha.class})
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
    public void vaciarBD(){
        Class controlBD = this.getClass();
        //Obtenemos la lista de todos los metodos declarados en esta clase
        Method[] metodos = controlBD.getDeclaredMethods();
        for(Method metodo: metodos){
            //Si el metodo contiene la palabra Dao
            if(metodo.toString().contains("Dao")){
                try {
                    //Devolver el dao en una variable
                    Object dao = metodo.invoke(this);
                    //Obtener la clase del dao
                    Class claseDao = dao.getClass();
                    //Buscando el metodo limpiarTabla
                    Method metodoLimpiarTabla = claseDao.getMethod("limpiarTabla",null);
                    //Ejecutar el metodo limpiar tabla
                    metodoLimpiarTabla.invoke(dao);

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public abstract CalificacionDao calificacionDao();
    public abstract ComentarioDao comentarioDao();
    public abstract ImagenDao imagenDao();
    public abstract UbicacionDao ubicacionDao();
    public abstract UsuarioDao usuarioDao();
    public abstract ParqueoDao parqueoDao();
}
