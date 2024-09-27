package com.cr.usuario.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="\"user\"")
public class User {

    private static final String VOID = "";
    private static final String ASTERISK = "*";

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    private String name;
    private String email;
    private String password;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Phone> phones;
    @Column(name="created")
    @DateTimeFormat(pattern = "yyyy-MM-dd-HH.mm.ss")
    private Timestamp created;
    @Column(name="modified")
    @DateTimeFormat(pattern = "yyyy-MM-dd-HH.mm.ss")
    private Timestamp modified;
    private String token;
    @Column(name="is_active")
    private Boolean isActive;
    @DateTimeFormat(pattern = "yyyy-MM-dd-HH.mm.ss")
    @Column(name="last_login")
    private Timestamp lastLogin;

    public String obfuscatePassword() {
        if (Optional.ofNullable(password).isEmpty() || password.isEmpty()) {
            return VOID;
        }
        return  ASTERISK.repeat(password.length());
    }
}