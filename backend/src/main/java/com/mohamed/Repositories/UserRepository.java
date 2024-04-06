package com.mohamed.Repositories;

import com.mohamed.Entities.User;
import com.mohamed.Enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long > {
    User findByRole(Role role);

}
