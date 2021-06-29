package com.revature.quizzard.services;

import org.junit.*;
import org.springframework.security.core.userdetails.*;
import static org.junit.Assert.*;

public class UserDetailsServiceImplTest {

    UserDetailsServiceImpl userDetailsService;

    @Before
    public void setup(){
        userDetailsService = new UserDetailsServiceImpl();
    }

    @Test
    public void test_loadUserByUsername(){
        UserDetails userDetails = userDetailsService.loadUserByUsername("username");

        assertNull(userDetails);

    }
}
