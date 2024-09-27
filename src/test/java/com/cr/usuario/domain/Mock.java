package com.cr.usuario.domain;

import com.cr.usuario.adapter.controller.model.PhoneModel;
import com.cr.usuario.adapter.controller.model.UserRequestModel;

import java.util.List;
import java.util.UUID;

public class Mock {

    public static UserRequestModel mockUserRequestModel() {
        return UserRequestModel.builder()
                .name("Juan Rodriguez")
                .email("juan5@rodriguez.org")
                .password("hunter2")
                .phones(
                        List.of(
                                PhoneModel.builder()
                                        .number("1234567")
                                        .cityCode("1")
                                        .countryCode("57")
                                        .build()
                        )
                )
                .build();
    }

    public static UserRequestModel mockUserRequestWithoutPhonesModel() {
        return UserRequestModel.builder()
                .name("Juan Rodriguez")
                .email("juan5@rodriguez.org")
                .password("hunter2")
                .phones(
                        List.of()
                )
                .build();
    }

    public static UserRequestModel mockUserRequestWithNameNullModel() {
        return UserRequestModel.builder()
                .email("juan5@rodriguez.org")
                .password("hunter2")
                .phones(
                        List.of(
                                PhoneModel.builder()
                                        .number("1234567")
                                        .cityCode("1")
                                        .countryCode("57")
                                        .build()
                        )
                )
                .build();
    }

    public static User mockUser() {
        return User.builder()
                .id(UUID.fromString("0c3b825c-c742-46d8-8fb3-8c8d63675e43"))
                .name("Juan Rodriguez")
                .email("juan5@rodriguez.org")
                .password("hunter2")
                .phones(
                        List.of(
                                Phone.builder()
                                        .number("1234567")
                                        .cityCode("1")
                                        .countryCode("57")
                                        .build()
                        )
                )
                .token("The Token")
                .build();
    }
}
