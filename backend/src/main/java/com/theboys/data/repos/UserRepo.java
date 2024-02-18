package com.theboys.data.repos;

import com.theboys.security.User;
import com.theboys.security.UserRole;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    @Transactional
    @Modifying
    @Query("update User u set u.role = ?1 where u.id = ?2")
    void updateUpdateUserRole(UserRole role, Integer id);
    User findByLogin(@Param(value = "login") String login);
}
