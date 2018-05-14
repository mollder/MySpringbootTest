package com.example.project.RequestObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.project.domain.User;

/**
 * Created by dingue on 2018-03-05.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEmailRequestObject {
    private String newEmail;
    private User user;
}
