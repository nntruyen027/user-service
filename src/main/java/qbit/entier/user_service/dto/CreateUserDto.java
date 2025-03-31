package qbit.entier.user_service.dto;

import lombok.*;
import qbit.entier.user_service.entity.User;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreateUserDto {
    private Long accountId;

    private String fullName;

    private Boolean isMale;

    private String phone;

    private String email;

    private String avatar;

    private List<AddressDto> addresses;

    private String username;

    private String password;

    public static UserDto fromEntity(User user) {
        return UserDto.builder()
                .accountId(user.getAccountId())
                .avatar(user.getAvatar())
                .email(user.getEmail())
                .phone(user.getPhone())
                .fullName(user.getFullName())
                .isMale(user.getIsMale())
                .addresses(user.getAddresses().stream().map(AddressDto::fromEntity).toList())
                .build();
    }

    public User toEntity() {
        return User.builder()
                .accountId(this.getAccountId())
                .avatar(this.getAvatar())
                .email(this.getEmail())
                .phone(this.getPhone())
                .fullName(this.getFullName())
                .isMale(this.getIsMale())
                .addresses(this.getAddresses().stream().map(AddressDto::toEntity).toList())
                .build();
    }
}
