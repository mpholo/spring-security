package com.example.demo.auth;

import com.google.common.collect.Lists;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.example.demo.security.ApplicationUserRole.STUDENT;

@Repository("fake")
public class FakeApplicationUseDaoService implements AppliationUserDao {

    private final PasswordEncoder passwordEncoder;

    public FakeApplicationUseDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return getApplicationUsers()
                .stream()
                .filter(applicationUser->username.equals(applicationUser.getUsername()))
                .findFirst();
    }

    private List<ApplicationUser> getApplicationUsers() {
        List<ApplicationUser> applicationUsers = Lists.newArrayList(
               new ApplicationUser("user1",
                       passwordEncoder.encode("pass1"),
                       STUDENT.getGrantedAuthorities(),
                       true,
                       true,
                       true,
                  true),
                new ApplicationUser("user2",
                        passwordEncoder.encode("pass3"),
                        STUDENT.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true),
                new ApplicationUser("user3",
                        passwordEncoder.encode("pass3"),
                        STUDENT.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true)
        );

        return  applicationUsers;
    }
}
