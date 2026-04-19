package com.grupo3.saloncomunal.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.grupo3.saloncomunal.interfaces.IUsuarioRepository;
import com.grupo3.saloncomunal.models.Usuario;

public class UsuarioRepository extends BaseRepository implements IUsuarioRepository {

    public UsuarioRepository(SQLiteDatabase database) {
        super(database);
    }

    @Override
    public void guardarUsuario(Usuario usuario) {
        if (!validarCorreo(usuario.getCorreo()) || !validarNombre(usuario.getNombre())) {
            return;
        }
        ContentValues values = new ContentValues();
        values.put("nombre", usuario.getNombre());
        values.put("correo", usuario.getCorreo());
        values.put("contrasena_encriptada", usuario.getContrasenaEncriptada());
        database.insert("usuarios", null, values);
    }

    @Override
    public Usuario obtenerUsuario(String correo) {
        Cursor cursor = database.query("usuarios", null, "correo = ?", new String[]{correo}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Usuario usuario = new Usuario();
            usuario.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            usuario.setNombre(cursor.getString(cursor.getColumnIndexOrThrow("nombre")));
            usuario.setCorreo(cursor.getString(cursor.getColumnIndexOrThrow("correo")));
            usuario.setContrasenaEncriptada(cursor.getString(cursor.getColumnIndexOrThrow("contrasena_encriptada")));
            cursor.close();
            return usuario;
        }
        return null;
    }

    @Override
    public boolean existeUsuario(String correo) {
        Cursor cursor = database.query("usuarios", null, "correo = ?", new String[]{correo}, null, null, null);
        boolean existe = (cursor != null && cursor.getCount() > 0);
        if (cursor != null) cursor.close();
        return existe;
    }

    @Override
    public void eliminarUsuario(String correo) {
        database.delete("usuarios", "correo = ?", new String[]{correo});
    }
}
