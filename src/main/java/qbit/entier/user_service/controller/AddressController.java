package qbit.entier.user_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import qbit.entier.user_service.entity.Address;
import qbit.entier.user_service.service.AddressService;

@RestController
@RequestMapping("/users/addresses")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping()
    public ResponseEntity<?> getSelfAddresses(Pageable pageable) {
        try {
            return ResponseEntity.ok(addressService.getSelfAddresses(pageable));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/default")
    public ResponseEntity<?> getSelfDefaultAddress() {
        try {
            return ResponseEntity.ok(addressService.getSelfDefault());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<?> createAddress(@RequestBody Address address) {
        try {
            return ResponseEntity.ok(addressService.createSelfAddress(address));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAddress(@PathVariable Long id, @RequestBody Address address) {
        try {
            return ResponseEntity.ok(addressService.updateSelfAddress(id, address));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> updateAddress(@PathVariable Long id) {
        try {
            addressService.deleteSelfAddress(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
