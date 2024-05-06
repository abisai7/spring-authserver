package dev.abisai.authserver.service;


import dev.abisai.authserver.model.User;

public interface UserService {

    User getByUsername(String username);

    User save(User entity);

}
