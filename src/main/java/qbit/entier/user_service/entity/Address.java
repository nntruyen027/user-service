package qbit.entier.user_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "addresses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "tinh", nullable = false)
    private String tinh;

    @Column(name = "huyen", nullable = false)
    private String huyen;

    @Column(name = "xa", nullable = false)
    private String xa;

    @Column(name = "chi_tiet")
    private String chiTiet;

    @Column(name = "kinh_do")
    private String kinhDo;

    @Column(name = "vi_do")
    private String viDo;

    @Column(name = "is_default")
    private Boolean isDefault;
}
