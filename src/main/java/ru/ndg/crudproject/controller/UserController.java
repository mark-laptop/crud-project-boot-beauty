package ru.ndg.crudproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.ndg.crudproject.dto.AuthenticationUser;
import ru.ndg.crudproject.model.User;
import ru.ndg.crudproject.service.role.RoleService;
import ru.ndg.crudproject.service.user.UserService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping(value = "/")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping(value = "/admin")
    public String showAllUserPage(Model model, Principal principal) {
        model.addAttribute("user_auth", getAuthenticationUser(principal));
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", roleService.getAllRoles());
        return "users";
    }

    @GetMapping(value = "/user")
    public String showUserPage(Model model, Principal principal) {
        model.addAttribute("user_auth", getAuthenticationUser(principal));
        model.addAttribute("user", userService.getUserByUsername(principal.getName()));
        return "user";
    }

    @GetMapping(value = "/admin/{id}")
    public String showUpdateUserPage(Model model, @PathVariable(name = "id") Long id, Principal principal) {
        model.addAttribute("user_auth", getAuthenticationUser(principal));
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("user", userService.getUserById(id));
        return "update_user";
    }

    @GetMapping(value = "/admin/create")
    public String showCreateUserPage(Model model, Principal principal) {
        model.addAttribute("user_auth", getAuthenticationUser(principal));
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("user", new User());
        return "create_user";
    }

    @PostMapping(value = "/admin/update")
    public String updateUser(@Valid @ModelAttribute User user, BindingResult bindingResult, Principal principal, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user_auth", getAuthenticationUser(principal));
            model.addAttribute("roles", roleService.getAllRoles());
            return "update_user";
        }
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @PostMapping(value = "/admin/create")
    public String createUser(@Valid @ModelAttribute User user, BindingResult bindingResult, Principal principal, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user_auth", getAuthenticationUser(principal));
            model.addAttribute("roles", roleService.getAllRoles());
            return "create_user";
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    // TODO: 21.01.2021 Если удалили пользователя, выкидываем его
    @GetMapping(value = "/admin/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    private AuthenticationUser getAuthenticationUser(Principal principal) {
        return new AuthenticationUser(principal.getName(),
                AuthorityUtils.authorityListToSet(((Authentication) principal).getAuthorities()));
    }
}
