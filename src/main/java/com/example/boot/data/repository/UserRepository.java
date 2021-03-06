package com.example.boot.data.repository;

import com.example.boot.data.entity.UserData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserData, Long> {

    UserData findByEmail(String email);
}
