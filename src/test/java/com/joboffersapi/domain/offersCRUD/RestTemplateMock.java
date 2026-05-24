package com.joboffersapi.domain.offersCRUD;

import org.jspecify.annotations.Nullable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;
import java.util.Set;

class RestTemplateMock extends RestTemplate  {

    @Override
    public @Nullable <T> T getForObject(final String url, final Class<T> responseType, final @Nullable Object... uriVariables) throws RestClientException {
        return null;
    }

    @Override
    public @Nullable <T> T getForObject(final String url, final Class<T> responseType, final Map<String, ?> uriVariables) throws RestClientException {
        return null;
    }

    @Override
    public @Nullable <T> T getForObject(final URI url, final Class<T> responseType) throws RestClientException {
        return null;
    }

    @Override
    public <T> ResponseEntity<T> getForEntity(final String url, final Class<T> responseType, final @Nullable Object... uriVariables) throws RestClientException {
        return null;
    }

    @Override
    public <T> ResponseEntity<T> getForEntity(final String url, final Class<T> responseType, final Map<String, ?> uriVariables) throws RestClientException {
        return null;
    }

    @Override
    public <T> ResponseEntity<T> getForEntity(final URI url, final Class<T> responseType) throws RestClientException {
        return null;
    }

    @Override
    public HttpHeaders headForHeaders(final String url, final @Nullable Object... uriVariables) throws RestClientException {
        return null;
    }

    @Override
    public HttpHeaders headForHeaders(final String url, final Map<String, ?> uriVariables) throws RestClientException {
        return null;
    }

    @Override
    public HttpHeaders headForHeaders(final URI url) throws RestClientException {
        return null;
    }

    @Override
    public @Nullable URI postForLocation(final String url, @Nullable final Object request, final @Nullable Object... uriVariables) throws RestClientException {
        return null;
    }

    @Override
    public @Nullable URI postForLocation(final String url, @Nullable final Object request, final Map<String, ?> uriVariables) throws RestClientException {
        return null;
    }

    @Override
    public @Nullable URI postForLocation(final URI url, @Nullable final Object request) throws RestClientException {
        return null;
    }

    @Override
    public @Nullable <T> T postForObject(final String url, @Nullable final Object request, final Class<T> responseType, final @Nullable Object... uriVariables) throws RestClientException {
        return null;
    }

    @Override
    public @Nullable <T> T postForObject(final String url, @Nullable final Object request, final Class<T> responseType, final Map<String, ?> uriVariables) throws RestClientException {
        return null;
    }

    @Override
    public @Nullable <T> T postForObject(final URI url, @Nullable final Object request, final Class<T> responseType) throws RestClientException {
        return null;
    }

    @Override
    public <T> ResponseEntity<T> postForEntity(final String url, @Nullable final Object request, final Class<T> responseType, final @Nullable Object... uriVariables) throws RestClientException {
        return null;
    }

    @Override
    public <T> ResponseEntity<T> postForEntity(final String url, @Nullable final Object request, final Class<T> responseType, final Map<String, ?> uriVariables) throws RestClientException {
        return null;
    }

    @Override
    public <T> ResponseEntity<T> postForEntity(final URI url, @Nullable final Object request, final Class<T> responseType) throws RestClientException {
        return null;
    }

    @Override
    public void put(final String url, @Nullable final Object request, final @Nullable Object... uriVariables) throws RestClientException {

    }

    @Override
    public void put(final String url, @Nullable final Object request, final Map<String, ?> uriVariables) throws RestClientException {

    }

    @Override
    public void put(final URI url, @Nullable final Object request) throws RestClientException {

    }

    @Override
    public @Nullable <T> T patchForObject(final String url, @Nullable final Object request, final Class<T> responseType, final @Nullable Object... uriVariables) throws RestClientException {
        return null;
    }

    @Override
    public @Nullable <T> T patchForObject(final String url, @Nullable final Object request, final Class<T> responseType, final Map<String, ?> uriVariables) throws RestClientException {
        return null;
    }

