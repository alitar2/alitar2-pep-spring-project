package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    MessageRepository messageRepository;
    AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message persistMessage(Message message){
        Optional<Account> optionalAccount = this.accountRepository.findById(message.getPostedBy());
        if (message.getMessageText()!="" && message.getMessageText().length()<255 && optionalAccount.isPresent()){
            return this.messageRepository.save(message);
        }
        else{
            return null;
        }
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Message getMessageById(int id){
        Optional<Message> optionalMessage = this.messageRepository.findById(id);
        if (optionalMessage.isPresent()){
            return optionalMessage.get();
        }
        else{
            return null;
        }
    }

    public Message deleteMessage(int id){
        Optional<Message> optionalMessage = this.messageRepository.findById(id);
        if (optionalMessage.isPresent()){
            Message deleted = optionalMessage.get();
            this.messageRepository.deleteById(id);
            return deleted;
        }
        else{
            return null;
        }
    }

    public Message updateMessage(int id, String newMessage){
        Optional<Message> optionalMessage = this.messageRepository.findById(id);
        if (optionalMessage.isPresent() && newMessage.length()<255 &&  newMessage!=""){
            Message message = optionalMessage.get();
            message.setMessageText(newMessage);
            this.messageRepository.save(message);
            return message;
        }
        else{
            return null;
        }
    }

    public List<Message> getMessagesByUser(int postedBy){
        return this.messageRepository.findMessagesByUser(postedBy);
    }
}
