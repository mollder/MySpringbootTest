package com.example.project.RequestObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by dingue on 2018-03-04.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserRequestObject implements Serializable {
    private String userId;
    private String password;
}
