package com.cr.usuario.config.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

@Component
@Data
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

    @NotBlank
    private String secretKey;

}

