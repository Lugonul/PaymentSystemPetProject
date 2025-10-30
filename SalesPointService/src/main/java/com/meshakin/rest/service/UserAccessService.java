package com.meshakin.rest.service;

import com.meshakin.rest.repository.UserAccessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserAccessService implements UserDetailsService {
    private final UserAccessRepository userAccessRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        return userAccessRepository.findByUserLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Пользователь не найден: " + username
                ));
    }
}
