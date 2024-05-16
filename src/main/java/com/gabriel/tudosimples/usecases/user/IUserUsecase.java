package com.gabriel.tudosimples.usecases.user;

import com.gabriel.tudosimples.models.User;

import java.util.List;

public interface IUserUsecase {
    User create(User user);

    List<User> listAll();

    User findById(Long id);

    User findByusername(String username);

    void update(User user);

    void delete(Long id);
}
