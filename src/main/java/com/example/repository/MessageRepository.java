package com.example.repository;
import com.example.entity.Message;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MessageRepository extends JpaRepository<Message,Integer>{

    @Query("FROM Message WHERE postedBy = :postedByVar")
    List<Message> findMessagesByUser(@Param("postedByVar") int postedBy);


}
