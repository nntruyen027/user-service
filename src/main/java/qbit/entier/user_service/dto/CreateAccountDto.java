package qbit.entier.user_service.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class CreateAccountDto {
    private String username;
    private String email;
    private String password;
}
