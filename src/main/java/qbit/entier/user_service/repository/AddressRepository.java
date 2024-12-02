package qbit.entier.user_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import qbit.entier.user_service.entity.Address;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Page<Address> findByUserId(Long id, Pageable pageable);

    List<Address> findByUserIdAndIsDefaultTrue(Long id);

    Optional<Address> findByIdAndUserId(Long id, Long userId);
}
