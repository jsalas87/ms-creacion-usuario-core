package com.cr.usuario.adapter.controller.model;

import com.cr.usuario.domain.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Value
@Builder
public class UserResponseModel {

    UUID id;
    String name;
    String email;
    String password;
    List<PhoneModel> phones;
    Timestamp created;
    Timestamp modified;
    String token;
    @JsonProperty("isactive")
    Boolean isActive;

    public static UserResponseModel of(User user) {
        return UserResponseModel.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .phones(
                        user.getPhones().stream()
                                .map(PhoneModel::of)
                                .collect(Collectors.toList())
                )
                .created(user.getCreated())
                .modified(user.getModified())
                .token(user.getToken())
                .isActive(user.getIsActive())
                .build();
    }
}
