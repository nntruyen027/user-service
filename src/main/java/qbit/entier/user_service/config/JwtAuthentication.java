package qbit.entier.user_service.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthentication extends UsernamePasswordAuthenticationToken {
    private final String jwt;

    public JwtAuthentication(Object principal, Object credentials, Collection<?> authorities, String jwt) {
        super(principal, credentials, (Collection<? extends GrantedAuthority>) authorities);
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}
