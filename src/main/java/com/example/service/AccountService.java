package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account persistAccount(Account account){
        if (account.getUsername()!="" && account.getPassword().length()>=4){
            return this.accountRepository.save(account);
        }
        else{
            return null;
        }
    }

    public Account getAccountByUsername(String username){
        Optional<Account> optionalAccount = this.accountRepository.findAccountByUsername(username);
        if (optionalAccount.isPresent()){
            return optionalAccount.get();
        }
        else{
            return null;
        }
}
}
