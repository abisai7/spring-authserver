package dev.abisai.authserver.service;

import dev.abisai.authserver.model.Role;

public interface RoleService {
    Role getByName(String name);
    Role getDefaultRole();
}