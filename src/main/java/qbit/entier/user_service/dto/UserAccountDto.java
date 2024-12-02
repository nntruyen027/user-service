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
public class UserAccountDto {
    private Long id;

    private String fullName;

    private boolean isMale;

    private String phone;

    private String email;

    private String avatar;

    private AccountDto account;

    private List<AddressDto> addresses;

    public static UserAccountDto fromEntity(User user) {
        return UserAccountDto.builder()
                .id(user.getId())
                .avatar(user.getAvatar())
                .email(user.getEmail())
                .phone(user.getPhone()) // Sửa đúng trường
                .fullName(user.getFullName())
                .isMale(user.getIsMale())
                .addresses(user.getAddresses().stream().map(AddressDto::fromEntity).toList())
                .build();
    }

    public static UserAccountDto fromEntity(User user, AccountDto account) {
        return UserAccountDto.builder()
                .id(user.getId())
                .avatar(user.getAvatar())
                .email(user.getEmail())
                .phone(user.getPhone()) // Sửa đúng trường
                .fullName(user.getFullName())
                .isMale(user.getIsMale())
                .account(account)
                .addresses(user.getAddresses().stream().map(AddressDto::fromEntity).toList())
                .build();
    }
}
