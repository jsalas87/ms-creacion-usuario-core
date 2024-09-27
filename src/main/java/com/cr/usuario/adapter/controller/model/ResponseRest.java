package com.cr.usuario.adapter.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.Map;

@Value
@Builder
@AllArgsConstructor
public class ResponseRest<T> {

    String resource;
    T data;
    Map<String, String> metadata;
}
