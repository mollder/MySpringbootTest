package com.example.project.controller;

import com.example.project.RequestObject.*;
import com.example.project.authentication.AuthenticationToken;
import com.example.project.service.UserService;
import com.example.project.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseStatus(HttpStatus.OK)
    @Async("threadPoolTaskExecutor")
    public Future<AuthenticationToken> login(@RequestBody LoginUserRequestObject loginUserRequestObject, HttpSession session) {
        return new AsyncResult<AuthenticationToken>(userService.userLogin(loginUserRequestObject, session));
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseStatus(HttpStatus.OK)
    @Async("threadPoolTaskExecutor")
    public void logout(@RequestBody User user) {
        userService.userLogout(user);
    }

    @RequestMapping(value = "/register", method = RequestMethod.PUT, headers = "Accept=application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @Async("threadPoolTaskExecutor")
    public Future<User> createNewUser(@RequestBody User user) {
        return new AsyncResult<User>(userService.insertNewUSer(user));
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE, headers = "Accept=application/json")
    @ResponseStatus(HttpStatus.OK)
    @Async("threadPoolTaskExecutor")
    public void removeUser(@RequestBody User user) {
        userService.deleteUser(user);
    }

    @RequestMapping(value = "", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseStatus(HttpStatus.OK)
    @Async("threadPoolTaskExecutor")
    public Future<User> getUser(@RequestParam(value="userId", required = true) String userId) {
        return new AsyncResult<User>(userService.getUser(userId));
    }

    @RequestMapping(value = "/password", method = RequestMethod.PUT, headers = "Accept=application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Async("threadPoolTaskExecutor")
    public Future<Integer> updateUserPassword(@RequestBody UpdatePasswordRequestObject updatePasswordRequestObject) {
        return new AsyncResult<Integer>(userService.updateUserPassword(updatePasswordRequestObject));
    }

    @RequestMapping(value = "/name", method = RequestMethod.PUT, headers = "Accept=application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Async("threadPoolTaskExecutor")
    public Future<Integer> updateUserName(@RequestBody UpdateNameRequestObject updateNameRequestObject) {
        return new AsyncResult<Integer>(userService.updateUserName(updateNameRequestObject));
    }

    @RequestMapping(value = "/name", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Async("threadPoolTaskExecutor")
    public Future<String> getUserName(@RequestParam(value="userId", required = true) String userId) {
        return new AsyncResult<String>(userService.getUserName(userId));
    }

    @RequestMapping(value = "/email", method = RequestMethod.PUT, headers = "Accept=application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Async("threadPoolTaskExecutor")
    public Future<Integer> updateUserEmail(@RequestBody UpdateEmailRequestObject updateEmailRequestObject) {
        return new AsyncResult<Integer>(userService.updateUserEmail(updateEmailRequestObject));
    }

    @RequestMapping(value = "/email", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Async("threadPoolTaskExecutor")
    public Future<String> getUserEmail(@RequestParam(value="userId", required = true) String userId) {
        return new AsyncResult<String>(userService.getUserEmail(userId));
    }

    @RequestMapping(value = "/phoneNum", method = RequestMethod.PUT, headers = "Accept=application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Async("threadPoolTaskExecutor")
    public Future<Integer> updateUserPhoneNum(@RequestBody UpdatePhoneNumRequestObject updatePhoneNumRequestObject) {
        return new AsyncResult<Integer>(userService.updateUserPhoneNum(updatePhoneNumRequestObject));
    }

    @RequestMapping(value = "/phoneNum", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Async("threadPoolTaskExecutor")
    public Future<String> getUserPhoneNum(@RequestParam(value="userId", required = true) String userId) {
        return new AsyncResult<String>(userService.getUserPhoneNum(userId));
    }
}