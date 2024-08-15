package com.example.repository;
import com.example.entity.Account;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account,Integer>{ 

    @Query("FROM Account WHERE username = :usernameVar")
    Optional<Account> findAccountByUsername(@Param("usernameVar") String username);


}
