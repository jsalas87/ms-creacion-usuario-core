package com.cr.usuario.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="phone")
public class Phone {
    @Id
    @GeneratedValue(strategy= javax.persistence.GenerationType.IDENTITY)
    private Integer id;
    @Column(name="number")
    private String number;
    @Column(name="city_code")
    private String cityCode;
    @Column(name="country_code")
    private String countryCode;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
