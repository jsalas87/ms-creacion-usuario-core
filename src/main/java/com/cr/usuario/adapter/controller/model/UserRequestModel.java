package com.cr.usuario.adapter.controller.model;

import com.cr.usuario.domain.User;
import lombok.Builder;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Value
@Builder
public class UserRequestModel {

    @NotNull @NotBlank
    String name;
    @NotNull @NotBlank
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "El email debe ser válido"
    )
    String email;
    @NotNull @NotBlank
    String password;
    @Valid
    List<PhoneModel> phones;

    public void validate(String regex, String msg) {
        if (!java.util.regex.Pattern.matches(regex, password)) {
            throw new ValidationException(msg);
        }
    }

    public User toDomain() {
        return User.builder()
                .name(name)
                .email(email)
                .password(password)
                .phones(
                        Optional.ofNullable(phones)
                                .map(
                                        p -> p.stream()
                                                .map(PhoneModel::toDomain)
                                                .collect(Collectors.toList())
                                ).orElse(List.of())
                )
                .build();
    }
}
