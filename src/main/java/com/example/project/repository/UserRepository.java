package com.example.project.repository;

import com.example.project.domain.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by dingue on 2018-03-03.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findUserByuserId(String userId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update User u set u.password = ?1 where u.userId = ?2")
    int updateUserPassword(String newPassword, String userId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update User u set u.email = ?1 where u.userId = ?2")
    int updateUserEmail(String newEmail, String userId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update User u set u.name =?1 where u.userId =?2")
    int updateUserName(String newName, String userId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update User u set u.phoneNum = ?1 where u.userId = ?2")
    int updateUserPhoneNum(String newPhoneNum, String userId);
}