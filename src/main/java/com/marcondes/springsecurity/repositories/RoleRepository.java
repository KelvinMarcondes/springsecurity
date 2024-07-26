package com.marcondes.springsecurity.repositories;

import com.marcondes.springsecurity.entities.Role;
import com.marcondes.springsecurity.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
