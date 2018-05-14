package com.example.project.service;

import com.example.project.RequestObject.*;
import com.example.project.authentication.AuthenticationToken;
import com.example.project.UserStrategy.UserUpdateStrategy;
import com.example.project.domain.User;
import com.example.project.exception.AuthenticationFailException;
import com.example.project.exception.BadUserRequestException;
import com.example.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * Created by dingue on 2018-03-02.
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public AuthenticationToken userLogin(LoginUserRequestObject loginUserRequestObject, HttpSession session) {
        String userId = loginUserRequestObject.getUserId();
        String password = loginUserRequestObject.getPassword();
        User user = userRepository.findUserByuserId(userId);

        if (user == null) throw new AuthenticationFailException("user not found");
        if (!passwordEncoder.matches(password, user.getPassword())) throw new AuthenticationFailException("password is wrong");

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userId, password, AuthorityUtils.createAuthorityList("USER")));
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

        return new AuthenticationToken(user.getName(), SecurityContextHolder.getContext().getAuthentication(), session.getId());
    }

    public void userLogout(User user) {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user.getUserId(), user.getPassword(), AuthorityUtils.NO_AUTHORITIES));
    }

    public User insertNewUSer(User user) {
        if (!isUserRight(user)) throw new BadUserRequestException();
        else {
            User insertUser = new User();

            insertUser.setUserId(user.getUserId());
            insertUser.setPassword(passwordEncoder.encode(user.getPassword()));
            insertUser.setEmail(user.getEmail());
            insertUser.setName(user.getName());
            insertUser.setPhoneNum(user.getPhoneNum());

            return userRepository.save(insertUser);
        }
    }

    public void deleteUser(User user) {
        if (!isUserRight(user)) throw new BadUserRequestException();

        userRepository.delete(user);
    }

    public User getUser(String userId) {
        return userRepository.findUserByuserId(userId);
    }

    public int updateUserPassword(UpdatePasswordRequestObject updatePasswordRequestObject) {
        return userUpdateData(updatePasswordRequestObject.getUser(), updatePasswordRequestObject.getNewPassword(),
                (data, userId) -> userRepository.updateUserPassword(data, userId));
    }

    public String getUserName(String userId) {
        User user = userRepository.findUserByuserId(userId);

        return user.getName();
    }

    public int updateUserName(UpdateNameRequestObject updateNameRequestObject) {
        return userUpdateData(updateNameRequestObject.getUser(), updateNameRequestObject.getNewName(),
                (data, userId) -> userRepository.updateUserName(data, userId)
        );
    }

    public String getUserEmail(String userId) {
        User user = userRepository.findUserByuserId(userId);

        return user.getEmail();
    }

    public int updateUserEmail(UpdateEmailRequestObject updateEmailRequestObject) {
        return userUpdateData(updateEmailRequestObject.getUser(), updateEmailRequestObject.getNewEmail(),
                (data, userId) -> userRepository.updateUserEmail(data, userId));
    }

    public String getUserPhoneNum(String userId) {
        User user = userRepository.findUserByuserId(userId);

        return user.getPhoneNum();
    }

    public int updateUserPhoneNum(UpdatePhoneNumRequestObject updatePhoneNumRequestObject) {
        return userUpdateData(updatePhoneNumRequestObject.getUser(), updatePhoneNumRequestObject.getNewPhoneNum(),
                (data, userId) -> userRepository.updateUserPhoneNum(data, userId));
    }

    private boolean isUserRight(User user) {
        if (user == null) return false;

        if (user.getUserId() == null) return false;

        if (user.getPassword() == null) return false;

        return true;
    }

    private int userUpdateData(User user, String data, UserUpdateStrategy userUpdateStrategy) {
        if (!isUserRight(user)) throw new BadUserRequestException();

        int updateDataCount = userUpdateStrategy.updateUserData(data, user.getUserId());

        if (updateDataCount == 0) throw new BadUserRequestException();

        return updateDataCount;
    }
}