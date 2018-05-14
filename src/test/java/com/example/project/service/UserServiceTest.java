package com.example.project.service;

import com.example.project.RequestObject.UpdateEmailRequestObject;
import com.example.project.exception.BadUserRequestException;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.example.project.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    private User testUser1;
    @Autowired
    private UserService userService;

    @Before
    public void setUp() {
        testUser1 = new User(1, "testUser", "testPassword", "test", "test@email", "01000000001");
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(testUser1.getUserId(), testUser1.getPassword(),
                AuthorityUtils.createAuthorityList("USER")));
    }

    @Test
    public void isSetUserAuthentication() {
        Assertions.assertTrue(SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
    }

    @Test
    public void isGetUserAuthorities() {
        Assertions.assertEquals("USER",SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()[0].toString());
    }

    @Test
    public void isLogOut() {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(testUser1.getUserId(), testUser1.getPassword(), AuthorityUtils.NO_AUTHORITIES));
        Assertions.assertTrue(SecurityContextHolder.getContext().getAuthentication().getAuthorities().isEmpty());
    }

    @Test(expected = BadUserRequestException.class)
    public void isBadUserRequestExceptionOccur() {
        User notInDBUser = new User(1, "11", "22", "33", "44", "55");

        userService.updateUserEmail(new UpdateEmailRequestObject("newEmail", notInDBUser));
    }
}
