package ru.ndg.crudproject.dao.role;

import ru.ndg.crudproject.model.Role;

import java.util.List;

public interface RoleDao {
    Role getRoleByName(String name);
    List<Role> getAllRoles();
    Role getRoleById(Long id);
}
