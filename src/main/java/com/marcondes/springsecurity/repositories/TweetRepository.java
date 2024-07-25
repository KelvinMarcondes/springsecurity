package com.marcondes.springsecurity.repositories;

import com.marcondes.springsecurity.entities.Tweet;
import com.marcondes.springsecurity.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {
}
