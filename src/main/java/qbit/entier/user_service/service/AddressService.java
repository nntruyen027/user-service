package qbit.entier.user_service.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import qbit.entier.user_service.dto.AddressDto;
import qbit.entier.user_service.entity.Address;
import qbit.entier.user_service.repository.AddressRepository;

import java.util.Optional;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserService userService;

    public Page<AddressDto> getSelfAddresses(Pageable pageable) {
        if (userService.getSelfUser().isPresent())
            return addressRepository.findByUserId(userService.getSelfUser().get().getId(), pageable).map(AddressDto::fromEntity);
        else
            throw new EntityNotFoundException("User not found");
    }

    public AddressDto getSelfDefault() {
        if (userService.getSelfUser().isPresent())
            return AddressDto.fromEntity(addressRepository.findByUserIdAndIsDefaultTrue(userService.getSelfUser().get().getId()).getFirst());
        else
            throw new EntityNotFoundException("User not found");
    }

    public AddressDto createSelfAddress(Address address) {
        if (userService.getSelfUser().isPresent()) {
            address.setUser(userService.getSelfUser().get());
            return AddressDto.fromEntity(addressRepository.save(address));
        } else
            throw new EntityNotFoundException("User not found");
    }

    public AddressDto updateSelfAddress(Long id, Address address) {
        if (userService.getSelfUser().isPresent()) {
            Optional<Address> updatedAddress = addressRepository.findByIdAndUserId(id,
                    userService.getSelfUser().get().getId());
            if (updatedAddress.isPresent()) {
                if (address.getTinh() != null)
                    updatedAddress.get().setTinh(address.getTinh());
                if (address.getHuyen() != null)
                    updatedAddress.get().setHuyen(address.getHuyen());
                if (address.getXa() != null)
                    updatedAddress.get().setXa(address.getXa());
                if (address.getChiTiet() != null)
                    updatedAddress.get().setChiTiet(address.getChiTiet());
                if (address.getIsDefault() != null)
                    updatedAddress.get().setIsDefault(address.getIsDefault());
                if (address.getKinhDo() != null)
                    updatedAddress.get().setKinhDo(address.getKinhDo());
                if (address.getViDo() != null)
                    updatedAddress.get().setViDo(address.getViDo());
                return AddressDto.fromEntity(addressRepository.save(updatedAddress.get()));
            } else {
                throw new EntityNotFoundException("Address not found");
            }
        } else
            throw new EntityNotFoundException("User not found");
    }

    public void deleteSelfAddress(Long id) {
        if (userService.getSelfUser().isPresent()) {
            Optional<Address> updatedAddress = addressRepository.findByIdAndUserId(id,
                    userService.getSelfUser().get().getId());
            if (updatedAddress.isPresent()) {
                addressRepository.delete(updatedAddress.get());
            } else {
                throw new EntityNotFoundException("Address not found");
            }
        } else
            throw new EntityNotFoundException("User not found");
    }
}
