package com.cr.usuario.adapter.controller.model;

import brave.Tracer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.function.Function;

public interface RequestProcessor {
    <T> ResponseRest<T> processRequest(EnrichedRequest request, Function<EnrichedRequest, ResponseRest<T>> operation);


    Map<String, String> buildMetadata(HttpServletRequest request, Tracer tracer);

    @Getter
    @RequiredArgsConstructor
    @EqualsAndHashCode
    final class EnrichedRequest {
        private final String id;
        private final HttpServletRequest request;

        public static EnrichedRequest of(HttpServletRequest request) {
            return of("", request);
        }

        public static EnrichedRequest of(String id, HttpServletRequest request) {
            return new EnrichedRequest(id, request);
        }
    }
}
