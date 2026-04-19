package com.grupo3.saloncomunal.data;

import android.database.sqlite.SQLiteDatabase;

public class BaseRepository {
    protected SQLiteDatabase database;

    public BaseRepository(SQLiteDatabase database) {
        this.database = database;
    }

    protected boolean validarCorreo(String correo) {
        return correo != null && !correo.isEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches();
    }

    protected boolean validarNombre(String nombre) {
        return nombre != null && !nombre.isEmpty();
    }
}
