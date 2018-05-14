package com.example.project.repository;

import com.example.project.domain.User;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    private User testUser1;
    private User testUser2;
    private User testUser3;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() {
        testUser1 = new User(1, "testUser", "testPassword", "test", "test@email", "01000000001");
        testUser2 = new User(2, "testUser2", "testPassword2", "test2", "test2@email", "01000000002");
        testUser3 = new User(3, "testUser3", "testPassword3", "test3", "test3@email", "01000000003");
    }

    @Test
    @Transactional
    public void dbRollBackTest() {
        Assertions.assertEquals(0, userRepository.count());
    }

    @Test
    @Transactional
    public void insertThreeUser() {
        userRepository.save(testUser1);

        Assertions.assertEquals(1, userRepository.count());

        userRepository.save(testUser2);

        Assertions.assertEquals(2, userRepository.count());

        userRepository.save(testUser3);

        Assertions.assertEquals(3, userRepository.count());
    }

    @Test
    @Transactional
    public void updateUserPasswordTest() {
        String newPassword = "newPassword";

        Assertions.assertNotEquals(newPassword, testUser1.getPassword());

        userRepository.save(testUser1);

        userRepository.updateUserPassword(newPassword, testUser1.getUserId());

        User updateTestUser1 = userRepository.findUserByuserId(testUser1.getUserId());

        Assertions.assertNotEquals(testUser1.getPassword(), updateTestUser1.getPassword());
    }

    @Test
    @Transactional
    public void updateUserNameTest() {
        String newName = "newName";

        Assertions.assertNotEquals(newName, testUser1.getName());

        userRepository.save(testUser1);
        userRepository.updateUserName(newName, testUser1.getUserId());

        User updateTestUser1 = userRepository.findUserByuserId(testUser1.getUserId());

        Assertions.assertNotEquals(testUser1.getName(), updateTestUser1.getName());
    }

    @Test
    @Transactional
    public void updateUserEmailTest() {
        String newEmail = "newEmail@email";

        Assertions.assertNotEquals(newEmail, testUser1.getEmail());

        userRepository.save(testUser1);
        userRepository.updateUserEmail(newEmail, testUser1.getUserId());

        User updateTestUser1 = userRepository.findUserByuserId(testUser1.getUserId());

        Assertions.assertNotEquals(testUser1.getEmail(), updateTestUser1.getEmail());
    }

    @Test
    @Transactional
    public void updateUserPhoneNumTest() {
        String newPhoneNum = "01000000011";

        Assertions.assertNotEquals(newPhoneNum, testUser1.getPhoneNum());

        userRepository.save(testUser1);
        userRepository.updateUserPhoneNum(newPhoneNum, testUser1.getUserId());

        User updateTestUser1 = userRepository.findUserByuserId(testUser1.getUserId());

        Assertions.assertNotEquals(testUser1.getPhoneNum(), updateTestUser1.getPhoneNum());
    }

    @Test
    @Transactional
    public void deleteAll() {
        userRepository.save(testUser1);
        userRepository.save(testUser2);
        userRepository.save(testUser3);

        Assertions.assertEquals(3, userRepository.count());

        userRepository.deleteAll();

        Assertions.assertEquals(0, userRepository.count());
    }
}
