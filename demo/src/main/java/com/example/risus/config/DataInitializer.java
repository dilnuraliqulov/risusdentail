package com.example.risus.config;

import com.example.risus.entity.Admin;
import com.example.risus.enums.UserRole;
import com.example.risus.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements ApplicationRunner {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        if (adminRepository.count() == 0) {
            Admin admin = Admin.builder()
                    .email("admin@risusdental.com")
                    .passwordHash(passwordEncoder.encode("Admin@1234"))
                    .role(UserRole.ROLE_ADMIN)
                    .build();

            adminRepository.save(admin);
            log.info("==============================================");
            log.info("  Default admin created:");
            log.info("  Email   : admin@risusdental.com");
            log.info("  Password: Admin@1234");
            log.info("  CHANGE THIS PASSWORD AFTER FIRST LOGIN");
            log.info("==============================================");
        } else {
            log.info("Admin account already exists — skipping seed.");
        }
    }
}