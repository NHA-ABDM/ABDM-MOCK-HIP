package in.gov.abdm.hip.security;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

import java.net.URI;

@EqualsAndHashCode(callSuper = true)
@Value
public class Request extends AbstractAuthenticationToken {
    HttpMethod method;
    URI uri;
    HttpHeaders httpHeaders;

    public Request(HttpMethod method, HttpHeaders httpHeaders, URI uri) {
        super(AuthorityUtils.NO_AUTHORITIES);
        this.method = method;
        this.httpHeaders = httpHeaders;
        this.uri = uri;
    }

    @Override
    public Request getCredentials() {
        return this;
    }

    @Override
    public Request getPrincipal() {
        return this;
    }
}