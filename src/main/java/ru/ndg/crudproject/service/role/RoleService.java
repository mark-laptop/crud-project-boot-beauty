package ru.ndg.crudproject.service.role;

import ru.ndg.crudproject.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();
    Role getRoleById(Long id);
    Role getRoleByName(String name);
}
