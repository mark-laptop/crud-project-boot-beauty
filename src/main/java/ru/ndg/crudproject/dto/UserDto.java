package ru.ndg.crudproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {

    private String nickname;
    private String firstName;
    private String lastName;
    private Byte age;
    private String password;
    private String email;
    private Set<String> roles;
}
