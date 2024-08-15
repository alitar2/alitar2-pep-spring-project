package com.example.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    private ResponseEntity register(@RequestBody Account a){
        if (this.accountService.getAccountByUsername(a.getUsername())!=null){
            return ResponseEntity.status(409).body("");
        }
        Account addedAccount = this.accountService.persistAccount(a);
        if (addedAccount == null){
            return ResponseEntity.status(400).body("");
        }
        else{
            return ResponseEntity.status(200).body(addedAccount);
        }
    }

    @PostMapping("/login")
    private ResponseEntity login(@RequestBody Account a){
        Account retreivedAccount = this.accountService.getAccountByUsername(a.getUsername());
        if (retreivedAccount!=null && retreivedAccount.getPassword().equals(a.getPassword())){
            return ResponseEntity.status(200).body(retreivedAccount);
        }
        else{
            return ResponseEntity.status(401).body("");
        }
    }

    @PostMapping("/messages")
    private ResponseEntity addMessage(@RequestBody Message m){
        Message addedMessage = this.messageService.persistMessage(m);
        if (addedMessage!=null){
            return ResponseEntity.status(200).body(addedMessage);
        }
        else{
            return ResponseEntity.status(400).body("");
        }
    }

    @GetMapping("/messages")
    private ResponseEntity getAllMessages(){
        return ResponseEntity.status(200).body(this.messageService.getAllMessages());
    }

    @GetMapping("/messages/{messageId}")
    private ResponseEntity getMessageById(@PathVariable int messageId){
        Message m = this.messageService.getMessageById(messageId);
        if (m==null){
            return ResponseEntity.status(200).body("");
        }
        else{
            return ResponseEntity.status(200).body(m);
        }
    }

    @DeleteMapping("/messages/{messageId}")
    private ResponseEntity deleteMessageById(@PathVariable int messageId){
        Message deletedMessage = this.messageService.deleteMessage(messageId);
        if (deletedMessage!=null){
            return ResponseEntity.status(200).body(1);
        }
        else{
            return ResponseEntity.status(200).body("");
        }
    }

    @PatchMapping("/messages/{messageId}")
    private ResponseEntity patchMessageById(@PathVariable int messageId, @RequestBody Message m){
        Message changedMessage = this.messageService.updateMessage(messageId, m.getMessageText());
        if (changedMessage!=null){
            return ResponseEntity.status(200).body(1);
        }
        else{
            return ResponseEntity.status(400).body("");
        }
    }

    @GetMapping("/accounts/{accountId}/messages")
    private ResponseEntity getMessagesByAccount(@PathVariable int accountId){
        return ResponseEntity.status(200).body(this.messageService.getMessagesByUser(accountId));
    }
}
