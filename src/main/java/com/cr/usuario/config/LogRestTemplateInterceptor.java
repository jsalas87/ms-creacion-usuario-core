package com.cr.usuario.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StopWatch;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Slf4j
public class LogRestTemplateInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {

        traceRequest(request, body);
        StopWatch watch = new StopWatch();
        watch.start();
        ClientHttpResponse response = execution.execute(request, body);
        ClientHttpResponse wrappedResponse = new BufferedClientHttpResponse(response);
        watch.stop();
        traceResponse(wrappedResponse, watch.getTotalTimeMillis());
        return wrappedResponse;
    }

    private void traceRequest(HttpRequest request, byte[] body) {
        log.info("Request URI     : {}", request.getURI());
        log.info("Request Method  : {}", request.getMethod());
        log.info("Request Headers : {}", request.getHeaders());
        log.info("Request body    : {}", new String(body, StandardCharsets.UTF_8));
    }

    private void traceResponse(ClientHttpResponse response, long callTime) throws IOException {
        String responseBody = StreamUtils.copyToString(response.getBody(), Charset.defaultCharset());

        log.info("Response Status code : {}", response.getStatusCode());
        log.info("Response Status text : {}", response.getStatusText());
        log.info("Response Headers     : {}", response.getHeaders());
        log.info("Response body        : {}", responseBody);
        log.info("Response time        : {}ms", callTime);
    }

    private static class BufferedClientHttpResponse implements ClientHttpResponse {

        private final ClientHttpResponse originalResponse;
        private final byte[] bufferedBodyBytes;

        public BufferedClientHttpResponse(ClientHttpResponse originalResponse) throws IOException {
            this.originalResponse = originalResponse;
            InputStream originalBodyStream = originalResponse.getBody();
            this.bufferedBodyBytes = StreamUtils.copyToByteArray(originalBodyStream);
            originalBodyStream.close();
        }

        @Override
        public HttpStatus getStatusCode() throws IOException {
            return originalResponse.getStatusCode();
        }

        @Override
        public int getRawStatusCode() throws IOException {
            return originalResponse.getRawStatusCode();
        }

        @Override
        public String getStatusText() throws IOException {
            return originalResponse.getStatusText();
        }

        @Override
        public void close() {
            originalResponse.close();
        }

        @Override
        public InputStream getBody() {
            return new ByteArrayInputStream(bufferedBodyBytes);
        }

        @Override
        public HttpHeaders getHeaders() {
            return originalResponse.getHeaders();
        }
    }
}
