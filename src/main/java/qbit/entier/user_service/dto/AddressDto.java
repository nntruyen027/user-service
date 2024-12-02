package qbit.entier.user_service.dto;


import lombok.*;
import qbit.entier.user_service.entity.Address;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class AddressDto {
    private Long id;
    private String tinh;
    private String huyen;
    private String xa;
    private String chiTiet;
    private String kinhDo;
    private String viDo;
    private Boolean isDefault;

    public static AddressDto fromEntity(Address address) {
        return AddressDto
                .builder()
                .id(address.getId())
                .tinh(address.getTinh())
                .huyen(address.getHuyen())
                .xa(address.getXa())
                .chiTiet(address.getChiTiet())
                .kinhDo(address.getKinhDo())
                .viDo(address.getViDo())
                .isDefault(address.getIsDefault())
                .build();
    }
}
