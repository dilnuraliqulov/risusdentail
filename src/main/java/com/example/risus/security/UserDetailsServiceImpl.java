package com.example.risus.security;


import com.example.risus.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return adminRepository.findByEmail(email)
                .map(admin -> new User(
                        admin.getEmail(),
                        admin.getPasswordHash(),
                        List.of(new SimpleGrantedAuthority(admin.getRole().name()))
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Admin not found: " + email));
    }
}