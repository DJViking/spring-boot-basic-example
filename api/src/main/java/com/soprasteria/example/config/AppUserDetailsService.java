package com.soprasteria.example.config;

import com.soprasteria.example.domain.AppRole;
import com.soprasteria.example.domain.AppUser;
import com.soprasteria.example.repo.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final AppUser appUser = appUserRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", username)));

        return User.builder()
            .username(appUser.getUsername())
            .password(passwordEncoder.encode(appUser.getPassword()))
            .roles(appUser.getRoles()
                .stream()
                .map(AppRole::getName)
                .toArray(String[]::new)
            )
            .build();
    }

}
