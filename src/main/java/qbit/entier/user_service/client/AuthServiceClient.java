package qbit.entier.user_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import qbit.entier.user_service.config.FeignClientConfig;
import qbit.entier.user_service.dto.AuthDto;

@FeignClient(name = "auth-service", url = "${auth.service.url}", configuration = FeignClientConfig.class)
public interface AuthServiceClient {
    @GetMapping(value = "/auth/self")
    ResponseEntity<AuthDto> getUserByJwt(@RequestHeader("Authorization") String authorizationHeader);
}
