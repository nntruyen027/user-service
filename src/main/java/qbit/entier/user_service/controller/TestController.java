package qbit.entier.user_service.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/")
    public String test() {
        return "ok";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('admin')")
    public String testRoleAdmin() {
        return "ok";
    }

    @GetMapping("/superadmin")
    @PreAuthorize("hasRole('super_admin')")
    public String testRoleSuperAdmin() {
        return "ok";
    }
}
