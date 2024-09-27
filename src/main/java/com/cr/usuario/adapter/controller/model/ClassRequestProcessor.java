package com.cr.usuario.adapter.controller.model;

import brave.Span;
import brave.Tracer;
import brave.propagation.TraceContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

@Component
public class ClassRequestProcessor implements RequestProcessor{
    private static final String QUERY_STRING = "query_string";
    private static final String X_B3_TRACEID = "X-B3-TraceId";
    private static final String X_B3_SPANID = "X-B3-SpanId";

    @Override
    public <T> ResponseRest<T> processRequest(final EnrichedRequest request,
                                              final Function<EnrichedRequest, ResponseRest<T>> operation) {
        return operation.apply(request);
    }

    public <T> ResponseRest<T> processRequest(
            final HttpServletRequest request,
            final Tracer tracer,
            final Supplier<T> supplier
            ){
        return processRequest(
                RequestProcessor.EnrichedRequest.of(request),
                result -> ResponseRest.<T>builder()
                        .resource(result.getRequest().getRequestURI())
                        .data(supplier.get())
                        .metadata(buildMetadata(request, tracer))
                        .build()
        );
    }

    @Override
    public Map<String, String> buildMetadata(final HttpServletRequest request,
            final Tracer tracer) {

        final var metadata = new HashMap<String, String>();
        final var traceContext = Optional.ofNullable(tracer.currentSpan()).map(Span::context);

        traceContext.map(TraceContext::traceIdString)
                .ifPresent(qs -> metadata.put(X_B3_TRACEID, qs));
        traceContext.map(TraceContext::spanIdString).ifPresent(qs -> metadata.put(X_B3_SPANID, qs));
        Optional.ofNullable(request.getQueryString())
                .ifPresent(qs -> metadata.put(QUERY_STRING, qs));
        return metadata;
    }

}
