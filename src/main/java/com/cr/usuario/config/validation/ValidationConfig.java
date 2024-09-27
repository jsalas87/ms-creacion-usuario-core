package com.cr.usuario.config.validation;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

@Component
@Data
@ConfigurationProperties(prefix = "validation")
public class ValidationConfig {

    private Password password;

    @Data
    public static class Password {
        @NotBlank
        private String regex;
        @NotBlank
        private String msg;
    }

}

