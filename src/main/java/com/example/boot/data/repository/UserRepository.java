package com.example.boot.data.repository;

import com.example.boot.data.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserData, Long> {

    UserData findByEmail(String email);
}
