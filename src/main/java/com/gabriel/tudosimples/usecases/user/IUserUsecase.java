package com.gabriel.tudosimples.usecases.user;

import com.gabriel.tudosimples.models.Usuario;

import java.util.List;

public interface IUserUsecase {
    Usuario create(Usuario usuario);

    List<Usuario> listAll();

    Usuario findById(Long id);

    Usuario findByusername(String username);

    void update(Usuario usuario);

    void delete(Long id);
}
