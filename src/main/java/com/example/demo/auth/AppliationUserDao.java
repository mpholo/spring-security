package com.example.demo.auth;

import java.util.Optional;

public interface AppliationUserDao  {

    Optional<ApplicationUser> selectApplicationUserByUsername(String username);
}
