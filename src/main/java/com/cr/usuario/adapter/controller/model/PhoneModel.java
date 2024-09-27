package com.cr.usuario.adapter.controller.model;

import com.cr.usuario.domain.Phone;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
@Builder
public class PhoneModel {

    @NotNull @NotBlank
    String number;
    @NotNull(message = "citycode no puede ser nulo")
    @NotBlank(message = "citycode no puede ser vacío")
    @JsonProperty("citycode")
    String cityCode;
    @NotNull(message = "countrycode no puede ser nulo")
    @NotBlank(message = "countrycode no puede ser vacío")
    @JsonProperty("countrycode")
    String countryCode;

    public Phone toDomain() {
        return Phone.builder()
                .number(number)
                .cityCode(cityCode)
                .countryCode(countryCode)
                .build();
    }

    public static PhoneModel of(Phone phone) {
        return PhoneModel.builder()
                .number(phone.getNumber())
                .cityCode(phone.getCityCode())
                .countryCode(phone.getCountryCode())
                .build();
    }
}
