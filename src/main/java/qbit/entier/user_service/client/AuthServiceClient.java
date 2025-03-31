package qbit.entier.user_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import qbit.entier.user_service.config.FeignClientConfig;
import qbit.entier.user_service.dto.AccountDto;
import qbit.entier.user_service.dto.CreateAccountDto;

@FeignClient(name = "auth-service", url = "${auth.service.url}", configuration = FeignClientConfig.class)
public interface AuthServiceClient {
    @GetMapping(value = "/self")
    ResponseEntity<AccountDto> getUserByJwt(@RequestHeader("Authorization") String authorizationHeader);

    @GetMapping(value = "/{id}")
    ResponseEntity<AccountDto> getUserById(@PathVariable Long id);

    @DeleteMapping(value = "/{id}")
    void deleteUserById(@RequestHeader("Authorization") String auth, @PathVariable Long id);

    @PostMapping(value = "/register")
    ResponseEntity<AccountDto> createUser(@RequestHeader("Authorization") String auth, @RequestBody CreateAccountDto accountDto);
}