    @Override
    public @Nullable <T> T patchForObject(final URI url, @Nullable final Object request, final Class<T> responseType) throws RestClientException {
        return null;
    }

    @Override
    public void delete(final String url, final @Nullable Object... uriVariables) throws RestClientException {

    }

    @Override
    public void delete(final String url, final Map<String, ?> uriVariables) throws RestClientException {

    }

    @Override
    public void delete(final URI url) throws RestClientException {

    }

    @Override
    public Set<HttpMethod> optionsForAllow(final String url, final @Nullable Object... uriVariables) throws RestClientException {
        return Set.of();
    }

    @Override
    public Set<HttpMethod> optionsForAllow(final String url, final Map<String, ?> uriVariables) throws RestClientException {
        return Set.of();
    }

    @Override
    public Set<HttpMethod> optionsForAllow(final URI url) throws RestClientException {
        return Set.of();
    }

    @Override
    public <T> ResponseEntity<T> exchange(final String url, final HttpMethod method, @Nullable final HttpEntity<?> requestEntity, final Class<T> responseType, final @Nullable Object... uriVariables) throws RestClientException {
        return null;
    }

    @Override
    public <T> ResponseEntity<T> exchange(final String url, final HttpMethod method, @Nullable final HttpEntity<?> requestEntity, final Class<T> responseType, final Map<String, ?> uriVariables) throws RestClientException {
        return null;
    }

    @Override
    public <T> ResponseEntity<T> exchange(final URI url, final HttpMethod method, @Nullable final HttpEntity<?> requestEntity, final Class<T> responseType) throws RestClientException {
        return null;
    }

    @Override
    public <T> ResponseEntity<T> exchange(final String url, final HttpMethod method, @Nullable final HttpEntity<?> requestEntity, final ParameterizedTypeReference<T> responseType, final @Nullable Object... uriVariables) throws RestClientException {
        return null;
    }

    @Override
    public <T> ResponseEntity<T> exchange(final String url, final HttpMethod method, @Nullable final HttpEntity<?> requestEntity, final ParameterizedTypeReference<T> responseType, final Map<String, ?> uriVariables) throws RestClientException {
        return null;
    }

    @Override
    public <T> ResponseEntity<T> exchange(final URI url, final HttpMethod method, @Nullable final HttpEntity<?> requestEntity, final ParameterizedTypeReference<T> responseType) throws RestClientException {
        return null;
    }

    @Override
    public <T> ResponseEntity<T> exchange(final RequestEntity<?> requestEntity, final Class<T> responseType) throws RestClientException {
        return null;
    }

    @Override
    public <T> ResponseEntity<T> exchange(final RequestEntity<?> requestEntity, final ParameterizedTypeReference<T> responseType) throws RestClientException {
        return null;
    }

    @Override
    public @Nullable <T> T execute(final String uriTemplate, final HttpMethod method, @Nullable final RequestCallback requestCallback, @Nullable final ResponseExtractor<T> responseExtractor, final Map<String, ?> uriVariables) throws RestClientException {
        String mockResponseBody = """
                [
                    {
                        "title": "Java Developer",
                        "company": "Tech Company",
                        "location": "Remote",
                        "url": "https://www.example.com/job/java-developer"
                    },
                    {
                        "title": "Frontend Developer",
                        "company": "Web Solutions",
                        "location": "New York, NY",
                        "url": "https://www.example.com/job/frontend-developer"
                    }
                ]
                """;
        return (T) mockResponseBody;
    }

    @Override
    public @Nullable <T> T execute(final URI url, final HttpMethod method, @Nullable final RequestCallback requestCallback, @Nullable final ResponseExtractor<T> responseExtractor) throws RestClientException {
        String mockResponseBody = """
                [
                    {
                        "title": "Java Developer",
                        "company": "Tech Company",
                        "location": "Remote",
                        "url": "https://www.example.com/job/java-developer"
                    },
                    {
                        "title": "Frontend Developer",
                        "company": "Web Solutions",
                        "location": "New York, NY",
                        "url": "https://www.example.com/job/frontend-developer"
                    }
                ]
                """;
        return (T) mockResponseBody;
    }
}
