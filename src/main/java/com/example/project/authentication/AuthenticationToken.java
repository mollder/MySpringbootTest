package com.example.project.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;

/**
 * Created by dingue on 2018-03-05.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationToken {
    private String name;
    private Authentication authentication;
    private String password;
}
