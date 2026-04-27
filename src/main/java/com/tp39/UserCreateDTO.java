package com.tp39;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDTO {
    @NotBlank
    private String name;
    @NotBlank private String role;
    @NotBlank @Email
    private String email;
    @NotBlank @Size(min = 4, max = 10) private String password;
    private ProfileDTO profile;
    private DepartmentDTO department;
    private List<RoleDTO> roles;
}
