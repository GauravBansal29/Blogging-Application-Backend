package com.blogging.blogapplication.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogging.blogapplication.Entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
