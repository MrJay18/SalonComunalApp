package com.grupo3.saloncomunal.interfaces;

import com.grupo3.saloncomunal.models.Usuario;

public interface IUsuarioRepository {
    void guardarUsuario(Usuario usuario);
    Usuario obtenerUsuario(String correo);
    boolean existeUsuario(String correo);
    void eliminarUsuario(String correo);
}
