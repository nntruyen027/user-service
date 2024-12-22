package qbit.entier.user_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import qbit.entier.user_service.config.FeignClientConfig;
import qbit.entier.user_service.dto.AccountDto;

@FeignClient(name = "auth-service", url = "${auth.service.url}", configuration = FeignClientConfig.class)
public interface AuthServiceClient {
    @GetMapping(value = "/self")
    ResponseEntity<AccountDto> getUserByJwt(@RequestHeader("Authorization") String authorizationHeader);

    @GetMapping(value = "/{id}")
    ResponseEntity<AccountDto> getUserById(@RequestHeader("Authorization") String authorizationHeader, @PathVariable Long id);

    @DeleteMapping(value = "/{id}")
    ResponseEntity<?> deleteUserById(@RequestHeader("Authorization") String auth, @PathVariable Long id);
}