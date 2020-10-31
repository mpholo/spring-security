package com.example.demo.auth;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserService implements UserDetailsService {

    private final AppliationUserDao appliationUserDao;

    public ApplicationUserService(@Qualifier("fake") AppliationUserDao appliationUserDao) {
        this.appliationUserDao = appliationUserDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appliationUserDao
                .selectApplicationUserByUsername(username)
                .orElseThrow(()->
                        new UsernameNotFoundException(String.format("Username %s not found.",username)));
    }
}
