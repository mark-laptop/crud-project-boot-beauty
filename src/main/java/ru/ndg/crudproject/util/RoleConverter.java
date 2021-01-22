package ru.ndg.crudproject.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.ndg.crudproject.model.Role;
import ru.ndg.crudproject.service.role.RoleService;

// TODO: 21.01.2021 Должен мапить Role без конвертера, обычно работало само. Теперь сука не хочет
@Component
public class RoleConverter implements Converter<String, Role> {

    private final RoleService roleService;

    @Autowired
    public RoleConverter(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public Role convert(String value) {
        if (value.startsWith("ROLE_")) {
            return roleService.getRoleByName(value);
        }
        try {
            return roleService.getRoleById(Long.parseLong(value));
        } catch (NumberFormatException e) {
            throw new UnsupportedOperationException(String.format("Can't convert Role by value: %s", value), e);
        }
    }
}
