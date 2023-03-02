package com.nahush.springboot.firstrestapi.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
    List<UserDetail> findByRole(String role);
}
